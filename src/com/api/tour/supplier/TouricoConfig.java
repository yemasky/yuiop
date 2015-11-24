package com.api.tour.supplier;

public class TouricoConfig {
	public String culture = "en_US";
	public String version = "9";
	public String[] Continent = { "Africa", "Asia/Far East", "Australia", "Caribbean",
			"Central America / Latin America", "Europe", "Middle East", "North America", "Pacific",
			"South America/Caribbean" };
	//
	public String getDestinationURL = "http://destservices.touricoholidays.com/DestinationsService.svc?wsdl";
	public String[][] getDestinationHeader = new String[][] { { "SOAPAction",
			"http://touricoholidays.com/WSDestinations/2008/08/Contracts/IDestinationContracts/GetDestination" } };
	
	public String filePath() {
		return (this.getClass().getResource("/") + "resources/supplier/tourico/").substring(6) ;
	}
	public String xmlHeader() {
		String xml_header = "<soapenv:Header>" + "<dat:LoginHeader>" + "<dat:username>UBtour15</dat:username>"
				+ "<dat:password>UBT@th2015</dat:password>" + "<dat:culture>" + this.culture + "</dat:culture>"
				+ "<dat:version>" + this.version + "</dat:version>" + "</dat:LoginHeader>" + "</soapenv:Header>";
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
		String xml_destination = this.xmlGetDestination(Continent, Country, State, City, StatusDate);
		String xml_GetHotelsByDestination = "<?xml version=\"1.0\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:dat=\"http://touricoholidays.com/WSDestinations/2008/08/DataContracts\">"
				+ xml_header + "<soapenv:Body>" + "<dat:GetHotelsByDestination>" + xml_destination
				+ "</dat:GetHotelsByDestination>" + "</soapenv:Body>" + "</soapenv:Envelope>";
		return xml_GetHotelsByDestination;
	}

}
