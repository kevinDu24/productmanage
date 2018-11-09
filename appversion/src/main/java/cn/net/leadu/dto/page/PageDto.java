package cn.net.leadu.dto.page;

import lombok.Data;

import java.util.List;

/**
 * Created by PengChao on 16/9/23.
 */
@Data
public class PageDto {
    private long totalElements;
    private List content;
    private int number;
    private int size;
    private int numberOfElements;
    private boolean isFirst;
    private boolean isLast;
    private boolean hasNext;
    private boolean hasPrevious;
    private int totalPages;

    public PageDto(){}

    public PageDto(long totalElements, List content){
        this.totalElements = totalElements;
        this.content = content;
    }

    public PageDto(Integer totalElements, List content, Integer page, Integer size){
        this.totalElements = totalElements;
        this.content = content;
        this.number = page;
        this.size = content.size();
        this.numberOfElements = size;
        this.isFirst = page == 0;
        this.hasPrevious = page > 0 && totalElements != 0;
        this.totalPages = (int)Math.ceil((double)totalElements / size);
        this.isLast = page + 1 == totalPages;
        this.hasNext = !this.isLast;
    }

}
