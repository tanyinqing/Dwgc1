package com.yzkj.model;

import java.io.Serializable;

public class BasicInformation implements Serializable{
	//private String stage;
	private	String name;
	//private String stagedata;
	private String startdata;
	
	private String commissioningdata;
	private	String buildname;
	private String supervisorunit;
	private String counstructedunit;
	
	private String voltageclass1;
	private	String projectscale1;
//	public String getStage() {
//		return stage;
//	}
//	public void setStage(String stage) {
//		this.stage = stage;
//	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
//	public String getStagedata() {
//		return stagedata;
//	}
//	public void setStagedata(String stagedata) {
//		this.stagedata = stagedata;
//	}
	public String getStartdata() {
		return startdata;
	}
	public void setStartdata(String startdata) {
		this.startdata = startdata;
	}
	public String getCommissioningdata() {
		return commissioningdata;
	}
	public void setCommissioningdata(String commissioningdata) {
		this.commissioningdata = commissioningdata;
	}
	public String getBuildname() {
		return buildname;
	}
	public void setBuildname(String buildname) {
		this.buildname = buildname;
	}
	public String getSupervisorunit() {
		return supervisorunit;
	}
	public void setSupervisorunit(String supervisorunit) {
		this.supervisorunit = supervisorunit;
	}
	public String getCounstructedunit() {
		return counstructedunit;
	}
	public void setCounstructedunit(String counstructedunit) {
		this.counstructedunit = counstructedunit;
	}
	
	public String getVoltageclass1() {
		return voltageclass1;
	}
	public void setVoltageclass1(String voltageclass1) {
		this.voltageclass1 = voltageclass1;
	}
	public String getProjectscale1() {
		return projectscale1;
	}
	public void setProjectscale1(String projectscale1) {
		this.projectscale1 = projectscale1;
	}
	@Override
	public String toString() {
		return "BasicInformation [ name=" + name
				+ ", startdata=" + startdata
				+ ", commissioningdata=" + commissioningdata + ", buildname="
				+ buildname + ", supervisorunit=" + supervisorunit
				+ ", counstructedunit=" + counstructedunit + ", voltageclass1="
				+ voltageclass1 + ", projectscale1=" + projectscale1 + "]";
	}
	

	
	
	
	
}
