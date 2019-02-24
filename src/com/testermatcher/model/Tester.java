package com.testermatcher.model;

import java.util.HashMap;
import java.util.Map;

public class Tester {

	private String testerId;
	private String firstName;
	private String lastName;
	private String country;
	private String lastLogin;
	private Map <String, Device> devices;
	private Map <Device, Integer> experience;
	
	public Tester(String testerId, String firstName, String lastName, String country, String lastLogin) {
		this.testerId = testerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.country = country;
		this.lastLogin = lastLogin;
		this.devices = new HashMap<String, Device>();
		this.experience = new HashMap<Device, Integer>();
	}

	public String getTesterId() {
		return testerId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getCountry() {
		return country;
	}
	
	public String getLastLogin() {
		return lastLogin;
	}
	
	public Map<String, Device> getDevices() {
		return devices;
	}
	
	public Map<Device, Integer> getExperience() {
		return experience;
	}
	
	public void addExperience(Device device) {
		Integer currentDeviceExperience = experience.get(device);
		
		if(currentDeviceExperience != null) {
			experience.put(device, currentDeviceExperience.intValue()+1);
		} else {
			experience.put(device, new Integer(1));
		}
	}
	
	public void addDevice(Device device) {
		devices.put(device.getId(), device);
	}
	
}
