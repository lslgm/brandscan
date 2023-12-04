package com.comeandsee.brandscan.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@Setter
@ToString
public class PageDTO<T> {
    private List<T> dataList;
    private int pageNumber;
    private int pageSize;
    private int totalPage;
    private boolean hasPrev;
    private boolean hasNext;
    private List<Integer> pages;

    @Builder
    public PageDTO(List<T> dataList, int pageNumber, int pageSize, int totalPage, boolean hasPrev, boolean hasNext) {
        this.dataList = dataList;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.hasPrev = hasPrev;
        this.hasNext = hasNext;

        // 기본 페이지 수 : 10
        int startPage = (((int) Math.ceil((double) this.pageNumber / 10)) - 1) * 10 + 1;
        int endPage = Math.min(startPage + 9, this.totalPage);

        this.pages = IntStream.rangeClosed(startPage, endPage)
                .boxed().collect(Collectors.toList());
    }
}
