package com.smartflow.ievent.service;

import com.smartflow.ievent.dao.RoleDao;
import com.smartflow.ievent.dao.UserDao;
import com.smartflow.ievent.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public List<Map<String, Object>> getUserListByRoleId(Integer roleId) {
        return userDao.getUserListByRoleId(roleId);
    }
}
