package info.bodong.blog.service;

import java.util.List;

import info.bodong.blog.domain.Comment;

/**
 * @author sarwo.wibowo
 *
 */
public interface CommentService {
	List<Comment> getComments(Comment filter);
}
