package com.kolon.comlife.admin.post.service.impl;

import com.kolon.comlife.admin.post.exception.PostRsvGeneralException;
import com.kolon.comlife.admin.post.exception.ReservedAlreadyException;
import com.kolon.comlife.admin.post.model.PostRsvInfo;
import com.kolon.comlife.admin.post.model.PostRsvItemInfo;
import com.kolon.comlife.admin.post.model.PostRsvStatusInfo;
import com.kolon.comlife.admin.post.service.PostRsvService;
import com.kolon.comlife.admin.users.model.PostUserInfo;
import com.kolon.comlife.admin.users.service.impl.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("postRsvService")
public class PostRsvServiceImpl implements PostRsvService {

    static private final Logger logger = LoggerFactory.getLogger(PostRsvServiceImpl.class);

    @Autowired
    private PostRsvDAO postRsvDAO;

    @Autowired
    private UserDAO userDAO;


    @Override
    public PostRsvInfo getRsvInfoWithUserListByPostId(int postId ) {
        PostRsvInfo                rsvInfo;
        List<Integer>              userIds;
        List<PostRsvItemInfo>      rsvInfoItemList;

        List<PostUserInfo>         userList;
        Map<Integer, PostUserInfo> userListMap;

        // 해당 Post의 정보 가져오기
        rsvInfo = postRsvDAO.selectRsvInfo( postId );
        if( rsvInfo  == null ) {
            return null;
        }

        rsvInfoItemList = postRsvDAO.selectRsvItemList( postId );

        // USR_ID 추출
        userIds = new ArrayList<>();
        for (PostRsvItemInfo e : rsvInfoItemList) {
            userIds.add( e.getUsrId() );
        }

        if( userIds.size() > 0 ) {
            // 추출한 ID로 유저 정보 SELECT
            userList = userDAO.getUserListForPostById( userIds );
            userListMap = new HashMap();


            // 사용자 정보 Map 생성
            for( PostUserInfo user : userList ) {
                userListMap.put( Integer.valueOf(user.getUsrId()), user);
            }

            // 유저 정보 바인딩
            for( PostRsvItemInfo item : rsvInfoItemList ) {
                PostUserInfo userInfo;
                userInfo = userListMap.get( item.getUsrId() );
                item.setUser( userInfo );
            }
        }

        rsvInfo.setRsvItemInfoList( rsvInfoItemList );


        return rsvInfo;
    }


    @Override
    public PostRsvStatusInfo requestRsv( int parentIdx,
                                         int usrId ) throws PostRsvGeneralException, ReservedAlreadyException {
        int               rsvAvailableFlag; // 0 = NOT, 1 = POSSIBLE;
        int               affectedRsvItemCnt;
        PostRsvStatusInfo retRsvStatusInfo;
        PostRsvInfo       rsvInfo;

        // 1. 기존 신청 여부 확인
        if( postRsvDAO.isReserved( parentIdx, usrId ) ) {
            throw new ReservedAlreadyException("이미 신청하였습니다.");
        }

        // 2. 예약 가능한지 확인 및 선점
        rsvAvailableFlag = postRsvDAO.incRsvCntIfAvailable( parentIdx );
        logger.debug(">>> Is reserved? " + rsvAvailableFlag);

        if( rsvAvailableFlag < 1 ) {
            throw new PostRsvGeneralException("인원 초과로 신청이 가능하지 않습니다.");
        }

        // 3. Item 항목에 추가
        affectedRsvItemCnt = postRsvDAO.addRsvItem( parentIdx, usrId );
        if( affectedRsvItemCnt < 1 ) {
            logger.error(
                    ">> POST_RSV와 POST_RSV_ITEMS 간의 일관성이 깨졌습니다. " +
                    "affectedRsvItemCnt-기대 값: 1, 실제 값: " + affectedRsvItemCnt );
            throw new PostRsvGeneralException("내부 오류로 진행할 수 없습니다.");
        }

        // 4. 현재 신청자 수 값 가져오기
        rsvInfo = postRsvDAO.selectRsvInfo( parentIdx );
        if( rsvInfo == null ) {
            logger.error(
                    ">> POST_RSV와 POST_RSV_ITEMS 간의 일관성이 깨졌습니다. " +
                    "rsvInfo-기대 값: NOT NULL, 실제 값: " + rsvInfo );
            throw new PostRsvGeneralException("내부 오류로 진행할 수 없습니다.");
        }

        retRsvStatusInfo = new PostRsvStatusInfo();
        retRsvStatusInfo.setRsvFlag(true);
        retRsvStatusInfo.setRsvCount( rsvInfo.getRsvCurrCnt() );
        retRsvStatusInfo.setMsg("성공적으로 신청하였습니다.");

        return retRsvStatusInfo;
    }

    @Override
    public PostRsvStatusInfo cancelRsv( int parentIdx,
                                        int usrId ) throws PostRsvGeneralException, ReservedAlreadyException {
        int               rsvAvailableFlag; // 0 = NOT, 1 = POSSIBLE;
        int               affectedRsvItemCnt;
        PostRsvStatusInfo retRsvStatusInfo;
        PostRsvInfo       rsvInfo;

        // 1. 기존 신청 여부 확인
        if( true != postRsvDAO.isReserved( parentIdx, usrId ) ) {
            throw new ReservedAlreadyException("신청 내역이 없습니다.");
        }

        // 2. 취소 가능한지 확인 및 선점
        rsvAvailableFlag = postRsvDAO.decRsvCntIfAvailable( parentIdx );
        logger.debug(">>> Is reserved? " + rsvAvailableFlag);

        if( rsvAvailableFlag < 1 ) {
            throw new PostRsvGeneralException("취소 신청이 가능하지 않습니다.");
        }

        // 3. Item 항목에 추가
        affectedRsvItemCnt = postRsvDAO.removeRsvItem( parentIdx, usrId );
        if( affectedRsvItemCnt < 1 ) {
            logger.error(
                    ">> POST_RSV와 POST_RSV_ITEMS 간의 일관성이 깨졌습니다. " +
                    "affectedRsvItemCnt-기대 값: 1, 실제 값: " + affectedRsvItemCnt );
            throw new PostRsvGeneralException("내부 오류로 진행할 수 없습니다.");
        }

        // 4. 현재 신청자 수 값 가져오기
        rsvInfo = postRsvDAO.selectRsvInfo( parentIdx );
        if( rsvInfo == null ) {
            logger.error(
                    ">> POST_RSV와 POST_RSV_ITEMS 간의 일관성이 깨졌습니다. " +
                    "rsvInfo-기대 값: NOT NULL, 실제 값: " + rsvInfo );
            throw new PostRsvGeneralException("내부 오류로 진행할 수 없습니다.");
        }

        retRsvStatusInfo = new PostRsvStatusInfo();
        retRsvStatusInfo.setRsvFlag(false);
        retRsvStatusInfo.setRsvCount( rsvInfo.getRsvCurrCnt() );
        retRsvStatusInfo.setMsg("참여 신청을 취소하였습니다.");

        return retRsvStatusInfo;
    }
}
