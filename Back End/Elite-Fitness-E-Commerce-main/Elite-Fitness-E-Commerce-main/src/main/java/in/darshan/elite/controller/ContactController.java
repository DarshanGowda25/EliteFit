package in.darshan.elite.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.darshan.elite.entity.Contact;
import in.darshan.elite.entity.UserPrincipal;
import in.darshan.elite.exception.AdminException;
import in.darshan.elite.repository.ContactRepository;
import in.darshan.elite.request.ContactRequest;
import in.darshan.elite.request.contactUpdateRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api/contact")
public class ContactController {
	
	@Autowired
	private ContactRepository repo;
	
	@PostMapping("/register")
	public ResponseEntity<?> registerContact(@RequestBody ContactRequest contact){
	
		
		Contact newContact = new Contact();
		newContact.setName(contact.getName());
		newContact.setEmail(contact.getEmail());
		newContact.setStatus("unanswered");
		newContact.setPhoneNo(Long.parseLong(contact.getPhone()));
		newContact.setMessage(contact.getMsg());
		Map<String, String> response = new HashMap<>();
		String status = "";
		Integer id = repo.save(newContact).getContact_id();
		if(id>0) {
			status = "Message Successfully Sent ";
		}else {
			status = "Failed ";
		}
		
		response.put("status", status);
		return ResponseEntity.ok(response);
				
	}
	
	@GetMapping("/getAll")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAllQueries(@AuthenticationPrincipal UserPrincipal userDetails){
		if(userDetails == null) {
			throw new AdminException("Unautherized");
		}
		
		List<Contact> queries = repo.findAll();
		return ResponseEntity.ok(queries);
		
	}
	
	@PatchMapping("update")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateStatus(@AuthenticationPrincipal UserPrincipal userDetails,@RequestBody contactUpdateRequest request){
		if(userDetails == null) {
			throw new AdminException("Unautherized");
		}
		if(request.getId() == null || request.getStatus()==null) {
			throw new AdminException("Invalid Request");
		}
		Contact contact =  repo.findById(request.getId()).get();
		if(contact == null) {
			throw new AdminException("No such query");
		}
		contact.setStatus(request.getStatus());
		repo.save(contact);
		return ResponseEntity.ok("updated successfully");
		
	}

	
	
	
	
	
	

}
