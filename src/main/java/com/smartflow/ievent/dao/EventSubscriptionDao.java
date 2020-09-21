package com.smartflow.ievent.dao;

import com.smartflow.ievent.dto.MySubscriptionListConditionInputDTO;
import com.smartflow.ievent.model.EventSubscription;

import java.util.List;

public interface EventSubscriptionDao {
    /**
     * 查询我的订阅
     *
     * @param mySubscriptionListConditionInputDTO
     * @return
     */
    public List<EventSubscription> getEventSubscriptionList(MySubscriptionListConditionInputDTO mySubscriptionListConditionInputDTO);

    /**
     * 查询我的订阅总条数
     *
     * @param mySubscriptionListConditionInputDTO
     * @return
     */
    public Integer getTotalCountEventSubscriptionList(MySubscriptionListConditionInputDTO mySubscriptionListConditionInputDTO);

    /**
     * 添加订阅
     *
     * @param eventSubscription
     */
    public void addEventSubscription(EventSubscription eventSubscription);

    /**
     * 根据事件订阅id查询事件订阅信息
     *
     * @param eventSubscriptionId
     */
    public EventSubscription getEventSubscriptionById(Integer eventSubscriptionId);

    /**
     * 修改事件订阅
     *
     * @param eventSubscription
     */
    public void updateEventSubscription(EventSubscription eventSubscription);

    /**
     * 取消订阅
     *
     * @param eventSubscriptionId
     */
    public void cancelSubscription(Integer eventSubscriptionId);
}
