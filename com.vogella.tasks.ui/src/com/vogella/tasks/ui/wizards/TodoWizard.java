package com.vogella.tasks.ui.wizards;

import org.eclipse.jface.wizard.Wizard;

import com.vogella.tasks.model.Todo;

public class TodoWizard extends Wizard {

	private Todo todo;

	public TodoWizard(Todo todo) {
		this.todo = todo;
		setWindowTitle("New Wizard");
	}

	@Override
	public void addPages() {
		addPage(new TodoWizardPage1(todo));
		addPage(new TodoWizardPage2());
	}

	@Override
	public boolean performFinish() {
		return true;
	}

}
