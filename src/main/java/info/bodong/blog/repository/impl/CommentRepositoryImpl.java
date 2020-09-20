package info.bodong.blog.repository.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import info.bodong.blog.domain.Comment;
import info.bodong.blog.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * @author sarwo.wibowo
 */
@Slf4j
public class CommentRepositoryImpl implements CommentRepository {
	private static final String URL_COMMENTS = "https://jsonplaceholder.typicode.com/comments";

	@Override
	public Map<Long, Collection<Comment>> getMapComments() {
		Flux<Comment> comments = getCommentData();
		Map<Long, Collection<Comment>> mapComment = comments.collectMultimap(data -> data.getPostId()).block();
		return mapComment;
	}
	
	@Override
	public List<Comment> getAll() {
		Flux<Comment> comments = getCommentData();
		
		if(comments == null) {
			return new ArrayList<>();
		}
		
		List<Comment> all = toList(comments); 
		return all;
	}

	@Override
	public List<Comment> getComents(Comment filter) {
		List<Comment> filtered = getFiltered(filter, getAll());
		return filtered;
	}
	
	private List<Comment> getFiltered(Comment filter, List<Comment> all) {
		Map<String, Object> validFilter = toValidFilter(filter);
		return all.stream().filter(comment -> doFilter(comment, validFilter)).collect(Collectors.toList());
	}

	private boolean doFilter(Comment comment, Map<String, Object> validFilter) {

		Set<String> fieldsToFilter = validFilter.keySet();

		Set<Boolean> res = new HashSet<Boolean>();
		for (String fieldFilter : fieldsToFilter) {
			Object value = getValue(fieldFilter, comment);

			if (value != null) {
				res.add(value.equals(validFilter.get(fieldFilter)));
			}
		}
		return !res.contains(Boolean.FALSE);
	}
	
	private Object getValue(String fieldFilter, Comment comment) {
		try {
			Field field = comment.getClass().getDeclaredField(fieldFilter);
			field.setAccessible(true);
			Object value = field.get(comment);
			return value;
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			log.error("There is an error to getValue ", e);
			return null;
		}
	}
	
	private Map<String, Object> toValidFilter(Comment filter) {

		Map<String, Object> validFilter = new HashMap<String, Object>();

		Field[] fields = filter.getClass().getDeclaredFields();
		for (Field field : fields) {
			setValidFilterValue(filter, validFilter, field);
		}

		return validFilter;
	}

	private void setValidFilterValue(Comment filter, Map<String, Object> validFilter, Field field) {
		Object value;
		try {
			field.setAccessible(true);
			value = field.get(filter);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			value = null;
		}

		if (value != null) {
			if (value instanceof String) {
				String paramString = value.toString();
				if (!blank(paramString)) {
					validFilter.put(field.getName(), value);
				}
			} else {
				validFilter.put(field.getName(), value);
			}

		}
	}

	private boolean blank(String paramString) {
		if(paramString == null) {
			return true;
		}  
		
		paramString = paramString.trim();
		
		if("".equalsIgnoreCase(paramString) || "*".equals(paramString)) {
			return true;
		} else if("\'".equalsIgnoreCase(paramString) || "\''".equalsIgnoreCase(paramString)) {
			return true;
		} else if("\"".equalsIgnoreCase(paramString) || "\"\"".equalsIgnoreCase(paramString)) {
			return true;
		}
		return false;
	}

	private List<Comment> toList(Flux<Comment> comments) {
		return comments.collect(Collectors.toList()).block();
	}

	private Flux<Comment> getCommentData() {
		WebClient webClient = WebClient.builder().baseUrl(URL_COMMENTS).defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
		Flux<Comment> comments = webClient.get().retrieve().bodyToFlux(Comment.class);
		return comments;
	}
	
}
