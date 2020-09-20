package info.bodong.blog.repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import info.bodong.blog.domain.Comment;

/**
 * @author sarwo.wibowo
 */
public interface CommentRepository {
	Map<Long, Collection<Comment>> getMapComments();
	
	List<Comment> getComents(Comment filter);
	
	List<Comment> getAll();
}
