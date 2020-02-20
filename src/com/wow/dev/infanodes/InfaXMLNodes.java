package com.wow.dev.infanodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class InfaXMLNodes {
	
	protected Map<String, String> map;
	
	public InfaXMLNodes() {
//		map=new HashMap<String,String>();
		map=new LinkedHashMap<String, String>();
	}
	
	public void setValue(String key,String value) {
		map.put(key, value);
	}
	
	public void printValues() {
		System.out.println(map);
	}
	
	public void iterateMap() {
		Set<String> keys=map.keySet();
		for(String key:keys) {
			System.out.println(key + " : "+map.get(key));
		}
	}
	
	public Map<String, String> getMap() {
		return map;
	}
	
	public void nullValidation(String type, String value,ArrayList<String> errorList) {

		System.out.println("Validating " +  type + " Value... [" + type + " = " + value +"]");
		if(value==null || value.isEmpty() || value=="")
			errorList.add("Invalid " + type +". ["+ type + "="+ value +"]");
	}
	
	public void validate(ArrayList<String> infoList,ArrayList<String> errorList, ArrayList<String> warningList,String folderName) {}
}
