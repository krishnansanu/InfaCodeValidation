package com.wow.dev.infanodes;

import java.util.Map;

public class StoredProcedure extends Transformation{
	
	private String transformationName;
	
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
		super.trace(transformationType, transformationName);
		transformationValidationResults.put("STORED_PROCEDURE_NAME", extractStoredProcedureName());
		transformationValidationResults.put("STORED_PROCEDURE_TYPE_VALIDATION",extractStoredProcedureType());
		transformationValidationResults.put("STORED_PROCEDURE_CONNECTION_VALIDATION",extractConnectionInformation());
		transformationValidationResults.put("STORED_PROCEDURE_CALL_TEXT_VALIDATION",extractStoredProcedureCallText());
		transformationValidationResults.put("STORED_PROCEDURE_TRACING_LEVEL_VALIDATION",validatetracingLevel(validationList,i)?"PASS":"FAIL");
	}

	public String getTransformationName() {
		return transformationName;
	}

}
