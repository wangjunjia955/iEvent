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
@Table(name = "EventSymptom", schema = "ievent")
public class EventSymptom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String SymptomCode;
    private String Name;
    private String Description;
    private Integer DefaultImpact;
    private Integer DefaultUrgency;
    private Integer State;

    //    @JsonIgnore
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//只能为反序列化（使用setter）编写属性，但不会在序列化（getter）时读取
    @Getter(onMethod = @__(@JsonIgnore))
    @Setter
    @OneToMany(mappedBy = "EventSymptom", targetEntity = EventSubscription.class)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private Set<EventSubscription> EventSubscriptions;

    //    @JsonIgnore
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//只能为反序列化（使用setter）编写属性，但不会在序列化（getter）时读取
    @Getter(onMethod = @__(@JsonIgnore))
    @Setter
    @OneToMany(mappedBy = "EventSymptom", targetEntity = Event.class)
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private Set<Event> Events;

    public EventSymptom(Integer id, String name) {
        Id = id;
        Name = name;
    }

    public EventSymptom(Integer id, String symptomCode, String name) {
        Id = id;
        SymptomCode = symptomCode;
        Name = name;
    }

}
