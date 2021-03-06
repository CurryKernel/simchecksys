/*
 * Copyright (c) mokeeqian 2021.
 * Bugs and suggestions please contact me via mokeeqian@gmail.com
 */

package ncu.edu.simchecksys.service.impl;


import lombok.extern.slf4j.Slf4j;
import ncu.edu.simchecksys.constant.BasicConstant;
import ncu.edu.simchecksys.constant.DatabaseConstant;
import ncu.edu.simchecksys.constant.OtherConstant;
import ncu.edu.simchecksys.dao.AuthorityDao;
import ncu.edu.simchecksys.dao.RoleDao;
import ncu.edu.simchecksys.dao.UserDao;
import ncu.edu.simchecksys.entity.Authority;
import ncu.edu.simchecksys.entity.Role;
import ncu.edu.simchecksys.entity.User;
import ncu.edu.simchecksys.service.FileService;
import ncu.edu.simchecksys.service.UserService;
import ncu.edu.simchecksys.util.PageBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author Ke Qiwen
 * @Date 2022/3/31 23:46
 * @Version 1.0
 * @description
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private RoleDao roleDao;
    private AuthorityDao authorityDao;
    private FileService fileService;

    public UserServiceImpl(UserDao userDao, RoleDao roleDao, AuthorityDao authorityDao, FileService fileService) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.authorityDao = authorityDao;
        this.fileService = fileService;
    }



    @Override
    public User selectUserBySort(String type, String keyword){
        HashMap<String, Object> pageMap = new HashMap<>(2);
        if (BasicConstant.User.USERNAME.getString().equals(type)){
            User user = userDao.selectUserByUsername(keyword);
            return user;
        }else if (BasicConstant.User.REAL_NAME.getString().equals(type)){
            User user = userDao.selectUserByRealname(keyword);
            return user;
        }else {
            return new User();
        }
    }

    @Override
    public HashMap<String, Object> selectUser(String username) {
        User user = userDao.selectUserByUsername(username);
        List<Role> role = roleDao.selectRole(username);
        HashMap<String, Object> res = new HashMap<>(4);
        res.put("user", user);
        res.put("role", role);
        return res;
    }

    @Override
    public List<User> selectUsersByIds(List<Integer> ids) {
        return userDao.selectByIds(ids);
    }

    @Override
    public User selectUserById(Integer id) {
        return userDao.selectUserById(id);
    }

    @Override
    public HashMap<String, Object> selectUserByRealname(String realname) {
        User user = userDao.selectUserByRealname("%"+realname+"%");
        List<Role> role = roleDao.selectRole(user.getUsername());
        HashMap<String, Object> res = new HashMap<>(4);
        res.put("user", user);
        res.put("role", role);
        return res;
    }

    @Override
    public List<Authority> selectAuthoritiesByUsername(String username) {
        return authorityDao.selectAuthoritiesByUsername(username);
    }

    @Override
    public Role selectRoleByUsername(String username) {
        //?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        return roleDao.selectRole(username).get(0);
    }

    @Override
    public List<Role> selectAllRole() {
        return roleDao.selectAllRole();
    }

    @Override
    public List<Authority> selectAllAuthority() {
        return authorityDao.selectAllAuthotities();
    }

    @Override
    public PageBean<User> selectUsersByRole(Integer currentPage, Integer pageSize,
                                            Integer roleId, HashMap<String,String> keywordMap) {
        HashMap<String, Object> pageMap = new HashMap<>();
        PageBean<User> pageBean = new PageBean<>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        pageMap.put("start", (currentPage - 1) * pageSize);
        pageMap.put("limit", pageBean.getPageSize());
        if (roleId != null) {
            pageMap.put("roleId", roleId);
        }
        if (keywordMap.size() > 0){
            Set<String> set = keywordMap.keySet();
            for (String key : set){
                pageMap.put(key, keywordMap.get(key));
            }
        }
        List<User> users = userDao.selectUsersByRole(pageMap);
        pageBean.setList(users);
        int count = 0;
        if(roleId != null){
            count = userDao.countCurrentUsers(roleId);
            pageBean.setTotalRecord(count);
        }else {
            count = userDao.countUsers();
            pageBean.setTotalRecord(count);
        }
        if (count % pageSize == 0) {
            pageBean.setTotalPage(count / pageSize);
        } else {
            pageBean.setTotalPage((count / pageSize) + 1);
        }
        return pageBean;
    }

    @Override
    public List<User> selectUsersByDepartment(String department) {
        return userDao.selectUsersByDepartment(department);
    }

    @Override
    public List<User> selectUsersByMajor(String major) {
        return userDao.selectUsersByMajor(major);
    }

    @Override
    public List<User> selectAllStudents() {
        return userDao.selectAllStudents();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //???????????????????????????
        User user = userDao.selectUserByUsername(username);
        if (user != null) {
            //???????????????????????????
            List<Role> roles = roleDao.selectRole(username);
            List<Authority> authorities = new ArrayList<>();
            List<GrantedAuthority> authorityList = new ArrayList<>();
            for (Role role : roles) {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
                authorityList.add(grantedAuthority);

                authorities = role.getAuthorityList();
                for (Authority authority : authorities) {
                    grantedAuthority = new SimpleGrantedAuthority(authority.getTag());
                    authorityList.add(grantedAuthority);
                }
            }
            //????????????????????????
            user.setLastLoginTime(OtherConstant.DATE_FORMAT.format(new Date()));
            List<User> users = new ArrayList<>();
            users.add(user);
            userDao.updateUsers(users);
            //?????????????????????????????????User??????
            user.setAuthorities(authorityList);
            return user;
        }
        throw new UsernameNotFoundException("????????????????????????");
    }

    @Override
    public int countUsers() {
        return userDao.countUsers();
    }

    @Override
    public HashMap<String, Object> addUsersByExcel(List<User> users, Integer roleId) {
        HashMap<String, Object> res = new HashMap<>(2);
        if (users.size() == 0){
            log.info("??????????????????,?????????????????????????????????");
            return res;
        }else {
            //??????????????????????????????????????????????????????????????????????????????????????????????????????existUsers?????????????????????
            Iterator<User> userIterator = users.iterator();
            List<String> oldRecord = userDao.checkUsernames();
            List<User> existUsers = new ArrayList<>();
            while (userIterator.hasNext()) {
                User tmp = userIterator.next();
                int isExist = Collections.binarySearch(oldRecord, tmp.getUsername());
                if (isExist >= 0) {
                    existUsers.add(tmp);
                    userIterator.remove();
                }
            }
            //????????????????????????????????????????????????????????????existUsers
            if (users.size() == 0) {
                res.put("exist",existUsers);
                return res;
            } else {
                //?????????????????????????????????roleId???????????????
                List<Integer> ids = new ArrayList<>();
                if (roleId == DatabaseConstant.Role.ROLE_TEACHER.ordinal()+1) {
                    //????????????????????????
                    for (User user : users) {
                        user.setPassword(new BCryptPasswordEncoder().encode("111111"));
                        user.setCreateTime(OtherConstant.DATE_FORMAT.format(new Date()));
                    }
                    //?????????????????????,????????????????????????id
                    Integer userRes = userDao.addUsers(users);
                    for (User user:users){
                        ids.add(user.getId());
                        // ?????????????????????????????????????????????????????????!!!
                        int fileRes = fileService.newTeacherFile(user.getUsername(), user.getRealname(), user.getId());
                        if (fileRes != 1) {
                            log.info("??????-" + user.getRealname() + "-??????????????????");
                        }
                    }
                    //?????????????????????
                    Integer midRes = userDao.addUserRole(ids, roleId);
                    res.put("res", (userRes + midRes));
                    if (existUsers.size() == 0) {
                        return res;
                    } else {
                        res.put("exist", existUsers);
                        for (User user : existUsers) {
                            log.error("??????-" + user.getRealname() + "-?????????????????????????????????");
                        }
                        return res;
                    }
                } else if (roleId == DatabaseConstant.Role.ROLE_STUDENT.ordinal()+1) {
                    //????????????????????????
                    for (User user : users) {
                        user.setPassword(new BCryptPasswordEncoder().encode("111111"));
                        user.setCreateTime(OtherConstant.DATE_FORMAT.format(new Date()));
                    }
                    //?????????????????????,????????????????????????id
                    Integer userRes = userDao.addUsers(users);
                    for (User user:users){
                        ids.add(user.getId());
                    }
                    //?????????????????????
                    Integer midRes = userDao.addUserRole(ids, roleId);
                    res.put("res", (userRes + midRes));
                    if (existUsers.size() == 0) {
                        return res;
                    } else {
                        res.put("exist", existUsers);
                        for (User user : existUsers) {
                            log.error("??????-" + user.getRealname() + "-?????????????????????????????????");
                        }
                        return res;
                    }
                } else {
                    for (User user : users) {
                        user.setPassword(new BCryptPasswordEncoder().encode("root"));
                        user.setRealname("???????????????");
                        user.setCreateTime(OtherConstant.DATE_FORMAT.format(new Date()));
                    }
                    Integer userRes = userDao.addUsers(users);
                    for (User user:users){
                        ids.add(user.getId());
                    }
                    Integer midRes = userDao.addUserRole(ids, roleId);
                    res.put("res", (userRes + midRes));
                    return res;
                }
            }
        }
    }

    @Override
    public int deleteUsers(List<Integer> ids) {
        int delUserRes = userDao.deleteUsers(ids);
        int delRoleRes = userDao.deleteUserRole(ids);
        return (delUserRes + delRoleRes);
    }


    @Override
    public int updateUsers(List<User> users) {
        return userDao.updateUsers(users);
    }

    @Override
    public int updatePassword(String newPassword, String oldPassword, User oldUser) {
        int res = 0;
        String encodedPassword = userDao.selectUserByUsername(oldUser.getUsername()).getPassword();
        if (BCryptPasswordEncoder().matches(oldPassword, encodedPassword)){
            User user = new User();
            user.setId(oldUser.getId());
            user.setPassword(BCryptPasswordEncoder().encode(newPassword));
            res = userDao.updatePassword(user);
        }else {
            res = -1;
        }
        return res;
    }

    private static BCryptPasswordEncoder BCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
