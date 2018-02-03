package com.kolon.comlife.like.service.impl;

import com.kolon.comlife.like.model.LikeInfo;
import com.kolon.comlife.like.service.LikeService;
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
    };
    @Override
    public LikeInfo addLike( int parentIdx, int usrIdx ) {
        return likeDAO.addLike( parentIdx, usrIdx );
    };
    @Override
    public void cancelLike( int parentIdx, int usrIdx ) {
        likeDAO.cancelLike( parentIdx, usrIdx );
        return;
    };
}
