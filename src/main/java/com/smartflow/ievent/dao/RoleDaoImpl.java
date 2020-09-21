package com.smartflow.ievent.dao;

import com.smartflow.ievent.model.Area;
import com.smartflow.ievent.model.Role;
import com.smartflow.ievent.util.ParseFieldToMapUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Map<String, Object>> getRoleInit() {
        String jpql = "select new Role(Id, RoleName) from Role where State = 1";
        List<Role> roleList = entityManager.createQuery(jpql).getResultList();
        List<Map<String, Object>> mapList = new ArrayList<>();
        ParseFieldToMapUtil parseFieldToMapUtil = new ParseFieldToMapUtil();
        if (roleList != null && !roleList.isEmpty()) {
            for (Role role : roleList) {
                mapList = parseFieldToMapUtil.parseFiledToMap(role.getId(), role.getRoleName(), null);
            }
        }
        return mapList;
    }

    @Override
    public Role getRoleById(Integer roleId) {
        return entityManager.find(Role.class, roleId);
    }

    @Override
    public List<Map<String, Object>> getRoleListByUserId(Integer userId) {
        String jpql = "select new Role(Id, RoleName) from Role where State = 1 and Id in(select RoleId from Roles_Users where UserId = "+userId+") ";
        List<Role> roleList = entityManager.createQuery(jpql).getResultList();
        List<Map<String, Object>> mapList = new ArrayList<>();
        ParseFieldToMapUtil parseFieldToMapUtil = new ParseFieldToMapUtil();
        if (roleList != null && !roleList.isEmpty()) {
            for (Role role : roleList) {
                mapList = parseFieldToMapUtil.parseFiledToMap(role.getId(), role.getRoleName(), null);
            }
        }
        return mapList;
    }

    @Override
    public List<String> getVisitButtonListByUserId(Integer userId) {
        String jpql = "select visitBtn from Role where id in(select role.id from Roles_Users where user.id = "+userId+")";
        return entityManager.createQuery(jpql).getResultList();
    }
}
