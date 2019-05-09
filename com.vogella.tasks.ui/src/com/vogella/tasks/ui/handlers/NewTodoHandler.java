package com.vogella.tasks.ui.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import com.vogella.tasks.model.ITodoService;
import com.vogella.tasks.model.Todo;
import com.vogella.tasks.ui.wizards.TodoWizard;

public class NewTodoHandler {

	@Execute
	public void execute(Shell shell, ITodoService service) {
//		testDialog.open();
		Todo todo = new Todo(-1);
		TodoWizard wizard = new TodoWizard(todo);
		int open = new WizardDialog(shell, wizard).open();
		if (open == Window.OK) {
			service.saveTodo(todo);
		}
	}



}
