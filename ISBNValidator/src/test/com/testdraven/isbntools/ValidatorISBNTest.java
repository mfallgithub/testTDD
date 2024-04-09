package test.com.testdraven.isbntools;

import com.testdraven.isbntools.ValidatorISBN;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorISBNTest {

    @Test
    public void checkAValidISBN() {
        ValidatorISBN validator = new ValidatorISBN();
        boolean result = validator.checkISBN("0140449116");
        assertTrue(result, "First value");
        result = validator.checkISBN("0140177396");
        assertTrue(result, "Second value");
    }

    @Test
    public void CheckAValid13DigitsISBN(){
        ValidatorISBN validator = new ValidatorISBN();
        boolean result = validator.checkISBN("9781853266666");
        //assertTrue(result, "First value");
    }

    @Test
    public void ISBNNumbersEndingInAnXAreValid(){
        ValidatorISBN validator = new ValidatorISBN();
        boolean result = validator.checkISBN("012000030X");
        assertTrue(result);
    }

    @Test
    public void checkAnInValidISBN() {
        ValidatorISBN validator = new ValidatorISBN();
        boolean result = validator.checkISBN("0140449117");
        assertFalse(result);
    }

    @Test
    public void nineDigitISBNsAreNotAllowed() {
        ValidatorISBN validator = new ValidatorISBN();
        assertThrows(NumberFormatException.class, () -> {
            validator.checkISBN("123456789");
                }
        );
    }

    @Test
    public void nonNumericISBNsAreNotAllowed() {
        ValidatorISBN validator = new ValidatorISBN();
        assertThrows(NumberFormatException.class, () -> {
            validator.checkISBN("Helloword");
        });
    }
}
