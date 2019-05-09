package com.vogella.tasks.ui.handlers;

import java.util.List;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class TestHandler {

	@Execute
	public void execute(EPartService partService, MWindow window, EModelService modelService) {
//		MPart createPart = partService.createPart("com.vogella.tasks.ui.partdescriptor.larsseinpart");
//		window.getChildren().add(createPart);
//		partService.switchPerspective("com.vogella.tasks.ui.perspective.1");
		List<MPart> findElements = modelService.findElements(window, null, MPart.class);
		findElements.forEach(e -> e.setCloseable(!e.isCloseable()));
		MPart mpart = modelService.createModelElement(MPart.class);
		mpart.setLabel("Lars sein Part");
		mpart.setCloseable(true);
		List<MPartStack> stacks = modelService.findElements(window, null, MPartStack.class);
		stacks.get(1).getChildren().add(mpart);
	}

}
