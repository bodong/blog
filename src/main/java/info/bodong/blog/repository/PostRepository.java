package info.bodong.blog.repository;

import java.util.Map;

import info.bodong.blog.domain.Post;

/**
 * @author sarwo.wibowo
 */
public interface PostRepository {
	Map<Long, Post> getPosts();
}
