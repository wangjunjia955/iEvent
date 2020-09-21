package com.smartflow.ievent.dao;

import com.smartflow.ievent.model.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    /**
     * 根据用户id查询用户
     *
     * @param userId
     * @return
     */
    public User getUserById(Integer userId);

    /**
     *  (根据角色id)获取人员下拉框
     * @param roleId
     * @return
     */
    public List<Map<String,Object>> getUserListByRoleId(Integer roleId);
}
