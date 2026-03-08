package com.calmarti.paykompi.common.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PageMetaData {

    private int page;
    private int size;
    private long totalItems;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;
    private boolean sorted;

    public PageMetaData(Page<?> pageData) {
        this.page = pageData.getNumber() + 1;
        this.size = pageData.getSize();
        this.totalItems = pageData.getTotalElements();
        this.totalPages = pageData.getTotalPages();
        this.hasNext = pageData.hasNext();
        this.hasPrevious = pageData.hasPrevious();
        this.sorted = pageData.getSort().isSorted();
    }
}
