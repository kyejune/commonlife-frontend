package com.kolon.comlife.postFile.service;

import com.kolon.comlife.postFile.model.PostFileInfo;

public interface PostFileService {

    public PostFileInfo getPostFile( int id );
    public PostFileInfo setPostFile( PostFileInfo postFileInfo );
    public PostFileInfo updatePostFile( int id, PostFileInfo postFileInfo );
    public void deletePostFile( int id );

}
