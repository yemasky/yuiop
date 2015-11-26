package com.api.tour.supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TouricoApi {
	private static Logger logger = LogManager.getLogger(TouricoApi.class.getName());

	public void GetDestination() throws Exception {
		new TouricoService().GetDestination();
	}

	public void GetHotelsbyDestination() throws Exception {
		new TouricoService().GetHotelsbyDestination();
	}
	
	public void GetHotelDetailsV3() throws Exception {
		new TouricoService().GetHotelDetailsV3();
	}
	
	public void newBookHotelV3Demo () throws Exception{
		new TouricoService().newBookHotelV3Demo();
	}

	public static void main(String[] args) throws Exception {
		TouricoApi ta = new TouricoApi();
		ta.newBookHotelV3Demo();
		// ta.GetDestination();
		// System.out.println(c.getProperties("supplier/tourico.xml",
		// "dat:username", "xml"));
	}
}
