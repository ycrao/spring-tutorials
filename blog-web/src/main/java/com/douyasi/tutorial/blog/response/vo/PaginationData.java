package com.douyasi.tutorial.blog.response.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.io.Serializable;
import java.util.List;

/**
 * PaginationData
 *
 * @author raoyc
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@SuppressWarnings("unused")
public class PaginationData<T> implements Serializable {
    private static final long serialVersionUID = 3671912845470168009L;

    /**
     * PaginationData implement a simple pagination object like laravel pagination
     *
     * @param total total count
     * @param perPage page size alias
     * @param currentPage current page
     * @param items object items
     */
    public PaginationData(int total, int perPage, int currentPage, List<T> items) {
        this.total = total;
        this.currentPage = currentPage;
        this.items = items;
        this.perPage = perPage;
    }

    /**
     * as it says
     */
    private int total, perPage, currentPage;

    /**
     * object items
     */
    private List<T> items;

    /**
     * getTotal
     *
     * @return total
     */
    public int getTotal() {
        return total;
    }

    /**
     * setTotal
     *
     * @param total total count
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * getPerPage
     *
     * @return perPage
     */
    public int getPerPage() {
        return perPage;
    }

    /**
     * setPerPage
     *
     * @param perPage page size alias
     */
    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    /**
     * getCurrentPage
     *
     * @return currentPage
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * setCurrentPage
     *
     * @param currentPage current page
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * getItems
     *
     * @return items
     */
    public List<T> getItems() {
        return items;
    }

    /**
     * setItems
     * @param items object items
     */
    public void setItems(List<T> items) {
        this.items = items;
    }
}
