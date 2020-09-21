package com.smartflow.ievent.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "EventCatetory", schema = "ievent")
public class EventCatetory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String CategoryCode;
    private String Name;
    private String Description;
    @ManyToOne
    @JoinColumn(name = "DefaultOwnerRoleId")
    private Role Role;
    @ManyToOne
    @JoinColumn(name = "CategoryId")
    private EventCatetory EventCategory;
    @ManyToOne
    @JoinColumn(name = "DefaultEscalationProcessId")
    private EventEscalationProcess EventEscalationProcess;
    private Integer State;
    private String ParentCategory;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//只能为反序列化（使用setter）编写属性，但不会在序列化（getter）时读取
//    @Getter(onMethod = @__( @JsonIgnore ))
//    @Setter
    @OneToMany(mappedBy = "EventCategory", targetEntity = EventSubscription.class)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private Set<EventSubscription> EventSubscriptions;

    //    @JsonIgnore
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//只能为反序列化（使用setter）编写属性，但不会在序列化（getter）时读取
    @Getter(onMethod = @__(@JsonIgnore))
    @Setter
    @OneToMany(mappedBy = "EventCategory", targetEntity = Event.class)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private Set<Event> Events;

    public EventCatetory(Integer id, String categoryCode, String name) {
        Id = id;
        CategoryCode = categoryCode;
        Name = name;
    }

    public EventCatetory(Integer id, String name) {
        Id = id;
        Name = name;
    }

}
