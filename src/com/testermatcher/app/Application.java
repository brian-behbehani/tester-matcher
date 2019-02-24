package com.testermatcher.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import com.testermatcher.model.Bug;
import com.testermatcher.model.Device;
import com.testermatcher.model.Tester;
import com.testermatcher.model.TesterDevice;

public class Application {

	public static void main(String[] args) {

		Map<String,Bug> bugMap = loadBugs();
		Map<String, Tester> testerMap = loadTesters();
		Map<String, Device> deviceMap = loadDevices();
		List<TesterDevice> testerDeviceList = loadTesterDevices();

		addDevicesToTesters(testerMap, testerDeviceList, deviceMap);
		addExperienceToTesters(testerMap, bugMap, deviceMap);

		List<Tester> testerList = new ArrayList<Tester>(testerMap.values());
		
		Scanner in = new Scanner(System.in);
		while(true) {
			System.out.println("Please enter the search criteria. For multiple values, please separate using a comma. To choose all, enter ALL");

			System.out.println("Countries?");
			String countriesString = in.nextLine();
			String[] countryStringArray = countriesString.split(",");
			List<String> countries = new ArrayList<String>();
			for(String country : countryStringArray) {
				countries.add(country.trim());
			}
			
			System.out.println("Phones?");
			String phonesString = in.nextLine();
			String[] phoneStringArray = phonesString.split(",");
			List<String> phones = new ArrayList<String>();
			for(String phone : phoneStringArray) {
				phones.add(phone.trim());
			}
			
			System.out.println();
			
			Map<Tester, Integer> matchingTesters = findMatchingTesters(countries, phones, testerList);
			printResults(countries, phones, matchingTesters);
		}

	}

	/**
	 * Perform the matching of testers to country and device
	 * 
	 * @param country
	 * @param phoneDescription
	 * @param testerMap
	 * @return a map of matching testers as Tester,Experience
	 */
	private static Map<Tester, Integer> findMatchingTesters(List<String> countries, List<String> phones, List<Tester> testers) {
		Map<Tester, Integer> testerResultMap = new HashMap<Tester, Integer>();

		for(Tester tester : testers) {

			if(countries.contains(tester.getCountry()) || countries.contains("ALL")) {
				List<Device> testerDevices = new ArrayList<Device>(tester.getDevices().values());

				for(Device device : testerDevices) {
					
					if(phones.contains(device.getDescription()) || phones.contains("ALL")) {

						boolean testerInResultAlready = testerResultMap.containsKey(tester);
						
						Integer additionalExperience = tester.getExperience().get(device);
						
						if(testerInResultAlready) {
							Integer relatedDeviceExperience = testerResultMap.get(tester);
							Integer totalExperience = additionalExperience + relatedDeviceExperience;
							testerResultMap.put(tester, ((additionalExperience!= null)?totalExperience:relatedDeviceExperience));
						} else {
							testerResultMap.put(tester, ( (additionalExperience != null) ? additionalExperience:0));
						}
					}
				}
			}
		}

		return testerResultMap;
	}

	/**
	 * Format and print the test matching results to standard out
	 * 
	 * @param country
	 * @param phone
	 * @param testerResultMap
	 */
	private static void printResults(List<String> country, List<String> phone, Map<Tester, Integer> testerResultMap) {

		List<Entry<Tester, Integer>> list = new ArrayList<>(testerResultMap.entrySet());
		list.sort(Entry.comparingByValue(Collections.reverseOrder()));

		Map<Tester, Integer> result = new LinkedHashMap<>();
		for (Entry<Tester, Integer> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}

		if(result.isEmpty()) {
			System.out.println("There were no matching testers found with: " + phone + " in: " + country);
		} else {
			System.out.println("The following matching testers were found with: " + phone + " in: " + country);
			StringBuilder resultString = new StringBuilder();
			for(Tester tester : result.keySet()) {
				Integer experience = result.get(tester);

				resultString.append(tester.getFirstName());
				resultString.append(" ");
				resultString.append(tester.getLastName());
				resultString.append("=>");
				resultString.append(experience);
				resultString.append(", Last login: ");
				resultString.append(tester.getLastLogin());
				resultString.append("\n");
			}
			System.out.println(resultString.toString());
		}

	}

