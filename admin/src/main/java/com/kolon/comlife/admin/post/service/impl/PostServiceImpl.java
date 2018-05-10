package com.kolon.comlife.admin.post.service.impl;

import com.kolon.comlife.admin.imageStore.model.ImageInfoUtil;
import com.kolon.comlife.admin.imageStore.service.ImageStoreService;
import com.kolon.comlife.admin.manager.service.impl.ManagerDAO;
import com.kolon.comlife.admin.post.exception.NotFoundException;
import com.kolon.comlife.admin.post.exception.OperationFailedException;
import com.kolon.comlife.admin.post.model.PostFileInfo;
import com.kolon.comlife.admin.post.model.PostInfo;
import com.kolon.comlife.admin.post.service.PostFileStoreService;
import com.kolon.comlife.admin.post.service.PostService;
import com.kolon.comlife.admin.users.model.PostUserInfo;
import com.kolon.comlife.admin.users.service.impl.UserDAO;
import com.kolon.comlife.common.paginate.PaginateInfo;
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
    private static final String DELETED_POST_MSG = "{{비공개 처리 됨}";

    @Autowired
    private PostDAO postDAO;

    @Autowired
    private PostRsvDAO postRsvDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ManagerDAO managerDAO;

    @Autowired
    private PostFileDAO postFileDAO;

    @Autowired
    private PostFileStoreService postFileStoreService;


    @Resource(name = "servicePropertiesMap")
    private ServicePropertiesMap serviceProp;

    @Autowired
    private ImageStoreService imageStoreService;

    @Override
    public PostInfo getPostById(int id, int currUsrId ) throws NotFoundException {
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
            throw new NotFoundException("해당 게시물이 없습니다.");
        }

        if( "Y".equals( postInfo.getAdminYn() ) ) {
            userIds.add(postInfo.getAdminIdx());
            postUserList = managerDAO.getAdminListForPostById( userIds );
        } else {
            userIds.add(postInfo.getUsrId());
            postUserList = userDAO.getUserListForPostById( userIds );
        }

        if( postUserList == null || postUserList.size() < 1 ) {
            throw new NotFoundException("게시물에 대한 사용자 정보가 없습니다. 해당 게시물을 가져올 수 없습니다.");
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

        // 비공개(삭제)처리된 게시물의 내용 변경 및 공개 게시물에 대해서 이미지 정보 가져오기
        if( "Y".equals(postInfo.getDelYn()) ) {
            // 사용자가 작성한 Feed에 대해서만 비공개 표시함
            if( "feed".equals(postInfo.getPostType()) ) {
                postInfo.setContent( DELETED_POST_MSG );
            }
        } else {
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
        String                     imgSrc = null;

        PaginateInfo paginateInfo;
        int          countPosts;
        int          limit;
        int          pageNum;
        int          offset;

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
            if( "Y".equals(e.getAdminYn()) ) {
                adminIdxs.add( e.getAdminIdx() );
            } else {
                userIds.add( e.getUsrId() );
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
            adminList = managerDAO.getAdminListForPostById( adminIdxs );

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

            // Post File(업로드한 이미지) 정보 바인딩 및 비공개 처리 게시물 처리
            for( PostInfo post : postInfoList ) {

                // 사용자 Feed 중, 비공개(삭제)처리된 게시물의 내용 변경 및 공개 게시물에 대해서 이미지 정보 가져오기
                if( "Y".equals(post.getDelYn()) && post.getPostType().equals("feed") ) {
                    post.setContent( DELETED_POST_MSG );
                } else {
                    for( PostFileInfo postFile : postFileList ) {
                        if( post.getPostIdx() == postFile.getPostIdx() ) {
                            postFile.setOriginPath( postFileStoreService.getImageFullPathByIdx(
                                                        postFile.getPostFileIdx() ));
                            post.getPostFiles().add( postFile );
                        }
                    }
                }
            }
        }

//        // 페이지네이션 계산
        countPosts = postDAO.countPostList( params );

        paginateInfo = new PaginateInfo();
        paginateInfo.setData( postInfoList );
        paginateInfo.setCurrentPageNo( pageNum );
        paginateInfo.setRecordCountPerPage( limit );
        paginateInfo.setPageSize( limit );
        paginateInfo.setTotalRecordCount( countPosts );

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
    public PostInfo setPostWithImage(PostInfo newPost, List<Integer> filesIdList, int adminIdx) {

        PostInfo            retPostInfo;
        List<PostFileInfo>  fileInfoList;

        retPostInfo = postDAO.insertPostByAdmin( newPost );
        if( "Y".equals( newPost.getRsvYn() ) ) {
            postRsvDAO.upsertPostRsv( retPostInfo.getPostIdx(), newPost.getRsvMaxCnt() );
        }

        if( (filesIdList != null) && (filesIdList.size() > 0) ) {
            logger.debug(">>> Post Files Count: " +  filesIdList.size());

            fileInfoList = postFileDAO.bindPostToPostFiles( retPostInfo.getPostIdx(), filesIdList, adminIdx);
            retPostInfo.setPostFiles( fileInfoList );
        }

        return retPostInfo;
    }

    @Override
    public PostInfo updatePost( PostInfo newPost ) throws OperationFailedException {
        List<Integer>      newFileIdxList = new ArrayList<>();
        List<Integer>      oldFileIdxList = new ArrayList<>();
        PostInfo           retPostInfo;
        List<PostFileInfo> retPostFileInfoList;
        List<PostFileInfo> oldPostFileInfoList;

        // 이전에 업로드 된 이미지가 있는지 체크
        oldPostFileInfoList = postFileDAO.getPostFilesByPostId( newPost.getPostIdx() );
        logger.debug("oldPost.getPostFiles().size()>>> " + oldPostFileInfoList.size() );
        for( PostFileInfo f : oldPostFileInfoList ) {
            logger.debug("f.getPostFileIdx>>>> " + f.getPostFileIdx() );
            oldFileIdxList.add( f.getPostFileIdx() );
        }

        if( newPost.getPostType().equals("feed") ) {
            throw new OperationFailedException( "사용자 Feed는 변경할 수 없습니다." );
        }

        if( "Y".equals( newPost.getRsvYn() ) ) {
            postRsvDAO.upsertPostRsv( newPost.getPostIdx(), newPost.getRsvMaxCnt() );
        }

        for( PostFileInfo f : newPost.getPostFiles() ) {
            logger.debug("f.getPostFileIdx>>>> " + f.getPostFileIdx() );
            newFileIdxList.add( f.getPostFileIdx() );
        }

        // 업데이트 수행
        retPostInfo = postDAO.updatePost( newPost );

        if( oldFileIdxList.size() > 0 && newFileIdxList.size() > 0 &&
            oldFileIdxList.get(0) == newFileIdxList.get(0) ) {
            // do nothing
        } else {
            if( oldFileIdxList.size() > 0 ) {
                // delete¸
                postFileDAO.deletePostFile( oldFileIdxList.get(0) );
                retPostInfo.setPostFiles( new ArrayList<PostFileInfo>() );
            }

            if( newFileIdxList.size() > 0 ) {
                // Bind
                retPostFileInfoList = postFileDAO.bindPostToPostFiles( newPost.getPostIdx(), newFileIdxList, -1 );
                retPostInfo.setPostFiles( retPostFileInfoList );
            }
        }

        return retPostInfo;
    }

    @Override
    public PostInfo makePostPrivate(int id, int cmplxId, int adminIdx) {
        PostInfo deletedPostInfo = null;

        if( postDAO.updatePostDelYn( id, cmplxId, "Y") > 0 ) {
            deletedPostInfo = new PostInfo();
            deletedPostInfo.setDelYn("Y");
            deletedPostInfo.setPostIdx(id);
        }

        return deletedPostInfo;
    }

    @Override
    public PostInfo makePostPublic(int id, int cmplxId, int adminIdx) {
        PostInfo deletedPostInfo = null;

        if( postDAO.updatePostDelYn( id, cmplxId, "N") > 0 ) {
            deletedPostInfo = new PostInfo();
            deletedPostInfo.setDelYn("N");
            deletedPostInfo.setPostIdx(id);
        }

        return deletedPostInfo;
    }

}
