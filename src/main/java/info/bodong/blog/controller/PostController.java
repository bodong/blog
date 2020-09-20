package info.bodong.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import info.bodong.blog.model.TopPost;
import info.bodong.blog.service.PostService;

/**
 * @author sarwo.wibowo
 *
 */
@RestController
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@GetMapping("/api/v1/top-posts")
	public List<TopPost> getTopPost() {
		return postService.getTopPosts();
	}
}
