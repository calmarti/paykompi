package com.calmarti.paykompi.common.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter

public class CustomPage<T> {
    private List<T> items;
    private PageMetaData metadata;

    public CustomPage(Page<T> pageData) {
        this.items = pageData.getContent();
        this.metadata = new PageMetaData(pageData);
    }
}
