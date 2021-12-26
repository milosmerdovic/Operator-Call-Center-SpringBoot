package net.beotel.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.beotel.models.OutgoingCallTemplate;
import net.beotel.repository.OutgoingCallRepository;
import net.beotel.repository.OutgoingCallTemplateRepository;

@Service
public class OutgoingCallTemplateServiceImpl implements OutgoingCallTemplateService{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Autowired
	OutgoingCallTemplateRepository outgoingCallTemplateRepository;
	
	@Autowired
	OutgoingCallRepository outgoingCallRepository;

	@Override
	public List<OutgoingCallTemplate> loadAllTempaltes() {
		
		List<OutgoingCallTemplate> listOfTemplates = outgoingCallTemplateRepository.findAll();
		
		return listOfTemplates;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<String> findOutgoingCallNamesByTemplate(int id) {
		String queryForName="SELECT DISTINCT name FROM OUTGOING_CALL oc "
					+"where oc.OUTGOING_CALL_TEMPLATE_ID=:templateId";
		
		List<String> listNames=new ArrayList<>();
		
		Query query=getEntityManager().createNativeQuery(queryForName);
		
		query.setParameter("templateId", id);
		
		listNames=query.getResultList();
		
		return listNames;
	}

	@Override
	public OutgoingCallTemplate findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
