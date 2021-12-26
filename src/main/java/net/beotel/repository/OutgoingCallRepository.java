package net.beotel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.beotel.models.OutgoingCall;

public interface OutgoingCallRepository extends JpaRepository<OutgoingCall, Long>{

	Optional<OutgoingCall> findById(Long id);
}
