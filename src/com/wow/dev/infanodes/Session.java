package com.wow.dev.infanodes;

import java.util.Map;
import java.util.Set;

public class Session{
	
	//Session Property Attributes
	private Map<String, String> map;
	private String sessionName;
	private String mappingName;
	private String sessionIsValid;
	private String SESSION_BACKWARD_COMPATIBLE;
	private String sessionLogDirectory;
	private String sessionLogName;
	private String folderName;
	
	//Session Validation Attributes
	private String sessionNameValidation;
	private String sessionIsValidValidation;
	private String SESSION_BACKWARD_COMPATIBLEValidation;
	private String sessionLogDirectoryValidation;
	private String sessionLogNameValidation;
	private String sessionStopOnErrosValidation;
	private String sessionDTMBufferedSizeValidation;
	private String sessionSqlQueryValidation;
	private String sessionSqlOverrideValidation;
	
	
	
	public Session(Map<String,String> map,String folderName) {
		this.map=map;
		this.folderName=folderName;
	}
	
	public boolean validateSessionName(Map<String,String> validationList) {
		sessionName=map.get("SESSION.NAME");
		boolean tmp=true;
//		super.nullValidation("Session Name", sessionName, errorList);
		System.out.println("Validating session name. [SessionName="+ sessionName +"]");
		if(!sessionName.substring(0, 2).contentEquals("s_")) {
			validationList.put("SESSION.NAME", "Invalid Start of Session Name. Session ["+ sessionName +"] Name Should Start with 's_'");
			tmp=false;
		}
		mappingName=map.get("SESSION.MAPPINGNAME");
		if(!sessionName.equals("s_"+mappingName)) {
			validationList.put("SESSION.NAME","Invalid Session Name. Session Name ["+sessionName+"/"+mappingName+"] Should Same as Mapping Name with prefix s_");
			tmp=false;
		}
		
		return tmp;
	}
	
	public boolean  isSessionValid(Map<String,String> validationList) {
		sessionIsValid=map.get("SESSION.ISVALID");
		System.out.println("Validating Session isValid Option. [isValid=" + sessionIsValid + "]");
		if(!sessionIsValid.equals("YES")) {
			validationList.put("SESSION.ISVALID","Session [" + map.get("SESSION.NAME") + "]is not valid. Please validate the session to find out the issue");
			return false;
		}
		return true;
	}
	
	public boolean validateSessionBackwardCompatible(Map<String,String> validationList) {
		SESSION_BACKWARD_COMPATIBLE=map.get("SESSION_ATTRIBUTE.Write Backward Compatible Session Log File");
		System.out.println("Validating Session Backward Compatible Option. [Backward Compatible=" + SESSION_BACKWARD_COMPATIBLE + "]");
		if(!SESSION_BACKWARD_COMPATIBLE.equals("YES")) {
			validationList.put("SESSION_ATTRIBUTE.Write Backward Compatible Session Log File","Backward Compatible is not enabled in the session [" + map.get("SESSION.NAME") + "]");
			return false;
		}		
		return true;
	}
	
	
	public boolean validateSessionLogDirectory(Map<String,String> validationList, String folderName) {
		sessionLogDirectory=map.get("SESSION_ATTRIBUTE.Session Log File directory");
		sessionLogDirectory=sessionLogDirectory.replace("$PMSessionLogDir", "/infadata/Logs");
		System.out.println("Validating Sessin Log Directory. [Session Log Directory=" + sessionLogDirectory + "]");
		if(!sessionLogDirectory.equals("/infadata/Logs/" + folderName + "/SessLogs/")) {
			validationList.put("SESSION_ATTRIBUTE.Session Log File directory","Session [" + map.get("SESSION.NAME") + "] Logs are not pointing to Project Folder. Session Logs should be written under /infadata/Logs/" + folderName + "/SessLogs/");
			return false;
		}
		return true;
	}
	
	public boolean validateSessionLog(Map<String,String> validationList) {
		sessionLogName=map.get("SESSION_ATTRIBUTE.Session Log File Name");
		System.out.println("Validating Session log Name. [Session Log Name=" + sessionLogName + "]");
		if(!sessionLogName.equals(map.get("SESSION.NAME")+".log")) {
			validationList.put("SESSION_ATTRIBUTE.Session Log File Name","Session [" + map.get("SESSION.NAME") + "] log name should be same as session name.");
			return false;
		}
		return true;
	}
	
