package net.beotel.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserController {
	
	@GetMapping("/all")
	public String allAccess() {
		return "React-SpringBoot Web App.";
	}
	//
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')") //ovu anotaciju mozemo da koristimo zahvaljujuci @EnableGlobalMethodSecurity(prePostEnabled = true) 
	public String userAccess() {												// iz WebSecurity konfiguracije!
		return "User Content.";
	}
	
	
	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Board.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}
}
