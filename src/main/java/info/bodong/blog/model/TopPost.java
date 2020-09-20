package info.bodong.blog.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sarwo.wibowo
 *
 */
@Getter
@Setter
public class TopPost {
	private Long postId;
	private String postTitle;
	private String postBody;
	private int totalNumberOfComments = 0;
	
	public static int compareTotalComments(TopPost top1, TopPost top2) {
		if(top1 == null || top2 == null) {
			return -1;
		}
		
		return Integer.compare(top1.getTotalNumberOfComments(), top2.getTotalNumberOfComments());
		
	}
	
}
