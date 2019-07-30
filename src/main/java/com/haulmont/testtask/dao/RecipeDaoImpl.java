package com.haulmont.testtask.dao;

import com.haulmont.testtask.db.DatabaseConnection;
import com.haulmont.testtask.model.Recipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fishn on 24.07.2019.
 */
public class RecipeDaoImpl implements RecipeDao {

    @Override
    public List<Recipe> showAll(String descriptionFilter, String priorityFilter, String patientIdFilter) throws SQLException {

        List<Recipe> recipes = new ArrayList<>();
        String request = "SELECT * FROM \"PUBLIC\".\"RECIPE\"";
        if (descriptionFilter!=null && !descriptionFilter.isEmpty()){
            request += " WHERE \"DESCRIPTION\" LIKE '%" + descriptionFilter + "'";
        }
        if (priorityFilter!=null && !priorityFilter.isEmpty()){
            if (request.contains("WHERE")){
                request += " AND \"PRIORITY\" LIKE '%" + priorityFilter + "'";
            }
            else{
                request += " WHERE \"PRIORITY\" LIKE '%" + priorityFilter + "'";
            }
        }
        if (patientIdFilter!=null && !patientIdFilter.isEmpty()){
            if (request.contains("WHERE")){
                request += " AND \"PATIENTID\"=" + Long.parseLong(patientIdFilter);
            }
            else{
                request += " WHERE \"PATIENTID\"=" + Long.parseLong(patientIdFilter);
            }
        }
        try(
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(request);
        ){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Long id = rs.getLong("id");
                String description = rs.getString("description");
                Long patientId = rs.getLong("patientId");
                Long doctorId = rs.getLong("doctorId");
                Date creationDate = rs.getDate("creationDate");
                int duration = rs.getInt("duration");
                String priority = rs.getString("priority");
                Recipe recipe = new Recipe(id,description,patientId,doctorId,creationDate,duration,priority);
                recipes.add(recipe);
            }
        }
        return recipes;
    }

    @Override
    public Recipe showRecipeById(Long recipeId) throws SQLException {
        Recipe recipe = null;
        try(
            Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM \"PUBLIC\".\"RECIPE\" WHERE \"ID\"="+recipeId);
    ){
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            Long id = rs.getLong("id");
            String description = rs.getString("description");
            Long patientId = rs.getLong("patientId");
            Long doctorId = rs.getLong("doctorId");
            Date creationDate = rs.getDate("creationDate");
            int duration = rs.getInt("duration");
            String priority = rs.getString("priority");
            recipe = new Recipe(id,description,patientId,doctorId,creationDate,duration,priority);
        }
    }
        return recipe;
    }

    @Override
    public boolean add(Recipe recipe) throws SQLException {
        Date date = recipe.getCreationDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String str = format.format(date);
        System.out.print(str);
        try(
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("INSERT INTO \"PUBLIC\".\"RECIPE\" (\"DESCRIPTION\", \"PATIENTID\"," +
                        " \"DOCTORID\", \"CREATIONDATE\", \"DURATION\", \"PRIORITY\") VALUES ('"+recipe.getDescription()+"', " +
                        ""+recipe.getPatientId()+", "+recipe.getDoctorId()+", '"+str+"', "+recipe.getDuration()+"," +
                        " '"+recipe.getPriority()+"');")
        ) {
            return ps.executeUpdate() != 0;
        }
    }

    @Override
    public boolean update(Recipe recipe) throws SQLException {
        Date date = recipe.getCreationDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String str = format.format(date);
        try(
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("UPDATE \"PUBLIC\".\"RECIPE\" SET \"DESCRIPTION\"='"
                        +recipe.getDescription()+"', \"PATIENTID\"="+recipe.getPatientId()+", \"DOCTORID\"="
                        +recipe.getDoctorId()+", \"CREATIONDATE\"='"+str+"', \"DURATION\"='"
                        +recipe.getDuration()+"', \"PRIORITY\"='"+recipe.getPriority()+"' WHERE \"ID\"="+recipe.getId()+";")
        ) {
            return ps.executeUpdate() !=0;
        }
    }

    @Override
    public boolean delete(Long recipeId) throws SQLException {

        try(
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("DELETE FROM \"PUBLIC\".\"RECIPE\" WHERE \"ID\"="+recipeId+";")
        ) {
            return ps.executeUpdate() !=0;
        }
    }

    @Override
    public HashMap<Long, Integer> showNumberOfDoctorsRecipes() throws SQLException {

        HashMap<Long, Integer> numberOfDoctorRecipes = new HashMap<>();
        try(
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT \"RECIPE\".\"DOCTORID\", COUNT(\"RECIPE\".\"DOCTORID\")" +
                        " AS recipes FROM \"PUBLIC\".\"RECIPE\" GROUP BY \"RECIPE\".\"DOCTORID\";")
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Long doctorId = rs.getLong("doctorId");
                Integer count = rs.getInt("recipes");
                numberOfDoctorRecipes.put(doctorId,count);
            }
        }
        return numberOfDoctorRecipes;
    }

    public boolean checkDoctorContent(Long id) throws SQLException{
        try(
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT COUNT(\"PUBLIC\".\"RECIPE\".\"DOCTORID\") AS tmp FROM " +
                        "\"PUBLIC\".\"RECIPE\" WHERE \"DOCTORID\"="+id+";")
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Integer count = rs.getInt("tmp");
                if (count!=0){
                    return true;
                }
            }
            return false;
        }
    }

    public boolean checkPatientContent(Long id) throws SQLException{
        try(
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement("SELECT COUNT(\"PUBLIC\".\"RECIPE\".\"PATIENTID\") AS tmp FROM " +
                        "\"PUBLIC\".\"RECIPE\" WHERE \"PATIENTID\"="+id+";")
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Integer count = rs.getInt("tmp");
                if (count!=0){
                    return true;
                }
            }
            return false;
        }
    }
}
