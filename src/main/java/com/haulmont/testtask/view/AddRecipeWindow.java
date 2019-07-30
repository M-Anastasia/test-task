package com.haulmont.testtask.view;

import com.haulmont.testtask.controller.DoctorController;
import com.haulmont.testtask.controller.PatientController;
import com.haulmont.testtask.controller.RecipeController;
import com.haulmont.testtask.model.Doctor;
import com.haulmont.testtask.model.Patient;
import com.haulmont.testtask.model.Recipe;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by fishn on 30.07.2019.
 */
public class AddRecipeWindow extends Window {

    private RecipeController recipeController;
    private TextField description;
    private NativeSelect patientId;
    private NativeSelect doctorId;
    private TextField duration;
    private NativeSelect priority;

    public AddRecipeWindow(){
        recipeController = new RecipeController();
        description = new TextField("Description");
        duration = new TextField("Duration");
        priority = new NativeSelect("Priority");
        init();
    }

    protected void init(){

        setContent(new Label("Adding new recipe"));

        VerticalLayout addContent = new VerticalLayout();

        description.addValidator(new StringLengthValidator("maximal length - 255",1,255,false));
        description.setRequired(true);
        description.setRequiredError("field is empty!");

        PatientController patientController = new PatientController();
        List<Long> patientsIs = null;
        try {
            patientsIs = patientController.showAllId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BeanItemContainer<Long> patientContainer = new BeanItemContainer<>(Long.class, patientsIs);
        patientId = new NativeSelect("Patient", patientContainer);
        patientId.setRequired(true);
        patientId.setNullSelectionAllowed(false);
        patientId.setValue(patientId.getItemIds().iterator().next());

        DoctorController doctorController = new DoctorController();
        List<Long> doctorsId = null;
        try {
            doctorsId = doctorController.showAllId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BeanItemContainer<Long> doctorContainer = new BeanItemContainer<>(Long.class, doctorsId);
        doctorId = new NativeSelect("Doctor", doctorContainer);
        doctorId.setRequired(true);
        doctorId.setNullSelectionAllowed(false);
        doctorId.setValue(doctorId.getItemIds().iterator().next());

        duration.setRequired(true);
        duration.setRequiredError("field is empty!");

        priority = new NativeSelect("Priority");
        Collection<String> prioritis = new ArrayList<>();
        prioritis.add("Normal");
        prioritis.add("Cito");
        prioritis.add("Statim");
        priority.addItems(prioritis);
        priority.setRequired(true);
        priority.setNullSelectionAllowed(false);
        priority.setValue(priority.getItemIds().iterator().next());

        addContent.addComponent(description);
        addContent.addComponent(patientId);
        addContent.addComponent(doctorId);
        addContent.addComponent(duration);
        addContent.addComponent(priority);


        DateField dateField = new DateField();
        dateField.setValue(new Date());
        dateField.setDateFormat("yyyy.MM.dd");
        dateField.setLenient(true);
        Date date = dateField.getValue();

        Button add = new Button("Add", e -> addRecipe(new Recipe(description.getValue(),(Long)patientId.getValue(),
                (Long)doctorId.getValue(),date,Integer.parseInt(duration.getValue()),(String)priority.getValue())));
        addContent.addComponent(add);
        Button cancel = new Button("Cancel", e -> close());
        cancel.setDisableOnClick(true);
        addContent.addComponent(cancel);

        setContent(addContent);

        center();

    }

    private void addRecipe(Recipe recipe) {
        if (description.isValid()){
            try {
                recipeController.add(recipe);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            close();
        }
        else {
            Notification.show("you need to fill all fields", Notification.Type.TRAY_NOTIFICATION);
            close();
        }
    }
}
