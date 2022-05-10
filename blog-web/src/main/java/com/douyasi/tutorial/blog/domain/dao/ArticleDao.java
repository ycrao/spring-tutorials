package com.douyasi.tutorial.blog.domain.dao;

import com.douyasi.tutorial.blog.domain.entity.Article;
import com.douyasi.tutorial.blog.request.dto.ArticleQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * ArticleDao
 *
 * @author raoyc
 */
@Mapper
@Component
@SuppressWarnings("unused")
public interface ArticleDao {

    /**
     * getArticle
     *
     * @param id id
     * @return article
     */
    Article getArticle(
        @NotNull @Param("id") Long id
    );

    /**
     * getUserArticle
     *
     * @param id id
     * @param userId user_id
     * @return article
     */
    Article getUserArticle(
        @NotNull @Param("id") Long id,
        @NotNull @Param("userId") Long userId
    );

    /**
     * createArticle
     *
     * @param userId user id
     * @param content  content
     * @return int
     */
    int createArticle(
        @NotNull @Param("userId") Long userId,
        @NotNull @Param("content") String content
    );

    /**
     * updateArticle
     *
     * @param id id
     * @param userId user id
     * @param content content
     * @return int
     */
    int updateArticle(
        @NotNull @Param("id") Long id,
        @NotNull @Param("userId") Long userId,
        @NotNull @Param("content") String content
    );

    /**
     * getArticlesByCondition
     *
     * @param articleQuery article request query
     * @return int
     */
    List<Article> getArticlesByCondition(
        @NotNull ArticleQuery articleQuery
    );

    /**
     * getArticlesCountByCondition
     *
     * @param articleQuery article request query
     * @return int
     */
    int getArticlesCountByCondition(
        @NotNull ArticleQuery articleQuery
    );


    /**
     * deleteArticle
     *
     * @param id id
     * @param userId user id
     * @return int
     */
    int deleteArticle(
        @NotNull @Param("id") Long id,
        @NotNull @Param("userId") Long userId
    );
}
