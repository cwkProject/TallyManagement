package com.port.tally.management.bean;

public class FormPicFile {
	 
	private byte[] data;
 
	private String formname;
 
	private String contentType = "image/jpeg"; 

	public FormPicFile(byte[] data, String formname,
			String contentType) {
		this.data = data;
		
		this.formname = formname;
		if (contentType != null)
			this.contentType = contentType;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getFormname() {
		return formname;
	}

	public void setFormname(String formname) {
		this.formname = formname;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

}