	/**
	 * Add the device data to the testers by combining the tester and device data sets
	 * 
	 * @param testerMap
	 * @param testerDeviceList
	 * @param deviceMap
	 */
	private static void addDevicesToTesters(Map<String, Tester> testerMap, List<TesterDevice> testerDeviceList, Map<String, Device> deviceMap) {

		for (TesterDevice testerDeviceEntry : testerDeviceList) { 

			String testerId = testerDeviceEntry.getTesterId();
			String deviceId = testerDeviceEntry.getDeviceId();

			Tester tester = testerMap.get(testerId);
			Device device = deviceMap.get(deviceId);
			if(tester != null && device != null) {
				tester.addDevice(device);
			}

		}

	}

	/**
	 * Add experience data into our testers utilizing the tester, bug, and device data sets
	 * 
	 * @param testerMap
	 * @param bugMap
	 * @param deviceMap
	 */
	private static void addExperienceToTesters(Map<String, Tester> testerMap, Map<String, Bug> bugMap, Map<String, Device> deviceMap) {

		for(Bug bug : bugMap.values()) {

			String testerId = bug.getTesterId();
			String deviceId = bug.getDeviceId();
			Tester tester = testerMap.get(testerId);
			Device device = deviceMap.get(deviceId);

			if(tester != null && device != null) {
				tester.addExperience(device);
			}
		}
	}

	/**
	 * Load device data from csv data file
	 * 
	 * @return map of devices, keyed off deviceId
	 */
	private static Map<String, Device> loadDevices() {
		Map<String, Device> deviceMap = new HashMap<String, Device>();
		InputStream in;
		try {
			
			in = ClassLoader.getSystemClassLoader().getResourceAsStream("data/devices.csv");
			Reader reader = new InputStreamReader(in);

			Iterable<CSVRecord> records;
			records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);

			for (CSVRecord record : records) {
				String id = record.get("deviceId");
				String description = record.get("description");

				Device device = new Device(id, description);
				deviceMap.put(id, device);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return deviceMap;
	}

	/**
	 * Load list of tester devices from csv data file
	 * 
	 * @return List of TesterDevice.
	 */
	private static List<TesterDevice> loadTesterDevices() {
		List<TesterDevice> testerDeviceList = new ArrayList<TesterDevice>();
		try {
			
			InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream("data/tester_device.csv");
			Reader reader = new InputStreamReader(in);

			Iterable<CSVRecord> records;
			records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);

			for (CSVRecord record : records) {
				String deviceId = record.get("deviceId");
				String testerId = record.get("testerId");

				TesterDevice testerDevice = new TesterDevice(testerId, deviceId);
				testerDeviceList.add(testerDevice);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return testerDeviceList;
	}

	/**
	 * Load tester data in from csv data file
	 * 
	 * @return a map of Testers, keyed off testerId
	 */
	private static Map<String, Tester> loadTesters() {
		Map<String, Tester> testerMap = new HashMap<String, Tester>();
		try {
			
			InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream("data/testers.csv");
			Reader reader = new InputStreamReader(in);
			
			Iterable<CSVRecord> records;
			records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);

			for (CSVRecord record : records) {
				String id = record.get("testerId");
				String firstName = record.get("firstName");
				String lastName = record.get("lastName");
				String country = record.get("country");
				String lastLogin = record.get("lastLogin");

				Tester tester = new Tester(id, firstName, lastName, country, lastLogin);
				testerMap.put(id, tester);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return testerMap;
	}

	/**
	 * Load bug data from csv file
	 * 
	 * @return a map of Bugs, keyed off bugId
	 */
	private static Map<String, Bug> loadBugs() {

		Map<String, Bug> bugMap = new HashMap<String, Bug>();
		try {
			InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream("data/bugs.csv");
			Reader reader = new InputStreamReader(in);

			Iterable<CSVRecord> records;
			records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);

			for (CSVRecord record : records) {
				String id = record.get("bugId");
				String deviceId = record.get("deviceId");
				String testerId = record.get("testerId");
				Bug bug = new Bug(id, testerId, deviceId);
				bugMap.put(id, bug);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return bugMap;
	}

}
