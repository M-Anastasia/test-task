package com.haulmont.testtask.view;

import com.haulmont.testtask.controller.DoctorController;
import com.haulmont.testtask.model.Doctor;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

import java.sql.SQLException;

/**
 * Created by fishn on 29.07.2019.
 */
public class AddDoctorWindow extends Window {

    private TextField name;
    private TextField surname;
    private TextField patronymic;
    private TextField specialization;

    AddDoctorWindow(){
        name = new TextField("Name");
        surname = new TextField("Surname");
        patronymic = new TextField("Patronymic");
        specialization = new TextField("Specialization");
        init();
    }

    protected void init() {

        setContent(new Label("Adding new doctor"));

        VerticalLayout addContent = new VerticalLayout();

        name.addValidator(new StringLengthValidator("maximal length - 50",1,50,false));
        name.setRequired(true);
        name.setRequiredError("field is empty!");
        surname.addValidator(new StringLengthValidator("maximal length - 50",1,50,false));
        surname.setRequired(true);
        surname.setRequiredError("field is empty!");
        patronymic.addValidator(new StringLengthValidator("maximal length - 50",1,50,false));
        patronymic.setRequired(true);
        patronymic.setRequiredError("field is empty!");
        specialization.addValidator(new StringLengthValidator("maximal length - 50",1,50,false));
        specialization.setRequired(true);
        specialization.setRequiredError("field is empty!");

        addContent.addComponent(name);
        addContent.addComponent(surname);
        addContent.addComponent(patronymic);
        addContent.addComponent(specialization);
        Button add = new Button("Add", e -> addDoctor(new Doctor(name.getValue(), surname.getValue(),
                patronymic.getValue(), specialization.getValue())));
        addContent.addComponent(add);
        Button cancel = new Button("Cancel", e -> close());
        cancel.setDisableOnClick(true);
        addContent.addComponent(cancel);

        setContent(addContent);

        center();
    }

    private void addDoctor(Doctor doctor) {
        DoctorController doctorController = new DoctorController();
        if (name.isValid()&&surname.isValid()&&patronymic.isValid()&&specialization.isValid()){
            try {
                doctorController.add(doctor);
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
