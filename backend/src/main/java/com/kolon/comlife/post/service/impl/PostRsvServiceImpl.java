package com.kolon.comlife.post.service.impl;

import com.kolon.comlife.like.model.LikeInfo;
import com.kolon.comlife.like.model.LikeStatusInfo;
import com.kolon.comlife.like.service.LikeService;
import com.kolon.comlife.post.service.PostRsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("postRsvService")
public class PostRsvServiceImpl implements PostRsvService {
    @Autowired
    private PostRsvDAO postRsvDAO;

    @Override
    public List<LikeInfo> getLikeList(int parentIdx ) {
        return postRsvDAO.selectLikeList( parentIdx );
    };

    @Override
    public boolean hasLike( int parentIdx, int usrIdx ) {
        return postRsvDAO.hasLike( parentIdx, usrIdx );
    }

    @Override
    public LikeStatusInfo requestRsv(int parentIdx, int usrId ) {
        LikeStatusInfo likeStatus;

        postRsvDAO.addLike( parentIdx, usrId );
        likeStatus = postRsvDAO.selectLikeCountByPostId(parentIdx, usrId);

        return likeStatus;
    }

    @Override
    public LikeStatusInfo cancelRsv(int parentIdx, int usrId ) {
        LikeStatusInfo likeStatus;

        postRsvDAO.cancelLike( parentIdx, usrId );
        likeStatus = postRsvDAO.selectLikeCountByPostId(parentIdx, usrId);

        return likeStatus;
    }
}
