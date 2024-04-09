package test.com.testdraven.isbntools;

import com.testdraven.isbntools.Book;
import com.testdraven.isbntools.ExternalISBNDataService;
import com.testdraven.isbntools.StockManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StockManagementTests {
    ExternalISBNDataService testWebService;
    ExternalISBNDataService testDatabaseService;
    StockManager stockManager;

    @Before
    public void setup() {
        testWebService = mock(ExternalISBNDataService.class);
        testDatabaseService = mock(ExternalISBNDataService.class);
        stockManager = new StockManager();
        stockManager.setWebService(testWebService);
        stockManager.setDatabaseService(testDatabaseService);
    }

    @Test
    public void testCanGetACorrectLocatorCode() {
        when(testWebService.lookup(anyString())).thenReturn(new Book("0140177396", "Of Mice And Men", "J. Steinbeck"));
        when(testDatabaseService.lookup(anyString())).thenReturn(null);
        String isbn = "0140177396";
        String locatorCode = stockManager.getLocatorCode(isbn);
        assertEquals("7396J4", locatorCode);
    }

    @Test
    public void databaseIsUsedIfDataIsPresent() {
        // given
        when(testDatabaseService.lookup(anyString())).thenReturn(new Book("0140177396", "abc", "abc"));
        String isbn = "0140177396";
        //when
        String locatorCode = stockManager.getLocatorCode(isbn);
        //then
        assertEquals("7396a1", locatorCode);
        verify(testDatabaseService).lookup("0140177396");
        verify(testWebService, never()).lookup(anyString());

    }

    @Test
    public void webserviceIsUsedIfDataIsNotPresentInDatabase() {
        // given
        when(testDatabaseService.lookup("0140177396")).thenReturn(null);
        when(testWebService.lookup("0140177396")).thenReturn(new Book("0140177396", "abc", "abc"));
        StockManager stockManager = new StockManager();
        stockManager.setWebService(testWebService);
        stockManager.setDatabaseService(testDatabaseService);
        String isbn = "0140177396";
        //when
        String locatorCode = stockManager.getLocatorCode(isbn);
        //then
        verify(testDatabaseService).lookup("0140177396");
        verify(testWebService).lookup("0140177396");
    }
}
