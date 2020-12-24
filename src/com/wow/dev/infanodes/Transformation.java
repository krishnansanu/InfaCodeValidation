package com.wow.dev.infanodes;

import java.util.Map;
import java.util.Set;

public abstract class Transformation {
	
	protected Map<String, String> map;
	protected String transformationType;
	
	
	public Transformation(Map<String, String> map,String transformationType) {
		this.map=map;
		this.transformationType=transformationType;
	}
	
	public Map<String, String> getMap() {
		return map;
	}

	public abstract void validate(Map<String,String> validationList,int i);
	
	public String  getTransformationType() {
		return transformationType;
	}
	
	
	
	public boolean validateTransforamtionName(String transformationName,Map<String, String> validationList, int i,int endIndex, String prefixValue) {
		System.out.println("Validating TRANSFORMATION NAME... [TRANSFORMATION.NAME="+transformationName+"]");
		if(!transformationName.substring(0, endIndex).equalsIgnoreCase(prefixValue)) {
			validationList.put(i+"_TRANSFORMATION.NAME","Invalid Start of "+transformationType+" Name [" + transformationName + "]. "+transformationType+" Name Should Start with '"+prefixValue+"'");
			return false;
		}
		
		return true;
		
	}
	
	public boolean validatetracingLevel(String tracingLevel,String transformationName,Map<String, String> validationList, int i) {
		System.out.println("Validating TRANSFORMATION_TABLEATTRIBUTE.Tracing Level... [TRANSFORMATION_TABLEATTRIBUTE.Tracing Level="+tracingLevel+"]");
		if(!tracingLevel.equalsIgnoreCase("Normal")) {
			validationList.put(i+"_TRANSFORMATION_TABLEATTRIBUTE.Tracing Level","Transofrmation [" + transformationName + "] tracing level should be Normal");
			return false;
		}
		
		return true;
	}
	
	public boolean validatePortName(String transformationName,Map<String, String> validationList, int i) {
		boolean fieldport=true;
		int totalPort=0;
		String PORTNAME="";
		String PORTEXPRESSION="";
		String PORTTYPE="";

		Set<String> keys=map.keySet();
		for(String key:keys) {
			if(key.contains("TRANSFORMFIELD.NAME"))
				totalPort++;
			
		}
		
		
		for(int j=0;j<totalPort;j++) {
			if(j==0) {
				PORTNAME=map.get("TRANSFORMFIELD.NAME"); PORTTYPE=map.get("TRANSFORMFIELD.PORTTYPE"); PORTEXPRESSION=map.get("TRANSFORMFIELD.EXPRESSION");
			}else {
				PORTNAME=map.get("TRANSFORMFIELD.NAME("+j+")"); PORTTYPE=map.get("TRANSFORMFIELD.PORTTYPE("+j+")"); PORTEXPRESSION=map.get("TRANSFORMFIELD.EXPRESSION("+j+")");
			}
			
			if(PORTTYPE!=null && PORTNAME!=null) {
				System.out.println("Validating EXPRESSION FIELD NAME... [FIELD NAME="+PORTNAME+"]");
				
				if(PORTTYPE.equals("INTPUT")) {
					
					if(!( PORTNAME.substring(0,3).equalsIgnoreCase("in_"))) {
						validationList.put(j+"_TRANSFORMFIELD.NAME", "Field Name ["+PORTNAME+"] in the "+transformationType+" [" + transformationName + "]is ["+PORTTYPE+"]. FieldName should be prefixed with in_ (or) IN_");
						fieldport=false;
					}
				}

				if(PORTTYPE.equals("OUTPUT")) {
					
					if(!( PORTNAME.substring(0,2).equalsIgnoreCase("o_") || (PORTNAME.substring(0,4).equalsIgnoreCase("out_")) )) {
						validationList.put(j+"_TRANSFORMFIELD.NAME", "Field Name ["+PORTNAME+"] in the "+transformationType+" [" + transformationName + "]is ["+PORTTYPE+"]. FieldName should be prefixed with o_ (or) out_");
						fieldport=false;
					}
				}
				
				if(PORTTYPE.contains("VARIABLE")) {
					
					if(!( PORTNAME.substring(0,2).equalsIgnoreCase("v_") || (PORTNAME.substring(0,4).equalsIgnoreCase("var_")) )) {
						validationList.put(j+"_TRANSFORMFIELD.NAME", "Field Name ["+PORTNAME+"] in the "+transformationType+" [" + transformationName + "]is ["+PORTTYPE+"]. FieldName should be prefixed with v_ (or) var_");
						fieldport=false;
					}
				}
			}
			
			
		}
		return fieldport;
		
	}
	
	
	
}
