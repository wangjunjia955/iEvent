package com.smartflow.ievent.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Event", schema = "ievent")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String EventCode;
    private String Title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EventSymptomId")
    private EventSymptom EventSymptom;
    private String Description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AreaId")
    private Area Area;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "StationId")
    private Station Station;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EventCategoryId")
    private EventCatetory EventCategory;
    private Integer Impact;
    private Integer Urgency;
    private Date IssueDateTime;
    private Date ReportDateTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ReportUserId")
    private User ReportUser;
    private Date AssignedDateTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AssigneeId")
    private User Assignee;
    private Date ActionStartedDateTime;
    private Date ActionFinishedDateTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ActionOwnerRoleId")
    private Role ActionOwnerRole;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ActionOwnerId")
    private User ActionOwner;
    private String Clarify;
    private String Solution;
    private Date SignedOffDateTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SignerId")
    private User Signer;
    private Integer State;
    @JsonIgnore
    @OneToOne(mappedBy = "Event",fetch = FetchType.LAZY)
    private EventEvaluationRecord EventEvaluationRecord;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getEventCode() {
        return EventCode;
    }

    public void setEventCode(String eventCode) {
        EventCode = eventCode;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public EventSymptom getEventSymptom() {
        return EventSymptom;
    }

    public void setEventSymptom(com.smartflow.ievent.model.EventSymptom eventSymptom) {
        EventSymptom = eventSymptom;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Area getArea() {
        return Area;
    }

    public void setArea(Area area) {
        Area = area;
    }

    public com.smartflow.ievent.model.Station getStation() {
        return Station;
    }

    public void setStation(com.smartflow.ievent.model.Station station) {
        Station = station;
    }

    public EventCatetory getEventCategory() {
        return EventCategory;
    }

    public void setEventCategory(EventCatetory eventCategory) {
        EventCategory = eventCategory;
    }

    public Integer getImpact() {
        return Impact;
    }

    public void setImpact(Integer impact) {
        Impact = impact;
    }

    public Integer getUrgency() {
        return Urgency;
    }

    public void setUrgency(Integer urgency) {
        Urgency = urgency;
    }

    public Date getIssueDateTime() {
        return IssueDateTime;
    }

    public void setIssueDateTime(Date issueDateTime) {
        IssueDateTime = issueDateTime;
    }

    public Date getReportDateTime() {
        return ReportDateTime;
    }

    public void setReportDateTime(Date reportDateTime) {
        ReportDateTime = reportDateTime;
    }

    public User getReportUser() {
        return ReportUser;
    }

    public void setReportUser(User reportUser) {
        ReportUser = reportUser;
    }

    public Date getAssignedDateTime() {
        return AssignedDateTime;
    }

    public void setAssignedDateTime(Date assignedDateTime) {
        AssignedDateTime = assignedDateTime;
    }

    public User getAssignee() {
        return Assignee;
    }

    public void setAssignee(User assignee) {
        Assignee = assignee;
    }

    public Date getActionStartedDateTime() {
        return ActionStartedDateTime;
    }

    public void setActionStartedDateTime(Date actionStartedDateTime) {
        ActionStartedDateTime = actionStartedDateTime;
    }

    public Date getActionFinishedDateTime() {
        return ActionFinishedDateTime;
    }

    public void setActionFinishedDateTime(Date actionFinishedDateTime) {
        ActionFinishedDateTime = actionFinishedDateTime;
    }

    public Role getActionOwnerRole() {
        return ActionOwnerRole;
    }

    public void setActionOwnerRole(Role actionOwnerRole) {
        ActionOwnerRole = actionOwnerRole;
    }

    public User getActionOwner() {
        return ActionOwner;
    }

    public void setActionOwner(User actionOwner) {
        ActionOwner = actionOwner;
    }

    public String getClarify() {
        return Clarify;
    }

    public void setClarify(String clarify) {
        Clarify = clarify;
    }

    public String getSolution() {
        return Solution;
    }

    public void setSolution(String solution) {
        Solution = solution;
    }

    public Date getSignedOffDateTime() {
        return SignedOffDateTime;
    }

    public void setSignedOffDateTime(Date signedOffDateTime) {
        SignedOffDateTime = signedOffDateTime;
    }

    public User getSigner() {
        return Signer;
    }

    public void setSigner(User signer) {
        Signer = signer;
    }

    public Integer getState() {
        return State;
    }

    public void setState(Integer state) {
        State = state;
    }
}
