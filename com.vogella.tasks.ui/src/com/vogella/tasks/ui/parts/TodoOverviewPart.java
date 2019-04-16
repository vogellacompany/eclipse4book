package com.vogella.tasks.ui.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.vogella.tasks.model.ITodoService;

public class TodoOverviewPart {

    @Inject
    ITodoService todoService;

    private Button btnLoadData;
    private Label lblNumberOfTodo;

    @PostConstruct
    public void createControls(Composite parent) {
        parent.setLayout(new GridLayout(2, false));

        btnLoadData = new Button(parent, SWT.PUSH);
        btnLoadData.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                todoService.getTodos(todos -> {
                    lblNumberOfTodo.setText("Number of Todo items " + todos.size());
                });
            }
        });
        btnLoadData.setText("Load Data");

        lblNumberOfTodo = new Label(parent, SWT.NONE);
        lblNumberOfTodo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                false, 1, 1));
        lblNumberOfTodo.setText("Data not available.");


    }

    @Focus
    public void setFocus() {
        btnLoadData.setFocus();
    }

}