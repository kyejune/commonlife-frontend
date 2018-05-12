package com.kolon.comlife.post.service.impl;

import com.kolon.comlife.post.model.LikeInfo;
import com.kolon.comlife.post.model.LikeStatusInfo;
import com.kolon.comlife.post.service.LikeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("likeService")
public class LikeServiceImpl implements LikeService {
    @Resource(name = "likeDAO")
    private LikeDAO likeDAO;

    @Override
    public List<LikeInfo> getLikeList(int parentIdx ) {
        return likeDAO.selectLikeList( parentIdx );
    };

    @Override
    public boolean hasLike( int parentIdx, int usrIdx ) {
        return likeDAO.hasLike( parentIdx, usrIdx );
    }

    @Override
    public LikeStatusInfo addLike(int parentIdx, int usrId ) {
        LikeStatusInfo likeStatus;

        likeDAO.addLike( parentIdx, usrId );
        likeStatus = likeDAO.selectLikeCountByPostId(parentIdx, usrId);

        return likeStatus;
    }

    @Override
    public LikeStatusInfo cancelLike( int parentIdx, int usrId ) {
        LikeStatusInfo likeStatus;

        likeDAO.cancelLike( parentIdx, usrId );
        likeStatus = likeDAO.selectLikeCountByPostId(parentIdx, usrId);

        return likeStatus;
    }
}
