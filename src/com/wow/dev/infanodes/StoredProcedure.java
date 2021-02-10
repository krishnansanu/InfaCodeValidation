package com.wow.dev.infanodes;

import java.util.Map;

public class StoredProcedure extends Transformation{
	
	private String transformationName;
	private String storedProcedureName;
	private String connectionInformationName;
	private String storedProcedureCallText;
	private String storedProcedureType;
	private String tracingLevelValidation;
	
	public StoredProcedure(Map<String, String> map, String transformationType) {
		super(map, transformationType);
	}

	public String extractTransformationName() {return map.get("TRANSFORMATION.NAME");}
	public String extractStoredProcedureName() {return map.get("TRANSFORMATION_TABLEATTRIBUTE.Stored Procedure Name");}
	public String extractConnectionInformation() {return map.get("TRANSFORMATION_TABLEATTRIBUTE.Connection Information");}
	public String extractStoredProcedureCallText() {return map.get("TRANSFORMATION_TABLEATTRIBUTE.Call Text");}
	public String extractStoredProcedureType() {return map.get("TRANSFORMATION_TABLEATTRIBUTE.Stored Procedure Type");}
	
	public boolean validatetracingLevel(Map<String, String> validationList, int i) {
		String tracingLevel=map.get("TRANSFORMATION_TABLEATTRIBUTE.Tracing Level");
		return super.validatetracingLevel(tracingLevel, transformationName, validationList, i);
		
	}
	
	@Override
	public void validate(Map<String, String> validationList, int i) {
		transformationName=extractTransformationName();
		storedProcedureName=extractStoredProcedureName();
		connectionInformationName=extractConnectionInformation();
		storedProcedureCallText=extractStoredProcedureCallText();
		storedProcedureType=extractStoredProcedureType();
		tracingLevelValidation=validatetracingLevel(validationList,i)?"PASS":"FAIL";
	}

	public String getTransformationName() {
		return transformationName;
	}

	public String getStoredProcedureName() {
		return storedProcedureName;
	}

	public String getConnectionInformationName() {
		return connectionInformationName;
	}

	public String getStoredProcedureCallText() {
		return storedProcedureCallText;
	}

	public String getStoredProcedureType() {
		return storedProcedureType;
	}

	public String getTracingLevelValidation() {
		return tracingLevelValidation;
	}



}
