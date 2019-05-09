package com.vogella.tasks.ui.parts;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.databinding.viewers.ViewerSupport;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import com.vogella.tasks.model.ITodoService;
import com.vogella.tasks.model.Todo;

public class TodoOverviewPart {

	@Inject EMenuService menuService;

    @Inject
    ITodoService todoService;

    @Inject
    ESelectionService selectionService;

    private Button btnLoadData;
    private TableViewer viewer;

    private WritableList<Todo> writableList;

    @PostConstruct
    public void createControls(Composite parent) {
        parent.setLayout(new GridLayout(1, false));

        btnLoadData = new Button(parent, SWT.PUSH);
        btnLoadData.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                // pass in updateViewer method as Consumer
                todoService.getTodos(TodoOverviewPart.this::updateViewer);
            }
        });
        btnLoadData.setText("Load Data");

        // more code, e.g. your search box
        // ...
        // ...

        viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
        Table table = viewer.getTable();
        table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);

        column.getColumn().setWidth(100);
        column.getColumn().setText("Summary");
        column = new TableViewerColumn(viewer, SWT.NONE);

        column.getColumn().setWidth(100);
        column.getColumn().setText("Description");

        // more code for your table, e.g. filter, etc.

        // use data binding to bind the viewer
        writableList = new WritableList<>();
        // fill the writable list, when Consumer callback is called. Databinding
        // will do the rest once the list is filled
        todoService.getTodos(writableList::addAll);
        ViewerSupport.bind(viewer, writableList,
                BeanProperties.values(new String[] { Todo.FIELD_SUMMARY, Todo.FIELD_DESCRIPTION }));
        viewer.addSelectionChangedListener(event -> {
        	IStructuredSelection structuredSelection = viewer.getStructuredSelection();
        	List list = structuredSelection.toList();
        	// context legen
        	selectionService.setSelection(list);
		});
        menuService.registerContextMenu(viewer.getTable(), "com.vogella.tasks.ui.popupmenu.contextmenutable");
    }

    public void updateViewer(List<Todo> list) {
        if (viewer != null) {
            writableList.clear();
            writableList.addAll(list);
        }
    }

    @Focus
    private void setFocus() {
        btnLoadData.setFocus();
    }
}