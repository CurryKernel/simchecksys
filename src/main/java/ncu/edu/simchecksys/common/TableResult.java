package ncu.edu.simchecksys.common;

import java.util.List;

/**
 * @Author Ke Qiwen
 * @Date 2022/4/1 11:08
 * @Version 1.0
 * @Copyright KernelCurry
 * @description layui数据接口接受的数据结构
 */
public class TableResult<T> {
    private int code;
    private String msg;
    private long count;
    private List<T> data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}

