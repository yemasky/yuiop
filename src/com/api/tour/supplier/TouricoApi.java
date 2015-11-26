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
import com.utils.JsonUtil;
import com.webservice.client.SoapHttpClient;

public class TouricoApi {
	private static Logger logger = LogManager.getLogger(TouricoApi.class.getName());

	public void GetDestination() throws Exception {
		TouricoConfig Tconfig = new TouricoConfig();
		FileUtil fileUtil = new FileUtil();
		String fileName = "tourioc.GetDestination.xml";
		String xmlGetDestination = "";
		// logger.info(cg.filePath() + fileName);
		// System.exit(0);
		if (fileUtil.fileExists(Tconfig.filePath() + fileName)) {
			xmlGetDestination = fileUtil.readTxt(Tconfig.filePath() + fileName, "utf-8");
			// TouricoXmlUtil.xmlSoapParse(cg.filePath() + fileName, "name");
		}
		// System.exit(0);
		// System.out.println("xmlGetDestination:" + xmlGetDestination);
		if (xmlGetDestination.trim().isEmpty()) {
			SoapHttpClient shc = new SoapHttpClient();
			xmlGetDestination = shc.httpClient(Tconfig.getDestinationURL, Tconfig.xmlGetDestination("", "", "", "", ""),
					Tconfig.getDestinationHeader);
			fileUtil.createFile(Tconfig.filePath(), fileName, xmlGetDestination);
		}
		HashMap<String, Object> hmXmlparse = new HashMap<String, Object>();
		hmXmlparse = TouricoXmlUtil.xmlSoapParse(new StringReader(xmlGetDestination), "name");
		if (!fileUtil.fileExists(Tconfig.filePath() + "1.txt")) {
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
		FileUtil fileUtil = new FileUtil();
		String fileNamePrefix = "tourioc.GetHotelsbyDestination_";
		String fileName = "";
		String xmlGetHotelsbyDestination = "";
		int length = cg.Continent.length;
		SoapHttpClient shc = new SoapHttpClient();

		for (int i = 0; i < length; i++) {
			fileName = fileNamePrefix + cg.Continent[i].replace(" ", "_").replace("/", ".") + ".xml";
			logger.info(fileName);
			if (fileUtil.fileExists(cg.filePath() + fileName)) {
				xmlGetHotelsbyDestination = fileUtil.readTxt(cg.filePath() + fileName, "utf-8");
			}
			if (xmlGetHotelsbyDestination.trim().isEmpty()) {
				xmlGetHotelsbyDestination = shc.httpClient(cg.getHotelsByDestinationURL,
						cg.xmlGetHotelsByDestination(cg.Continent[i], "", "", "", ""), cg.getHotelsByDestinationHeader);
				fileUtil.createFile(cg.filePath(), fileName, xmlGetHotelsbyDestination);
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
		TouricoConfig Tconfig = new TouricoConfig();
		FileUtil fileUtil = new FileUtil();
		String fileNamePrefix = "tourioc.GetHotelsbyDestination_";
		String fileName = "";
		String fileContinent = "";
		String xmlGetHotelsbyDestination = "", xmlGetHotelDetailsV3 = "", hotelPath = "", hotelFlie = "";

		int length = Tconfig.Continent.length;
		SoapHttpClient soapClient = new SoapHttpClient();
		String compileStr = "hotelId=\"([0-9]+)\"";
		Pattern pattern = null;
		Matcher matcher = null;
		String hotelid = null;

		for (int i = 0; i < length; i++) {
			fileContinent = Tconfig.Continent[i].replace(" ", "_").replace("/", ".");
			hotelPath = Tconfig.filePath() + "hotels/" + fileContinent;
			fileUtil.createFolder(hotelPath);
			fileName = fileNamePrefix + fileContinent + ".xml";
			xmlGetHotelsbyDestination = fileUtil.readTxt(Tconfig.filePath() + fileName, "utf-8");

			pattern = Pattern.compile(compileStr);
			matcher = pattern.matcher(xmlGetHotelsbyDestination);
			// logger.info(matcher.groupCount());

			while (matcher.find()) {
				hotelid = matcher.group(1);
				if (!fileUtil.fileExists(hotelPath + "/" + hotelid + ".xml")) {
					// xmlGetHotelsbyDestination =
					// fileUtil.readTxt(cg.filePath() + fileName, "utf-8");
					xmlGetHotelDetailsV3 = soapClient.httpClient(Tconfig.getHotelDetailsV3URL,
							Tconfig.xmlGetHotelDetailsV3(hotelid), Tconfig.getHotelDetailsV3Header);
					fileUtil.createFile(hotelPath, hotelid + ".xml", xmlGetHotelDetailsV3);
				} else {
					xmlGetHotelDetailsV3 = fileUtil.readTxt(hotelPath + "/" + hotelid + ".xml", "utf-8");
				}
				// System.out.println("json=" +
				// JsonUtil.xml2JSON(xmlGetHotelDetailsV3));
				System.out.println("json=" + JsonUtil.jsonParse(JsonUtil.xml2JSON(xmlGetHotelDetailsV3), "s:Body")
						.get("GetHotelDetailsV3Response"));
				System.exit(0);
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

	public void newBookHotelV3Demo() throws Exception {
		TouricoConfig Tconfig = new TouricoConfig();
		FileUtil fileUtil = new FileUtil();
		SoapHttpClient soapClient = new SoapHttpClient();
		
		String BookingInfo = fileUtil.readTxt(Tconfig.filePath() + "BookHotelV3.xml", "utf-8");
		String BookXml = Tconfig.xmlBookHotelV3(BookingInfo);
		BookXml.replace("RecordLocatorIdValue", "0");// 新订单
		BookXml.replace("HotelIdValue", "1203719");
		BookXml.replace("HotelRoomTypeIdValue", "1699316");
		BookXml.replace("CheckInValue", "2015-12-15");
		BookXml.replace("CheckOutValue", "2015-12-20");
		BookXml.replace("RoomIdValue", "1");
		BookXml.replace("FirstNameValue", "FirstNameValue");
		BookXml.replace("MiddleNameValue", "MiddleNameValue");
		BookXml.replace("LastNameValue", "LastNameValue");
		BookXml.replace("HomePhoneValue", "111-111-1111");
		BookXml.replace("MobilePhoneValue", "222-222-2222");
		BookXml.replace("SelectedBoardBaseValue", "<hot1:Id>4</hot1:Id><hot1:Price>0</hot1:Price>");
		BookXml.replace("SelectedSupplementsValue",
				"<hot1:SupplementInfo suppId=\"1000615\" supTotalPrice=\"23.00\" suppType=\"8\">"
			   +"<hot1:SupAgeGroup><hot1:SuppAges suppFrom=\"1\" suppTo=\"99\" suppQuantity=\"2\" suppPrice=\"11.50\"/></hot1:SupAgeGroup></hot1:SupplementInfo>");
		BookXml.replace("BeddingValue", "2,1");
		BookXml.replace("NoteValue", "Can we please have a city view? Thanks!");
		BookXml.replace("AdultNumValue", "2");
		BookXml.replace("ChildNumValue", "0");
		BookXml.replace("ChildAgesValue", "<hot1:ChildAge age=\"0\"/>");
		BookXml.replace("PaymentTypeValue", "Obligo");
		BookXml.replace("AgentRefNumberValue", "123NA");
		BookXml.replace("ContactInfoValue", "333-333-3333");
		BookXml.replace("RequestedPriceValue", "322.80");
		BookXml.replace("DeltaPriceValue", "0");
		BookXml.replace("CurrencyValue", "USD");
		BookXml.replace("IsOnlyAvailableValue", "true");
		BookXml.replace("ConfirmationEmailValue", "kefu@yelove.cn");
		BookXml.replace("ConfirmationLogoValue", "logo.gif");
		String xmlGetHotelDetailsV3 = soapClient.httpClient(Tconfig.getBookHotelV3URL,
				BookXml, Tconfig.getBookHotelV3Header);
		logger.info(xmlGetHotelDetailsV3);

	}

	public static void main(String[] args) throws Exception {
		TouricoApi ta = new TouricoApi();
		ta.newBookHotelV3Demo();
		// ta.GetDestination();
		// System.out.println(c.getProperties("supplier/tourico.xml",
		// "dat:username", "xml"));
	}
}
