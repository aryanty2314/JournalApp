package net.company.unique.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests
{

    @Autowired
    private  EmailService emailService;

    @Test
    public void sendEmailTest()
    {
        emailService.sendEmail("tyagiaryan2314@gmail.com","hello","Checking the functionality");
    }
}
