
package com.vogella.tasks.ui.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.core.di.annotations.CanExecute;

public class SaveHandler {
	@Execute
	public void execute(EPartService partService) {
		partService.saveAll(false);
	}

	@CanExecute
	public boolean canExecute(EPartService partService) {
		return !partService.getDirtyParts().isEmpty();
	}

}