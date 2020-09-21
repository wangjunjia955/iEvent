package com.smartflow.ievent.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "EventSubscription", schema = "ievent")
public class EventSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @ManyToOne
    @JoinColumn(name = "EventCategoryId")
    private EventCatetory EventCategory;
    private Integer Impact;
    private Integer Urgency;
    @ManyToOne
    @JoinColumn(name = "EventSymptomId")
    private EventSymptom EventSymptom;
    @ManyToOne
    @JoinColumn(name = "SubscriberId")
    private User User;
    @ManyToOne
    @JoinColumn(name = "SubscriberRoleId")
    private Role Role;
    private Date CreationDateTime;
    private Integer State;
}
