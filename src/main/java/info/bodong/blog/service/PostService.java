package info.bodong.blog.service;

import java.util.List;

import info.bodong.blog.model.TopPost;

/**
 * @author sarwo.wibowo
 *
 */
public interface PostService {
	List<TopPost> getTopPosts();
}
