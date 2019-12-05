package com.wow.dev.infanodes;

import java.util.ArrayList;

public class Folder extends InfaXMLNodes {
	

	
	public Folder() {
		super();
	}
	
	public void validateFolderName(ArrayList<String> errorList) {
		String folderName=map.get("FOLDER.NAME");
		super.nullValidation("Folder Name", folderName, errorList);
	}
	
	public void validateFolderGroup(ArrayList<String> errorList) {
		String folderGroup=map.get("FOLDER.GROUP");
		super.nullValidation("Folder Group", folderGroup, errorList);
	}
	
	public void validateFolderOwner(ArrayList<String> errorList) {
		String folderOwner=map.get("FOLDER.OWNER");
		super.nullValidation("Folder Owner", folderOwner, errorList);
	}
	
	public void validateFolderDescription(ArrayList<String> errorList) {
		String folderDescription=map.get("FOLDER.DESCRIPTION");
		super.nullValidation("Folder Description", folderDescription, errorList);
	}
	
	public void validateFolderPermission(ArrayList<String> errorList) {
		String folderPermission=map.get("FOLDER.PERMISSION");
		super.nullValidation("Folder Permission", folderPermission, errorList);
	}
	
	public String getFolderName() {return map.get("FOLDER.NAME");}

}
