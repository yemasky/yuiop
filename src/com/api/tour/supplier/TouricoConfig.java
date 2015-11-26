package com.api.tour.supplier;

import java.util.HashMap;

public class TouricoConfig {
	public String culture = "en_US";
	public String version = "9";
	public String[] Continent = { "Africa", "Asia/Far East", "Australia", "Caribbean",
			"Central America / Latin America", "Europe", "Middle East", "North America", "Pacific",
			"South America/Caribbean" };
	//
	public String getDestinationURL = "http://destservices.touricoholidays.com/DestinationsService.svc?wsdl";
	public String getHotelsByDestinationURL = "http://destservices.touricoholidays.com/DestinationsService.svc";
	public String getHotelDetailsV3URL = "http://demo-hotelws.touricoholidays.com/HotelFlow.svc/bas";
	public String getBookHotelV3URL = "https://demo-hotelwssecure.touricoholidays.com/HotelFlow.svc/bas";
	
	public String[][] getDestinationHeader = new String[][] { { "SOAPAction",
			"http://touricoholidays.com/WSDestinations/2008/08/Contracts/IDestinationContracts/GetDestination" } };
	public String[][] getHotelsByDestinationHeader = new String[][] { { "SOAPAction",
			"http://touricoholidays.com/WSDestinations/2008/08/Contracts/IDestinationContracts/GetHotelsByDestination" } };
	public String[][] getHotelDetailsV3Header = new String[][] { { "SOAPAction",
			"http://tourico.com/webservices/hotelv3/IHotelFlow/GetHotelDetailsV3" } };
	public String[][] getBookHotelV3Header = new String[][] { { "SOAPAction",
			"http://tourico.com/webservices/hotelv3/IHotelFlow/BookHotelV3" } };		
	
	public String filePath() {
		return (this.getClass().getResource("/") + "resources/supplier/tourico/").substring(6) ;
	}
	public String xmlHeader() {
		String xml_header = "<soapenv:Header>" + "<dat:LoginHeader>" + "<dat:username>UBtour15</dat:username>"
				+ "<dat:password>UBT@th2015</dat:password>" + "<dat:culture>" + this.culture + "</dat:culture>"
				+ "<dat:version>" + this.version + "</dat:version>" + "</dat:LoginHeader>" + "</soapenv:Header>";
		return xml_header;
	}
	
	public String xmlGetHotelDetailsV3Header() {
		String xml_header = "<soapenv:Header>" + "<aut:AuthenticationHeader>" + "<aut:LoginName>bei104</aut:LoginName>"
				+ "<aut:Password>111111</aut:Password>" + "<aut:Culture>" + this.culture + "</aut:Culture>"
				+ "<aut:Version>" + this.version + "</aut:Version>" + "</aut:AuthenticationHeader>" + "</soapenv:Header>";
		return xml_header;
	}

	public String xmlDestination(String Continent, String Country, String State, String City, String StatusDate) {
		String xml_Destination = "<dat:Destination>" + "<dat:Continent>" + Continent + "</dat:Continent>"
				+ "<dat:Country>" + Country + "</dat:Country>" + "<dat:State>" + State + "</dat:State>" + "<dat:City>"
				+ City + "</dat:City>" + "<dat:Providers>" + "<dat:ProviderType>Default</dat:ProviderType>"
				+ "</dat:Providers>";
		if (!StatusDate.isEmpty()) {
			xml_Destination += "<dat:StatusDate>" + StatusDate + "</dat:StatusDate>";
		} // +"<dat:StatusDate>2015-12-17T00:00:00</dat:StatusDate>"
		xml_Destination += "</dat:Destination>";
		return xml_Destination;
	}

	public String xmlGetDestination(String Continent, String Country, String State, String City, String StatusDate) {
		String xml_header = this.xmlHeader();
		String xml_destination = this.xmlDestination(Continent, Country, State, City, StatusDate);
		String xml_getDestination = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:dat=\"http://touricoholidays.com/WSDestinations/2008/08/DataContracts\">"
				+ xml_header + "<soapenv:Body>" + "<dat:GetDestination>" + xml_destination + "</dat:GetDestination>"
				+ "</soapenv:Body>" + "</soapenv:Envelope>";
		return xml_getDestination;
	}

	public String xmlGetHotelsByDestination(String Continent, String Country, String State, String City,
			String StatusDate) {
		String xml_header = this.xmlHeader();
		String xml_destination = this.xmlDestination(Continent, Country, State, City, StatusDate);
		String xml_GetHotelsByDestination = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:dat=\"http://touricoholidays.com/WSDestinations/2008/08/DataContracts\">"
				+ xml_header + "<soapenv:Body>" + "<dat:GetHotelsByDestination>" + xml_destination
				+ "</dat:GetHotelsByDestination>" + "</soapenv:Body>" + "</soapenv:Envelope>";
		return xml_GetHotelsByDestination;
	}
	
	public String xmlGetHotelDetailsV3(String HotelID) {
		String xml_header = this.xmlGetHotelDetailsV3Header();
		String xml_GetHotelDetailsV3 = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:aut=\"http://schemas.tourico.com/webservices/authentication\" xmlns:hot=\"http://tourico.com/webservices/hotelv3\">"
				+ xml_header + "<soapenv:Body>" + "<hot:GetHotelDetailsV3><hot:HotelIds><hot:HotelID id=\"" + HotelID
				+ "\"/></hot:HotelIds></hot:GetHotelDetailsV3>" + "</soapenv:Body>" + "</soapenv:Envelope>";
		return xml_GetHotelDetailsV3;
	}
	
	public String xmlBookHotelV3(String BookingInfo) {
		String xml_header = this.xmlGetHotelDetailsV3Header();
		String xml_GetHotelDetailsV3 = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
				+ "xmlns:aut=\"http://schemas.tourico.com/webservices/authentication\" xmlns:hot=\"http://tourico.com/webservices/hotelv3\""
				+" xmlns:hot1=\"http://schemas.tourico.com/webservices/hotelv3\">"
				+ xml_header + "<soapenv:Body>" + "<hot:BookHotelV3>"+BookingInfo+"</hot:BookHotelV3>" + "</soapenv:Body>" + "</soapenv:Envelope>";
		return xml_GetHotelDetailsV3;
	}

}
