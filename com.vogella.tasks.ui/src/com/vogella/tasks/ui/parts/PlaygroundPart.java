package com.vogella.tasks.ui.parts;


import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.vogella.swt.widgets.Checkbox;


public class PlaygroundPart {

	@PostConstruct
	public void createControls(Composite parent) {
	 Checkbox checkbox = new Checkbox(parent, SWT.NONE);
	}
}