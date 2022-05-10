package com.douyasi.tutorial.blog.request.dto;

import com.douyasi.tutorial.blog.common.Constants;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ArticleQuery
 *
 * @author raoyc
 */
@Accessors(chain = true)
@Data
public class ArticleQuery {

    /**
     * user id: not exposed to request query
     */
    @ApiParam(hidden = true)
    private Long userId = null;

    /**
     * offset in sql: not exposed to request query
     */
    @ApiParam(hidden = true)
    private int offset = 0;

    /**
     * limit in sql: not exposed to request query
     */
    @ApiParam(hidden = true)
    private int limit;

    @ApiParam("当前页码")
    private int page = 1;

    @ApiParam(value = "分页大小", name = "pre_page")
    private int perPage = 10;

    @ApiParam(value = "开始时间范围", name ="start_time")
    private String startTime = null;

    @ApiParam(value = "截止时间范围", name ="end_time")
    private String endTime = null;

    @ApiParam("限定文章ids,多个以英文逗号分割")
    private String strArticleIds = null;

    @ApiParam("文章正文搜索关键词")
    private String keyword = null;

    @ApiParam(hidden = true)
    private List<Long> listArticleIds = null;


    /**
     * getListArticleIds
     *
     * @return List
     */
    public List<Long> getListArticleIds() {
        listArticleIds = Collections.emptyList();
        if (strArticleIds == null) {
            return listArticleIds;
        }
        String[] explodedArticleIds = strArticleIds.split(",");
        if (explodedArticleIds.length > 0) {
            ArrayList<String> articleIdsList = new ArrayList<String>(explodedArticleIds.length);
            Collections.addAll(articleIdsList, explodedArticleIds);
            listArticleIds = articleIdsList.stream().map(Long::parseLong).collect(Collectors.toList());
        }
        return listArticleIds;
    }

    /**
     * getPerPage get page size
     *
     * @return int
     */
    public int getPerPage() {
        if (perPage < 0 || perPage > Constants.MAX_PAGE_SIZE) {
            perPage = Constants.DEFAULT_PAGE_SIZE;
        }
        return perPage;
    }
    
    /**
     * getPage get current page
     *
     * @return int
     */
    public int getPage() {
        if (page <= 0) {
            page = 1;
        }
        return page;
    }


    /**
     * getLimit page size alias
     *
     * @return int
     */
    public int getLimit() {
        limit = getPerPage();
        return limit;
    }

    /**
     * getOffset
     *
     * @return offset
     */
    public int getOffset() {
        offset = (getPage() - 1) * getPerPage();
        return offset;
    }
}
