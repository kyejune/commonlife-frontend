package com.kolon.comlife.post.service.impl;

import com.kolon.comlife.common.model.PaginateInfo;
import com.kolon.comlife.complexes.model.ComplexInfo;
import com.kolon.comlife.complexes.service.ComplexService;
import com.kolon.comlife.complexes.service.impl.ComplexDAO;
import com.kolon.comlife.imageStore.model.ImageInfoUtil;
import com.kolon.comlife.imageStore.service.ImageStoreService;
import com.kolon.comlife.post.model.PostInfo;
import com.kolon.comlife.post.service.PostService;
import com.kolon.comlife.post.model.PostFileInfo;
import com.kolon.comlife.post.service.PostFileStoreService;
import com.kolon.comlife.users.model.PostUserInfo;
import com.kolon.comlife.users.service.impl.UserDAO;
import com.kolon.common.prop.ServicePropertiesMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("postService")
public class PostServiceImpl implements PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Autowired
    private ComplexDAO complexDAO;

    @Autowired
    private PostDAO postDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PostFileDAO postFileDAO;

    @Autowired
    private PostFileStoreService postFileStoreService;


    @Resource(name = "servicePropertiesMap")
    private ServicePropertiesMap serviceProp;

    @Autowired
    private ImageStoreService imageStoreService;


    @Override
    public PostInfo getPostById(int id, int currUsrId ) throws Exception {
        List<Integer>        userIds;
        List<Integer>        postIdxs;
        List<PostUserInfo>   postUserList;
        PostFileInfo         postFile;
        List<PostFileInfo>   postFileList;
        PostUserInfo         userInfo;

        PostInfo  postInfo;

        userIds = new ArrayList<>();
        postIdxs = new ArrayList<>();

        postInfo = postDAO.selectPost(id, currUsrId);
        if( postInfo == null ) {
            throw new Exception("해당 게시물이 없습니다.");
        }

        if( "Y".equals( postInfo.getAdminYn() ) ) {
            userIds.add(postInfo.getAdminIdx());
            postUserList = userDAO.getAdminListForPostById( userIds );
        } else {
            userIds.add(postInfo.getUsrId());
            postUserList = userDAO.getUserListForPostById( userIds );
        }

        if( postUserList == null || postUserList.size() < 1 ) {
            throw new Exception("게시물에 대한 사용자 정보가 없습니다. 해당 게시물을 가져올 수 없습니다.");
        }
        // 게시물의 사용자 정보 연결
        userInfo = postUserList.get(0);

        if( userInfo.getImageIdx() > -1 ) {
            userInfo.setImgSrc(
                    imageStoreService.getImageFullPathByIdx(
                            userInfo.getImageIdx(), ImageInfoUtil.SIZE_SUFFIX_SMALL ) );
        } else {
            userInfo.setImgSrc( null ); // 이미지 없는 경우, NULL 셋팅
        }

        postInfo.setUser( userInfo );

        if( userInfo.getImageIdx() > -1 ) {
            userInfo.setImgSrc(
                    imageStoreService.getImageFullPathByIdx(
                            userInfo.getImageIdx(), ImageInfoUtil.SIZE_SUFFIX_SMALL ) );
        } else {
            userInfo.setImgSrc( null ); // 이미지 없는 경우, NULL 셋팅
        }

        postInfo.setUser( userInfo );

        postIdxs.add(id);
        postFileList = postFileDAO.getPostFilesByPostIds( postIdxs );
        if( postFileList != null && postFileList.size() > 0 ) {
            postFile = postFileList.get(0);
            String fullPath = postFileStoreService.getImageFullPathByIdx( postFile.getPostFileIdx() );
            logger.debug(">>>>>>>>> postFile:" + fullPath );
            postFile.setOriginPath( fullPath );

            // PostInfo에 이미지 파일 연결
            postInfo.getPostFiles().add( postFile );
        }

        return postInfo;
    }

    @Override
    public PaginateInfo getPostWithLikeInfoList(Map params) throws Exception {
        List<PostInfo>             postInfoList;
        List<Integer>              userIds;
        List<Integer>              adminIdxs;
        List<PostUserInfo>         userList;
        Map<Integer, PostUserInfo> userListMap = new HashMap<>();
        List<PostUserInfo>         adminList;
        Map<Integer, PostUserInfo> adminListMap = new HashMap<>();
        List<Integer>              postIdxs;
        List<PostFileInfo>         postFileList;
        String       imgSrc;

        PaginateInfo paginateInfo;
        double       totalPages;
        int          countPosts;
        int          limit;
        int          pageNum;
        int          offset;
        String       feedWriteAllowYn;

        limit = ((Integer)params.get("limit")).intValue();
        pageNum = ((Integer)params.get("pageNum")).intValue();

        // 포스트 목록 추출
        offset = limit * ( pageNum - 1 );
        params.put("offset", Integer.valueOf(offset ));
        postInfoList = postDAO.selectPostListByComplexId( params );

        // USR_ID 추출
        userIds = new ArrayList<>();
        adminIdxs = new ArrayList<>();

        // Post를 작성한 사용자+관리자 ID 목록 생성
        for (PostInfo e : postInfoList) {
            logger.debug(">>>>>>>>>>>>>>>>> e.getAdminYn(): " +e.getAdminYn() );
            if( "Y".equals(e.getAdminYn()) ) {
                logger.debug(">>>>>>>>>>>>>>>>> Admin: " + e.getAdminIdx() );
                adminIdxs.add( e.getAdminIdx() );
            } else {
                userIds.add( e.getUsrId() );
                logger.debug(">>>>>>>>>>>>>>>>> Usr: " + e.getUsrId() );
            }
        }

        if( userIds.size() > 0 ) {
            // 추출한 ID로 유저 정보 SELECT
            userList = userDAO.getUserListForPostById( userIds );

            // 사용자 정보 Map 생성
            for( PostUserInfo user : userList ) {

                if( user.getImageIdx() > -1 ) {
                    imgSrc = imageStoreService.getImageFullPathByIdx(
                            user.getImageIdx(),
                            ImageInfoUtil.SIZE_SUFFIX_SMALL );
                    user.setImgSrc( imgSrc );
                } else {
                    user.setImgSrc( null ); // 이미지 없는 경우, NULL 셋팅
                }
                userListMap.put( Integer.valueOf(user.getUsrId()), user );
            }
        }

        if (adminIdxs.size() > 0 ) {
            adminList = userDAO.getAdminListForPostById( adminIdxs );

            for( PostUserInfo adminInfo : adminList ) {

                if( adminInfo.getImageIdx() > -1 ) {
                    imgSrc = imageStoreService.getImageFullPathByIdx(
                            adminInfo.getImageIdx(),
                            ImageInfoUtil.SIZE_SUFFIX_SMALL );
                    adminInfo.setImgSrc( imgSrc );
                } else {
                    adminInfo.setImgSrc( null ); // 이미지 없는 경우, NULL 셋팅
                }
                adminListMap.put( Integer.valueOf( adminInfo.getUsrId()), adminInfo );
            }
        }

        // 유저/관리자 정보 바인딩
        for( PostInfo post : postInfoList ) {
            PostUserInfo userInfo;

            if( "Y".equals( post.getAdminYn() ) ) {
                // 관리자 정보에서 가져오기
                userInfo = adminListMap.get( post.getAdminIdx() );
            } else {
                // 사용자 정보에서 가져오기
                userInfo = userListMap.get( post.getUsrId() );
            }

            post.setUser( userInfo );
        }

        // POST_IDX 추출
        postIdxs = new ArrayList<>();
        for (PostInfo e : postInfoList) {
            postIdxs.add( e.getPostIdx() );
        }

        if( postIdxs.size() > 0 ) {
            postFileList = postFileDAO.getPostFilesByPostIds( postIdxs );

            // Post File(업로드한 이미지) 정보 바인딩
            for( PostInfo post : postInfoList ) {
                for( PostFileInfo postFile : postFileList ) {
                    if( post.getPostIdx() == postFile.getPostIdx() ) {
                        postFile.setOriginPath( postFileStoreService.getImageFullPathByIdx( postFile.getPostFileIdx() ));
                        post.getPostFiles().add( postFile );
                    }
                }
            }
        }

        // 페이지네이션 계산
        countPosts = postDAO.countPostList( params );
        totalPages = Math.ceil( ( double ) countPosts / ( double ) limit );

        feedWriteAllowYn = complexDAO.selectFeedWriteAllowByCmplxId(
                                    Integer.valueOf( (Integer) params.get("cmplxId") ) );

        paginateInfo = new PaginateInfo();
        paginateInfo.setCurrentPage( pageNum );
        paginateInfo.setTotalPages( totalPages );
        paginateInfo.setPerPage( limit );
        paginateInfo.setData( postInfoList );
        paginateInfo.setFeedWriteAllowYn( feedWriteAllowYn );

        return paginateInfo;
    }

    @Override
    public List<PostInfo> getPostList(Map params) {
        return postDAO.selectPostList( params );
    }

    @Override
    public List<PostInfo> getPostListByComplexId(Map params) {
        return postDAO.selectPostListByComplexId( params );
    }

    @Override
    public PostInfo setPost(PostInfo post) {
        return postDAO.insertPost(post);
    }

    @Override
    public PostInfo setPostWithImage(PostInfo newPost, List<Integer> filesIdList, int usrId) {

        PostInfo            retPostInfo;
        List<PostFileInfo>  fileInfoList;

        retPostInfo = postDAO.insertPost( newPost );

        if( (filesIdList != null) && (filesIdList.size() > 0) ) {
            logger.debug(">>> Post Files Count: " +  filesIdList.size());
            fileInfoList = postFileDAO.bindPostToPostFiles(retPostInfo.getPostIdx(), filesIdList, usrId);
            retPostInfo.setPostFiles( fileInfoList );
        }

        return retPostInfo;
    }

    @Override
    public PostInfo updatePost( Map params ) {
        PostInfo postInfo;
        List<PostFileInfo> fileInfoList;
        List<Integer> postFilesIdx;

            postInfo = postDAO.updatePost( params );
        if( postInfo != null ) {

            // 이미지 정보 업데이트 및 Post 업데이트
            if( params.get("postFiles") != null )  {
                postFilesIdx = (List)params.get("postFiles");
                // 기존에 연결된 이미지 모두 삭제 표시 (DEL_YN = "Y")
                postFileDAO.deletePostFileByPostIdx( postInfo.getPostIdx() );


                if( postFilesIdx.size() > 0 ) {
                    // 신규 이미지로 업데이트
                    fileInfoList = postFileDAO.bindPostToPostFiles(
                            postInfo.getPostIdx(),
                            postFilesIdx,
                            (Integer)params.get("usrId") );
                } else {
                    fileInfoList = new ArrayList<>();
                }
                postInfo.setPostFiles( fileInfoList );
            } else {
                //  params.postFiles == null, 기존 이미지 정보를 업데이트하지 않음
            }
        }

        return postInfo;
    }

    @Override
    public PostInfo deletePost(int id, int usrId) {
        PostInfo deletedPostInfo = null;

        if( postDAO.deletePost(id, usrId) > 0 ) {
            deletedPostInfo = new PostInfo();
            deletedPostInfo.setDelYn("Y");
            deletedPostInfo.setPostIdx(id);
        }

        return deletedPostInfo;
    }

}
