package com.haulmont.testtask.view;

import com.haulmont.testtask.controller.PatientController;
import com.haulmont.testtask.model.Patient;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

import java.sql.SQLException;

/**
 * Created by fishn on 29.07.2019.
 */
public class AddPatientWindow extends Window{

    private PatientController patientController;
    private TextField name;
    private TextField surname;
    private TextField patronymic;
    private TextField phoneNumber;

    AddPatientWindow(){
        patientController = new PatientController();
        name = new TextField("Name");
        surname = new TextField("Surname");
        patronymic = new TextField("Patronymic");
        phoneNumber = new TextField("Phone Number");
        init();
    }

    protected void init() {

        setContent(new Label("Adding new patient"));

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
        phoneNumber.addValidator(new StringLengthValidator("maximal length - 12",1,12,false));
        phoneNumber.setRequired(true);
        phoneNumber.setRequiredError("field is empty!");

        addContent.addComponent(name);
        addContent.addComponent(surname);
        addContent.addComponent(patronymic);
        addContent.addComponent(phoneNumber);
        Button add = new Button("Add", e -> addPatient(new Patient(name.getValue(), surname.getValue(),
                patronymic.getValue(), phoneNumber.getValue())));
        addContent.addComponent(add);
        Button cancel = new Button("Cancel", e -> close());
        cancel.setDisableOnClick(true);
        addContent.addComponent(cancel);

        setContent(addContent);

        center();
    }

    private void addPatient(Patient patient) {

        if (name.isValid()&&surname.isValid()&&patronymic.isValid()&&phoneNumber.isValid()){
            try {
                patientController.add(patient);
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
