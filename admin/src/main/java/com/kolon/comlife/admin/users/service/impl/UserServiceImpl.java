package com.kolon.comlife.admin.users.service.impl;

import com.kolon.comlife.admin.users.exception.UserGeneralException;
import com.kolon.comlife.admin.users.model.PostUserInfo;
import com.kolon.comlife.admin.users.model.UserExtInfo;
import com.kolon.comlife.admin.users.model.UserInfo;
import com.kolon.comlife.admin.users.service.UserService;
import com.kolon.comlife.common.paginate.PaginateInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    ////////////////// ADMIN ONLY //////////////////
    // 해당 현장의 전체 회원 가입자 수
    public int getTotalUserCountByComplexId( int cmplxId ) throws UserGeneralException {
        return userDAO.selectUserCountByComplexId( cmplxId );
    }

    public UserExtInfo getUserExtInfoById( int usrId ) {
        return userDAO.selectUserExtInfoByUsrId( usrId );
    }

    @Override
    public PaginateInfo getUserListByComplexId(Map params) throws UserGeneralException {
        List<UserExtInfo>          userInfoList;
        List<Integer>              userIds;
        List<Integer>              adminIdxs;
        List<PostUserInfo>         userList;
        Map<Integer, PostUserInfo> userListMap = new HashMap<>();
        String                     imgSrc = null;

        PaginateInfo paginateInfo;
        double       totalPages;
        int          countPosts;
        int          limit;
        int          pageNum;
        int          offset;

        limit = ((Integer)params.get("limit")).intValue();
        pageNum = ((Integer)params.get("pageNum")).intValue();

        offset = limit * ( pageNum - 1 );
        params.put("offset", Integer.valueOf(offset ));

        userInfoList = userDAO.getUserListByComplexId( params );

        // USR_ID 추출
        userIds = new ArrayList<>();

//        // Post를 작성한 사용자+관리자 ID 목록 생성
//        for (PostInfo e : postInfoList) {
//            if( "Y".equals(e.getAdminYn()) ) {
//                adminIdxs.add( e.getAdminIdx() );
//            } else {
//                userIds.add( e.getUsrId() );
//            }
//        }
//
//        if( userIds.size() > 0 ) {
//            // 추출한 ID로 유저 정보 SELECT
//            userList = userDAO.getUserListForPostById( userIds );
//
//            // 사용자 정보 Map 생성
//            for( PostUserInfo user : userList ) {
//
//                if( user.getImageIdx() > -1 ) {
//                    imgSrc = imageStoreService.getImageFullPathByIdx(
//                            user.getImageIdx(),
//                            ImageInfoUtil.SIZE_SUFFIX_SMALL );
//                    user.setImgSrc( imgSrc );
//                } else {
//                    user.setImgSrc( null ); // 이미지 없는 경우, NULL 셋팅
//                }
//                userListMap.put( Integer.valueOf(user.getUsrId()), user );
//            }
//        }
//
//        if (adminIdxs.size() > 0 ) {
//            adminList = managerDAO.getAdminListForPostById( adminIdxs );
//
//            for( PostUserInfo adminInfo : adminList ) {
//
//                if( adminInfo.getImageIdx() > -1 ) {
//                    imgSrc = imageStoreService.getImageFullPathByIdx(
//                            adminInfo.getImageIdx(),
//                            ImageInfoUtil.SIZE_SUFFIX_SMALL );
//                    adminInfo.setImgSrc( imgSrc );
//                } else {
//                    adminInfo.setImgSrc( null ); // 이미지 없는 경우, NULL 셋팅
//                }
//                adminListMap.put( Integer.valueOf( adminInfo.getUsrId()), adminInfo );
//            }
//        }
//
//        // 유저/관리자 정보 바인딩
//        for( PostInfo post : postInfoList ) {
//            PostUserInfo userInfo;
//
//            if( "Y".equals( post.getAdminYn() ) ) {
//                // 관리자 정보에서 가져오기
//                userInfo = adminListMap.get( post.getAdminIdx() );
//            } else {
//                // 사용자 정보에서 가져오기
//                userInfo = userListMap.get( post.getUsrId() );
//            }
//
//            post.setUser( userInfo );
//        }
//
//
//        // POST_IDX 추출
//        postIdxs = new ArrayList<>();
//        for (PostInfo e : postInfoList) {
//            postIdxs.add( e.getPostIdx() );
//        }
//
//        if( postIdxs.size() > 0 ) {
//            postFileList = postFileDAO.getPostFilesByPostIds( postIdxs );
//
//            // Post File(업로드한 이미지) 정보 바인딩 및 비공개 처리 게시물 처리
//            for( PostInfo post : postInfoList ) {
//
//                // 사용자 Feed 중, 비공개(삭제)처리된 게시물의 내용 변경 및 공개 게시물에 대해서 이미지 정보 가져오기
//                if( "Y".equals(post.getDelYn()) && post.getPostType().equals("feed") ) {
//                    post.setContent( DELETED_POST_MSG );
//                } else {
//                    for( PostFileInfo postFile : postFileList ) {
//                        if( post.getPostIdx() == postFile.getPostIdx() ) {
//                            postFile.setOriginPath( postFileStoreService.getImageFullPathByIdx(
//                                    postFile.getPostFileIdx() ));
//                            post.getPostFiles().add( postFile );
//                        }
//                    }
//                }
//            }
//        }

//        // 페이지네이션 계산
        countPosts = userDAO.countUserList( params );
//        totalPages = Math.ceil( ( double ) countPosts / ( double ) limit );

        paginateInfo = new PaginateInfo();
        paginateInfo.setData( userInfoList );
        paginateInfo.setCurrentPageNo( pageNum );
        paginateInfo.setRecordCountPerPage( limit );
        paginateInfo.setPageSize( limit );
        paginateInfo.setTotalRecordCount( countPosts );

        return paginateInfo;
    }



    @Override
    public PaginateInfo getHeadListByComplexId(Map params) throws UserGeneralException {
        List<UserExtInfo>          userInfoList;
        List<Integer>              userIds;
        List<Integer>              adminIdxs;
        List<PostUserInfo>         userList;
        Map<Integer, PostUserInfo> userListMap = new HashMap<>();
        String                     imgSrc = null;

        PaginateInfo paginateInfo;
        double       totalPages;
        int          countPosts;
        int          limit;
        int          pageNum;
        int          offset;

        limit = ((Integer)params.get("limit")).intValue();
        pageNum = ((Integer)params.get("pageNum")).intValue();

        offset = limit * ( pageNum - 1 );
        params.put("offset", Integer.valueOf(offset ));

        userInfoList = userDAO.getHeadListByComplexId( params );

        // USR_ID 추출
        userIds = new ArrayList<>();

//        // 페이지네이션 계산
        countPosts = userDAO.countHeadList( params );
//        totalPages = Math.ceil( ( double ) countPosts / ( double ) limit );

        paginateInfo = new PaginateInfo();
        paginateInfo.setData( userInfoList );
        paginateInfo.setCurrentPageNo( pageNum );
        paginateInfo.setRecordCountPerPage( limit );
        paginateInfo.setPageSize( limit );
        paginateInfo.setTotalRecordCount( countPosts );

        return paginateInfo;
    }




    ////////////////// BACKEND SHARED //////////////////
    @Override
    public List<PostUserInfo> getUserListForPostById(List<Integer> ids) {
        return userDAO.getUserListForPostById( ids );
    }

    @Override
    public List<UserInfo> getUserListById(List<Integer> ids ) {
        return userDAO.getUserListById( ids );
    }

    @Override
    public UserInfo getUsrIdByUserIdAndPwd( String userId, String userPw ) {
        return userDAO.getUsrIdByUserIdAndPwd( userId, userPw );
    }

    @Override
    public boolean isExistedUser( String userId ) {
        return (userDAO.getUsrIdByUserId( userId ) != null);
    }

}
