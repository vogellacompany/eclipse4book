package com.vogella.tasks.ui.parts;

import javax.annotation.PostConstruct;

import org.eclipse.swt.widgets.Composite;

public class PlaygroundPart {

	@PostConstruct
	public void createControls(Composite parent) {
	    System.out.println(this.getClass().getSimpleName()
	    + " @PostConstruct method called.");
	}
}