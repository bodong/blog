package info.bodong.blog.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sarwo.wibowo
 *
 */
@Getter
@Setter
public class Comment {
	private Long id;
	private Long postId;
	private String name;
	private String email;
	private String body;
	
}
