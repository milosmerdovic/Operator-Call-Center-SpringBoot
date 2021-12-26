package net.beotel.services;

import java.util.List;

import net.beotel.models.OutgoingCallTemplate;

public interface OutgoingCallTemplateService {

	OutgoingCallTemplate findById(int id);
	
	/* dohvata sve postojece templejte iz baze ako postoje*/
	List<OutgoingCallTemplate> loadAllTempaltes();
	
	List<String> findOutgoingCallNamesByTemplate(int id);
	
}
