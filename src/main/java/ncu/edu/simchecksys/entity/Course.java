package ncu.edu.simchecksys.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Ke Qiwen
 * @Date 2022/3/31 23:47
 * @Version 1.0
 * @Copyright KernelCurry
 */
@Data
public class Course implements Serializable {
    private Integer id;
    private String course_id;
    private String course_name;
    private String course_grade;
    private String course_teacher;

    public Course(Integer id, String course_id, String course_name, String course_grade, String course_teacher) {
        this.id = id;
        this.course_id = course_id;
        this.course_name = course_name;
        this.course_grade = course_grade;
        this.course_teacher = course_teacher;
    }
}

