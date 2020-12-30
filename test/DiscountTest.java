/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import mvc_structure.Model.Discount;
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
public class DiscountTest {
   
    Discount d;  
    
    public DiscountTest() {
    }
    
    @Before
    public void setUp() {
        d=new Discount(">",30.00,100);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getCondition method, of class Discount.
     */
    @Test
    public void testGetCondition() {
        System.out.println("getCondition");
        assertEquals(">",d.getCondition());
    }

    /**
     * Test of setCondition method, of class Discount.
     */
    @Test
    public void testSetCondition() {
        System.out.println("setCondition");
        d.setCondition("<");
        assertEquals("<",d.getCondition());
    }

    /**
     * Test of getConditionValue method, of class Discount.
     */
    @Test
    public void testGetConditionValue() {
        System.out.println("getConditionValue");
       assertEquals(30.00,d.getConditionValue(),0);
    }

    /**
     * Test of setConditionValue method, of class Discount.
     */
    @Test
    public void testSetConditionValue() {
        System.out.println("setConditionValue");
       d.setConditionValue(40.00);
       assertEquals(40.00,d.getConditionValue(),0);
    }

    /**
     * Test of getAmount method, of class Discount.
     */
    @Test
    public void testGetAmount() {
        System.out.println("getAmount");
        assertEquals(100,d.getAmount(),0);
    }

    /**
     * Test of setAmount method, of class Discount.
     */
    @Test
    public void testSetAmount() {
        System.out.println("setAmount");
        d.setAmount(200);
        assertEquals(200,d.getAmount(),0);
    }
    
}
