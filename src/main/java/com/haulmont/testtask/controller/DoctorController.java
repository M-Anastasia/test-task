package com.haulmont.testtask.controller;

import com.haulmont.testtask.dao.DoctorDao;
import com.haulmont.testtask.dao.DoctorDaoImpl;
import com.haulmont.testtask.model.Doctor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fishn on 25.07.2019.
 */
public class DoctorController {

    private DoctorDao doctorDao;

    public DoctorController() {
        doctorDao = new DoctorDaoImpl();
    }

    public List<Doctor> showAll() throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        doctors = doctorDao.showAll();
        return doctors;
    }

    public List<Long> showAllId() throws SQLException {

        List<Long> doctors = new ArrayList<>();
        doctors = doctorDao.showAllId();
        return doctors;
    }

    public Doctor showDoctorById(Long id) throws SQLException {
        return doctorDao.showDoctorById(id);
    }

    public boolean add(Doctor doctor) throws SQLException {
        return doctorDao.add(doctor);
    }

    public boolean update(Doctor doctor) throws SQLException {
        return doctorDao.update(doctor);
    }

    public boolean delete(Long doctorId) throws SQLException {
        return doctorDao.delete(doctorId);
    }
}
