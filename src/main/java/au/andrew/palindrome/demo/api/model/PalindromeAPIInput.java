package au.andrew.palindrome.demo.api.model;

import au.andrew.palindrome.demo.api.advice.WordValidator;

public class PalindromeAPIInput {

    @WordValidator(field="word", message ="")
    private String word;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
