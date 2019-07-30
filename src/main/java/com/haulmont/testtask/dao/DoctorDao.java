package com.haulmont.testtask.dao;

import com.haulmont.testtask.model.Doctor;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by fishn on 24.07.2019.
 */
public interface DoctorDao {

    List<Doctor> showAll() throws SQLException;
    List<Long> showAllId() throws SQLException;
    Doctor showDoctorById(Long id) throws SQLException;
    boolean add(Doctor doctor) throws SQLException;
    boolean update(Doctor doctor) throws SQLException;
    boolean delete(Long doctorId) throws SQLException;
}
