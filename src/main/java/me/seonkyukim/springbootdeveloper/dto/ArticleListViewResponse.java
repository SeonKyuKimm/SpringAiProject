package me.seonkyukim.springbootdeveloper.dto;

import lombok.Getter;
import me.seonkyukim.springbootdeveloper.domain.Article;

@Getter
public class ArticleListViewResponse {
	
	private final Long id;
	private final String title;
	private final String content;
	private final String imageUrl;

	
	public ArticleListViewResponse(Article atc) {
		this.id = atc.getId();
		this.title = atc.getTitle();
		this.content = atc.getContent();
		this.imageUrl = atc.getImageUrl();
	}
}
