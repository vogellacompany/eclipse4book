package com.vogella.tasks.ui.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class PlaygroundPart {

	@Inject
	MPart part;

	@PostConstruct
	public void createControls(Composite parent) {
		GridLayout gridLayout = new GridLayout(1, false);
		gridLayout.marginWidth = 5;
		gridLayout.marginHeight = 5;
		gridLayout.verticalSpacing = 0;
		gridLayout.horizontalSpacing = 0;
		parent.setLayout(gridLayout);

		Text model = new Text(parent, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		model.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		model.setText("");
		model.addModifyListener(e-> part.setDirty(true));
	}
	@Persist
	public void saveIt() {
		System.out.println("SaveIt!");
		part.setDirty(false);
	}

}