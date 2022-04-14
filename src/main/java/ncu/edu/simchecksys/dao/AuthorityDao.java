package ncu.edu.simchecksys.dao;

import ncu.edu.simchecksys.entity.Authority;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Ke Qiwen
 * @Date 2022/4/1 11:13
 * @Version 1.0
 * @Copyright KernelCurry
 */
@Repository
@Mapper
public interface AuthorityDao {
    /**
     * 用户名搜索用户的全部权限，用于登录认证
     * @param username
     * @return
     */
    List<Authority> selectAuthoritiesByUsername(String username);

    /**
     * 所有权限
     * @return
     */
    List<Authority> selectAllAuthotities();
}