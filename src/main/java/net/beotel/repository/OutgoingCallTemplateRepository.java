package net.beotel.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.beotel.models.OutgoingCallTemplate;

@Repository
public interface OutgoingCallTemplateRepository extends JpaRepository<OutgoingCallTemplate, Long> {
	
	/*pronalazi sve templejte u bazi ako postoje*/
	List<OutgoingCallTemplate> findAll (Sort sort);
	
	//Optional<OutgoingCallTemplate> findByOutgoingCallTemplateId(Long id);
	OutgoingCallTemplate findById(int idTemplate);
	
}