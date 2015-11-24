package com.api.tour.supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.utils.XmlUtil;

public class TouricoXmlUtil extends XmlUtil {
	private static Logger logger = LogManager.getLogger(TouricoXmlUtil.class
			.getName());

	public static void main(String[] args) throws Exception {
		String xml = "E:/workspace/tour_infterface/build/classes/resources/supplier/tourico/tourioc.GetDestination.xml";
		xmlSoapParse(xml, "names");
	}
}
