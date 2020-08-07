package com.wow.dev.infanodes;

import java.util.Map;
import java.util.Set;

public class Mapping{
	
	// Mapping Properties Attribute
	private Map<String, String> map;
	private String mappingName;
	private String isValid;
	private String folderName;
	
	// Mapping Validation Attribute
	private String mappingNameValidation;
	private String mappingIsValidValidation;
	private String mappingVariableNameValidation;
	private String mappingSQLQueryValidation;
	private String mappingSQLOverrideValidation;
	
	public Mapping(Map<String, String> map,String folderName) {
		this.map=map;
		this.folderName=folderName;
	}
	
	
	public boolean  validateMappingName(Map<String,String> validationList) {
		mappingName=map.get("MAPPING.NAME");
		
		if(!mappingName.substring(0, 2).contentEquals("m_")) {
			validationList.put("MAPPING.NAME","Invalid Start of Mapping Name [" + map.get("MAPPING.NAME") + "]. Mapping Name Should Start with 'm_'");
			return false;
		}
		return true;
	}
	
	public boolean isMappingvalid(Map<String,String> validationList) {
		isValid=map.get("MAPPING.ISVALID");
		System.out.println("Validating if Mapping is valid... [Mapping IsValid=" + isValid + "] ");
		
		if(!isValid.equals("YES")) {
			validationList.put("MAPPING.ISVALID","Mapping "+ map.get("MAPPING.NAME")  +" is not valid. Please Validate the mapping to fix the issues.");
			return false;
		}
		return true;
	}

	
	public boolean validateMappingVariableName(Map<String,String> validationList) {
		Set<String> keys=map.keySet();
		for(String key:keys) {
			if(key.contains("MAPPINGVARIABLE.NAME")) {
				System.out.println("Validating MappingVariable Name... ["+ key +" = "+ map.get(key) + "] ");
				String mappingVariableName=map.get(key);
				if(!mappingVariableName.substring(0, 2).equals("$$")) {
					validationList.put("MAPPINGVARIABLE.NAME","Invalid MappingVariable Name - " + mappingVariableName + " on [" + map.get("MAPPING.NAME") + "]. MappingVariable should be suffixed with $$");
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
					validationList.put("MAPPING.Sql Query","Source SQL Override Query has been idenified in the mapping - [" + map.get("MAPPING.NAME") + "].");
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
					validationList.put("MAPPING.SQL Override","LKP Override Query has been idenified in the mapping - [" + map.get("MAPPING.NAME") + "].");
					return false;
				}
			}
		}
		return true;
	}
	
	public void validate(Map<String,String> validationList) {
		mappingNameValidation=validateMappingName(validationList)?"PASS":"FAIL";
		mappingIsValidValidation=isMappingvalid(validationList)?"PASS":"FAIL";
		mappingVariableNameValidation=validateMappingVariableName(validationList)?"PASS":"FAIL";
		mappingSQLQueryValidation=validateSQLQuery(validationList)?"PASS":"WARNING";
		mappingSQLOverrideValidation=validateOverrideQuery(validationList)?"PASS":"WARNING";
	}
	
	public void mappingValidationResults() {
		System.out.println(mappingName);
		System.out.println("\tMapping Naming Standards\t\t\t: " + mappingNameValidation);
		System.out.println("\tIs Mapping Valid\t\t\t\t: " + mappingIsValidValidation);
		System.out.println("\tMapping  variables Standards\t\t\t: " + mappingVariableNameValidation);
		System.out.println("\tMapping SQL Query Validation\t\t\t: " + mappingSQLQueryValidation);
		System.out.println("\tMapping SQL Override Validation\t\t\t: " + mappingSQLOverrideValidation);
	}


	public Map<String, String> getMap() {
		return map;
	}


	public String getMappingName() {
		return mappingName;
	}


	public String getIsValid() {
		return isValid;
	}


	public String getMappingNameValidation() {
		return mappingNameValidation;
	}


	public String getMappingIsValidValidation() {
		return mappingIsValidValidation;
	}


	public String getMappingVariableNameValidation() {
		return mappingVariableNameValidation;
	}


	public String getMappingSQLQueryValidation() {
		return mappingSQLQueryValidation;
	}


	public String getMappingSQLOverrideValidation() {
		return mappingSQLOverrideValidation;
	}
	
	
}
