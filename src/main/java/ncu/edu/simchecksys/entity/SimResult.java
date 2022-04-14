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
public class SimResult implements Serializable {

    private Integer id;
    private String user1;
    private String user2;
    private Double sim;


    public SimResult(String user1, String user2, Double sim) {
        this.user1 = user1;
        this.user2 = user2;
        this.sim = sim;
    }

}