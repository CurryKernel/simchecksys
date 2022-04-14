package ncu.edu.simchecksys.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Ke Qiwen
 * @Date 2022/3/31 23:46
 * @Version 1.0
 * @Copyright KernelCurry
 */
@Data
public class Authority implements Serializable {
    private Integer id;
    private String name;
    private String tag;
}

