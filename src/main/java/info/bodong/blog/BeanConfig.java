package info.bodong.blog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import info.bodong.blog.repository.CommentRepository;
import info.bodong.blog.repository.PostRepository;
import info.bodong.blog.repository.impl.CommentRepositoryImpl;
import info.bodong.blog.repository.impl.PostRepositoryImpl;
import info.bodong.blog.service.CommentService;
import info.bodong.blog.service.PostService;
import info.bodong.blog.service.impl.CommentServiceImpl;
import info.bodong.blog.service.impl.PostServiceImpl;

/**
 * @author sarwo.wibowo
 */
@Configuration
public class BeanConfig {

	@Bean
	public PostRepository postRepository() {
		return new PostRepositoryImpl();
	}
	
	@Bean
	public CommentRepository commentRepository() {
		return new CommentRepositoryImpl();
	}
	
	@Bean
	public PostService postService() {
		return new PostServiceImpl();
	}
	
	@Bean
	public CommentService commentService() {
		return new CommentServiceImpl();
	}
}
