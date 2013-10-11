package com.patientkeeper.flower.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.apache.regexp.RE;

public class UploadService {

	private static Logger log = Logger.getLogger(UploadService.class);
	
	private static int limit = 500;
	private static RE regex = new RE("^([^:]+):<?([^:]+):?(:?[0-9]+)? \\(.* = ([0-9].*) ms,.*= ([0-9].*) ms\\)");
	
	private boolean includeSize = false;
	
	public String getJson(File file, boolean includeSize) throws IOException {
		this.includeSize = true;
		return getJson(file);
	}
	
	public String getJson(File file) throws IOException {		
		
		log.info("includeSize="+includeSize);
		
		StringBuilder json = new StringBuilder();
		FileInputStream inputStream = null;
		BufferedReader reader = null;
		
		if(!file.isDirectory()) {
			try {
				inputStream = new FileInputStream(file);
	            reader = new BufferedReader(new InputStreamReader(inputStream));
	            
	            json.append("{ \"name\": \"thread\", \"children\": [ {");
	            	            
	            int currLevel = 0;
	            int prevLevel = currLevel;
	            
				String line;     
				while ((line = reader.readLine()) != null) {
					if(line.length() <= limit) {
						if(regex.match(line.trim()) && includeSize) {
							String method = regex.getParen(2);
							currLevel = line.length() - line.trim().length();
							if (currLevel == 0) {
								json.append(" \"name\": \"" + method + "\", \"size\": " + regex.getParen(4) + " ");
							} else if (currLevel == prevLevel) {
								json.append("}, { \"name\": \"" + method + "\", \"size\": " + regex.getParen(4) + " ");
							} else if (currLevel > prevLevel) {
								json.append(", \"children\": [ { \"name\": \"" + method + "\", \"size\": " + regex.getParen(4) + " ");
							} else if (currLevel < prevLevel) {
								int diff = prevLevel - currLevel;
								while (diff > 0) {
									json.append("}]");
									diff--;
								}
								json.append("},{ \"name\": \"" + method + "\", \"size\": " + regex.getParen(4) + " ");
							}
							prevLevel = currLevel;
						} else if (regex.match(line.trim())) {
							String method = regex.getParen(2);
							currLevel = line.length() - line.trim().length();
							if (currLevel == 0) {
								json.append(" \"name\": \"" + method + " (" + regex.getParen(4) +  ")\" ");								
							} else if (currLevel == prevLevel) {
								json.append("}, { \"name\": \"" + method + " (" + regex.getParen(4) +  ")\" ");								
							} else if (currLevel > prevLevel) {
								json.append(", \"children\": [ { \"name\": \"" + method + " (" + regex.getParen(4) +  ")\" ");
							} else if (currLevel < prevLevel) {
								int diff = prevLevel - currLevel;
								while (diff > 0) {
									json.append("}]");
									diff--;
								}
								json.append("},{ \"name\": \"" + method + " (" + regex.getParen(4) +  ")\" ");
							}
							prevLevel = currLevel;
						}
					} 
				continue;
				}
				while (currLevel >= 0) {
					json.append("}]");
					currLevel--;
				}
				json.append("}");
			} catch (IOException e) {
				log.error("Error parsring file: " , e);
			} finally {
				if(inputStream != null) {
					inputStream.close();
				}
				if(reader != null) {
					reader.close();
				}
			}
		} else {
			log.error("File given to parseFile appears to be a directory: " + file.getAbsolutePath());
		}
		
		return json.toString();
	}
}
