package com.haulmont.testtask.controller;

import com.haulmont.testtask.dao.PatientDao;
import com.haulmont.testtask.dao.PatientDaoImpl;
import com.haulmont.testtask.model.Patient;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fishn on 25.07.2019.
 */
public class PatientController {

    private PatientDao patientDao;

    public PatientController() {
        patientDao = new PatientDaoImpl();
    }

    public List<Patient> showAll() throws SQLException {

        List<Patient> patients = new ArrayList<>();
        patients = patientDao.showAll();
        return patients;
    }

    public List<Long> showAllId() throws SQLException {
        List<Long> patients = new ArrayList<>();
        patients = patientDao.showAllId();
        return patients;
    }

    public Patient showPatientById(Long id) throws SQLException{
        return patientDao.showPatientById(id);
    }

    public boolean add(Patient patient) throws SQLException {
        return patientDao.add(patient);
    }

    public boolean update(Patient patient) throws SQLException {
        return patientDao.update(patient);
    }

    public boolean delete(Long patientId) throws SQLException {
        return patientDao.delete(patientId);
    }
}
