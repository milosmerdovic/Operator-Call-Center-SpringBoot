package net.beotel.util;


import org.springframework.web.multipart.MultipartFile;

public class OutgoingCallContactList {
	
	//private String name;
	//private String phone;
	//private Date date;
	private MultipartFile file;
	private int templateId;
	private String name;
	private String note;
	private boolean oldList;
	private int countSuccessRows;
	private String contactName;
	
	
	
	
	public OutgoingCallContactList(String contactName, String contactPhone) {
		super();
		this.contactName = contactName;
		this.contactPhone = contactPhone;
	}


	public int getCountSuccessRows() {
		return countSuccessRows;
	}


	public void setCountSuccessRows(int countSuccessRows) {
		this.countSuccessRows = countSuccessRows;
	}


	public String getContactName() {
		return contactName;
	}


	public void setContactName(String contactName) {
		this.contactName = contactName;
	}


	public String getContactPhone() {
		return contactPhone;
	}


	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}


	private String contactPhone;
	
	public OutgoingCallContactList() {
		
	}
	

	public OutgoingCallContactList(MultipartFile file, int templateId) {
		super();
		this.file = file;
		this.templateId = templateId;
	}
	
	public OutgoingCallContactList(MultipartFile file, int templateId, String name) {
		super();
		this.file = file;
		this.templateId = templateId;
		this.name=name;
	}


	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}



	public int getTemplateId() {
		return templateId;
	}



	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public boolean isOldList() {
		return oldList;
	}


	public void setOldList(boolean oldList) {
		this.oldList = oldList;
	}

	
}
