package ncu.edu.simchecksys.dao;

import ncu.edu.simchecksys.entity.SimResult;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * @Author Ke Qiwen
 * @Date 2022/4/1 11:11
 * @Version 1.0
 * @Copyright KernelCurry
 */
@Repository
@Mapper
public interface SimResultDao {
    List<SimResult> findAll();
    void saveOne(SimResult simResult);
    List<SimResult> findByUsername(String username);
    Integer countAll();
    List<SimResult> findAllByPage(HashMap<String, Object> param);
}