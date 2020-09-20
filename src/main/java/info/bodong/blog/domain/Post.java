package info.bodong.blog.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sarwo.wibowo
 *
 */
@Getter
@Setter
public class Post {
	private Long id;
	private Long userId;
	private String title;
	private String body;
	
}
