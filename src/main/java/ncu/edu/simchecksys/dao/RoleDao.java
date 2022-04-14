package ncu.edu.simchecksys.dao;

import ncu.edu.simchecksys.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Ke Qiwen
 * @Date 2022/4/1 11:11
 * @Version 1.0
 * @Copyright KernelCurry
 */
@Repository
@Mapper
public interface RoleDao {
    /**
     * 用户名查角色，多对多包括权限
     * @param username
     * @return
     */
    List<Role> selectRole(String username);

    /**
     * 所有角色
     * @return
     */
    List<Role> selectAllRole();
}

