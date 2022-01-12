package com.eqriesracingteam.garage.controller;

import com.eqriesracingteam.garage.dto.RegistrationPaperRequestDto;
import com.eqriesracingteam.garage.model.RegistrationPaper;
import com.eqriesracingteam.garage.repository.RegistrationPaperRepository;
import com.eqriesracingteam.garage.service.RegistrationPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/garage/registration_papers")
@CrossOrigin
public class RegistrationPaperController {

    private RegistrationPaperService registrationPaperService;

    @Autowired
    public RegistrationPaperController(RegistrationPaperService registrationPaperService) {
        this.registrationPaperService = registrationPaperService;
    }

    // Methods
    // Get
    @GetMapping
    public ResponseEntity<Object> getDocuments() {
        Iterable<RegistrationPaper> documents = registrationPaperService.getDocuments();
        return ResponseEntity.ok().body(documents);
    }

    // Get file info
    // Upload document - working
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> uploadDocument(MultipartFile file) {
        long newId = registrationPaperService.uploadDocument(file);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newId).toUri();

        return ResponseEntity.created(location).body(location);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity downloadDocument(@PathVariable long id) {
        Resource resource = registrationPaperService.downloadDocument(id);
        String mediaType = "application/octet-stream";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mediaType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    // TODO: 12-1-2022 Testing upload downlaod

}