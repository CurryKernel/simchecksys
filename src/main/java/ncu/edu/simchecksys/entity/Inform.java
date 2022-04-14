package ncu.edu.simchecksys.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Ke Qiwen
 * @Date 2022/3/31 23:48
 * @Version 1.0
 * @Copyright KernelCurry
 */
@Data
public class Inform implements Serializable {

    private Integer id;
    private String title;
    private String publisher;
    private String department;
    private String content;
    private String date;
    private String path;
    private Integer type;

}
