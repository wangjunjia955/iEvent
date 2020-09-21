package com.smartflow.ievent.dao;

import com.smartflow.ievent.model.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

public interface RoleDao {

    /**
     * 获取角色下拉框
     *
     * @return 角色下拉框
     */
    public List<Map<String, Object>> getRoleInit();

    /**
     * 根据角色id获取角色
     *
     * @param roleId 角色id
     * @return 角色
     */
    public Role getRoleById(Integer roleId);

    /**
     * 根据用户id查询用户对应的角色
     * @param userId
     * @return
     */
    public List<Map<String,Object>> getRoleListByUserId(Integer userId);

    /**
     * 根据用户id查询按钮
     * @param userId
     * @return
     */
    public List<String> getVisitButtonListByUserId(Integer userId);
}
