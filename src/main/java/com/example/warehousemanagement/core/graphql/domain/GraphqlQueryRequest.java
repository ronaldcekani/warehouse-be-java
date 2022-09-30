package com.example.warehousemanagement.core.graphql.domain;
import com.example.warehousemanagement.core.graphql.ColExp;
import com.example.warehousemanagement.core.graphql.SortExp;

import java.util.List;
import java.util.Optional;

public class GraphqlQueryRequest {
    private List<ColExp> where;
    private List<SortExp> sort;
    private Optional<Integer> page;
    private Optional<Integer> limit;

    public List<ColExp> getWhere() {
        return where;
    }

    public void setWhere(List<ColExp> where) {
        this.where = where;
    }

    public List<SortExp> getSort() {
        return sort;
    }

    public void setSort(List<SortExp> sort) {
        this.sort = sort;
    }

    public Optional<Integer> getPage() {
        return page;
    }

    public void setPage(Optional<Integer> page) {
        this.page = page;
    }

    public Optional<Integer> getLimit() {
        return limit;
    }

    public void setLimit(Optional<Integer> limit) {
        this.limit = limit;
    }
}
