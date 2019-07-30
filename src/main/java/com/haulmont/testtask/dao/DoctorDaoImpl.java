package com.haulmont.testtask.dao;

import com.haulmont.testtask.controller.RecipeController;
import com.haulmont.testtask.db.DatabaseConnection;
import com.haulmont.testtask.model.Doctor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fishn on 24.07.2019.
 */
public class DoctorDaoImpl implements DoctorDao {
    @Override
    public List<Doctor> showAll() throws SQLException {

        List<Doctor> doctors = new ArrayList<>();
        try(
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT * FROM \"PUBLIC\".\"DOCTOR\"")
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String patronymic = rs.getString("patronymic");
                String specialization = rs.getString("specialization");
                Doctor doctor = new Doctor(id, name, surname, patronymic, specialization);
                doctors.add(doctor);
            }
        }
        return doctors;
    }

    @Override
    public List<Long> showAllId() throws SQLException {

        List<Long> doctorsId = new ArrayList<>();
        try(
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT \"ID\" FROM \"PUBLIC\".\"DOCTOR\"")
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Long id = rs.getLong("id");
                doctorsId.add(id);
            }
        }
        return doctorsId;
    }

    @Override
    public Doctor showDoctorById(Long id) throws SQLException {

        Doctor doctor = null;
        try(
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT * FROM \"PUBLIC\".\"DOCTOR\" WHERE \"ID\"="+id+";")
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String patronymic = rs.getString("patronymic");
                String specialization = rs.getString("specialization");
                doctor = new Doctor(id, name, surname, patronymic, specialization);
            }
        }
        return doctor;
    }

    @Override
    public boolean add(Doctor doctor) throws SQLException {

        try(
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("INSERT INTO \"PUBLIC\".\"DOCTOR\" (\"NAME\", \"SURNAME\", " +
                        "\"PATRONYMIC\", \"SPECIALIZATION\") VALUES ('"+doctor.getName()+"', '"+doctor.getSurname()+
                        "', '"+doctor.getPatronymic()+"', '"+ doctor.getSpecialization()+"');")
        ) {
            return ps.executeUpdate() !=0;
        }
    }

    @Override
    public boolean update(Doctor doctor) throws SQLException {

        try(
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("UPDATE \"PUBLIC\".\"DOCTOR\" SET \"NAME\"='"+doctor.getName()+"', \"SURNAME\"='"+
                        doctor.getSurname()+"', \"PATRONYMIC\"='"+doctor.getPatronymic()+"', \"SPECIALIZATION\"='"+
                        doctor.getSpecialization()+"' WHERE \"ID\"="+doctor.getId()+";")
        ) {
            return ps.executeUpdate() !=0;
        }
    }

    @Override
    public boolean delete(Long doctorId) throws SQLException {
        RecipeController recipeController = new RecipeController();
        if (!recipeController.checkDoctorContent(doctorId)){
            try(
                    Connection con = DatabaseConnection.getConnection();
                    PreparedStatement ps = con.prepareStatement("DELETE FROM \"PUBLIC\".\"DOCTOR\" WHERE \"ID\"="+doctorId+";")
            ) {
                return ps.executeUpdate() !=0;
            }
        }
        return false;
    }
}
