package me.seonkyukim.springbootdeveloper.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import me.seonkyukim.springbootdeveloper.domain.Article;
import me.seonkyukim.springbootdeveloper.dto.ArticleListViewResponse;
import me.seonkyukim.springbootdeveloper.dto.ArticleViewResponse;
import me.seonkyukim.springbootdeveloper.service.BlogService;

@RequiredArgsConstructor
@Controller
public class BlogViewController {
	
	private final BlogService blogService;
	
	@GetMapping("/articles")
	public String getArticles(Model m) {
		
		List<ArticleListViewResponse> articles = blogService.findAll()
														    .stream()
														    .map(ArticleListViewResponse::new)
														    .toList();
		m.addAttribute("atcl", articles);
		return "articleList";
	}

	@GetMapping("/articles/{id}")
	public String getArticles(@PathVariable("id") Long id, Model m) {
		
		Article article = blogService.findById(id);
		m.addAttribute("article", new ArticleViewResponse(article));
		return "article";
	}
	
	@GetMapping("/new-article")
	public String newArtricle(@RequestParam(value = "id" ,required = false) Long id, Model m) {
		
		if(id == null) {
			m.addAttribute("article", new ArticleViewResponse());
		} else {
			Article article = blogService.findById(id);
			m.addAttribute("article", new ArticleViewResponse(article));
		}
		
		return "newArticle";
	}

}
