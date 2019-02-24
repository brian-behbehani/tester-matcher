package com.testermatcher.model;

public class Device {

	private String deviceId;
	private String description;
	
	public Device(String deviceId, String description) {
		this.deviceId = deviceId;
		this.description = description;
	}
	
	public String getId() {
		return deviceId;
	}
	
	public String getDescription() {
		return description;
	}
}
