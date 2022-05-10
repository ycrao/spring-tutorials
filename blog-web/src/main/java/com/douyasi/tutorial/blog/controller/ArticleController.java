package com.douyasi.tutorial.blog.controller;

import com.douyasi.tutorial.blog.annotation.AuthGatewayUser;
import com.douyasi.tutorial.blog.annotation.AuthTokenUser;
import com.douyasi.tutorial.blog.common.AuthUser;
import com.douyasi.tutorial.blog.domain.entity.Article;
import com.douyasi.tutorial.blog.request.dto.ArticleQuery;
import com.douyasi.tutorial.blog.request.dto.CreateOrUpdateArticleBody;
import com.douyasi.tutorial.blog.response.vo.CommonResult;
import com.douyasi.tutorial.blog.response.vo.PaginationData;
import com.douyasi.tutorial.blog.service.ArticleService;
import com.douyasi.tutorial.blog.util.RespUtil;
import com.douyasi.tutorial.blog.util.ValidateUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * ArticleController
 *
 * @author raoyc
 */
@RestController
@Api(value = "v1", tags = "ArticleApi")
public class ArticleController {

    private Long userId = null;

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
        // setting a specific user when testing
        // if you want get a authorized user, please using `@AuthTokenUser` or `@AuthGatewayUser` annotations
        this.userId = 1L;
    }

    /**
     * getIndex get article list by some search condition
     */
    @GetMapping("/article/index")
    @ApiOperation(value = "ArticleList", notes = "get article list by some conditions")
    public CommonResult<PaginationData<Article>> getIndex(
        @RequestParam(name = "page", required = false) int page,
        @RequestParam(name = "per_page", required = false) int perPage,
        @RequestParam(name = "keyword", required = false) String keyword,
        @RequestParam(name = "article_ids", required = false) String strArticleIds,
        @RequestParam(name = "start_time", required = false) String startTime,
        @RequestParam(name = "end_time", required = false) String endTime,
        /*@AuthGatewayUser AuthUser user,*/
        @AuthTokenUser AuthUser user
    ) {
        // request param mapper to ArticleQuery
        ArticleQuery articleQuery = new ArticleQuery();
        articleQuery.setUserId(user.getUserId())
                .setPage(page)
                .setPerPage(perPage)
                .setKeyword(keyword)
                .setStrArticleIds(strArticleIds)
                .setStartTime(startTime)
                .setEndTime(endTime);
        PaginationData<Article> articlePagination = articleService.getUserArticlesByCondition(articleQuery);
        return RespUtil.returnResult(articlePagination);
    }

    /**
     * getArticle get article by id
     */
    @GetMapping("/article/{id}")
    @ApiOperation(value = "ArticleDetail", notes = "get article by id")
    public CommonResult<Article> getArticleById(
        @NotNull @PathVariable("id") @ApiParam(value = "article id") Long id,
        @AuthTokenUser(guest = true) AuthUser user
    ) {
        Article article = null;
        if (user != null) {
            // if not guest set current auth user
            article = articleService.getUserArticleById(id, user.getUserId());
        } else {
            // else set default user (id=1L)
            //// article = articleService.getUserArticleById(id, userId);
            article = articleService.getArticleById(id);
        }
        if (article != null) {
           return RespUtil.returnResult(article);
        }
        return RespUtil.invalidResult("cannot found this article");
    }

    /**
     * postArticle create article
     */
    @PostMapping("/article")
    @ApiOperation(value = "CreateArticle", notes = "create article")
    public CommonResult postArticle(
            @RequestBody CreateOrUpdateArticleBody createArticleBody,
            @AuthTokenUser AuthUser user
    ) {
        String content = createArticleBody.getContent();
        if (ValidateUtil.isEmpty(content)) {
            return RespUtil.invalidResult("content is empty");
        }
        int row = articleService.createArticle(user.getUserId(), content);
        if (row == 1) {
            return RespUtil.returnResult();
        } else {
            return RespUtil.returnError("create article error", null);
        }
    }

    /**
     * putArticle update article
     */
    @PutMapping("/article/{id}")
    @ApiOperation(value = "UpdateArticle", notes = "update article by id")
    public CommonResult putArticle(
            @RequestBody CreateOrUpdateArticleBody updateArticleBody,
            @NotNull @PathVariable("id") @ApiParam(value = "article id") Long id,
            @AuthTokenUser AuthUser user
    ) {
        String content = updateArticleBody.getContent();
        if (ValidateUtil.isEmpty(content)) {
            return RespUtil.invalidResult("content is empty");
        }
        int row = articleService.updateArticle(id, user.getUserId(), content);
        if (row == 1) {
            return RespUtil.returnResult();
        } else {
            return RespUtil.returnError("update article error", null);
        }
    }

    /**
     * deleteArticle
     */
    @DeleteMapping("/article/{id}")
    @ApiOperation(value = "DeleteArticle", notes = "delete article by id")
    public CommonResult deleteArticle(
            @NotNull @PathVariable("id") @ApiParam(value = "article id") Long id,
            @AuthTokenUser AuthUser user
    ) {
        int row = articleService.deleteArticle(id, user.getUserId());
        if (row > 0) {
            return RespUtil.returnResult();
        } else {
            return RespUtil.returnError("delete article error", null);
        }
    }
}
