package com.haulmont.testtask.dao;

import com.haulmont.testtask.model.Recipe;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fishn on 24.07.2019.
 */
public interface RecipeDao {

    List<Recipe> showAll(String descriptionFilter, String priorityFilter, String patientIdFilter) throws SQLException;
    Recipe showRecipeById(Long id) throws SQLException;
    boolean add(Recipe recipe) throws SQLException;
    boolean update(Recipe recipe) throws SQLException;
    boolean delete(Long id) throws SQLException;
    HashMap<Long,Integer> showNumberOfDoctorsRecipes() throws SQLException;
    boolean checkDoctorContent(Long id) throws SQLException;
    boolean checkPatientContent(Long id) throws SQLException;
}
