/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import mvc_structure.Model.Book;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author karma
 */
public class BookTest {
    
    Book b;
    
    public BookTest() {
    }
   
    @Before
    public void setUp() {
        b= new Book("New Book",2019,550.00);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getBookTitle method, of class Book.
     */
    @Test
    public void testGetBookTitle() {
        System.out.println("getBookTitle");
        assertEquals("New Book",b.getBookTitle());
    }

    /**
     * Test of setBookTitle method, of class Book.
     */
    @Test
    public void testSetBookTitle() {
        b.setBookTitle("Book 2");
        assertEquals("Book 2",b.getBookTitle());
    }

    /**
     * Test of getBookYear method, of class Book.
     */
    @Test
    public void testGetBookYear() {
        System.out.println("getBookYear");
        assertEquals(2019,b.getBookYear());
    }

    /**
     * Test of setBookYear method, of class Book.
     */
    @Test
    public void testSetBookYear() {
        b.setBookYear(2018);
        assertEquals(2018,b.getBookYear());
    }

    /**
     * Test of getBookPrice method, of class Book.
     */
    @Test
    public void testGetBookPrice() {
        System.out.println("getBookPrice");
        assertEquals(550.00,b.getBookPrice(),0);
    }

    /**
     * Test of setBookPrice method, of class Book.
     */
    @Test
    public void testSetBookPrice() {
        b.setBookPrice(450.00);
        assertEquals(450.00,b.getBookPrice(),0);
    }
    
}
