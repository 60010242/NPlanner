package com.example.nplanner.model;

public class Schedule {
	private Integer schedule_id;
	private String date;
	private String time;
	private Boolean mon;
	private Boolean tue;
	private Boolean wed;
	private Boolean thu;
	private Boolean fri;
	private Boolean sat;
	private Boolean sun;
	private Integer task_id;
	
	
	
	public Integer getSchedule_id() {
		return schedule_id;
	}
	public void setSchedule_id(Integer schedule_id) {
		this.schedule_id = schedule_id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Boolean getMon() {
		return mon;
	}
	public void setMon(Boolean mon) {
		this.mon = mon;
	}
	public Boolean getTue() {
		return tue;
	}
	public void setTue(Boolean tue) {
		this.tue = tue;
	}
	public Boolean getWed() {
		return wed;
	}
	public void setWed(Boolean wed) {
		this.wed = wed;
	}
	public Boolean getThu() {
		return thu;
	}
	public void setThu(Boolean thu) {
		this.thu = thu;
	}
	public Boolean getFri() {
		return fri;
	}
	public void setFri(Boolean fri) {
		this.fri = fri;
	}
	public Boolean getSat() {
		return sat;
	}
	public void setSat(Boolean sat) {
		this.sat = sat;
	}
	public Boolean getSun() {
		return sun;
	}
	public void setSun(Boolean sun) {
		this.sun = sun;
	}
	public Integer getTask_id() {
		return task_id;
	}
	public void setTask_id(Integer task_id) {
		this.task_id = task_id;
	}
	
}
