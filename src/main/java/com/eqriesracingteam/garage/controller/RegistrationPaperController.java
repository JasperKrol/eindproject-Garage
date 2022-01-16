package com.eqriesracingteam.garage.controller;

import com.eqriesracingteam.garage.dto.RegistrationPaperResponseFile;
import com.eqriesracingteam.garage.exceptions.FileStorageException;
import com.eqriesracingteam.garage.exceptions.ResponseMessage;
import com.eqriesracingteam.garage.model.RegistrationPaper;
import com.eqriesracingteam.garage.service.RegistrationPaperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/garage/registration_papers")
@CrossOrigin
public class RegistrationPaperController {

    private RegistrationPaperService registrationPaperService;

    @Autowired
    public RegistrationPaperController(RegistrationPaperService registrationPaperService) {
        this.registrationPaperService = registrationPaperService;
    }

    // TODO: 16-1-2022 testing from callicoder
    @GetMapping
    public ResponseEntity<List<RegistrationPaperResponseFile>> getPictures() {

        List<RegistrationPaperResponseFile> files = registrationPaperService.getPictures().map(picture -> {

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/registration_papers/").path(String.valueOf(picture.getId())).toUriString();

            return new RegistrationPaperResponseFile(picture.getName(), fileDownloadUri, picture.getType(), picture.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);

    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getPicture(@PathVariable("id") Long id) {
        RegistrationPaper registrationPaper = registrationPaperService.getPicture(id);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename =\"" + registrationPaper.getName() + "\"").body(registrationPaper.getData());
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadPicture(@RequestBody MultipartFile file) {

        String message = "";

        try {

            registrationPaperService.storePicture(file);

            var registrationPaper = registrationPaperService.getPictureByNameEquals(file.getOriginalFilename());

            message = "Upload successfully " + registrationPaper;

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));

        } catch (Exception e) {

            message = "Could not upload the file: " + file.getOriginalFilename() + "!";

            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));

        }
    }

    @DeleteMapping("/{id}")
    public void deletePicture(@PathVariable("id") Long id) {
        registrationPaperService.deletePicture(id);
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = registrationPaperService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
