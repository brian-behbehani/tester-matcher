package com.testermatcher.model;

public class Bug {

	private String bugId;
	private String testerId;
	private String deviceId;
	
	public Bug(String bugId, String testerId, String deviceId) {
		this.bugId = bugId;
		this.testerId = testerId;
		this.deviceId = deviceId;
	}
	
	public String getId() {
		return bugId;
	}
	
	public String getTesterId() {
		return testerId;
	}
	
	public String getDeviceId() {
		return deviceId;
	}
}
