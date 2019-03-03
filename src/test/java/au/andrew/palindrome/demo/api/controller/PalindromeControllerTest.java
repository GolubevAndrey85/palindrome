package au.andrew.palindrome.demo.api.controller;

import au.andrew.palindrome.demo.api.model.PalindromeAPIInput;
import au.andrew.palindrome.demo.api.model.PalindromeAPIResponse;
import au.andrew.palindrome.demo.api.service.PalindromeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.StringUtils;


import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PalindromeController.class)
public class PalindromeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PalindromeService palindromeService;

    @Test
    public void checkIfPalindrome() throws Exception {
        testPalindrome("racEcaR", "racecar", true, 200);
        testPalindrome("pAlinDrome", "emordnilap", false, 200);
        testPalindrome(null, null, null, 400);
        PalindromeAPIInput input = new PalindromeAPIInput();
        input.setWord("racEcaR");
        testPalindromeWithJSON(input, true);
        input.setWord("pAlinDrome");
        testPalindromeWithJSON(input, false);
    }

    private void testPalindrome(String testWord, String testRversedWord, Boolean isPalindrome, int expectedStatus) throws Exception {
        String uri = "/palindrome/check?word=%word%";
        String expectedResponse;
        PalindromeAPIResponse response = new PalindromeAPIResponse();
        if(testWord != null) {
            uri = uri.replace("%word%", testWord);
            expectedResponse = "{receivedWord:%word%,reversedWord:%reversed%,receivedVia:parameters,palindrome:%isPalindrome%,errorMessage:NoErrors}";
            response.setErrorMessage("NoErrors");
        } else {
            uri = "/palindrome/check";
            expectedResponse = "{receivedWord:null,reversedWord:null,receivedVia:null,palindrome:false,errorMessage:\"No word's been received\"}";
        }
        response.setPalindrome(isPalindrome);
        response.setReceivedVia("parameters");
        response.setReceivedWord(testWord);
        response.setReversedWord(testRversedWord);
        Mockito.when(palindromeService.createResponse(Mockito.anyString(), Mockito.anyString())).thenReturn(response);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(expectedStatus, status);
        if(testWord != null) {
            expectedResponse = StringUtils.replace(expectedResponse, "%word%", testWord);
            expectedResponse = StringUtils.replace(expectedResponse, "%reversed%", testRversedWord);
            expectedResponse = StringUtils.replace(expectedResponse, "%isPalindrome%", isPalindrome.toString());
        }
        JSONAssert.assertEquals(expectedResponse, mvcResult.getResponse()
                .getContentAsString(), true);
    }

    private void testPalindromeWithJSON(PalindromeAPIInput input, Boolean isPalindrome) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String uri = "/palindrome/check";
        PalindromeAPIResponse response = new PalindromeAPIResponse();
        response.setPalindrome(isPalindrome);
        response.setReceivedVia("body");
        response.setReceivedWord(input.getWord());
        response.setReversedWord(new StringBuilder(response.getReceivedWord()).reverse().toString());
        response.setErrorMessage("NoErrors");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(input)).contentType(MediaType.APPLICATION_JSON);
        Mockito.when(palindromeService.createResponse(Mockito.anyString(), Mockito.anyString())).thenReturn(response);
        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String expectedResponse = "{receivedWord:%word%,reversedWord:%reversed%,receivedVia:body,palindrome:%isPalindrome%,errorMessage:NoErrors}";
        expectedResponse = StringUtils.replace(expectedResponse, "%word%", input.getWord());
        expectedResponse = StringUtils.replace(expectedResponse, "%reversed%", response.getReversedWord());
        expectedResponse = StringUtils.replace(expectedResponse, "%isPalindrome%", isPalindrome.toString());
        JSONAssert.assertEquals(expectedResponse, mvcResult.getResponse()
                .getContentAsString(), true);
    }
}