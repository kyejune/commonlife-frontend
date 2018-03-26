package com.kolon.comlife.postFile.service.impl;

import com.kolon.comlife.postFile.model.PostFileInfo;
import com.kolon.comlife.postFile.service.PostFileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("postFileService")
public class PostFileServiceImpl implements PostFileService {
    @Resource(name = "postFileDAO")
    private PostFileDAO postFileDAO;

    @Override
    public PostFileInfo getPostFile( int id ) {
        return postFileDAO.getPostFile( id );
    }

    @Override
    public List<PostFileInfo> getPostFilesByPostId( int id ) {
        return postFileDAO.getPostFilesByPostId( id );
    }

    @Override
    public List<PostFileInfo> getPostFilesByPostIds( List<Integer> ids ) {
        return postFileDAO.getPostFilesByPostIds( ids );
    }

    @Override
    public PostFileInfo setPostFile( PostFileInfo postFileInfo ) {
        return postFileDAO.setPostFile( postFileInfo );
    }
    @Override
    public PostFileInfo updatePostFile( int id, PostFileInfo postFileInfo ) {
        return postFileDAO.updatePostFile( id, postFileInfo );
    }

    @Override
    public List<PostFileInfo> bindPostToPostFiles( int postIdx, List<Integer> postFileIdxs, int usrId ) {
        return postFileDAO.bindPostToPostFiles( postIdx, postFileIdxs, usrId);
    }

    @Override
    public void deletePostFile( int id ) {
        postFileDAO.deletePostFile( id );
        return;
    }
}
