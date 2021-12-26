package net.beotel.controllers;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.beotel.models.OutgoingCall;
import net.beotel.models.OutgoingCallTemplate;
import net.beotel.payload.response.MessageResponse;
import net.beotel.repository.OutgoingCallRepository;
import net.beotel.repository.OutgoingCallTemplateRepository;
import net.beotel.services.OutgoingCallTemplateService;
import net.beotel.util.ExcelHelper;
import net.beotel.util.OutgoingCallContactList;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/outgoing")
public class OutgoingCallController {

	private static final String PHONE_NUMBER_COL_NAME = "Telefon";
	private static final String NAME_SURNAME_COL_NAME = "Ime";
	
	private static final String PHONE_NUMBER_REGEX="^0([1-3]|6)[0-9]{6,8}$";
	private static final String DATE_FORMAT="dd/MM/yyyy";
	
	private List<OutgoingCallContactList> failedRows=new ArrayList<>();



	@Autowired
	OutgoingCallRepository outgoingCallRepository;

	@Autowired
	OutgoingCallTemplateRepository outgoingCallTemplateRepository;
	
	@Autowired
	OutgoingCallTemplateService outgoingCallTemplateService;

	/*
	 * @GetMapping("/outgoingCallTemplate"signin)
	 * 
	 * @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	 * //ovu anotaciju mozemo da koristimo
	 * zahvaljujuci @EnableGlobalMethodSecurity(prePostEnabled = true) public String
	 * outgoingCallTemplateAccess() { // iz WebSecurity konfiguracije! return
	 * "OutgoingCallTemplate Content."; }
	 */

	@GetMapping("/outgoingCallTemplate")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	// //ovu anotaciju mozemo da koristimo zahvaljujuci
	// @EnableGlobalMethodSecurity(prePostEnabled = true)
	public Iterable<OutgoingCallTemplate> findTemplateList() {
		try {
			Iterable<OutgoingCallTemplate> list = outgoingCallTemplateRepository.findAll(Sort.by("active").descending().and(Sort.by("id")));
			return list;

		} catch (Exception e) {

		}
		return null;
	}
	
	@PostMapping("/outgoingCallTemplate")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> createNewTempalte(@RequestBody OutgoingCallTemplate template) {
		outgoingCallTemplateRepository.save(template);
		return ResponseEntity.ok(new MessageResponse("Uspesno snimljen novi templejt u bazu!"));
	}
	
	@PutMapping("/outgoingCallTemplate/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<OutgoingCallTemplate> updateTempalte(@RequestBody OutgoingCallTemplate template) {
		OutgoingCallTemplate templ = outgoingCallTemplateRepository.save(template);
		return ResponseEntity.ok().body(templ);
	}
	

