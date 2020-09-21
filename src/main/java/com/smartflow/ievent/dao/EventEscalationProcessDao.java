package com.smartflow.ievent.dao;

import com.smartflow.ievent.model.EventEscalationProcess;

public interface EventEscalationProcessDao {
    /**
     * 根据id查询EventEscalationProcess
     *
     * @param escalationProcessId
     * @return
     */
    public EventEscalationProcess getEventEscalationProcessById(Integer escalationProcessId);
}
