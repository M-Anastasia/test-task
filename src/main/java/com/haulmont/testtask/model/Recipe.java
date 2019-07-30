package com.haulmont.testtask.model;

import java.util.Date;

/**
 * Created by fishn on 20.07.2019.
 */
public class Recipe {

    private Long id;
    private String description;
    private Long patientId;
    private Long doctorId;
    private Date creationDate;
    private int duration;
    private String priority;

    public Recipe(Long id, String description, Long patientId, Long doctorId, Date creation_date, int duration, String priority) {
        this.id = id;
        this.description = description;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.creationDate = creation_date;
        this.duration = duration;
        this.priority = priority;
    }

    public Recipe(String description, Long patientId, Long doctorId, Date creationDate, int duration, String priority) {
        this.description = description;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.creationDate = creationDate;
        this.duration = duration;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
