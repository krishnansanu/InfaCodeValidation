package com.wow.dev.main;

import java.util.ArrayList;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.wow.dev.infanodes.Config;
import com.wow.dev.infanodes.Folder;
import com.wow.dev.infanodes.InfaXMLNodes;
import com.wow.dev.infanodes.Mapping;
import com.wow.dev.infanodes.Session;
import com.wow.dev.infanodes.Source;
import com.wow.dev.infanodes.Target;
import com.wow.dev.infanodes.Workflow;
import org.w3c.dom.NamedNodeMap;  

public class ExtractXMLDetails {
	
	private Document doc;
	
		
	//Public Constructor to create instance for XML to be read
	public ExtractXMLDetails(Document doc) {
		this.doc=doc;
	}

	// Function used
	public ArrayList<String> extractXMLNodes() {
		ArrayList<String> xmlnodelist = new ArrayList<String>();
		readChildNodes(doc.getDocumentElement().getChildNodes(),xmlnodelist);
		return xmlnodelist;
	}
	
	// Function used
	public void readChildNodes(NodeList node,ArrayList<String> xmlnodelist) {
		for(int i=0;i<node.getLength();i++) {
			Node n=node.item(i);
			if(n.getNodeName()!="#text") xmlnodelist.add(n.getNodeName());
			if(n.getNodeName()!="#text" && n.hasChildNodes()) {
				readChildNodes(n.getChildNodes(),xmlnodelist);
			}
		}
	}
	
	// Function Used
	public void putDetailsToMap(Node n,InfaXMLNodes infa, int i) {
		NamedNodeMap nodeMap = n.getAttributes();
		String msg="",key="",value="";
		for(int j=0;j<nodeMap.getLength();j++) {
			String attrib=nodeMap.item(j).getNodeName();
			String parentNode=n.getParentNode().getNodeName();
			String currentNode=n.getNodeName();
			String nodeAttribName=nodeMap.getNamedItem(attrib).getNodeName();
			String nodeAttribValue=nodeMap.getNamedItem(attrib).getNodeValue();
			
//			System.out.println("CurrentNode - " + currentNode);
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
			
			
			
			if(infa.getMap().containsKey(key)){
				infa.setValue(key+"("+i+")", value);
			}else {
				infa.setValue(key, value);
			}
			
			msg="";
		}
		
	}
	
	// Function Used
	public Folder[] extractFolderDetailsToMap(String xmlNodeName) {
		NodeList list = doc.getDocumentElement().getElementsByTagName(xmlNodeName);
		Folder folder[]=new Folder[list.getLength()];
		
		for(int i=0;i<list.getLength();i++) {
			Node n= list.item(i);
			folder[i]=new Folder();
			if(n.hasAttributes()) {
				putDetailsToMap(n,folder[i],i);
			}
		}
		return folder;
	}
	
	
	public void getChildDetailsToMap(Map<String, String> map, Node parentNode,InfaXMLNodes infa) {
		if(parentNode.hasChildNodes()) {
			NodeList childNodes = parentNode.getChildNodes();
			for(int i=0;i<childNodes.getLength();i++) {
				Node n = childNodes.item(i);
				if(n.hasAttributes()) {
					putDetailsToMap(n, infa,i);
					
				}
				
				if(n.hasChildNodes()) {
					getChildDetailsToMap(map,n,infa);
				}
				
			}
		}		
	}
	
	// Function to get the Node 
	public void getNodeDetails(String xmlNodeName, ArrayList<String> arrayList) {
		String format="";
		NodeList list = doc.getDocumentElement().getElementsByTagName(xmlNodeName);
		for(int i=0;i<list.getLength();i++) {
			Node n = list.item(i);
			if(!arrayList.contains(n.getNodeName())) {
				arrayList.add(n.getNodeName());
			}
			
			if(n.hasChildNodes()) {
				format=format + "\t ";
				NodeList child = n.getChildNodes();
				for(int j=0;j<child.getLength();j++) {
					Node c = child.item(j);
					getNodeDetails(c.getNodeName(),arrayList);
				}
			}
		}
	}
	

	
	// Function used
	public InfaXMLNodes[] extractDetailsToMap(String xmlNodeName) {
		NodeList list = doc.getDocumentElement().getElementsByTagName(xmlNodeName);
		InfaXMLNodes xmlNode[]=null;
		switch(xmlNodeName) {
		case "SOURCE": xmlNode=new Source[list.getLength()]; break;
		case "TARGET": xmlNode=new Target[list.getLength()];break;
		case "MAPPING": xmlNode=new Mapping[list.getLength()]; break;
		case "CONFIG":xmlNode=new Config[list.getLength()];break;
		case "SESSION": xmlNode=new Session[list.getLength()]; break;
		case "WORKFLOW": xmlNode=new Workflow[list.getLength()]; break;
		}
		
		
		for(int i=0;i<list.getLength();i++) {
			Node n= list.item(i);
			
			switch(xmlNodeName) {
			case "SOURCE": xmlNode[i]=new Source(); break;
			case "TARGET": xmlNode[i]=new Target();break;
			case "MAPPING": xmlNode[i]=new Mapping(); break;
			case "CONFIG":xmlNode[i]=new Config(); break;
			case "SESSION": xmlNode[i]=new Session(); break;
			case "WORKFLOW":xmlNode[i]=new Workflow(); break;
			}
			
			
			if(n.hasAttributes()) {
				putDetailsToMap(n,xmlNode[i],i);
			}
			
			getChildDetailsToMap(xmlNode[i].getMap(),n,xmlNode[i]);
			
		}
		return xmlNode;
	}
	
	
}
