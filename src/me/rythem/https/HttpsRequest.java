package me.rythem.https;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class HttpsRequest {

	protected URL url;
	protected HttpsURLConnection connection;
	protected ArrayList<RequestData> requestData = new ArrayList<RequestData>();
	
	public HttpsRequest(URL url) {
		this.url = url;
	}
	
	public HttpsURLConnection getConnection() throws Exception {
		
		if(connection != null) {
			return connection;
		}
		
		return null;
	}
	
	public void openConnection() throws Exception {
        connection = (HttpsURLConnection)url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        
        if(requestData.size() >= 1) {
        	for(RequestData data : requestData) {
        		connection.addRequestProperty(data.getProperty(), data.getContent());
        	}
        }
	}
	
	public void closeConnection() throws Exception {
        if(connection != null) {
        	tryClearRequestData();
        	connection.disconnect();
        	connection = null;
        }
	}
	
	public void addRequestData(String property, String content) throws Exception {
		this.requestData.add(new RequestData(property, content));
	}
	
	public void tryClearRequestData() throws Exception {
        if(requestData.size() >= 1) {
        	this.requestData.clear();
        }
	}
	
	public void setRequestMethod(String method) throws Exception {
        if(connection != null) {
        	connection.setRequestMethod(method);
        }
	}
	
	public String getContent() throws Exception {
		openConnection();
		String data = makeConnectionWithBufferedReader(connection.getInputStream());
		closeConnection();
		
		return data;
	}
	
	public int getResponseCode() throws Exception {
		openConnection();
		Integer data = connection.getResponseCode();
		closeConnection();
		
		return data;
	}

    public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	public ArrayList<RequestData> getRequestData() {
		return requestData;
	}

	public void setRequestData(ArrayList<RequestData> requestData) {
		this.requestData = requestData;
	}

	protected String makeConnectionWithBufferedReader(InputStream connection) throws Exception {
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection, "UTF-8"))) {
			String inputLine;
			StringBuilder stringBuilder = new StringBuilder();
			while ((inputLine = bufferedReader.readLine()) != null) {
				stringBuilder.append(inputLine);
			}
	
			return stringBuilder.toString();
		}
	}

}
