package ncu.edu.simchecksys.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Ke Qiwen
 * @Date 2022/3/31 23:48
 * @Version 1.0
 * @Copyright KernelCurry
 */
@Data
public class Role implements Serializable {

    private Integer id;
    private String name;
    private String detail;

    private List<Authority> authorityList;
}
