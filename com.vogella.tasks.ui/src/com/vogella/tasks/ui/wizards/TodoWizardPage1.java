package com.vogella.tasks.ui.wizards;

import java.util.Collections;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.vogella.tasks.model.Todo;
import com.vogella.tasks.ui.parts.TodoDetailsPart;

public class TodoWizardPage1 extends WizardPage {

	private Todo todo;

	public TodoWizardPage1(Todo todo) {
		super("wizardPage");
		this.todo = todo;
		setTitle("Wizard Page title");
		setDescription("Wizard Page description");
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		TodoDetailsPart todoDetailsPart = new TodoDetailsPart();
		todoDetailsPart.createControls(container);

		todoDetailsPart.setTodos(Collections.singletonList(todo));
		setControl(container);
	}

}
