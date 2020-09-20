package info.bodong.blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import info.bodong.blog.domain.Comment;
import info.bodong.blog.repository.CommentRepository;
import info.bodong.blog.service.CommentService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author sarwo.wibowo
 *
 */
@Slf4j
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	@Override
	public List<Comment> getComments(Comment filter) {
		log.info("Fetch comment with filter");
		if(filter != null) {
			return commentRepository.getComents(filter);
		}
		List<Comment> comments = commentRepository.getAll();
		log.info("Fetch comments data completed. Total data {}", comments.size());
		return comments;
	}

}
