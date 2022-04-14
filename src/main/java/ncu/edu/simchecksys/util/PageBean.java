package ncu.edu.simchecksys.util;

import lombok.Data;

import java.util.List;

/**
 * @Author Ke Qiwen
 * @Date 2022/3/29 14:03
 * @Version 1.0
 * @Copyright KernelCurry
 */
@Data
public class PageBean<T> {
    private Integer totalRecord;
    private Integer totalPage;
    private Integer currentPage;
    private Integer pageSize;
    private List<T> list;
}

