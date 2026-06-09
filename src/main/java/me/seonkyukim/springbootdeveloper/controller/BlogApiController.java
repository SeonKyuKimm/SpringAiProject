package me.seonkyukim.springbootdeveloper.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.seonkyukim.springbootdeveloper.domain.Article;
import me.seonkyukim.springbootdeveloper.dto.AddArticleRequest;
import me.seonkyukim.springbootdeveloper.dto.ArticleListViewResponse;
import me.seonkyukim.springbootdeveloper.dto.ArticleResponse;
import me.seonkyukim.springbootdeveloper.dto.UpdateArticleRequest;
import me.seonkyukim.springbootdeveloper.dto.WritingSuggestionRequest;
import me.seonkyukim.springbootdeveloper.dto.WritingSuggestionsResponse;
import me.seonkyukim.springbootdeveloper.service.BlogService;
import me.seonkyukim.springbootdeveloper.service.WritingAssistantService;

@RequiredArgsConstructor
@RestController
public class BlogApiController {

	private final BlogService blogService;
	private final WritingAssistantService writingAssistantService;
	
	@PostMapping("/api/articles")
	public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest req) {
		Article saveArticle = blogService.save(req);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(saveArticle);
	}

	/*
	 200 OK : 요청이 성공적으로 수행됨
	 201 Created : 요청이 성공적으로 소행되었고 , 새로운 리소스 생성
	 400 Bad Request : 요청 값이 잘못되어 요청에 실패
	 403 Forbidden : 권한이 없어 요청에 실패했음
	 404 Not Found : 요청 값으로 찾은 리소스가 없어 요청에 실패 ( 보통 페이지 업승ㅁ)
	 500 Internal Server Error : 서버 상에 문제가 있어 요청에 실패
	 */
	
	@GetMapping("/api/articles")
	public ResponseEntity<List<ArticleResponse>> findAllArticles() {
		List<ArticleResponse> art = blogService.findAll().stream().map(ArticleResponse::new).toList();
		return ResponseEntity.ok().body(art);
	}
	
	@GetMapping("/api/articles/{id}")
	public ResponseEntity<ArticleResponse> findArticle(@PathVariable("id") long id) {
		Article article = blogService.findById(id);
		
		return ResponseEntity.ok()
							 .body(new ArticleResponse(article));
	}
	
	@DeleteMapping("/api/articles/{id}")
	public ResponseEntity<Void> deleteArticle(@PathVariable("id") long id) {
		blogService.delete(id);
		
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/api/articles/{id}")
	public ResponseEntity<Article> updateArticle(@PathVariable("id") long id , 
												 @RequestBody UpdateArticleRequest req) {
		Article updtArticle = blogService.update(id, req);
		return ResponseEntity.ok().body(updtArticle);
	}
	
	@PostMapping("/api/ai-suggestions")
	public ResponseEntity<WritingSuggestionsResponse> writingAssist(@RequestBody WritingSuggestionRequest req) {
		WritingSuggestionsResponse resp = writingAssistantService.getWritingAssist(req);
		
		return ResponseEntity.ok()
							 .body(resp);
	}
}
