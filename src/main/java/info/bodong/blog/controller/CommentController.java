package info.bodong.blog.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import info.bodong.blog.domain.Comment;
import info.bodong.blog.service.CommentService;

/**
 * @author sarwo.wibowo
 *
 */
@RestController
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@GetMapping("/api/v1/comments")
	public List<Comment> getComments(@RequestParam(required = false) Map<String, String> filter, Pageable pageable) {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		Comment filterComment = mapper.convertValue(filter, Comment.class);

		return commentService.getComments(filterComment);
	}

}
