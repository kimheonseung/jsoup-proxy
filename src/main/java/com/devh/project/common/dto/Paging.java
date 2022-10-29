package com.devh.project.common.dto;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class Paging
{
    /* 총 데이터 갯수 */
    private long total;
    /* 한 페이지에 보여질 갯수 */
    private int rows;
    /* 현재 페이지 번호 */
    private int page;
    /* 목록 사이즈 */
    private static final int pageListSize = 5;
    /* 총 페이지 번호 */
    private int totalPage;
    /* 시작 페이지 번호, 끝 페이지 번호 */
    private int start, end;
    /* 이전, 다음 */
    private boolean prev, next;
    /* 페이지 번호 목록 */
    private List<Integer> pageList;

    private Paging() {}

    private Paging(
            int page
            , int rows
            , long total
            , int totalPage
            , int start
            , boolean prev
            , boolean next
            , int end
    )
    {
        this.page = page;
        this.rows = rows;
        this.total = total;
        this.totalPage = totalPage;
        this.start = start;
        this.prev = prev;
        this.next = next;
        this.end = end;
        this.pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }

    public static Paging build(int page, int rows, long total) {

        final int tempEnd = (int) (Math.ceil(page / (double) pageListSize)) * pageListSize;
        final int start = tempEnd - (pageListSize - 1);
        final int totalPage = (int) Math.ceil(total / (double) rows);
        final int end = Math.min(totalPage, tempEnd);

        return new Paging(
                page
                , rows
                , total
                , totalPage
                , start
                , start > 1
                , totalPage > tempEnd
                , end
        );
    }
}
