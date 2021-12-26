package net.beotel.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="OUTGOING_CALL")
public class OutgoingCall {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="CONTACT_NAME")
	private String contactName;
	
	@Column(name="CONTACT_PHONE")
	private String contactPhone;
	
	@Column(name="CREATION_DATE")
	private LocalDate creationDate;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="NOTE")
	private String note;
	

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OUTGOING_CALL_TEMPLATE_ID")
	private OutgoingCallTemplate outgoingCallTemplate;
	
	public OutgoingCall(String contactName, String contactPhone, LocalDate date, OutgoingCallTemplate out, String name, String note) {
		this.contactName = contactName;
		this.contactPhone = contactPhone;
		this.creationDate = date;
		this.outgoingCallTemplate = out;
		this.name=name;
		this.note=note;
	}
	
	

	public OutgoingCall(String contact_name, String contact_phone) {
		super();
		this.contactName = contact_name;
		this.contactPhone = contact_phone;
	}

	
	public OutgoingCall(String contact_name, String contact_phone, OutgoingCallTemplate outgoingCallTemplate) {
		this.contactName = contact_name;
		this.contactPhone = contact_phone;
		this.outgoingCallTemplate = outgoingCallTemplate;
	}

	public OutgoingCall(Long id, String contact_name, String contact_phone, OutgoingCallTemplate outgoingCallTemplate) {
		super();
		this.id = id;
		this.contactName = contact_name;
		this.contactPhone = contact_phone;
		this.outgoingCallTemplate = outgoingCallTemplate;
	}
	  
	 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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



	public OutgoingCallTemplate getOutgoingCallTemplate() {
		return outgoingCallTemplate;
	}



	public void setOutgoingCallTemplate(OutgoingCallTemplate outgoingCallTemplate) {
		this.outgoingCallTemplate = outgoingCallTemplate;
	}
	
	public LocalDate getCreationDate() {
		return creationDate;
	}



	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
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




}
