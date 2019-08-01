package com.haulmont.testtask.dao;

import com.haulmont.testtask.controller.RecipeController;
import com.haulmont.testtask.db.DatabaseConnection;
import com.haulmont.testtask.model.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fishn on 24.07.2019.
 */
public class PatientDaoImpl implements PatientDao {
    @Override
    public List<Patient> showAll() throws SQLException {

        List<Patient> patients = new ArrayList<>();
        try(
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT * FROM \"PUBLIC\".\"PATIENT\"")
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String patronymic = rs.getString("patronymic");
                String phoneNumber = rs.getString("phoneNumber");
                Patient patient = new Patient(id, name, surname, patronymic, phoneNumber);
                patients.add(patient);
            }
        }
        return patients;
    }

    @Override
    public List<Long> showAllId() throws SQLException {

        List<Long> patientsId = new ArrayList<>();
        try(
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT \"ID\" FROM \"PUBLIC\".\"PATIENT\"")
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Long id = rs.getLong("id");
                patientsId.add(id);
            }
        }
        return patientsId;
    }

    @Override
    public Patient showPatientById(Long id) throws SQLException {

        Patient patient = null;
        try(
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT * FROM \"PUBLIC\".\"PATIENT\" WHERE \"ID\"="+id+";")
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String patronymic = rs.getString("patronymic");
                String phoneNumber = rs.getString("phoneNumber");
                patient = new Patient(id, name, surname, patronymic, phoneNumber);
            }
        }
        return patient;
    }

    @Override
    public boolean add(Patient patient) throws SQLException {

        try(
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("INSERT INTO \"PUBLIC\".\"PATIENT\" (\"NAME\", \"SURNAME\", " +
                        "\"PATRONYMIC\", \"PHONENUMBER\") VALUES ('"+patient.getName()+"', '"+patient.getSurname()+"', '"+
                        patient.getPatronymic()+"', '" + patient.getPhoneNumber()+"');")
        ) {
            return ps.executeUpdate() !=0;
        }
    }

    @Override
    public boolean update(Patient patient) throws SQLException {

        try(
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("UPDATE \"PUBLIC\".\"PATIENT\" SET \"NAME\"='"+patient.getName()+
                        "', \"SURNAME\"='"+patient.getSurname()+"', \"PATRONYMIC\"='"+patient.getPatronymic()+
                        "', \"PHONENUMBER\"='"+patient.getPhoneNumber()+"' WHERE \"ID\"="+patient.getId()+";")
        ) {
            return ps.executeUpdate() !=0;
        }
    }

    @Override
    public boolean delete(Long patientId) throws SQLException {

        RecipeController recipeController = new RecipeController();
        if (!recipeController.checkPatientContent(patientId)){
            try(
                    Connection con = DatabaseConnection.getConnection();
                    PreparedStatement ps = con.prepareStatement("DELETE FROM \"PUBLIC\".\"PATIENT\" WHERE \"ID\"="+patientId+";")
            ) {
                return ps.executeUpdate() !=0;
            }
        }
        return false;

    }
}
