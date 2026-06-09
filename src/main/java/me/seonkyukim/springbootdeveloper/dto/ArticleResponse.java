package me.seonkyukim.springbootdeveloper.dto;

import lombok.Getter;
import me.seonkyukim.springbootdeveloper.domain.Article;

@Getter
public class ArticleResponse {
	
	private final String content;
	private final String title;
	
	public ArticleResponse(Article article) {
		this.content = article.getContent();
		this.title = article.getTitle();
	}
}
