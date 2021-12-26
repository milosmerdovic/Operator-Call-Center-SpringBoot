package net.beotel.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.beotel.models.Operator;
import net.beotel.repository.OperatorRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/operatorList/")
public class OperatorListController {
	
	@Autowired
	private OperatorRepository operatorRepository;

	@GetMapping("/list")
    public List<Operator> getAllUsers() {
        return operatorRepository.findAll();
    }
	
	
	
}
