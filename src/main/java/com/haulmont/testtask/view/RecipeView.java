package com.haulmont.testtask.view;

import com.haulmont.testtask.controller.RecipeController;
import com.haulmont.testtask.db.DatabaseInit;
import com.haulmont.testtask.model.Recipe;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by fishn on 30.07.2019.
 */
public class RecipeView extends VerticalLayout implements View {

    private RecipeController recipeController;
    private Grid grid;
    private TextField descrFilter;
    private TextField priorityFilter;
    private TextField patientIdFilter;
    private List<Recipe> recipeList;

    public RecipeView(){

        DatabaseInit dbInit = new DatabaseInit();
        dbInit.createAndFillDoctor();
        dbInit.createAndFillPatient();
        dbInit.createAndFillRecipe();

        HorizontalLayout filterContent = new HorizontalLayout();

        descrFilter = new TextField("Description filter");
        priorityFilter = new TextField("Priority Filter");
        patientIdFilter = new TextField("Patient ID Filter");

        Button apply = new Button("Apply", e -> updateList());

        recipeController = new RecipeController();
        recipeList = null;
        try {
            recipeList = recipeController.showAll(descrFilter.getValue(),priorityFilter.getValue(),patientIdFilter.getValue());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BeanItemContainer<Recipe> container = new BeanItemContainer<Recipe>(Recipe.class, recipeList);

        grid = new Grid(container);

        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        grid.setColumnOrder("id","description", "patientId","doctorId","creationDate","duration","priority");
        grid.getColumn("id").setHeaderCaption("ID");
        grid.getColumn("description").setHeaderCaption("Description");
        grid.getColumn("patientId").setHeaderCaption("Patient ID");
        grid.getColumn("doctorId").setHeaderCaption("Doctor ID");
        grid.getColumn("creationDate").setHeaderCaption("Creation Date");
        grid.getColumn("duration").setHeaderCaption("Duration");
        grid.getColumn("priority").setHeaderCaption("Priority");

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        Button addButton = new Button("add", e -> addRecipe());
        buttonsLayout.addComponent(addButton);
        Button updateButton = new Button("update",e -> updateRecipe());
        buttonsLayout.addComponent(updateButton);
        Button deleteButton = new Button("delete", e -> deleteRecipe());
        buttonsLayout.addComponent(deleteButton);

        filterContent.addComponent(descrFilter);
        filterContent.addComponent(priorityFilter);
        filterContent.addComponent(patientIdFilter);
        filterContent.addComponent(apply);
        filterContent.setComponentAlignment(apply,Alignment.BOTTOM_CENTER);
        addComponent(filterContent);
        addComponent(grid);
        addComponent(buttonsLayout);
        setComponentAlignment(filterContent,Alignment.TOP_CENTER);
        setComponentAlignment(grid, Alignment.MIDDLE_CENTER);
        setComponentAlignment(buttonsLayout,Alignment.BOTTOM_CENTER);

    }

    private void addRecipe() {
        AddRecipeWindow addWindow = new AddRecipeWindow();
        UI.getCurrent().addWindow(addWindow);
        addWindow.addCloseListener(e -> updateList());
    }

    private void updateRecipe() {
        Grid.SingleSelectionModel selectionModel = (Grid.SingleSelectionModel) grid.getSelectionModel();
        Object idObject = selectionModel.getSelectedRow();
        if (idObject!=null){
            Long id = (Long) grid.getContainerDataSource().getItem(idObject).getItemProperty("id").getValue();
            UpdateRecipeWindow updateWindow = new UpdateRecipeWindow(id);
            UI.getCurrent().addWindow(updateWindow);
            updateWindow.addCloseListener(e -> updateList());
            grid.getSelectionModel().reset();
        }
        else {
            Notification.show("select row to update", Notification.Type.TRAY_NOTIFICATION);
        }
    }

    private void deleteRecipe() {
        Grid.SingleSelectionModel selectionModel = (Grid.SingleSelectionModel) grid.getSelectionModel();
        Object idObject = selectionModel.getSelectedRow();
        if (idObject!=null){
            Long id = (Long) grid.getContainerDataSource().getItem(idObject).getItemProperty("id").getValue();
            try {
                recipeController.delete(id);
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
        try {
            recipeList = recipeController.showAll(descrFilter.getValue(),priorityFilter.getValue(),patientIdFilter.getValue());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BeanItemContainer<Recipe> container = (BeanItemContainer<Recipe>) grid.getContainerDataSource();
        container.removeAllItems();
        container.addAll(recipeList);
        grid.setContainerDataSource(container);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
