package me.seonkyukim.springbootdeveloper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import me.seonkyukim.springbootdeveloper.domain.Article;


public interface BlogRepository extends JpaRepository<Article, Long>{

}
