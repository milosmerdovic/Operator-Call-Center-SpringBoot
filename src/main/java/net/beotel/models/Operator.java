package net.beotel.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class Operator {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "status")
	private String status;
	
	@OneToMany(mappedBy = "operator", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private List <UserHistory> userHistory = new ArrayList<>();
	
	public Operator() {}
	
	public Operator(long id, String name, String phone, String status) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.status = status;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public List<UserHistory> getUserHistory() {
		return userHistory;
	}

	public void setUserHistory(List<UserHistory> userHistory) {
		this.userHistory = userHistory;
	}
	
	
}