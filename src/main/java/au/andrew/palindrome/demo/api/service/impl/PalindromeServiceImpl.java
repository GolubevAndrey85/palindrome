package au.andrew.palindrome.demo.api.service.impl;

import au.andrew.palindrome.demo.api.model.PalindromeAPIResponse;
import au.andrew.palindrome.demo.api.service.PalindromeService;
import org.springframework.stereotype.Service;

@Service
public class PalindromeServiceImpl implements PalindromeService {

    private String reverse(String word) {
        return new StringBuilder(word).reverse().toString();
    }

    @Override
    public PalindromeAPIResponse createResponse(String word, String receivedVia) {
        PalindromeAPIResponse response = new PalindromeAPIResponse();
        response.setReceivedWord(word);
        response.setReversedWord(this.reverse(word));
        response.setReceivedVia(receivedVia);
        response.setPalindrome(word.trim().toLowerCase().equals(response.getReversedWord().trim().toLowerCase()));
        response.setErrorMessage("NoErrors");
        return response;
    }
}
