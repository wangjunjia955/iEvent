package com.smartflow.ievent.service;

import com.smartflow.ievent.dto.*;
import com.smartflow.ievent.model.EventSubscription;

import java.util.List;

public interface EventSubscriptionService {
    /**
     * 查询我的订阅
     *
     * @param mySubscriptionListConditionInputDTO
     * @return
     */
    public List<MySubscriptionListConditionOutputDTO> getEventSubscriptionList(MySubscriptionListConditionInputDTO mySubscriptionListConditionInputDTO);

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
     * @param addEventSubscriptionInputDTO
     */
    public void addEventSubscription(AddEventSubscriptionInputDTO addEventSubscriptionInputDTO);

    /**
     * 根据事件订阅id查询事件订阅信息
     *
     * @param eventSubscriptionId
     */
    public EditEventSubscriptionInitOutputDTO getEventSubscriptionById(Integer eventSubscriptionId);

    /**
     * 修改事件订阅
     *
     * @param editEventSubscriptionInputDTO
     */
    public void updateEventSubscription(EditEventSubscriptionInputDTO editEventSubscriptionInputDTO);

    /**
     * 取消订阅
     *
     * @param eventSubscriptionId
     */
    public void cancelSubscription(Integer eventSubscriptionId);
}
