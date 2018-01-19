package com.kolon.comlife.post.web;

import com.kolon.comlife.post.model.PostInfo;
import com.kolon.comlife.post.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/post/*")
public class PostController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Resource(name = "postService")
    private PostService postService;

    @GetMapping(
            value = "/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PostInfo> getPostInJson(HttpServletRequest request) {
        List<PostInfo> postInfoList = postService.getPostList( request );

        for (PostInfo e : postInfoList) {
            logger.debug(">> " + e);
        }
        return postInfoList;
    }
}
