package com.calmarti.paykompi.common.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PageMetaData {

    private final int page;
    private final int size;
    private final long totalItems;
    private final int totalPages;
    private final boolean hasNext;
    private final boolean hasPrevious;
    private final boolean sorted;

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
