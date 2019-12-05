package com.wow.dev.main;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;

import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.OutputStreamCounter;
import com.itextpdf.text.pdf.PdfWriter;
import com.wow.dev.infanodes.Config;
import com.wow.dev.infanodes.Folder;
import com.wow.dev.infanodes.InfaXMLNodes;
import com.wow.dev.infanodes.Mapping;
import com.wow.dev.infanodes.Session;
import com.wow.dev.infanodes.Source;
import com.wow.dev.infanodes.Target;
import com.wow.dev.infanodes.Workflow;


public class XMLMainReader 
{  
	public static void main(String[] args)  
	{
		
		File xmlfile=null;  

		//Variable Decleration for XML File
		DocumentBuilderFactory dbf=null; DocumentBuilder db=null; Document doc=null;
		
		//ArrayList for XMLNode and ErrorList
		ArrayList<String> errorList;
//		ArrayList<String> xmlnodelist, mappingNodeList,sourceNodeList,targetNodeList,configNodeList,sessionNodeList,workflowNodeList;
		
		try {
			xmlfile = new File("config/XMLFile.xml");
			dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
			doc = db.parse(xmlfile);
			doc.getDocumentElement().normalize();
		}catch(Exception e) { e.getStackTrace(); }
		
		// Reading XML and extracting NodeDetails from XML file.
		ExtractXMLDetails xmlDetails = new ExtractXMLDetails(doc);
//		xmlnodelist=xmlDetails.extractXMLNodes();	System.out.println(xmlnodelist);
		
		// Creation of errorlist for capturing any errors in XML validation
		errorList=new ArrayList<String>();

		
		//Extraction and creation of Folder information from XML File...
		Folder[] folder= xmlDetails.extractFolderDetailsToMap("FOLDER");
		String folderName=null;
		for(Folder r:folder)
			folderName=r.getFolderName();

		
//		sourceNodeList= new ArrayList<String>();xmlDetails.getNodeDetails("SOURCE",sourceNodeList);System.out.println(sourceNodeList);
		extractValidate("SOURCE", xmlDetails, errorList, folderName);
		
//		targetNodeList= new ArrayList<String>();xmlDetails.getNodeDetails("TARGET",targetNodeList);System.out.println(targetNodeList);
		extractValidate("TARGET", xmlDetails, errorList, folderName);
		
		
//		mappingNodeList = new ArrayList<String>();xmlDetails.getNodeDetails("MAPPING",mappingNodeList);System.out.println(mappingNodeList);
		extractValidate("MAPPING", xmlDetails, errorList, folderName);
	
		System.out.println("");		
		
//		sessionNodeList= new ArrayList<String>();xmlDetails.getNodeDetails("SESSION",sessionNodeList);System.out.println(sessionNodeList);
		extractValidate("SESSION", xmlDetails, errorList, folderName);		
		System.out.println("");		
		
//		workflowNodeList= new ArrayList<String>(); xmlDetails.getNodeDetails("WORKFLOW",workflowNodeList); System.out.println(workflowNodeList);
//		workflowNodeList.removeAll(sessionNodeList);
		extractValidate("WORKFLOW", xmlDetails, errorList, folderName);
		System.out.println("");
		
		
//		configNodeList= new ArrayList<String>(); xmlDetails.getNodeDetails("CONFIG",configNodeList);		System.out.println(configNodeList);		

		
		
		
		Paragraph paragraph=new Paragraph();
		Font font = new Font(); font.setSize(10);
		paragraph.setFont(font);
		
		// Results
		if(errorList.size() > 0 ) {
			paragraph.add("");
			paragraph.add("-------------------------------------------------------------------------------------------\n");
			paragraph.add("Informatica Components is not created as per ICC Standards. Please find the comments Below\n");
			paragraph.add("-------------------------------------------------------------------------------------------\n");
			
			Iterator<String> it = errorList.iterator();
			while(it.hasNext()) {
				String str=it.next()+"\n";
				System.out.print(str);
				paragraph.add(str);
			}
		}		
		else {
			paragraph.add("------------------------------------------------------\n");
			paragraph.add("Informatica Components is created as per ICC Standards\n");
			paragraph.add("------------------------------------------------------\n");
		}
		

		com.itextpdf.text.Document pdfDoc= new com.itextpdf.text.Document();
		try {
			PdfWriter pdfWriter=PdfWriter.getInstance(pdfDoc, new FileOutputStream("InfaICCSTD.pdf"));
			pdfDoc.open();
			pdfDoc.add(paragraph);
			pdfDoc.close();
		}catch(Exception e) {
			
		}
		
		
		
	}
	
	private static void extractValidate(String nodeName, ExtractXMLDetails xmlDetails, ArrayList<String> errorList, String folderName) {
	
		InfaXMLNodes[] infaXML= null; 
				
		switch(nodeName) {
		case "SOURCE": infaXML=(Source[]) xmlDetails.extractDetailsToMap(nodeName); break;
		case "TARGET": infaXML=(Target[]) xmlDetails.extractDetailsToMap(nodeName); break;
		case "MAPPING": infaXML=(Mapping[]) xmlDetails.extractDetailsToMap(nodeName); break;
		case "CONFIG": infaXML=(Config[]) xmlDetails.extractDetailsToMap(nodeName); break;
		case "SESSION": infaXML=(Session[]) xmlDetails.extractDetailsToMap(nodeName); break;
		case "WORKFLOW": infaXML=(Workflow[]) xmlDetails.extractDetailsToMap(nodeName); break;
		}
		
		for(int i=0;i<infaXML.length;i++) {
			infaXML[i].validate(errorList,folderName);
		}
	} 
}  