package com.testermatcher.model;

public class TesterDevice {

	private String testerId;
	private String deviceId;
	
	public TesterDevice(String testerId, String deviceId) {
		this.testerId = testerId;
		this.deviceId = deviceId;
	}

	public String getTesterId() {
		return testerId;
	}
	
	public String getDeviceId() {
		return deviceId;
	}
	
}
