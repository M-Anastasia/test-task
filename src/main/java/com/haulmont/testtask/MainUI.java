package com.haulmont.testtask;

import com.haulmont.testtask.controller.RecipeController;
import com.haulmont.testtask.view.DefaultView;
import com.haulmont.testtask.view.DoctorView;
import com.haulmont.testtask.view.PatientView;
import com.haulmont.testtask.view.RecipeView;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

import java.sql.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    @Override
    protected void init(VaadinRequest request) {

        Label title = new Label("Menu");
        title.addStyleName(ValoTheme.MENU_TITLE);

        Button doctorView = new Button("Doctor", e -> getNavigator().navigateTo("doctorView"));
        doctorView.addStyleName(ValoTheme.BUTTON_LINK);
        doctorView.addStyleName(ValoTheme.MENU_ITEM);
        Button patientView = new Button("Patient", e -> getNavigator().navigateTo("patientView"));
        patientView.addStyleName(ValoTheme.BUTTON_LINK);
        patientView.addStyleName(ValoTheme.MENU_ITEM);
        Button recipeView = new Button("Recipe", e -> getNavigator().navigateTo("recipeView"));
        recipeView.addStyleName(ValoTheme.BUTTON_LINK);
        recipeView.addStyleName(ValoTheme.MENU_ITEM);

        CssLayout menu = new CssLayout(title,doctorView,patientView,recipeView);
        menu.addStyleName(ValoTheme.MENU_ROOT);
//
//        menu.setHeight(100, Unit.PERCENTAGE);
//        menu.setWidth(12,Unit.PERCENTAGE);

        CssLayout viewContainer = new CssLayout();
//
//        viewContainer.setHeight(100, Unit.PERCENTAGE);
//        viewContainer.setWidth(100,Unit.PERCENTAGE);

        HorizontalLayout mainLayout = new HorizontalLayout(menu,viewContainer);
        mainLayout.setComponentAlignment(menu,Alignment.TOP_LEFT);
        mainLayout.setComponentAlignment(viewContainer,Alignment.MIDDLE_CENTER);
        setContent(mainLayout);


        Navigator navigator = new Navigator(this, viewContainer);
        navigator.addView("", DefaultView.class);
        navigator.addView("doctorView", DoctorView.class);
        navigator.addView("patientView", PatientView.class);
        navigator.addView("recipeView", RecipeView.class);
    }
}