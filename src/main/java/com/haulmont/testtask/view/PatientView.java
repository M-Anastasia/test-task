package com.haulmont.testtask.view;

import com.haulmont.testtask.controller.PatientController;
import com.haulmont.testtask.db.DatabaseInit;
import com.haulmont.testtask.model.Patient;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by fishn on 28.07.2019.
 */
public class PatientView extends VerticalLayout implements View{

    private PatientController patientController;
    private Grid grid;

    public PatientView() {
        DatabaseInit dbInit = new DatabaseInit();
        dbInit.createAndFillDoctor();
        dbInit.createAndFillPatient();
        dbInit.createAndFillRecipe();

        patientController = new PatientController();
        List<Patient> patients = null;
        try {
            patients = patientController.showAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BeanItemContainer<Patient> container = new BeanItemContainer<Patient>(Patient.class, patients);

        grid = new Grid(container);

        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        grid.setColumnOrder("id","name", "surname", "patronymic", "phoneNumber");
        grid.getColumn("id").setHeaderCaption("ID");
        grid.getColumn("name").setHeaderCaption("Name");
        grid.getColumn("surname").setHeaderCaption("Surname");
        grid.getColumn("patronymic").setHeaderCaption("Patronymic");
        grid.getColumn("phoneNumber").setHeaderCaption("Phone Number");
        addComponent(grid);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        Button addButton = new Button("add", e -> addPatient());
        buttonsLayout.addComponent(addButton);
        Button updateButton = new Button("update",e -> updatePatient());
        buttonsLayout.addComponent(updateButton);
        Button deleteButton = new Button("delete", e -> deletePatient());
        buttonsLayout.addComponent(deleteButton);

        addComponent(buttonsLayout);
        setComponentAlignment(grid, Alignment.TOP_LEFT);
        setComponentAlignment(buttonsLayout,Alignment.BOTTOM_CENTER);
    }

    private void addPatient() {
        AddPatientWindow addWindow = new AddPatientWindow();
        UI.getCurrent().addWindow(addWindow);
        addWindow.addCloseListener(e -> updateList());
    }

    private void updatePatient() {
        Grid.SingleSelectionModel selectionModel = (Grid.SingleSelectionModel) grid.getSelectionModel();
        Object idObject = selectionModel.getSelectedRow();
        if (idObject!=null){
            Long id = (Long) grid.getContainerDataSource().getItem(idObject).getItemProperty("id").getValue();
            UpdatePatientWindow updateWindow = new UpdatePatientWindow(id);
            UI.getCurrent().addWindow(updateWindow);
            updateWindow.addCloseListener(e -> updateList());
            grid.getSelectionModel().reset();
        }
        else {
            Notification.show("select row to update", Notification.Type.TRAY_NOTIFICATION);
        }
    }

    private void deletePatient() {
        Grid.SingleSelectionModel selectionModel = (Grid.SingleSelectionModel) grid.getSelectionModel();
        Object idObject = selectionModel.getSelectedRow();
        if (idObject!=null){
            Long id = (Long) grid.getContainerDataSource().getItem(idObject).getItemProperty("id").getValue();
            try {
                if (!patientController.delete(id)){
                    Notification.show("this patient has a recipe!", Notification.Type.WARNING_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            updateList();
            grid.getSelectionModel().reset();
        }
        else {
            Notification.show("select row to delete", Notification.Type.TRAY_NOTIFICATION);
        }
    }

    private void updateList() {
        List<Patient> patientList = null;
        try {
            patientList = patientController.showAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BeanItemContainer<Patient> container = (BeanItemContainer<Patient>) grid.getContainerDataSource();
        container.removeAllItems();
        container.addAll(patientList);
        grid.setContainerDataSource(container);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
