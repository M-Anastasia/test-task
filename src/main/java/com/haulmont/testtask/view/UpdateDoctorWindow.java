package com.haulmont.testtask.view;

import com.haulmont.testtask.controller.DoctorController;
import com.haulmont.testtask.model.Doctor;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

import java.sql.SQLException;

/**
 * Created by fishn on 29.07.2019.
 */
public class UpdateDoctorWindow extends Window {

    private DoctorController doctorController;
    private TextField name;
    private TextField surname;
    private TextField patronymic;
    private TextField specialization;

    UpdateDoctorWindow(Long id){
        doctorController = new DoctorController();
        init(id);
    }

    protected void init(Long id) {
        setContent(new Label("Updating doctor"));

        VerticalLayout updateContent = new VerticalLayout();

        Doctor doctor = null;
        try {
            doctor = doctorController.showDoctorById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        name = new TextField("Name",doctor.getName());
        surname = new TextField("Surname", doctor.getSurname());
        patronymic = new TextField("Patronymic",doctor.getPatronymic());
        specialization = new TextField("Specialization", doctor.getSpecialization());

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


        updateContent.addComponent(name);
        updateContent.addComponent(surname);
        updateContent.addComponent(patronymic);
        updateContent.addComponent(specialization);
        Button update = new Button("Update", e -> updateDoctor(new Doctor(id,name.getValue(), surname.getValue(),
                patronymic.getValue(), specialization.getValue())));
        updateContent.addComponent(update);
        Button cancel = new Button("Cancel", e -> close());
        cancel.setDisableOnClick(true);
        updateContent.addComponent(cancel);

        setContent(updateContent);

        center();
    }

    private void updateDoctor(Doctor doctor) {
        if (name.isValid()&&surname.isValid()&&patronymic.isValid()&&specialization.isValid()){
            try {
                doctorController.update(doctor);
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
