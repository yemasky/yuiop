package com.api.tour.supplier;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.utils.FileUtil;
import com.utils.Xml2JsonUtil;
import com.webservice.client.SoapHttpClient;

public class TouricoApi {
	private static Logger logger = LogManager.getLogger(TouricoApi.class.getName());

	public void GetDestination() throws Exception {
		TouricoConfig cg = new TouricoConfig();
		FileUtil fo = new FileUtil();
		String fileName = "tourioc.GetDestination.xml";
		String xmlGetDestination = "";
		// logger.info(cg.filePath() + fileName);
		// System.exit(0);
		if (fo.fileExists(cg.filePath() + fileName)) {
			xmlGetDestination = fo.readTxt(cg.filePath() + fileName, "utf-8");
			// TouricoXmlUtil.xmlSoapParse(cg.filePath() + fileName, "name");
		}
		// System.exit(0);
		// System.out.println("xmlGetDestination:" + xmlGetDestination);
		if (xmlGetDestination.trim().isEmpty()) {
			SoapHttpClient shc = new SoapHttpClient();
			xmlGetDestination = shc.httpClient(cg.getDestinationURL, cg.xmlGetDestination("", "", "", "", ""),
					cg.getDestinationHeader);
			fo.createFile(cg.filePath(), fileName, xmlGetDestination);
		}
		HashMap<String, Object> hmXmlparse = new HashMap<String, Object>();
		hmXmlparse = TouricoXmlUtil.xmlSoapParse(new StringReader(xmlGetDestination), "name");
		if (!fo.fileExists(cg.filePath() + "1.txt")) {
			/*
			 * hmXmlparse = (HashMap<String, Object>) hmXmlparse.get("Body");
			 * hmXmlparse = (HashMap<String, Object>)
			 * hmXmlparse.get("DestinationResponse"); hmXmlparse =
			 * (HashMap<String, Object>) hmXmlparse.get("DestinationResult");
			 * hmXmlparse = (HashMap<String, Object>)
			 * hmXmlparse.get("Continent"); fo.createFile(cg.filePath(),
			 * "1.txt", hmXmlparse.toString());
			 */
		}
		logger.info(hmXmlparse);
		/*
		 * String key; Object val; for (Entry<String, Object> entry :
		 * hmXmlparse.entrySet()) { key = entry.getKey(); val =
		 * entry.getValue(); }
		 */

	}

	public void GetHotelsbyDestination() throws Exception {
		TouricoConfig cg = new TouricoConfig();
		FileUtil fo = new FileUtil();
		String fileNamePrefix = "tourioc.GetHotelsbyDestination_";
		String fileName = "";
		String xmlGetHotelsbyDestination = "";
		int length = cg.Continent.length;
		SoapHttpClient shc = new SoapHttpClient();

		for (int i = 0; i < length; i++) {
			fileName = fileNamePrefix + cg.Continent[i].replace(" ", "_").replace("/", ".") + ".xml";
			logger.info(fileName);
			if (fo.fileExists(cg.filePath() + fileName)) {
				xmlGetHotelsbyDestination = fo.readTxt(cg.filePath() + fileName, "utf-8");
			}
			if (xmlGetHotelsbyDestination.trim().isEmpty()) {
				xmlGetHotelsbyDestination = shc.httpClient(cg.getHotelsByDestinationURL,
						cg.xmlGetHotelsByDestination(cg.Continent[i], "", "", "", ""), cg.getHotelsByDestinationHeader);
				fo.createFile(cg.filePath(), fileName, xmlGetHotelsbyDestination);
			}
			xmlGetHotelsbyDestination = "";
		}

		/*
		 * HashMap<String, Object> hmXmlparse = new HashMap<String, Object>();
		 * hmXmlparse = TouricoXmlUtil.xmlSoapParse(new
		 * StringReader(xmlGetHotelsbyDestination), "name"); if
		 * (!fo.fileExists(cg.filePath() + "2.txt")) {
		 * fo.createFile(cg.filePath(), "2.txt", hmXmlparse.toString()); }
		 * logger.info(hmXmlparse);
		 */

	}

	public void GetHotelDetailsV3() throws Exception {
		this.GetHotelsbyDestination();
		TouricoConfig cg = new TouricoConfig();
		FileUtil fileUtil = new FileUtil();
		String fileNamePrefix = "tourioc.GetHotelsbyDestination_";
		String fileName = "";
		String fileContinent = "";
		String xmlGetHotelsbyDestination = "", xmlGetHotelDetailsV3 = "", hotelPath = "", hotelFlie = "";

		int length = cg.Continent.length;
		SoapHttpClient shc = new SoapHttpClient();
		String compileStr = "hotelId=\"([0-9]+)\"";
		Pattern pattern = null;
		Matcher matcher = null;
		String hotelid = null;

		for (int i = 0; i < length; i++) {
			fileContinent = cg.Continent[i].replace(" ", "_").replace("/", ".");
			hotelPath = cg.filePath() + "hotels/" + fileContinent;
			fileUtil.createFolder(hotelPath);
			fileName = fileNamePrefix + fileContinent + ".xml";
			xmlGetHotelsbyDestination = fileUtil.readTxt(cg.filePath() + fileName, "utf-8");

			pattern = Pattern.compile(compileStr);
			matcher = pattern.matcher(xmlGetHotelsbyDestination);
			//logger.info(matcher.groupCount());

			while (matcher.find()) {
				hotelid = matcher.group(1);
				if (!fileUtil.fileExists(hotelPath + "/" + hotelid + ".xml")) {
					// xmlGetHotelsbyDestination =
					// fileUtil.readTxt(cg.filePath() + fileName, "utf-8");
					xmlGetHotelDetailsV3 = shc.httpClient(cg.getHotelDetailsV3URL, cg.xmlGetHotelDetailsV3(hotelid),
							cg.getHotelDetailsV3Header);
					fileUtil.createFile(hotelPath, hotelid + ".xml", xmlGetHotelDetailsV3);
				} else {
					//xmlGetHotelDetailsV3 = fileUtil.readTxt(hotelPath + "/" + hotelid + ".xml", "utf-8");
				}
				//System.out.println("json=" +  Xml2JsonUtil.xml2JSON(xmlGetHotelDetailsV3));
				//System.exit(0);
				// logger.info(matchValue.substring(9, matchValue.length() -1));
			}
			// MatchResult ss = matcher.toMatchResult();
			// logger.info(ss);
			// System.exit(0);
			// xmlGetHotelDetailsV3 = shc.httpClient(cg.getHotelDetailsV3URL,
			// cg.xmlGetHotelDetailsV3(cg.Continent[i]),
			// cg.getHotelDetailsV3Header);

		}

	}

	public static void main(String[] args) throws Exception {
		TouricoApi ta = new TouricoApi();
		ta.GetHotelDetailsV3();
		// ta.GetDestination();
		// System.out.println(c.getProperties("supplier/tourico.xml",
		// "dat:username", "xml"));
	}
}
