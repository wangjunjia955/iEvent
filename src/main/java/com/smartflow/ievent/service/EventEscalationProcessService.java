package com.smartflow.ievent.service;

import com.smartflow.ievent.model.EventEscalationProcess;

public interface EventEscalationProcessService {
    /**
     * 根据id查询EventEscalationProcess
     *
     * @param escalationProcessId
     * @return
     */
    public EventEscalationProcess getEventEscalationProcessById(Integer escalationProcessId);
}
