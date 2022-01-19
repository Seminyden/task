package com.gmail.seminyden.dto.view;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class JsonViewPage<T> extends PageImpl<T> {

    public JsonViewPage(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public JsonViewPage(Page<T> page) {
        this(page.getContent(), page.getPageable(), page.getTotalElements());
    }


    @Override
    @JsonView({View.Lord.class,
               View.Planet.class})
    public List<T> getContent() {
        return super.getContent();
    }


    @Override
    @JsonView({View.Lord.class,
               View.Planet.class})
    public int getTotalPages() {
        return super.getTotalPages();
    }


    @Override
    @JsonView({View.Lord.class,
               View.Planet.class})
    public long getTotalElements() {
        return super.getTotalElements();
    }
}