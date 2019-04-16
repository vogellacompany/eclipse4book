package com.vogella.tasks.model;

import java.util.Date;

public class Todo {

	private final long id;
	private String summary = "";
	private String description = "";
	private boolean done = false;
	private Date dueDate = new Date();

	public Todo(long id, String summary, String description, boolean done, Date dueDate) {
		this.id = id;
		this.summary = summary;
		this.description = description;
		this.done = done;
		this.dueDate = dueDate;
	}

	public Todo(long id) {
		this.id = id;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public Date getDueDate() {
	    return new Date(dueDate.getTime());
	}

	public void setDueDate(Date dueDate) {
	    this.dueDate = new Date(dueDate.getTime());
	}
	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Todo [id=" + id + ", summary=" + summary + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Todo other = (Todo) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public Todo copy() {
	    return new Todo(this.id, this.summary,
	            this.description, this.done,
	            getDueDate());
	}


}