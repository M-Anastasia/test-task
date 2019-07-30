package com.haulmont.testtask.dao;

import com.haulmont.testtask.model.Patient;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by fishn on 24.07.2019.
 */
public interface PatientDao {

    List<Patient> showAll() throws SQLException;
    List<Long> showAllId() throws SQLException;
    Patient showPatientById(Long id) throws SQLException;
    boolean add(Patient patient) throws SQLException;
    boolean update(Patient patient) throws SQLException;
    boolean delete(Long id) throws SQLException;
}
