package com.smartflow.ievent.dao;

import com.smartflow.ievent.model.User;
import com.smartflow.ievent.util.ParseFieldToMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public User getUserById(Integer userId) {
        return entityManager.find(User.class, userId);
    }

    @Override
    public List<Map<String, Object>> getUserListByRoleId(Integer roleId) {
        String jpql = "select new User(Id,UserName) from User where State = 1 ";
        //if(roleId != 0){//查询某一角色下的用户
        jpql += " and Id in (select UserId from Roles_Users where RoleId = "+roleId+")";
        //}
        List<User> userList = entityManager.createQuery(jpql).getResultList();
        List<Map<String, Object>> mapList = new ArrayList<>();
        ParseFieldToMapUtil parseFieldToMapUtil = new ParseFieldToMapUtil();
        if (userList != null && !userList.isEmpty()) {
            for (User user : userList) {
                mapList = parseFieldToMapUtil.parseFiledToMap(user.getId(), user.getUserName(), null);
            }
        }
        return mapList;
    }
}
