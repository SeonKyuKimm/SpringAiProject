package me.seonkyukim.springbootdeveloper.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.seonkyukim.springbootdeveloper.domain.Article;
import me.seonkyukim.springbootdeveloper.dto.AddArticleRequest;
import me.seonkyukim.springbootdeveloper.dto.UpdateArticleRequest;
import me.seonkyukim.springbootdeveloper.repository.BlogRepository;

@RequiredArgsConstructor
@Service
public class BlogService {
	
	private final BlogRepository blogRepository;
	
	public Article save(AddArticleRequest req) {
		return blogRepository.save(req.toEntity());
	}
	
	public List<Article> findAll() {
		
		return blogRepository.findAll();
	}

	public Article findById(Long id) {
		
		return blogRepository.findById(id)
				.orElseThrow( () -> new IllegalArgumentException("not found : " + id));
	}

	public void delete(long id) {
		
		blogRepository.deleteById(id);
	}
	
	@Transactional
	public Article update(long id, UpdateArticleRequest req) {
		Article atc = blogRepository.findById(id)
									.orElseThrow( () -> new IllegalArgumentException("not found : " + id) );
		atc.update(req.getTitle(), req.getContent(), req.getImageUrl());
		
		return atc;
	}
}