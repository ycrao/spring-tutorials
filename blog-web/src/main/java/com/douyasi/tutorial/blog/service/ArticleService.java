package com.douyasi.tutorial.blog.service;

import com.douyasi.tutorial.blog.domain.dao.ArticleDao;
import com.douyasi.tutorial.blog.domain.entity.Article;
import com.douyasi.tutorial.blog.request.dto.ArticleQuery;
import com.douyasi.tutorial.blog.response.vo.PaginationData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ArticleService
 *
 * @author raoyc
 */
@Slf4j
@Service
public class ArticleService {

    private final ArticleDao articleDao;

    @Autowired
    public ArticleService(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    /**
     * getUserArticlesByCondition
     *
     * @param articleQuery articleQuery
     * @return PaginationData
     */
    public PaginationData<Article> getUserArticlesByCondition(ArticleQuery articleQuery) {
        log.debug("article query {}", articleQuery);
        List<Article> articles = articleDao.getArticlesByCondition(articleQuery);
        int articlesCount = articleDao.getArticlesCountByCondition(articleQuery);
        PaginationData<Article> articlePagination = new PaginationData<Article>(articlesCount, articleQuery.getPerPage(), articleQuery.getPage(), articles);
        return articlePagination;
    }

    /**
     * getArticleById
     *
     * @param id id
     * @return Article
     */
    public Article getArticleById(Long id) {
        Article article = articleDao.getArticle(id);
        return article;
    }

    /**
     * getUserArticleById
     *
     * @param id id
     * @param userId userId
     * @return Article
     */
    public Article getUserArticleById(Long id, Long userId) {
        Article article = articleDao.getUserArticle(id, userId);
        return article;
    }

    /**
     * createArticle
     *
     * @param userId userId
     * @param content content
     * @return int
     */
    public int createArticle(Long userId, String content) {
        Article article = new Article();
        article.setContent(content);
        article.setUserId(userId);
        return articleDao.createArticle(userId, content);
    }

    /**
     * updateArticle
     *
     * @param userId userId
     * @param content content
     * @return int
     */
    public int updateArticle(Long id, Long userId, String content) {
         return articleDao.updateArticle(id, userId, content);
    }

    /**
     * deleteArticle
     *
     * @param id id
     * @param userId userId
     * @return int
     */
    public int deleteArticle(Long id, Long userId) {
        return articleDao.deleteArticle(id, userId);
    }
}
