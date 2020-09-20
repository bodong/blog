package info.bodong.blog.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import info.bodong.blog.domain.Comment;
import info.bodong.blog.domain.Post;
import info.bodong.blog.model.TopPost;
import info.bodong.blog.repository.CommentRepository;
import info.bodong.blog.repository.PostRepository;
import info.bodong.blog.service.PostService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author sarwo.wibowo
 */
@Slf4j
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	
	@Override
	public List<TopPost> getTopPosts() {
		log.info("Fetching top post ordered by top comments");
		Map<Long, Post> postData = postRepository.getPosts();
		Map<Long, Collection<Comment>> commentData = commentRepository.getMapComments();

		if (postData == null || postData.isEmpty()) {
			return new ArrayList<>();
		}

		List<TopPost> topPosts = postData.keySet().stream()
				.map(postId -> toTopPost(postData.get(postId), commentData.get(postId)))
				.collect(Collectors.toList());
		
		if(topPosts == null) {
			log.error("There is problem on the data retrieval!");
			return new ArrayList<>();
		}
		
		topPosts.sort(TopPost::compareTotalComments);
		
		log.info("Fetching top post completed! Total data fetch : {}", topPosts.size());
		return topPosts;
	}

	private TopPost toTopPost(Post postSource, Collection<Comment> collection) {
		if(postSource == null) {
			return null;
		}
		
		TopPost topPost = new TopPost();
		topPost.setPostId(postSource.getId());
		topPost.setPostTitle(postSource.getTitle());
		topPost.setPostBody(postSource.getBody());

		int total = collection != null ? collection.size() : 0;
		topPost.setTotalNumberOfComments(total);

		return topPost;
	}

}
