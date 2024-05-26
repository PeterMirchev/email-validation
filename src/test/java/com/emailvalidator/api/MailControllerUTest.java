package com.emailvalidator.api;

import com.emailvalidator.service.EmailService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class MailControllerUTest {

    @Mock
    private EmailService emailService;
    @InjectMocks
    private MailController mailController;

    public static Stream<Arguments> fileWithListOfEmailsArguments() throws IOException {

        MultipartFile csvFile = new MockMultipartFile("ListOfEmails.csv", new FileInputStream("src/test/resources/ListOfEmails.csv"));
        MultipartFile jsonFile = new MockMultipartFile("ListOfEmails.json", new FileInputStream("src/test/resources/ListOfEmails.json"));
        MultipartFile txtFile = new MockMultipartFile("ListOfEmails.txt", new FileInputStream("src/test/resources/ListOfEmails.txt"));

        return Stream.of(
                Arguments.of(csvFile),
                Arguments.of(jsonFile),
                Arguments.of(txtFile)
        );
    }


    @ParameterizedTest
    @MethodSource("fileWithListOfEmailsArguments")
    void givenValidCsvFile_whenParseFile_thenHappyPath(MultipartFile file) {

        assertDoesNotThrow( () -> mailController.validateEmails(file));
    }
}
