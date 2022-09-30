package com.example.warehousemanagement.core.http;

import com.example.warehousemanagement.product.domain.dto.ProductEntityResponseDTO;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;

public class PageableResponse<R, E> {
    private long total;
    private int per_page;
    private int current_page;
    private int last_page;
    private int next_page;
    private int prev_page;
    private List<R> data;

    public PageableResponse(List<R> data, Page<E> page) {
        this.data = data;
        this.current_page = page.getNumber();
        this.next_page = this.current_page + 1;
        this.prev_page = this.current_page -1;
        this.total = page.getTotalElements();
    }
}
