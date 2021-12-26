package net.beotel.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String status;
	private String commnet;
	
	@ManyToOne
	@JoinColumn(name="user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Operator operator;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCommnet() {
		return commnet;
	}
	public void setCommnet(String commnet) {
		this.commnet = commnet;
	}
	public Operator getUserID() {
		return operator;
	}
	public void setUserID(Operator operator) {
		this.operator = operator;
	}
	
	
	
}
