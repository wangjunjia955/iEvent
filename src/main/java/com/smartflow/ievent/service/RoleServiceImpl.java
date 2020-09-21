package com.smartflow.ievent.service;

import com.smartflow.ievent.dao.RoleDao;
import com.smartflow.ievent.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleDao roleDao;

    @Override
    public List<Map<String, Object>> getRoleInit() {
        return roleDao.getRoleInit();
    }

    @Override
    public Role getRoleById(Integer roleId) {
        return roleDao.getRoleById(roleId);
    }

    @Override
    public List<Map<String, Object>> getRoleListByUserId(Integer userId) {
        return roleDao.getRoleListByUserId(userId);
    }
    @Override
    public List<String> getVisitButtonListByUserId(Integer userId) {
        return roleDao.getVisitButtonListByUserId(userId);
    }
}
