package com.vogella.tasks.ui.parts;

import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import com.vogella.tasks.model.Todo;

public class TodoDetailsPart {
    private Text txtSummary;
    private Text txtDescription;
    private DateTime dateTime;
    private Button btnDone;

    private java.util.Optional<Todo> todo = java.util.Optional.ofNullable(null);


    @PostConstruct
    public void createControls(Composite parent) {
        parent.setLayout(new GridLayout(2, false));

        Label lblSummary = new Label(parent, SWT.NONE);
        lblSummary.setText("Summary");

        txtSummary = new Text(parent, SWT.BORDER);
        txtSummary.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                false, 1, 1));

        Label lblDescription = new Label(parent, SWT.NONE);
        lblDescription.setText("Description");

        txtDescription = new Text(parent, SWT.BORDER| SWT.MULTI| SWT.V_SCROLL);
        txtDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                true, 1, 1));

        Label lblDueDate = new Label(parent, SWT.NONE);
        lblDueDate.setText("Due Date");

        dateTime = new DateTime(parent, SWT.BORDER);
        dateTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,
                1, 1));
        new Label(parent, SWT.NONE);

        btnDone = new Button(parent, SWT.CHECK);
        btnDone.setText("Done");

        updateUserInterface(todo);
    }

    @Inject
    public void setTodos(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) List<Todo> todos) {
        if(todos == null || todos.isEmpty()) {
            this.todo = java.util.Optional.empty();
        } else {
            this.todo = java.util.Optional.of(todos.get(0));
        }
        // Remember the todo as field update the user interface
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
        if (!todo.isPresent()) {
            enableUserInterface(false);
            return; // nothing left to do
        }

        enableUserInterface(true);
        // the following check ensures that the user interface is available,
        // it assumes that you have a text widget called "txtSummary"
        if (txtSummary != null && !txtSummary.isDisposed()) {
            enableUserInterface(true);
            txtSummary.setText(todo.get().getSummary());
            txtDescription.setText(todo.get().getDescription());
            // more code to fill the widgets with data from your Todo object
            // more code
            // ....
            // ....
        }
    }

    @Focus
    public void setFocus() {
        txtSummary.setFocus();
    }

}