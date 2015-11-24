package com.api.tour.supplier;

import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.utils.FileUtil;

public class TouricoXmlUtil {
	private static Logger logger = LogManager.getLogger(TouricoXmlUtil.class
			.getName());

	public static void xmlParse(String xmlFile) throws Exception {
		SAXReader reader = new SAXReader();
		Document document = reader.read(xmlFile);
		Element rootElement = document.getRootElement();
		if (rootElement.elements().size() > 0) {
			HashMap<String, Object> hmElements = new HashMap<String, Object>();
			hmElements = xmlElement(rootElement);
			FileUtil fo = new FileUtil();
			TouricoConfig cg = new TouricoConfig();
			fo.createFile(cg.filePath(), "1.txt", hmElements.toString());
			logger.info(hmElements.toString());
		}
	}

	protected static HashMap<String, Object> xmlElement(Element element) {
		List<Element> elements = element.elements();
		int elements_size = elements.size();
		HashMap<String, Object> hmElement = new HashMap<String, Object>();
		if (elements_size > 0) {
			HashMap<String, Object> hmElementChild = new HashMap<String, Object>();
			HashMap<String, Object> hmElementNameAttribute = new HashMap<String, Object>();
			HashMap<String, Object> hmAttribute = new HashMap<String, Object>();
			String sElementNameValue = "";
			int i;
			int iAttributeCount;
			for (i = 0; i < elements_size; i++) {
				iAttributeCount = elements.get(i).attributeCount();
				for (int j = 0; j < iAttributeCount; j++) {
					hmAttribute.put(elements.get(i).attribute(j).getName(),
							elements.get(i).attribute(j).getValue());
				}
				//
				sElementNameValue = elements.get(i).attributeValue("name");
				// logger.info("sElementNameValue:"+sElementNameValue);
				if (hmAttribute.containsKey("name")) {
					hmAttribute.remove("name");
				}//
				hmElementChild = xmlElement(elements.get(i));
				if(iAttributeCount > 0) {
					//hmElementNameAttribute.put(elements.get(i).getName(), hmAttribute);
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
		xmlParse(xml);
	}
}
