package au.andrew.palindrome.demo.api.controller;

import au.andrew.palindrome.demo.api.model.PalindromeAPIInput;
import au.andrew.palindrome.demo.api.model.PalindromeAPIResponse;
import au.andrew.palindrome.demo.api.service.PalindromeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/palindrome")
public class PalindromeController {

    @Autowired
    PalindromeService palindromeService;

    @GetMapping(value = "/check")
    public ResponseEntity<PalindromeAPIResponse> checkIfPalindrome(@RequestBody(required = false) @Valid PalindromeAPIInput input,
                                                                   @RequestParam(name = "word", required = false) String word) {
        PalindromeAPIResponse response = new PalindromeAPIResponse();
        try {
            if (word == null && input == null) {
                response.setPalindrome(false);
                response.setErrorMessage("No word's been received");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } else if (input == null) {
                if(word.contains(" ")){
                    response.setErrorMessage("word field is not a single word");
                    response.setReceivedVia("parameters");
                    response.setPalindrome(false);
                    response.setReceivedWord(word);
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                } else {
                    response = palindromeService.createResponse(word, "parameters");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            } else {
                response = palindromeService.createResponse(input.getWord(), "body");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            response.setErrorMessage(e.getMessage());
            if(input != null) response.setReceivedWord(input.getWord());
            else response.setReceivedWord(word);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
