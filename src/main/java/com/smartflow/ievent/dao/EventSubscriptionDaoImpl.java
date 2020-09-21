package com.smartflow.ievent.dao;

import com.smartflow.ievent.dto.MySubscriptionListConditionInputDTO;
import com.smartflow.ievent.model.EventSubscription;
import lombok.extern.log4j.Log4j2;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Log4j2
@Repository
public class EventSubscriptionDaoImpl implements EventSubscriptionDao {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<EventSubscription> getEventSubscriptionList(MySubscriptionListConditionInputDTO mySubscriptionListConditionInputDTO) {
        String jpql = "from EventSubscription where State = 1 and User.Id = " + mySubscriptionListConditionInputDTO.getUserId();
        Query query = entityManager.createQuery(jpql);
        query.setFirstResult((mySubscriptionListConditionInputDTO.getPageIndex() - 1) * mySubscriptionListConditionInputDTO.getPageSize());
        query.setMaxResults(mySubscriptionListConditionInputDTO.getPageSize());
        return query.getResultList();
    }

    @Override
    public Integer getTotalCountEventSubscriptionList(MySubscriptionListConditionInputDTO mySubscriptionListConditionInputDTO) {
        String jpql = "select count(*) from EventSubscription where State = 1 and User.Id = " + mySubscriptionListConditionInputDTO.getUserId();
        Query query = entityManager.createQuery(jpql);
        return query.getResultList().size() == 0 ? 0 : Integer.parseInt(query.getSingleResult().toString());
    }

    @Override
    public void addEventSubscription(EventSubscription eventSubscription) {
        entityManager.persist(eventSubscription);
    }

    @Override
    public EventSubscription getEventSubscriptionById(Integer eventSubscriptionId) {
        return entityManager.find(EventSubscription.class, eventSubscriptionId);
    }

    @Override
    public void updateEventSubscription(EventSubscription eventSubscription) {
        entityManager.merge(eventSubscription);
    }

    @Override
    public void cancelSubscription(Integer eventSubscriptionId) {
        String jpql = "update EventSubscription set State = -1 where Id = " + eventSubscriptionId;
        Query query = entityManager.createQuery(jpql);
        query.executeUpdate();
    }
}
