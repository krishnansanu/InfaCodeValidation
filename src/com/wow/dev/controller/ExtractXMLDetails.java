package com.wow.dev.controller;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.NamedNodeMap;  

public class ExtractXMLDetails {
	private File xmlfile=null;
	private DocumentBuilderFactory dbf; 
	private DocumentBuilder db; 
	private Document doc;
		
	//Public Constructor to create instance for XML to be read
	public ExtractXMLDetails(String workflowName) {
		try {
			xmlfile = new File(workflowName);
			dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
			doc = db.parse(xmlfile);
			doc.getDocumentElement().normalize();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ExtractXMLDetails(Document doc) {
		this.doc=doc;
	}
	
	// Function to create object for corresponding category and extract information for it
	public Map<String, String>[] extractDetailsToMap(String xmlNodeName) {
		NodeList list = doc.getDocumentElement().getElementsByTagName(xmlNodeName);
		Map<String, String> objectDetails[]=new LinkedHashMap[list.getLength()];
		for(int i=0;i<list.getLength();i++) {
			objectDetails[i]=new LinkedHashMap<String, String>();
			Node n= list.item(i);
			
			if(n.hasAttributes()) {
				putDetailsToMap(n,objectDetails[i],i);
			}
			
			getChildDetailsToMap(objectDetails[i],n);
		}
		return objectDetails;
	}
	
	
	//Function to extract specific object details and puts into its corresponding Map
	public void putDetailsToMap(Node n,Map objectDetails, int i) {
		NamedNodeMap nodeMap = n.getAttributes();
		String msg="",key="",value="";
		for(int j=0;j<nodeMap.getLength();j++) {
			String attrib=nodeMap.item(j).getNodeName();
			String parentNode=n.getParentNode().getNodeName();
			String currentNode=n.getNodeName();
			String nodeAttribName=nodeMap.getNamedItem(attrib).getNodeName();
			String nodeAttribValue=nodeMap.getNamedItem(attrib).getNodeValue();
			
			if(currentNode.contains("ATTRIBUTE") || nodeAttribName.contains("ATTRIBUTE") ) {
				if(msg=="") {
					msg=parentNode+"_"+currentNode+ "." + nodeAttribValue;
					key=parentNode+"_"+currentNode+ "." + nodeAttribValue;
					value="";
					continue;
				}else {
					msg+=nodeAttribValue+ "\n";
					value=nodeAttribValue;
				}
			}else {
				key=currentNode+"."+nodeAttribName;
				value=nodeAttribValue;
			}
			
			
			if(objectDetails.containsKey(key)){
				objectDetails.put(key+"("+(i/2)+")", value);
			}else {
				objectDetails.put(key, value);
			}
			msg="";
		}
	}
	
	//Function to check if a node has any child and gets its details
	public void getChildDetailsToMap(Map<String, String> map, Node parentNode) {
		if(parentNode.hasChildNodes()) {
			NodeList childNodes = parentNode.getChildNodes();
			for(int i=0;i<childNodes.getLength();i++) {
				Node n = childNodes.item(i);
				if(n.hasAttributes()) {
					putDetailsToMap(n, map,i);
				}
				
				if(n.hasChildNodes()) {
					getChildDetailsToMap(map,n);
				}
			}
		}		
	}

	
	
}
