package com.kolon.comlife.post.service;

import com.kolon.comlife.post.model.PostFileInfo;

import java.util.List;

public interface PostFileService {

    public PostFileInfo getPostFile( int id );
    public List<PostFileInfo> getPostFilesByPostId( int id );
    public List<PostFileInfo> getPostFilesByPostIds( List<Integer> ids );
    public PostFileInfo setPostFile( PostFileInfo postFileInfo );
    public PostFileInfo updatePostFile( int id, PostFileInfo postFileInfo );
    public List<PostFileInfo> bindPostToPostFiles( int postIdx, List<Integer> ids, int usrId );
    public void deletePostFile( int id );

}