	public boolean validateStopOnErros(Map<String,String> validationList) {
		Set<String> keys=map.keySet();
		for(String key:keys) {
			if(key.contains("Stop on errors")) {
				System.out.println("Validating Session Stop on erros... ["+ key +" = "+ map.get(key) + "] ");
				int stopOnErrors=Integer.parseInt(map.get(key));
				if(stopOnErrors==0) {
					validationList.put("SESSION.Stop on Errros","Session [" + map.get("SESSION.NAME") + "] stop on error should not be 0");
					return false;
				}
			}
		}
		return true;
	}
	
	
	public boolean validateDTMBufferedSize(Map<String,String> validationList) {
		
		Set<String> keys=map.keySet();
		for(String key:keys) {
			if(key.contains("DTM buffer size")) {
				System.out.println("Validating Session DTM Buffer Size... ["+ key +" = "+ map.get(key) + "] ");
				String DTMBufferedSize=map.get(key);
				
				if(!DTMBufferedSize.equals("Auto")) {
					validationList.put("SESSION.DTM buffer size","Session [" + map.get("SESSION.NAME") + "] DTM Buffer Size should be Auto");
					return false;
				}
			}
		}
		return true;
	}
	
	// Function to identify any Override Queries used in mapping for source
		public boolean validateSQLQuery(Map<String,String> validationList) {
			Set<String> keys=map.keySet();
			for(String key:keys) {
				if(key.contains("Sql Query")) {
					System.out.println("Validating MappingVariable SQL Overide Query... ["+ key +" ] ");
					String sqlOverride=map.get(key);
					
					if(!sqlOverride.equals("")) {
						validationList.put("SESSION.SQL Query","Source SQL Override Query has been idenified in the Session [" + map.get("SESSION.NAME") + "].");
						return false;
					}
				}
			}
			return true;
		}
		
		// Funcion to identify any Override Queries used in mapping for lookup
		public boolean validateOverrideQuery(Map<String,String> validationList) {
			Set<String> keys=map.keySet();
			for(String key:keys) {
				if(key.contains("Sql Override")) {
					System.out.println("Validating MappingVariable SQL Overide Query... ["+ key +"] ");
					String sqlOverride=map.get(key);
					if(!sqlOverride.equals("")) {
						validationList.put("SESSION.SQL override","LKP Override Query has been idenified in the Session [" + map.get("SESSION.NAME") + "].");
						return false;
					}
				}
			}
			return true;
		}
	
	
	public void validate(Map<String,String> validationList) {
		sessionNameValidation=validateSessionName(validationList)?"PASS":"FAIL";
		sessionIsValidValidation=isSessionValid(validationList)?"PASS":"FAIL";
		SESSION_BACKWARD_COMPATIBLEValidation=validateSessionBackwardCompatible(validationList)?"PASS":"FAIL";
		sessionLogDirectoryValidation=validateSessionLogDirectory(validationList, folderName)?"PASS":"FAIL";
		sessionLogNameValidation=validateSessionLog(validationList)?"PASS":"FAIL";
		sessionStopOnErrosValidation=validateStopOnErros(validationList)?"PASS":"WARNING";
		sessionDTMBufferedSizeValidation=validateDTMBufferedSize(validationList)?"PASS":"WARNING";
		sessionSqlQueryValidation=validateSQLQuery(validationList)?"PASS":"WARNING";
		sessionSqlOverrideValidation=validateOverrideQuery(validationList)?"PASS":"WARNING";
		
	}
	
	//Getter
	public Map<String, String> getMap() {
		return map;
	}

	public String getSessionName() {
		return sessionName;
	}

	public String getMappingName() {
		return mappingName;
	}

	public String getSessionIsValid() {
		return sessionIsValid;
	}

	public String getSESSION_BACKWARD_COMPATIBLE() {
		return SESSION_BACKWARD_COMPATIBLE;
	}

	public String getSessionLogDirectory() {
		return sessionLogDirectory;
	}

	public String getSessionLogName() {
		return sessionLogName;
	}

	public String getSessionNameValidation() {
		return sessionNameValidation;
	}

	public String getSessionIsValidValidation() {
		return sessionIsValidValidation;
	}

	public String getSESSION_BACKWARD_COMPATIBLEValidation() {
		return SESSION_BACKWARD_COMPATIBLEValidation;
	}

	public String getSessionLogDirectoryValidation() {
		return sessionLogDirectoryValidation;
	}

	public String getSessionLogNameValidation() {
		return sessionLogNameValidation;
	}

	public String getSessionStopOnErrosValidation() {
		return sessionStopOnErrosValidation;
	}

	public String getSessionDTMBufferedSizeValidation() {
		return sessionDTMBufferedSizeValidation;
	}

	public String getSessionSqlQueryValidation() {
		return sessionSqlQueryValidation;
	}

	public String getSessionSqlOverrideValidation() {
		return sessionSqlOverrideValidation;
	}
	


}
