package com.kolon.comlife.postFile.service;

import com.kolon.comlife.postFile.model.PostFileInfo;

import java.util.List;

public interface PostFileService {

    public PostFileInfo getPostFile( int id );
    public List<PostFileInfo> getPostFilesByPostId( int id );
    public List<PostFileInfo> getPostFilesByPostIds( List<Integer> ids );
    public PostFileInfo setPostFile( PostFileInfo postFileInfo );
    public PostFileInfo updatePostFile( int id, PostFileInfo postFileInfo );
    public List<PostFileInfo> bindPostToPostFiles( int postIdx, List<Integer> ids );
    public void deletePostFile( int id );

}
