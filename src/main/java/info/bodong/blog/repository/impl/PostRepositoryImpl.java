package info.bodong.blog.repository.impl;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import info.bodong.blog.domain.Post;
import info.bodong.blog.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * @author sarwo.wibowo
 */
@Slf4j
public class PostRepositoryImpl implements PostRepository {
	private static final String URL_POST = "https://jsonplaceholder.typicode.com/posts";
	
	@Override
	public Map<Long, Post> getPosts() {
		WebClient webClient = WebClient.builder().baseUrl(URL_POST).defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
		Flux<Post> datas = webClient.get().retrieve().bodyToFlux(Post.class);
		if (datas == null) {
			log.warn("No post data available");
			return null;
		}

		Map<Long, Post> mapData = datas.collectMap(data -> data.getId()).block();
		return mapData;
	}

}
