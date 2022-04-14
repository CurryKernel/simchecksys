package ncu.edu.simchecksys.util;

import lombok.Data;

import java.util.List;

/**
 * @Author Ke Qiwen
 * @Date 2022/3/29 17:30
 * @Version 1.0
 * @Copyright KernelCurry
 */
@Data
public class LayuiDtree {
    private String id;	// 节点唯一索引值，用于对指定节点进行各类操作
    private String title;
    private String field;	// 节点字段名
    private String href;	// 点击节点弹出新窗口对应的 url。需开启 isJump 参数
    private Boolean checked;	// 节点是否初始为选中状态（如果开启复选框的话），默认 false
    private Boolean spread;		//节点是否初始展开，默认 false
    private Boolean disabled;	// 节点是否为禁用状态。默认 false
    private List<LayuiDtree> children;	// 子节点  [{title: '子节点1', id: '111'}]
}
