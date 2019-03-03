package au.andrew.palindrome.demo.api.model;

public class PalindromeAPIResponse {

    private Boolean isPalindrome;
    private String receivedWord;
    private String reversedWord;
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getReceivedVia() {
        return receivedVia;
    }

    public void setReceivedVia(String receivedVia) {
        this.receivedVia = receivedVia;
    }

    private String receivedVia;

    public String getReceivedWord() {
        return receivedWord;
    }

    public void setReceivedWord(String receivedWord) {
        this.receivedWord = receivedWord;
    }

    public String getReversedWord() {
        return reversedWord;
    }

    public void setReversedWord(String reversedWord) {
        this.reversedWord = reversedWord;
    }

    public Boolean getPalindrome() {
        return isPalindrome;
    }

    public void setPalindrome(Boolean palindrome) {
        isPalindrome = palindrome;
    }


}
