package au.andrew.palindrome.demo.api.service;

import au.andrew.palindrome.demo.api.model.PalindromeAPIResponse;

public interface PalindromeService {

    PalindromeAPIResponse createResponse(String word, String receivedVia) throws Exception;
}
