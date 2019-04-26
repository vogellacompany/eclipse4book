package com.vogella.tasks.ui.parts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.vogella.tasks.model.Todo;

public class TodoDetailsPart {

    private Text txtSummary;
    private Text txtDescription;
    private Button btnDone;
    private DateTime dateTime;

    // define an initially empty todo as field
    private java.util.Optional<Todo> todo = java.util.Optional.empty();

    // observable placeholder for a todo
    private WritableValue<Todo> observableTodo = new WritableValue<>();

    private DataBindingContext dbc;

    @PostConstruct
    public void createControls(Composite parent) {

        Label lblSummary = new Label(parent, SWT.NONE);
        lblSummary.setText("Summary");

        txtSummary = new Text(parent, SWT.BORDER);

        Label lblDescription = new Label(parent, SWT.NONE);
        lblDescription.setText("Description");

        txtDescription = new Text(parent, SWT.BORDER | SWT.MULTI);

        Label lblDueDate = new Label(parent, SWT.NONE);
        lblDueDate.setText("Due Date");

        dateTime = new DateTime(parent, SWT.BORDER);
        new Label(parent, SWT.NONE);

        btnDone = new Button(parent, SWT.CHECK);
        btnDone.setText("Done");

        bindData();

        updateUserInterface(todo);

        GridLayoutFactory.swtDefaults().numColumns(2).generateLayout(parent);
    }

    @SuppressWarnings("unchecked")
    private void bindData() {
        // this assumes that widget field is called "summary"
        if (txtSummary != null && !txtSummary.isDisposed()) {

            dbc = new DataBindingContext();

            Map<String, IObservableValue<?>> fields = new HashMap<>();
            fields.put(Todo.FIELD_SUMMARY, WidgetProperties.text(SWT.Modify).observe(txtSummary));
            fields.put(Todo.FIELD_DESCRIPTION, WidgetProperties.text(SWT.Modify).observe(txtDescription));
            fields.put(Todo.FIELD_DUEDATE, WidgetProperties.selection().observe(dateTime));
            fields.put(Todo.FIELD_DONE, WidgetProperties.selection().observe(btnDone));
            fields.forEach((k, v) -> dbc.bindValue(v, BeanProperties.value(k).observeDetail(observableTodo)));
        }
    }

    @Inject
    public void setTodos(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) List<Todo> todos) {
        if (todos == null || todos.isEmpty()) {
            this.todo = java.util.Optional.empty();
        } else {
            this.todo = java.util.Optional.of(todos.get(0));
        }
        // Remember the todo as field
        // update the user interface
        updateUserInterface(this.todo);
    }

    // allows to disable/ enable the user interface fields
    // if no todo is set
    private void enableUserInterface(boolean enabled) {
        if (txtSummary != null && !txtSummary.isDisposed()) {
            txtSummary.setEnabled(enabled);
            txtDescription.setEnabled(enabled);
            dateTime.setEnabled(enabled);
            btnDone.setEnabled(enabled);
        }
    }

    private void updateUserInterface(java.util.Optional<Todo> todo) {

        // check if Todo is present
        if (todo.isPresent()) {
            enableUserInterface(true);
        } else {
            enableUserInterface(false);
            return;
        }

        // Check if the user interface is available
        // assume you have a field called "summary"
        // for a widget
        if (txtSummary != null && !txtSummary.isDisposed()) {
            this.observableTodo.setValue(todo.get());
        }
    }

    @Focus
    public void onFocus() {
        txtSummary.setFocus();
    }

    @PreDestroy
    public void dispose() {
        dbc.dispose();
    }
}