package com.patientkeeper.flower.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.apache.log4j.Logger;
import org.apache.regexp.RE;

public class UploadService {

	private static Logger log = Logger.getLogger(UploadService.class);
	
	private static int limit = 500;	
	private static RE timingPattern = new RE("^([^:]+):<?([^:]+):?(:?[0-9]+)? \\(.* = ([0-9].*) ms,.*= ([0-9].*) ms\\)");
	
	public String getJson(File file) throws IOException {		
		
		StringBuilder json = new StringBuilder();
		FileInputStream inputStream = null;
		BufferedReader reader = null;
		
		if(!file.isDirectory()) {
			try {
				inputStream = new FileInputStream(file);
	            reader = new BufferedReader(new InputStreamReader(inputStream));
	            
	            json.append("{ \"name\": \"thread\", \"children\": [ {");
	            	            
	            int level = 0;
	            int prevLevel = level;
	           
	            
				String line;     
				while ((line = reader.readLine()) != null) {
					if(line.length() <= limit) {
						if(timingPattern.match(line.trim())) {
							String lePackage = timingPattern.getParen(1);
							String leClass = lePackage.substring(lePackage.lastIndexOf(".")+1, lePackage.length());
							String leMethod = timingPattern.getParen(2);
							level = line.length() - line.trim().length();
							
							log.info(lePackage + " " + leClass + " " + leMethod + " " + level + " " + prevLevel);
							if (level == 0) {
								json.append(" \"name\": \"" + leMethod + " (" + timingPattern.getParen(4) +  ")\" ");
							} else if (level == prevLevel) {
								json.append("}, { \"name\": \"" + leMethod + " (" + timingPattern.getParen(4) +  ")\" ");
							} else if (level > prevLevel) {
								json.append(", \"children\": [ { \"name\": \"" + leMethod + " (" + timingPattern.getParen(4) +  ")\" ");
							} else if (level < prevLevel) {
								int diff = prevLevel - level;
								while (diff > 0) {
									json.append("}]");
									diff--;
								}
								json.append("},{ \"name\": \"" + leMethod + " (" + timingPattern.getParen(4) +  ")\" ");
							}
							prevLevel = level;
						}
					} 
				continue;
				}
				while (level >= 0) {
					json.append("}]");
					level--;
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
		
		log.info(json.toString());
		
		return json.toString();
	}
}
