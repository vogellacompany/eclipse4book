package com.example.e4.rcp.todo;

import com.beust.jcommander.Parameter;

public class ProgrammArgs {
	@Parameter(names = "-username", description = "The user to be logged in")
	private String username;

	@Parameter(names = "-password", description = "The user's password")
	private String password;

	@Parameter(names = "-autologin", description = "Specifies if the user should be logged in automatically without login dialog")
	private boolean autologin;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAutologin() {
		return autologin;
	}

	public void setAutologin(boolean autologin) {
		this.autologin = autologin;
	}
}