	/*
	 * @GetMapping("/outgoingCallList") public Iterable<OutgoingCallTemplate>
	 * sendTemplateList() { try { Iterable<OutgoingCallTemplate> list =
	 * outgoingCallTemplateRepository.findAll(); return list; } catch (Exception ex)
	 * { } return null;
	 * 
	 * }
	 */	
	@DeleteMapping("/outgoingCallTemplate/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<?> deleteTemplate(@PathVariable int id) {
		List <String> lista = outgoingCallTemplateService.findOutgoingCallNamesByTemplate(id);
		if(lista.isEmpty()) {
			outgoingCallTemplateRepository.deleteById(Long.valueOf(id));
			return ResponseEntity.ok(1);
		} else 
		return ResponseEntity.ok(0);
	}
	
	
	/*Lista koja vraca stare liste, da bi mogli da je popune*/
	@PostMapping("/oldOutgoingCallList")
	public List<String> sendOldOutgoingCallLists(@RequestBody int templateId){
		List<String> oldList=outgoingCallTemplateService.findOutgoingCallNamesByTemplate(templateId);
		
		return oldList;
	}

	
	/*Obradjuje podatke sa forme (kreiranje liste poziva)*/
	@PostMapping("/postOutgoingCallList")
	public ResponseEntity<?> postOutgoingCallList(@ModelAttribute OutgoingCallContactList list) { 
		String errorMessage="";
		
		List<String> oldList=outgoingCallTemplateService.findOutgoingCallNamesByTemplate(list.getTemplateId());
		
		boolean isExtendingOldList=list.isOldList();
		boolean nameAlreadyExists=false;
		
		
		for(String name:oldList) {
			if(name.equalsIgnoreCase(list.getName())) {
				nameAlreadyExists=true;
			}
		}
				
		/*Ako je izabrana opcija "Nova lista", a naziv liste vec postoji u bazi*/
		if(!isExtendingOldList && nameAlreadyExists) {
			errorMessage="Naziv liste već postoji. ";
			return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(new MessageResponse(errorMessage));
		}
		
		
		
		// Dohvatanje fajlova i konvertovanje
		ClassLoader classLoader = getClass().getClassLoader();

		MultipartFile uploadedFile = list.getFile();
		File outgoingCallFile = new File(classLoader.getResource(".").getFile() + "/template.xlsx"); // pronalazi resources paket i cuva fajl do sledeceg builda
		try {
			if (outgoingCallFile.createNewFile()) {
				System.out.println("File is created!");
			} else {
				System.out.println("File already exists.");
			}
			 
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			uploadedFile.transferTo(outgoingCallFile); // konvertovanje iz MultipartFile u File, da bi moglo da se
														// parsira
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}

		// Parsiranje fajla
		ExcelHelper excelHelper = new ExcelHelper();
		Map<String, Integer> columnMap = new HashMap<>();
		int idTemplate = list.getTemplateId();
		String listName=list.getName();
		String note=list.getNote();
		
		int countValidRows=0;

		//Procesuiranje redova u fajlu i cuvanje podataka u bazu
		List<XSSFRow> rowList = excelHelper.parseXLSFile(outgoingCallFile, columnMap);
		setFailedRows(processRowsAndSave(rowList, columnMap, idTemplate,listName,note));
		
		//countSuccessRows=rowList.size()-failedRows.size();
		countValidRows=countValidRows(rowList,failedRows);
		errorMessage="Sačuvani su ispravni redovi. Ukupno ispravnih redova: "+countValidRows+". "+System.lineSeparator()+"Neispravni redovi su: "+System.lineSeparator();

		
		for(OutgoingCallContactList row:failedRows) {
			errorMessage+=row.getContactName().isEmpty()?"Ime: - | ":"Ime: "+row.getContactName()+" | ";
			errorMessage+=row.getContactPhone().isEmpty()?"Telefon: - "+System.lineSeparator():"Telefon: "+row.getContactPhone()+System.lineSeparator();
		}
		
		if(failedRows.isEmpty()) {
			return ResponseEntity.ok(new MessageResponse("Uspešno prosleđeni podaci."));
		}
		if(countValidRows==0) {
			return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(new MessageResponse("Svi podaci iz učitanog šablona su neispravni. Pokušajte ponovo."));
		}
		
		return ResponseEntity.ok(new MessageResponse(errorMessage));
		
	}


	/*Obradjuje podatke iz excel fajla i ujedno cuva i ostale podatke u bazu. Vrsi validaciju podataka.*/
	private List<OutgoingCallContactList> processRowsAndSave(List<XSSFRow> rowList, Map<String, Integer> columnMap, int idTemplate, String listName,
			String note) {
		String contactPhone = null; 
		String contactName = null;
		String dateOfImport = null;
		List<OutgoingCallContactList> failedRows=new ArrayList<>();
		
		//boolean isValid=true;
		
		//Formatiranje podataka iz excela
		DataFormatter formatter = new DataFormatter(Locale.US);
		
		//Dohvata danasnji datum (creation date)
		LocalDate date = LocalDate.now();
	    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
	    dateOfImport = date.format(dateFormatter);
	    LocalDate parsedDate = LocalDate.parse(dateOfImport, dateFormatter);
		
	    //Dohvata templejt na osnovu njegovog ID-a
		Long templateId=Long.valueOf(idTemplate);
		OutgoingCallTemplate out=outgoingCallTemplateRepository.getOne(templateId);
		
		for (XSSFRow row : rowList) {
			contactName = formatter.formatCellValue(row.getCell(columnMap.get(NAME_SURNAME_COL_NAME)));
			contactPhone = formatter.formatCellValue(row.getCell(columnMap.get(PHONE_NUMBER_COL_NAME)));
			
			OutgoingCall outgoingCall = new OutgoingCall(contactName, contactPhone, parsedDate, out, listName,note);
			
			/*Ako nisu popunjena sva polja ili se format telefona ne poklapa sa regexom*/
			if((contactPhone.trim().length()==0 || contactPhone==null) || (contactName.trim().length()==0 || contactName==null) || 
					!contactPhone.matches(PHONE_NUMBER_REGEX)) {
				failedRows.add(new OutgoingCallContactList(contactName,contactPhone)); //dodaje u listu sve redove koji nisu prosli validaciju
			}
			/*U suprotnom upisuje validne podatke u bazu*/
			else {
				outgoingCallRepository.save(outgoingCall);
			}

		}
		return failedRows;
	}
	
	private int countValidRows(List<?> rowList, List<?> failedRows) {
		return rowList.size()-failedRows.size();
	}
	
	
	public List<OutgoingCallContactList> getFailedRows() {
		return failedRows;
	}

	public void setFailedRows(List<OutgoingCallContactList> failedRows) {
		this.failedRows = failedRows;
	}



}
