package com.smartflow.ievent.dao;

import com.smartflow.ievent.model.EventEvaluationRecord;

public interface EventEvaluationRecordDao {
    /**
     * 添加事件评价表
     * @param eventEvaluationRecord
     */
    public void addEventEvaluationRecord(EventEvaluationRecord eventEvaluationRecord);

    /**
     * 修改事件评价表
     * @param eventEvaluationRecord
     */
    public void updateEventEvaluationRecord(EventEvaluationRecord eventEvaluationRecord);
}
