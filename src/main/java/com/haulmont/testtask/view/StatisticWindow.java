package com.haulmont.testtask.view;

import com.haulmont.testtask.controller.RecipeController;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by fishn on 30.07.2019.
 */
public class StatisticWindow extends Window {

    private Table table;
    private RecipeController recipeController;

    StatisticWindow(){
        table = new Table();
        recipeController = new RecipeController();
        init();
    }

    private void init() {

        setContent(new Label("Statistic"));

        VerticalLayout layout = new VerticalLayout();

        HashMap<Long,Integer> statistic = null;
        try {
            statistic = recipeController.showNumberOfDoctorsRecipes();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Object[] keys = statistic.keySet().toArray();

        table.addContainerProperty("Doctor ID",Long.class, null);
        table.addContainerProperty("Number of recipes", Integer.class, null);

        for (int i=0; i<statistic.size(); i++){
            table.addItem(new Object[]{keys[i], statistic.get(keys[i])},i);
        }

        layout.addComponent(table);
        Button cancel = new Button("Cancel", e -> close());
        cancel.setDisableOnClick(true);
        layout.addComponent(cancel);
        setContent(layout);
        center();
    }


}
