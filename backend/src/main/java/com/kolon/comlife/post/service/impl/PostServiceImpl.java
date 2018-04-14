package com.kolon.comlife.post.service.impl;

import com.kolon.comlife.common.model.PaginateInfo;
import com.kolon.comlife.imageStore.model.ImageInfoUtil;
import com.kolon.comlife.imageStore.service.ImageStoreService;
import com.kolon.comlife.post.model.PostInfo;
import com.kolon.comlife.post.service.PostService;
import com.kolon.comlife.postFile.model.PostFileInfo;
import com.kolon.comlife.postFile.service.PostFileStoreService;
import com.kolon.comlife.postFile.service.impl.PostFileDAO;
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
        List<PostUserInfo>   userList;
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

        userIds.add(postInfo.getUsrId());
        userList = userDAO.getUserListForPostById( userIds );
        if( userList == null || userList.size() < 1 ) {
            throw new Exception("해당 게시물을 가져올 수 없습니다.");
        }
        // 게시물의 사용자 정보 연결
        userInfo = userList.get(0);
        userInfo.setImgSrc(
                imageStoreService.getImageFullPathByIdx(
                        userInfo.getImageIdx(), ImageInfoUtil.SIZE_SUFFIX_SMALL ) );
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
        List<PostUserInfo>         userList;
        Map<Integer, PostUserInfo> userListMap;
        List<Integer>              postIdxs;
        List<PostFileInfo>         postFileList;

        PaginateInfo paginateInfo;
        double       totalPages;
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
        for (PostInfo e : postInfoList) {
            userIds.add( e.getUsrId() );
        }

        if( userIds.size() > 0 ) {
            // 추출한 ID로 유저 정보 SELECT
            userList = userDAO.getUserListForPostById( userIds );
            userListMap = new HashMap();

            // 사용자 정보 Map 생성
            for( PostUserInfo user : userList ) {

                if( user.getImageIdx() > -1 ) {
                    user.setImgSrc( imageStoreService.getImageFullPathByIdx( user.getImageIdx(), ImageInfoUtil.SIZE_SUFFIX_SMALL ) );
                } else {
                    user.setImgSrc( null ); // 이미지 없는 경우, NULL 셋팅
                }
                userListMap.put( Integer.valueOf(user.getUsrId()), user );
            }

            // 유저 정보 바인딩
            for( PostInfo post : postInfoList ) {
                PostUserInfo userInfo;
                userInfo = userListMap.get( post.getUsrId() );
                post.setUser( userInfo );
            }
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
        countPosts = postDAO.countPostList();
        totalPages = Math.ceil( ( double ) countPosts / ( double ) limit );

        paginateInfo = new PaginateInfo();
        paginateInfo.setCurrentPage( pageNum );
        paginateInfo.setTotalPages( totalPages );
        paginateInfo.setPerPage( limit );
        paginateInfo.setData( postInfoList );

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
    public PostInfo updatePost(PostInfo post) {
        return postDAO.updatePost(post);
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
