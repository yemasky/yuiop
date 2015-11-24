package com.webservice.client;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SoapHttpClient {
	private static Logger logger = LogManager.getLogger(SoapHttpClient.class.getName());
	public static void main(String[] args) throws Exception {
		SoapHttpClient hc = new SoapHttpClient();
		String soapRequestData = getRequestXml();
		String soapURL = "http://destservices.touricoholidays.com/DestinationsService.svc?wsdl";
		String[][] soapHeader = new String[][]{{"SOAPAction", "http://touricoholidays.com/WSDestinations/2008/08/Contracts/IDestinationContracts/GetDestination"}};
		hc.httpClient(soapURL, soapRequestData, soapHeader);
	}

	public String httpClient(String soapURL, String soapRequestData, String[][] soapHeader) throws Exception {
		if(soapURL == null) {
			 throw new Exception("soapURL is null.");
		}
		HttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(soapURL);
		
		if(soapRequestData != null) {
			logger.info(soapRequestData);
			byte[] b_data = soapRequestData.getBytes("UTF-8");
			InputStream ISdata = new ByteArrayInputStream(b_data, 0, b_data.length);
			InputStreamEntity ISEdata = new InputStreamEntity(ISdata);	
			post.setEntity(ISEdata); 
		}
		if(soapHeader.length > 0) {
			for(String[] header : soapHeader) {
				post.addHeader(header[0], header[1]);
			}
		}
		post.addHeader("Accept-Encoding", "gzip,deflate");
		post.addHeader("Content-type", "text/xml");
		HttpResponse response = client.execute(post);

		HttpEntity entity = response.getEntity();
		Header ceheader = entity.getContentEncoding();
		HttpEntity Gizpresponse = null;
		if (ceheader != null) {
			for (HeaderElement element : ceheader.getElements()) {
				if (element.getName().equalsIgnoreCase("gzip")) {
					Gizpresponse = new GzipDecompressingEntity(response.getEntity());
					break;
				}
			}
		}
		if (Gizpresponse == null)
			Gizpresponse = response.getEntity();
		String S_result =  EntityUtils.toString(Gizpresponse);
		//logger.debug(S_result);
		return S_result;
	}

	private static String getRequestXml() {
		StringBuilder sb = new StringBuilder(
				"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:dat=\"http://touricoholidays.com/WSDestinations/2008/08/DataContracts\">");
		sb.append("<soapenv:Header>");
		sb.append("<dat:LoginHeader>");
		sb.append("<dat:username>UBtour15</dat:username>");
		sb.append("<dat:password>UBT@th2015</dat:password>");
		sb.append("<dat:culture>en_US</dat:culture>");
		sb.append("<dat:version>9</dat:version>");
		sb.append("</dat:LoginHeader>");
		sb.append("</soapenv:Header>");
		sb.append("<soapenv:Body>");
		sb.append("<dat:GetDestination>");
		sb.append("<dat:Destination>");
		sb.append("<dat:Continent></dat:Continent>");
		sb.append("<dat:Country></dat:Country>");
		sb.append("<dat:State></dat:State>");
		sb.append("<dat:City></dat:City>");
		sb.append("<dat:Providers>");
		sb.append("<dat:ProviderType>Default</dat:ProviderType>");
		sb.append("</dat:Providers>");
		// sb.append("<dat:StatusDate>2015-12-17T00:00:00</dat:StatusDate>");
		sb.append("</dat:Destination>");
		sb.append("</dat:GetDestination>");
		sb.append("</soapenv:Body>");
		sb.append("</soapenv:Envelope>");
		// System.out.println(sb);
		return sb.toString();
	}
}