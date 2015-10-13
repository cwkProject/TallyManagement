package com.port.tally.management.bean;

public class FormFile {
	/* �ϴ��ļ������ */
	private byte[] data;
	/* �ļ���� */
	private String filname;
	/* �?�ֶ���� */
	private String formname;
	/* �������� */
	private String contentType = "image/jpeg"; 

	public FormFile(String filname, byte[] data, String formname,
			String contentType) {
		this.data = data;
		this.filname = filname;
		this.formname = formname;
		if (contentType != null)
			this.contentType = contentType;
	}
	public FormFile(String filname, byte[] data) {
		this.data = data;
		this.filname = filname;
	
	
	}
	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getFilname() {
		return filname;
	}

	public void setFilname(String filname) {
		this.filname = filname;
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
