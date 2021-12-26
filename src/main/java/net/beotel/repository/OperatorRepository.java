package net.beotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.beotel.models.Operator;

@Repository
public interface OperatorRepository extends JpaRepository <Operator, Long>{
	
}
