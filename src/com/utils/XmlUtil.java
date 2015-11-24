package com.utils;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlUtil {

	public static HashMap<String, Object> xmlSoapParse(String xmlFile, String keyName) throws Exception {
		SAXReader reader = new SAXReader();
		Document document = reader.read(xmlFile);
		Element rootElement = document.getRootElement();
		HashMap<String, Object> hmElements = new HashMap<String, Object>();
		if (rootElement.elements().size() > 0) {
			hmElements = xmlElement(rootElement, keyName);
		}
		return hmElements;
	}

	public static HashMap<String, Object> xmlSoapParse(StringReader xmlFile, String keyName) throws Exception {
		SAXReader reader = new SAXReader();
		Document document = reader.read(xmlFile);
		Element rootElement = document.getRootElement();
		HashMap<String, Object> hmElements = new HashMap<String, Object>();
		if (rootElement.elements().size() > 0) {
			hmElements = xmlElement(rootElement, keyName);
		}
		return hmElements;
	}
	
	protected static HashMap<String, Object> xmlElement(Element element, String keyName) {
		List<Element> elements = element.elements();
		int elements_size = elements.size();
		HashMap<String, Object> hmElement = new HashMap<String, Object>();
		if (elements_size > 0) {
			HashMap<String, Object> hmElementChild = new HashMap<String, Object>();
			HashMap<String, Object> hmElementNameAttribute = new HashMap<String, Object>();
			HashMap<String, Object> hmAttribute = new HashMap<String, Object>();
			String sElementNameValue = "";
			//String sElementValue = "";
			int i;
			int iAttributeCount;
			for (i = 0; i < elements_size; i++) {
				iAttributeCount = elements.get(i).attributeCount();
				for (int j = 0; j < iAttributeCount; j++) {
					hmAttribute.put(elements.get(i).attribute(j).getName(),
							elements.get(i).attribute(j).getValue());
				}
				//
				sElementNameValue = elements.get(i).attributeValue(keyName);
				//sElementValue = elements.get(i).getText();
				if (hmAttribute.containsKey(keyName)) {
					hmAttribute.remove(keyName);
				}//
				hmElementChild = xmlElement(elements.get(i), keyName);
				if(iAttributeCount > 0) {
					hmElementChild.putAll(hmAttribute);
				}
				if(sElementNameValue != null && !sElementNameValue.isEmpty()) {
					hmElementNameAttribute.put(sElementNameValue.replace(" ", "_"), hmElementChild);
					hmElement.put(elements.get(i).getName().replace(" ", "_"), hmElementNameAttribute);
				} else {
					hmElement.put(elements.get(i).getName().replace(" ", "_"), hmElementChild);
				}
			}
		}
		return hmElement;
	}

	public static void main(String[] args) throws Exception {
		String xml = "E:/workspace/tour_infterface/build/classes/resources/supplier/tourico/tourioc.GetDestination.xml";
		xmlSoapParse(xml, "name");
	}
}
