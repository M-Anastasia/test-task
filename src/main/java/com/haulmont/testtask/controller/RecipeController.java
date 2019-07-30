package com.haulmont.testtask.controller;

import com.haulmont.testtask.dao.RecipeDao;
import com.haulmont.testtask.dao.RecipeDaoImpl;
import com.haulmont.testtask.db.DatabaseConnection;
import com.haulmont.testtask.model.Recipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fishn on 25.07.2019.
 */
public class RecipeController {

    private RecipeDao recipeDao;

    public RecipeController() {
        recipeDao = new RecipeDaoImpl();
    }

    public List<Recipe> showAll(String descriptionFilter, String priorityFilter, String patientIdFilter) throws SQLException {
        List<Recipe> recipes = new ArrayList<>();
        recipes = recipeDao.showAll(descriptionFilter,priorityFilter,patientIdFilter);
        return recipes;
    }

    public Recipe showRecipeById(Long id) throws SQLException {
        return recipeDao.showRecipeById(id);
    }

    public boolean add(Recipe recipe) throws SQLException {
        return recipeDao.add(recipe);
    }

    public boolean update(Recipe recipe) throws SQLException {
        return recipeDao.update(recipe);
    }

    public boolean delete(Long recipeId) throws SQLException {
        return recipeDao.delete(recipeId);
    }

    public HashMap<Long,Integer> showNumberOfDoctorsRecipes() throws SQLException{
        HashMap<Long,Integer> numberOfDoctorRecipes = new HashMap<>();
        numberOfDoctorRecipes = recipeDao.showNumberOfDoctorsRecipes();
        return numberOfDoctorRecipes;
    }

    public boolean checkDoctorContent(Long id) throws SQLException{
        return recipeDao.checkDoctorContent(id);
    }

    public boolean checkPatientContent(Long id) throws SQLException{
        return recipeDao.checkPatientContent(id);
    }
}
