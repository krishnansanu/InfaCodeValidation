package com.wow.dev.infanodes;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Session{
	
	private Map<String, String> map;
	private String sessionName;
	private String folderName;
	private SessionTaskInstance[] taskInstance;
	
	
	Map<String, String> sessionValidationResults;
	
	
	public Session(Map<String,String> map,String folderName,SessionTaskInstance[] taskInstances) {
		this.map=map;
		this.folderName=folderName;
		this.taskInstance=taskInstances;
		sessionValidationResults=new LinkedHashMap<String, String>();
	}
	
	public boolean validateSessionName(Map<String,String> validationList,int i) {
		sessionName=map.get("SESSION.NAME");
		boolean tmp=true;
		if(!sessionName.substring(0, 2).contentEquals("s_")) {
			validationList.put(i+"_SESSION.NAME", "Invalid Start of Session Name. Session ["+ sessionName +"] Name Should Start with 's_'");
			tmp=false;
		}
		String mappingName=map.get("SESSION.MAPPINGNAME");
		if(!sessionName.equals("s_"+mappingName)) {
			validationList.put(i+"_SESSION.MAPPINGNAME","Invalid Session Name. Session Name ["+sessionName+"/"+mappingName+"] Should Same as Mapping Name with prefix s_");
			tmp=false;
		}
		
		return tmp;
	}
	
	public boolean  isSessionValid(Map<String,String> validationList,int i) {
		String sessionIsValid=map.get("SESSION.ISVALID");
		if(!sessionIsValid.equals("YES")) {
			validationList.put(i+"_SESSION.ISVALID","Session [" + map.get("SESSION.NAME") + "]is not valid. Please validate the session to find out the issue");
			return false;
		}
		return true;
	}
	
	public String  validateIsResuableSession() {
		return map.get("SESSION.REUSABLE");
	}
	
	public boolean validateSessionBackwardCompatible(Map<String,String> validationList,int i) {
		String SESSION_BACKWARD_COMPATIBLE=map.get("SESSION_ATTRIBUTE.Write Backward Compatible Session Log File");
		if(!SESSION_BACKWARD_COMPATIBLE.equals("YES")) {
			validationList.put(i+"_SESSION_ATTRIBUTE.Write Backward Compatible Session Log File","Backward Compatible is not enabled in the session [" + map.get("SESSION.NAME") + "]");
			return false;
		}		
		return true;
	}
	
	
	public boolean validateSessionLogDirectory(Map<String,String> validationList, int i) {
		String sessionLogDirectory=map.get("SESSION_ATTRIBUTE.Session Log File directory");
		if(!sessionLogDirectory.contains(folderName)) {
			validationList.put(i+"_SESSION_ATTRIBUTE.Session Log File directory","Session [" + map.get("SESSION.NAME") + "] Log directory ["+sessionLogDirectory+"] is invalid");
			return false;
		}
		return true;
	}
	
	public boolean validateSessionLog(Map<String,String> validationList,int i) {
		String sessionLogName=map.get("SESSION_ATTRIBUTE.Session Log File Name");
		if(!sessionLogName.equals(map.get("SESSION.NAME")+".log")) {
			validationList.put(i+"_SESSION_ATTRIBUTE.Session Log File Name","Session [" + map.get("SESSION.NAME") + "] log name should be same as session name.");
			return false;
		}
		return true;
	}
	
	public boolean validateStopOnErros(Map<String,String> validationList,int i) {
		Set<String> keys=map.keySet();
		for(String key:keys) {
			if(key.contains("Stop on errors")) {
				int stopOnErrors=Integer.parseInt(map.get(key));
				if(stopOnErrors==0) {
					validationList.put(i+"_SESSION.Stop on Errros","Session [" + map.get("SESSION.NAME") + "] stop on error should not be 0");
					return false;
				}
			}
		}
		return true;
	}
	
	
	public boolean validateDTMBufferedSize(Map<String,String> validationList,int i) {
		
		Set<String> keys=map.keySet();
		for(String key:keys) {
			if(key.contains("DTM buffer size")) {
				String DTMBufferedSize=map.get(key);
				
				if(!DTMBufferedSize.equals("Auto")) {
					validationList.put(i+"_SESSION.DTM buffer size","Session [" + map.get("SESSION.NAME") + "] DTM Buffer Size should be Auto");
					return false;
				}
			}
		}
		return true;
	}
	
	// Function to identify any Override Queries used in mapping for source
		public boolean validateSQLQuery(Map<String,String> validationList,int i) {
			Set<String> keys=map.keySet();
			for(String key:keys) {
				if(key.contains("Sql Query")) {
					String sqlOverride=map.get(key);
					
					if(!sqlOverride.equals("")) {
						validationList.put(i+"_SESSION.SQL Query","Source SQL Override Query has been idenified in the Session [" + map.get("SESSION.NAME") + "].");
						return false;
					}
				}
			}
			return true;
		}
		
		// Funcion to identify any Override Queries used in mapping for lookup
		public boolean validateOverrideQuery(Map<String,String> validationList,int i) {
			Set<String> keys=map.keySet();
			for(String key:keys) {
				if(key.contains("Sql Override")) {
					String sqlOverride=map.get(key);
					if(!sqlOverride.equals("")) {
						validationList.put(i+"_SESSION.SQL override","LKP Override Query has been idenified in the Session [" + map.get("SESSION.NAME") + "].");
						return false;
					}
				}
			}
			return true;
		}
		
		public boolean validateOverrideTacing(Map<String,String> validationList,int i) {
			Set<String> keys=map.keySet();
			for(String key:keys) {
				if(key.contains("Override tracing")) {
					String overrideTracing=map.get(key);
					if(!(overrideTracing.equalsIgnoreCase("none"))) {
						validationList.put(i+"_SESSION.Override tracing","Session [" + map.get("SESSION.NAME") + "] Override tracing option set to "+ overrideTracing);
						return false;
					}
				}
			}
			return true;
			
		}
	
	
		public boolean validateFAIL_PARENT_IF_INSTANCE_DID_NOT_RUN(Map<String,String> validationList,int i) {
			String FAIL_PARENT_IF_INSTANCE_DID_NOT_RUN=null;
			for(SessionTaskInstance sessionTaskInstance:taskInstance) {
				if(sessionTaskInstance.getTaskName().equals(sessionName)) {
					FAIL_PARENT_IF_INSTANCE_DID_NOT_RUN=sessionTaskInstance.getFAIL_PARENT_IF_INSTANCE_DID_NOT_RUN();
					if(FAIL_PARENT_IF_INSTANCE_DID_NOT_RUN.equalsIgnoreCase("no")) {
						validationList.put(i+"_SESSION.FAIL_PARENT_IF_INSTANCE_DID_NOT_RUN","Session [" + map.get("SESSION.NAME") + "] FAIL_PARENT_IF_INSTANCE_DID_NOT_RUN option is not enabled");
						return false;
					}
				}
				
			}
			return true;
		}
		
		public boolean validateFAILPARENT_IF_INSTANCE_FAILS(Map<String,String> validationList,int i) {
			String FAIL_PARENT_IF_INSTANCE_FAILS=null;
			for(SessionTaskInstance sessionTaskInstance:taskInstance) {
				if(sessionTaskInstance.getTaskName().equals(sessionName)) {
					FAIL_PARENT_IF_INSTANCE_FAILS=sessionTaskInstance.getFAIL_PARENT_IF_INSTANCE_FAILS();
					if(FAIL_PARENT_IF_INSTANCE_FAILS.equalsIgnoreCase("no")) {
						validationList.put(i+"_SESSION.FAIL_PARENT_IF_INSTANCE_FAIL","Session [" + map.get("SESSION.NAME") + "] FAIL_PARENT_IF_INSTANCE_FAIL option is not enabled");
						return false;
					}
				}
			}
			return true;
		}
		
	public void validate(Map<String,String> validationList,int i) {
		sessionValidationResults.put("SESSION_NAME_VALIDATION", validateSessionName(validationList,i)?"PASS":"FAIL");
		System.out.println("Validating Session - " + sessionName);
		sessionValidationResults.put("SESSION_IS_VALID", isSessionValid(validationList,i)?"PASS":"FAIL");
		sessionValidationResults.put("SESSION_IS_RESULABLE_VALIDATION", validateIsResuableSession());
		sessionValidationResults.put("SESSION_BACKWARD_COMPATIBLE_VALIBATION", validateSessionBackwardCompatible(validationList,i)?"PASS":"FAIL");
		sessionValidationResults.put("SESSION_LOG_DIR_VALIDATION", validateSessionLogDirectory(validationList,i)?"PASS":"FAIL");
		sessionValidationResults.put("SESSION_LOG_VALIDATION", validateSessionLog(validationList,i)?"PASS":"FAIL");
		sessionValidationResults.put("SESSION_STOP_ON_ERROS_VALIDATION", validateStopOnErros(validationList,i)?"PASS":"WARNING");
		sessionValidationResults.put("SESSION_DTM_BUFFERED_SIZE_VALIDATION", validateDTMBufferedSize(validationList,i)?"PASS":"WARNING");
		sessionValidationResults.put("SESSION_SQL_QUERY_VALIDATION", validateSQLQuery(validationList,i)?"PASS":"WARNING");
		sessionValidationResults.put("SESSION_OVERRIDE_VALIDATION", validateOverrideQuery(validationList,i)?"PASS":"WARNING");
		sessionValidationResults.put("SESSION_OVERRIDE_TRACING_VALIDATION", validateOverrideTacing(validationList,i)?"PASS":"FAIL");
		sessionValidationResults.put("SESSION_FAIL_PARENT_IF_INSTANCE_DID_NOT_RUN_VALIDATION",validateFAIL_PARENT_IF_INSTANCE_DID_NOT_RUN(validationList,i)?"PASS":"FAIL");
		sessionValidationResults.put("SESSION_FAIL_PARENT_IF_INSTANCE_FAILS_VALIDATION",validateFAILPARENT_IF_INSTANCE_FAILS(validationList,i)?"PASS":"FAIL");
	}
	
	//Getter
	public Map<String, String> getMap() {
		return map;
	}

	public String getSessionName() {
		return sessionName;
	}

	public Map<String, String> getValidationResults() {
		return sessionValidationResults;
	}

}
