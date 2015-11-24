package com.api.tour.supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.utils.FileUtil;
import com.webservice.client.SoapHttpClient;

public class TouricoApi {
	private static Logger logger = LogManager.getLogger(SoapHttpClient.class
			.getName());

	public void GetDestination() throws Exception {
		TouricoConfig cg = new TouricoConfig();
		FileUtil fo = new FileUtil();
		String fileName = "tourioc.GetDestination.xml";
		String xmlGetDestination = "";
		//logger.info(cg.filePath() + fileName);
		// System.exit(0);
		if (fo.fileExists(cg.filePath() + fileName)) {
			//xmlGetDestination = fo.readTxt(cg.filePath() + fileName, "utf-8");
			TouricoXmlUtil.xmlParse(cg.filePath() + fileName);
		}
		System.exit(0);
		//System.out.println("xmlGetDestination:" + xmlGetDestination);
		if(xmlGetDestination.trim().isEmpty()) {
			SoapHttpClient shc = new SoapHttpClient();
			xmlGetDestination = shc.httpClient(cg.getDestinationURL,
					cg.xmlGetDestination("", "", "", "", ""),
					cg.getDestinationHeader);
			fo.createFile(cg.filePath(), fileName, xmlGetDestination);
		}
		logger.info(xmlGetDestination);
	}

	public static void main(String[] args) throws Exception {
		TouricoApi ta = new TouricoApi();
		ta.GetDestination();
		// System.out.println(c.getProperties("supplier/tourico.xml",
		// "dat:username", "xml"));
	}
}