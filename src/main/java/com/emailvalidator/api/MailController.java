package com.emailvalidator.api;

import com.emailvalidator.client.dto.EmailCollectionResponse;
import com.emailvalidator.service.EmailService;
import com.emailvalidator.gui.MainFrame;
import com.emailvalidator.gui.ProgressPanel;
import com.emailvalidator.gui.ProgressUpdater;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/mail-bulk-validations")
@EnableFeignClients
@CrossOrigin(origins = "http://localhost:3000")
public class MailController {

    private final EmailService mailService;

    public MailController(EmailService mailService) {

        this.mailService = mailService;
    }

    @PostMapping(value = "/emails", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = "application/json")
    public EmailCollectionResponse validateEmails(@RequestParam("file") MultipartFile file) {

        // Create the main frame
        MainFrame mainFrame = new MainFrame();

        // Create the progress panel
        ProgressPanel progressPanel = new ProgressPanel();
        mainFrame.add(progressPanel);

        // Start updating progress
        ProgressUpdater progressUpdater = new ProgressUpdater(progressPanel);
        progressUpdater.start();

        return mailService.checkMails(file);
    }


}
