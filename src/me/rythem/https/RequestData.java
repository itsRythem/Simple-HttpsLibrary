package me.rythem.https;

public class RequestData {

	protected String property, content;
	
	public RequestData(String property, String content) {
		this.property = property;
		this.content = content;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String data) {
		this.content = data;
	}
	
}
