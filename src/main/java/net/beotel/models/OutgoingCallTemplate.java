package net.beotel.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import net.beotel.util.TextUtil;

@Entity
@Table(name="OUTGOING_CALL_TEMPLATE")
public class OutgoingCallTemplate {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max = 100)
	private String name;
	
	@NotBlank
	@Column(name="PREDEFINED_RESULTS")
	private String predefinedResults;
	
	@Column(name="ACTIVE")
	private int active;
	
	@Transient
	private List<String> predefinedResultList;
	
	/*
	 * @OneToMany(fetch = FetchType.LAZY, mappedBy = "outgoingCallTemplate")
	 * List<OutgoingCall> outgoingCalls;
	 */
	
	public List<String> getPredefinedResultList() {
		return TextUtil.getInstance().notEmpty(predefinedResults) 
				? TextUtil.getInstance().splitString(predefinedResults, TextUtil.SEPARATOR)
				: null;
	}


	public void setPredefinedResultList(List<String> predefinedResultList) {
		this.predefinedResultList = predefinedResultList;
		if(TextUtil.getInstance().notEmpty(predefinedResultList)) {
			this.predefinedResults = TextUtil.getInstance().stringifyCollectionSeparatorJoining(predefinedResultList, false);
		}
	}


	public OutgoingCallTemplate() {
	
	}
	
	
	public OutgoingCallTemplate(Long id, String name, String predefined_results) {
		super();
		this.id = id;
		this.name = name;
		this.predefinedResults = predefined_results;
	}

	


	/*
	 * public OutgoingCallTemplate(Long id, String name, String predefined_results,
	 * List<OutgoingCall> outgoingCalls) { super(); this.id = id; this.name = name;
	 * this.predefinedResults = predefined_results; this.outgoingCalls =
	 * outgoingCalls; }
	 */
	
	

	public Long getId() {
		return id;
	}


	public int getActive() {
		return active;
	}


	public void setActive(int active) {
		this.active = active;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPredefinedResults() {
		return predefinedResults;
	}

	public void setPredefinedResults(String predefined_results) {
		this.predefinedResults = predefined_results;
	}


}
