package com.haulmont.testtask.view;

import com.haulmont.testtask.controller.DoctorController;
import com.haulmont.testtask.db.DatabaseInit;
import com.haulmont.testtask.model.Doctor;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * Created by fishn on 28.07.2019.
 */
public class DoctorView extends VerticalLayout implements View {

    private DoctorController doctorController;
    private Grid grid;


    public DoctorView() {
        DatabaseInit dbInit = new DatabaseInit();
        dbInit.createAndFillDoctor();
        dbInit.createAndFillPatient();
        dbInit.createAndFillRecipe();

        doctorController = new DoctorController();
        List<Doctor> doctors = null;
        try {
            doctors = doctorController.showAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BeanItemContainer<Doctor> container = new BeanItemContainer<Doctor>(Doctor.class, doctors);
        grid = new Grid(container);

        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        grid.setColumnOrder("id","name", "surname", "patronymic", "specialization");
        grid.getColumn("id").setHeaderCaption("ID");
        grid.getColumn("name").setHeaderCaption("Name");
        grid.getColumn("surname").setHeaderCaption("Surname");
        grid.getColumn("patronymic").setHeaderCaption("Patronymic");
        grid.getColumn("specialization").setHeaderCaption("Specialization");
        addComponent(grid);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        Button addButton = new Button("add", e -> addDoctor());
        buttonsLayout.addComponent(addButton);
        Button updateButton = new Button("update",e -> updateDoctor());
        buttonsLayout.addComponent(updateButton);
        Button deleteButton = new Button("delete", e -> deleteDoctor());
        buttonsLayout.addComponent(deleteButton);
        Button statisticButton = new Button("statistic", e -> showStatistic());
        buttonsLayout.addComponent(statisticButton);

        addComponent(buttonsLayout);
        setComponentAlignment(grid, Alignment.TOP_LEFT);
        setComponentAlignment(buttonsLayout,Alignment.BOTTOM_CENTER);
    }

    private void addDoctor() {
        AddDoctorWindow addWindow = new AddDoctorWindow();
        UI.getCurrent().addWindow(addWindow);
        addWindow.addCloseListener(e -> updateList());
    }

    private void updateDoctor(){
        Grid.SingleSelectionModel selectionModel = (Grid.SingleSelectionModel) grid.getSelectionModel();
        Object idObject = selectionModel.getSelectedRow();
        if (idObject!=null){
            Long id = (Long) grid.getContainerDataSource().getItem(idObject).getItemProperty("id").getValue();
            UpdateDoctorWindow updateWindow = new UpdateDoctorWindow(id);
            UI.getCurrent().addWindow(updateWindow);
            updateWindow.addCloseListener(e -> updateList());
            grid.getSelectionModel().reset();
        }
        else {
            Notification.show("select row to update", Notification.Type.TRAY_NOTIFICATION);
        }
    }

    private void deleteDoctor() {
        Grid.SingleSelectionModel selectionModel = (Grid.SingleSelectionModel) grid.getSelectionModel();
        Object idObject = selectionModel.getSelectedRow();
        if (idObject!=null){
            Long id = (Long) grid.getContainerDataSource().getItem(idObject).getItemProperty("id").getValue();
            try {
                if (!doctorController.delete(id)){
                    Notification.show("this doctor has a patient!", Notification.Type.WARNING_MESSAGE);
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

    private void showStatistic() {
        StatisticWindow statisticWindow = new StatisticWindow();
        UI.getCurrent().addWindow(statisticWindow);
    }

    private void updateList() {
        List<Doctor> doctorList = null;
        try {
            doctorList = doctorController.showAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BeanItemContainer<Doctor> container = (BeanItemContainer<Doctor>) grid.getContainerDataSource();
        container.removeAllItems();
        container.addAll(doctorList);
        grid.setContainerDataSource(container);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
