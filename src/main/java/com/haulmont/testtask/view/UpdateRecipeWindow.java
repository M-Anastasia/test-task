package com.haulmont.testtask.view;

import com.haulmont.testtask.controller.DoctorController;
import com.haulmont.testtask.controller.PatientController;
import com.haulmont.testtask.controller.RecipeController;
import com.haulmont.testtask.model.Recipe;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by fishn on 30.07.2019.
 */
public class UpdateRecipeWindow extends Window{

    private RecipeController recipeController;
    private TextField description;
    private NativeSelect patientId;
    private NativeSelect doctorId;
    private TextField duration;
    private NativeSelect priority;

    public UpdateRecipeWindow(Long id){
        recipeController = new RecipeController();
        init(id);
    }

    protected void init(Long id){
        setContent(new Label("Updating recipe"));

        VerticalLayout updateContent = new VerticalLayout();

        Recipe recipe = null;
        try {
            recipe = recipeController.showRecipeById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        description = new TextField("Description",recipe.getDescription());
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

        duration = new TextField("Duration", recipe.getDuration()+"");
        duration.addValidator(new StringLengthValidator("from 1 to 999",1,3,false));
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

        updateContent.addComponent(description);
        updateContent.addComponent(patientId);
        updateContent.addComponent(doctorId);
        updateContent.addComponent(duration);
        updateContent.addComponent(priority);

        Button update = new Button("Update", e -> updateRecipe(id));
        updateContent.addComponent(update);
        Button cancel = new Button("Cancel", e -> close());
        cancel.setDisableOnClick(true);
        updateContent.addComponent(cancel);

        setContent(updateContent);

        center();
    }

    private void updateRecipe(Long id) {
        String test = "\\d+";
        if (description.isValid()&&duration.getValue().matches(test)&&duration.isValid()){
            try {
                recipeController.update(new Recipe(id,description.getValue(),(Long)patientId.getValue(),
                        (Long)doctorId.getValue(),new Date(),Integer.parseInt(duration.getValue()),(String)priority.getValue()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            close();
        }
        else {
            Notification.show("You need to fill all fields," +
                    " or you enter letters in the \"Duration\" field, " +
                    "or duration is too long (valid values range from 1 to 999)", Notification.Type.TRAY_NOTIFICATION);
            close();
        }
    }
}
