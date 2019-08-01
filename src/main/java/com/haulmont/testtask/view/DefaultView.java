package com.haulmont.testtask.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by fishn on 28.07.2019.
 */
public class DefaultView extends VerticalLayout implements View {

    public DefaultView() {

        Label title = new Label("Haulmont Test Task");
        Label title2 = new Label("This service contains information about hospital recipes.");
        Label title3 = new Label("Select one of the menu items to view patients, doctors and recipes.");
        addComponent(title);
        addComponent(title2);
        addComponent(title3);
        setComponentAlignment(title, Alignment.TOP_CENTER);
        setComponentAlignment(title2, Alignment.MIDDLE_CENTER);
        setComponentAlignment(title3, Alignment.BOTTOM_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
