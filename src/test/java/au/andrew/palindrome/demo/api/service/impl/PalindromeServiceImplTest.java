package au.andrew.palindrome.demo.api.service.impl;

import au.andrew.palindrome.demo.api.service.PalindromeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration
public class PalindromeServiceImplTest {

    @Configuration
    static class AccountServiceTestContextConfiguration {
        @Bean
        public PalindromeService palindromeService() {
            return new PalindromeServiceImpl();
        }
    }

    @Autowired
    PalindromeService palindromeService;

    @Test
    public void createResponse() throws Exception {
        assertEquals("car", palindromeService.createResponse("rac", "parameters").getReversedWord());
        assertEquals("reverse", palindromeService.createResponse("esrever", "parameters").getReversedWord());
        assertNotEquals(" reverse ", palindromeService.createResponse("esrever", "parameters").getReversedWord());}
}