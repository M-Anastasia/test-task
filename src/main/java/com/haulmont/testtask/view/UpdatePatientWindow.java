package com.haulmont.testtask.view;

import com.haulmont.testtask.controller.PatientController;
import com.haulmont.testtask.model.Patient;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

import java.sql.SQLException;

/**
 * Created by fishn on 29.07.2019.
 */
public class UpdatePatientWindow extends Window {

    private PatientController patientController;
    private TextField name;
    private TextField surname;
    private TextField patronymic;
    private TextField phoneNumber;

    UpdatePatientWindow(Long id){
        patientController = new PatientController();
        init(id);
    }

    protected void init(Long id) {
        setContent(new Label("Updating patient"));

        VerticalLayout updateContent = new VerticalLayout();

        Patient patient = null;
        try {
            patient = patientController.showPatientById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        name = new TextField("Name",patient.getName());
        surname = new TextField("Surname", patient.getSurname());
        patronymic = new TextField("Patronymic",patient.getPatronymic());
        phoneNumber = new TextField("Phone Number", patient.getPhoneNumber());

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

        updateContent.addComponent(name);
        updateContent.addComponent(surname);
        updateContent.addComponent(patronymic);
        updateContent.addComponent(phoneNumber);
        Button update = new Button("Update", e -> updatePatient(new Patient(id,name.getValue(), surname.getValue(),
                patronymic.getValue(), phoneNumber.getValue())));
        updateContent.addComponent(update);
        Button cancel = new Button("Cancel", e -> close());
        cancel.setDisableOnClick(true);
        updateContent.addComponent(cancel);

        setContent(updateContent);

        center();
    }

    private void updatePatient(Patient patient) {
        if (name.isValid()&&surname.isValid()&&patronymic.isValid()&&phoneNumber.isValid()){
            try {
                patientController.update(patient);
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
