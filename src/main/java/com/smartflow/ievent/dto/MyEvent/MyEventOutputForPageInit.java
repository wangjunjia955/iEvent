package com.smartflow.ievent.dto.MyEvent;

import java.util.Date;

public class MyEventOutputForPageInit {
    Integer No;
    String AreaNumber;
    String StationNumber;
    String Description;
    String UrgencyDegree;
    String ImpactDegree;
    String Title;
    String ReportUser;
    String Assignee;
    Date ReportDateTime;
    Date IssueDateTime;
    Float TimeOut;
    Date AssignedDateTime;
    Date ActionStartedDateTime;
    Date ActionFinishedDateTime;
    Date SignedOffDateTime;
    String State;

    public Integer getNo() {
        return No;
    }

    public void setNo(Integer no) {
        No = no;
    }

    public String getAreaNumber() {
        return AreaNumber;
    }

    public void setAreaNumber(String areaNumber) {
        AreaNumber = areaNumber;
    }

    public String getStationNumber() {
        return StationNumber;
    }

    public void setStationNumber(String stationNumber) {
        StationNumber = stationNumber;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getUrgencyDegree() {
        return UrgencyDegree;
    }

    public void setUrgencyDegree(String urgencyDegree) {
        UrgencyDegree = urgencyDegree;
    }

    public String getImpactDegree() {
        return ImpactDegree;
    }

    public void setImpactDegree(String impactDegree) {
        ImpactDegree = impactDegree;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getReportUser() {
        return ReportUser;
    }

    public void setReportUser(String reportUser) {
        ReportUser = reportUser;
    }

    public String getAssignee() {
        return Assignee;
    }

    public void setAssignee(String assignee) {
        Assignee = assignee;
    }

    public Date getReportDateTime() {
        return ReportDateTime;
    }

    public void setReportDateTime(Date reportDateTime) {
        ReportDateTime = reportDateTime;
    }

    public Date getIssueDateTime() {
        return IssueDateTime;
    }

    public void setIssueDateTime(Date issueDateTime) {
        IssueDateTime = issueDateTime;
    }

    public Float getTimeOut() {
        return TimeOut;
    }

    public void setTimeOut(Float timeOut) {
        TimeOut = timeOut;
    }

    public Date getAssignedDateTime() {
        return AssignedDateTime;
    }

    public void setAssignedDateTime(Date assignedDateTime) {
        AssignedDateTime = assignedDateTime;
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

    public Date getSignedOffDateTime() {
        return SignedOffDateTime;
    }

    public void setSignedOffDateTime(Date signedOffDateTime) {
        SignedOffDateTime = signedOffDateTime;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

}
