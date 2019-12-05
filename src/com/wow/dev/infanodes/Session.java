package com.wow.dev.infanodes;

import java.util.ArrayList;
import java.util.Set;

public class Session extends InfaXMLNodes{

	public Session() {
		super();
	}
	
	
	public void validateSessionName(ArrayList<String> errorList) {
		String sessionName=map.get("SESSION.NAME");
		super.nullValidation("Session Name", sessionName, errorList);
		
		if(!sessionName.substring(0, 2).contentEquals("s_")) {
			errorList.add("Invalid Start of Session  Name. Session Name Should Start with 's_'");
		}
		
		String mappingName=map.get("SESSION.MAPPINGNAME");
		if(!sessionName.equals("s_"+mappingName)) {
			errorList.add("Invalid Session Name. Session Name ["+sessionName+"/"+mappingName+"] Should Same as Mapping Name with prefix s_");
		}
		
	}
	
	public void isSessionValid(ArrayList<String> errorList) {
		String isValid=map.get("SESSION.ISVALID");
		System.out.println("Validating Session isValid Option. [isValid=" + isValid + "]");
		if(!isValid.equals("YES")) {
			errorList.add("Session " + map.get("SESSION.NAME") + "is not valid. Please validate the session to find out the issue");
		}
	}
	
	public void validateSessionBackwardCompatible(ArrayList<String> errorList) {
		String SESSION_BACKWARD_COMPATIBLE=map.get("SESSION_ATTRIBUTE.Write Backward Compatible Session Log File");
		System.out.println("Validating Session Backward Compatible Option. [Backward Compatible=" + SESSION_BACKWARD_COMPATIBLE + "]");
		if(!SESSION_BACKWARD_COMPATIBLE.equals("YES")) {
			errorList.add("Backward Compatible is not enabled in the session - " + map.get("SESSION.NAME"));
		}		
	}
	
	
	public void validateSessionLogDirectory(ArrayList<String> errorList, String folderName) {
		String logDirectory=map.get("SESSION_ATTRIBUTE.Session Log File directory");
		logDirectory=logDirectory.replace("$PMSessionLogDir", "/infadata/Logs");
		System.out.println("Validating Sessin Log Directory. [Session Log Directory=" + logDirectory + "]");
		if(!logDirectory.equals("/infadata/Logs/" + folderName + "/SessLogs/")) {
			errorList.add("Session" + map.get("SESSION.NAME") + " Logs are not pointing to Project Folder. Session Logs should be written under /infadata/Logs/" + folderName + "/SessLogs/");
		}

		
	}
	
	public void validateSessionLog(ArrayList<String> errorList) {
		String logName=map.get("SESSION_ATTRIBUTE.Session Log File Name");
		System.out.println("Validating Session log Name. [Session Log Name=" + logName + "]");
		if(!logName.equals(map.get("SESSION.NAME")+".log")) {
			errorList.add("Session [" + map.get("SESSION.NAME") + "] log name should be same as session name.");
		}
	}
	
	public void validateStopOnErros(ArrayList<String> errorList) {
		Set<String> keys=map.keySet();
		for(String key:keys) {
			if(key.contains("Stop on errors")) {
				System.out.println("Validating Session Stop on erros... ["+ key +" = "+ map.get(key) + "] ");
				int stopOnErrors=Integer.parseInt(map.get(key));
				if(stopOnErrors==0) {
					errorList.add("Session [" + map.get("SESSION.NAME") + "] stop on error should not be 0");
				}
			}
		}
	}
	
	
	public void validateDTMBufferedSize(ArrayList<String> errorList) {
		
		Set<String> keys=map.keySet();
		for(String key:keys) {
			if(key.contains("DTM buffer size")) {
				System.out.println("Validating Session DTM Buffered Size... ["+ key +" = "+ map.get(key) + "] ");
				String DTMBufferedSize=map.get(key);
				
				if(!DTMBufferedSize.equals("Auto")) {
					errorList.add("Session [" + map.get("SESSION.NAME") + "] DTM Buffered Size should be Auto");
				}
			}
		}
	}
	
	// Function to identify any Override Queries used in mapping for source
		public void validateSQLQuery(ArrayList<String> errorList) {
			Set<String> keys=map.keySet();
			for(String key:keys) {
				if(key.contains("Sql Query")) {
					System.out.println("Validating MappingVariable SQL Overide Query... ["+ key +" ] ");
					String sqlOverride=map.get(key);
					
					if(!sqlOverride.equals("")) {
						errorList.add("Source SQL Override Query has been idenified in the Session [" + map.get("SESSION.NAME") + "].");
					}
				}
			}
		}
		
		// Funcion to identify any Override Queries used in mapping for lookup
		public void validateOverrideQuery(ArrayList<String> errorList) {
			Set<String> keys=map.keySet();
			for(String key:keys) {
				if(key.contains("Sql Override")) {
					System.out.println("Validating MappingVariable SQL Overide Query... ["+ key +"] ");
					String sqlOverride=map.get(key);
					if(!sqlOverride.equals("")) {
						errorList.add("LKP Override Query has been idenified in the Session [" + map.get("SESSION.NAME") + "].");
					}
				}
			}
		}
	
	
	@Override
	public void validate(ArrayList<String> errorList, String folderName) {
		validateSessionName(errorList);
		isSessionValid(errorList);
		validateSessionBackwardCompatible(errorList);
		validateSessionLogDirectory(errorList, folderName);
		validateSessionLog(errorList);
		validateStopOnErros(errorList);
		validateDTMBufferedSize(errorList);
		validateSQLQuery(errorList);
		validateOverrideQuery(errorList);
		
	}

}
