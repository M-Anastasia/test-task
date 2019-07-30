package com.haulmont.testtask.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by fishn on 28.07.2019.
 */
public class DatabaseInit {

    private static final String CREATE_DOCTOR = "CREATE TABLE doctor (" +
            "   id NUMERIC IDENTITY PRIMARY KEY," +
            "   name VARCHAR(50) NOT NULL," +
            "   surname VARCHAR(50) NOT NULL," +
            "   patronymic VARCHAR(50) NOT NULL," +
            "   specialization VARCHAR(50) NOT NULL" +
            ");";

    private static final String CREATE_PATIENT = "CREATE TABLE patient (" +
            "   id NUMERIC IDENTITY PRIMARY KEY," +
            "   name VARCHAR(50) NOT NULL," +
            "   surname VARCHAR(50) NOT NULL," +
            "   patronymic VARCHAR(50) NOT NULL," +
            "   phoneNumber VARCHAR(12)" +
            ");";

    private static final String CREATE_RECIPE = "CREATE TABLE recipe (" +
            "   id NUMERIC IDENTITY PRIMARY KEY," +
            "   description VARCHAR(255) NOT NULL," +
            "   patientId NUMERIC NOT NULL," +
            "   doctorId NUMERIC NOT NULL," +
            "   creationDate DATE NOT NULL," +
            "   duration INTEGER," +
            "   priority VARCHAR(50) NOT NULL," +
            "   FOREIGN KEY (patientId) REFERENCES patient(id)," +
            "   FOREIGN KEY (doctorId) REFERENCES doctor(id)" +
            ");";

    private static final String INSERT_DOCTOR = "" +
            "INSERT INTO \"PUBLIC\".\"DOCTOR\" (\"NAME\", \"SURNAME\", \"PATRONYMIC\", \"SPECIALIZATION\") VALUES " +
            "('AdocN', 'AdocS', 'AdocP', 'Aspec'), " +
            "('BdocN', 'BdocS', 'BdocP', 'Bspec'), " +
            "('CdocN', 'CdocS', 'CdocP', 'Cspec'), " +
            "('DdocN', 'DdocS', 'DdocP', 'Dspec'), " +
            "('EdocN', 'EdocS', 'EdocP', 'Espec');";

    private static final String INSERT_PATIENT = "INSERT INTO \"PUBLIC\".\"PATIENT\" (\"NAME\", \"SURNAME\", \"PATRONYMIC\", \"PHONENUMBER\") VALUES " +
            "('ApatN','ApatS','ApatP','000000'), " +
            "('BpatN','BpatS','BpatP','111111'), " +
            "('CpatN','CpatS','CpatP','222222'), " +
            "('DpatN','DpatS','DpatP','333333'), " +
            "('EpatN','EpatS','EpatP','444444');";

    private static final String INSERT_RECIPE = "" +
            "INSERT INTO \"PUBLIC\".\"RECIPE\" (\"DESCRIPTION\", \"PATIENTID\", \"DOCTORID\", \"CREATIONDATE\", \"DURATION\", \"PRIORITY\") VALUES " +
            "('descr0', 0, 0, '2019-07-20', 15, 'normal'), " +
            "('descr1', 1, 1, '2019-07-21', 10, 'statium'), " +
            "('descr2', 4, 1, '2019-07-20', 20, 'cito'), " +
            "('descr3', 3, 2, '2019-07-22', 30, 'cito'), " +
            "('descr4', 2, 4, '2019-07-26', 20, 'statium'), " +
            "('descr5', 1, 0, '2019-07-22', 10, 'normal'), " +
            "('descr6', 0, 3, '2019-07-24', 30, 'statium'), " +
            "('descr7', 2, 3, '2019-07-26', 10, 'cito'), " +
            "('descr8', 3, 4, '2019-07-26', 15, 'cito'), " +
            "('descr9', 4, 2, '2019-07-25', 20, 'normal');";

    private static final String DOCTOR = "DOCTOR";
    private static final String PATIENT = "PATIENT";
    private static final String RECIPE = "RECIPE";


    private Connection con;

    public DatabaseInit(){
        con = DatabaseConnection.getConnection();
    }

    private void createAndFill(Connection con, String createRequest, String insertRequest){
        try{
            PreparedStatement createStatement = con.prepareStatement(createRequest);
            createStatement.executeUpdate();
            PreparedStatement insertStatement = con.prepareStatement(insertRequest);
            insertStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void createAndFillDoctor(){
        if (!checkTableExisting(con, DOCTOR)){
            createAndFill(con, CREATE_DOCTOR, INSERT_DOCTOR);
        }
    }

    public void createAndFillPatient(){
        if (!checkTableExisting(con, PATIENT)){
            createAndFill(con, CREATE_PATIENT, INSERT_PATIENT);
        }
    }

    public void createAndFillRecipe(){
        if (!checkTableExisting(con, RECIPE)){
            createAndFill(con, CREATE_RECIPE, INSERT_RECIPE);
        }
    }

    private boolean checkTableExisting(Connection con, String tableName){
        try(
                ResultSet rs = con.getMetaData().getTables(null,null,tableName,null);
        ){
            if (!rs.next()){
                return false;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
}
