package com.hcs.prototype.hcs_prototype;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


/**
 * Created by mitch on 2015/08/16.
 */
public class HistoryTest {
    private History hist;

    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @Before
    public void setUp(){
        //hist = new History(1, "id");
        //hist.addStep("4");
    }

    /**
     * Tears down the test fixture.
     * (Called after every test case method.)
     */
    @After
    public void tearDown() {
        hist = null;
    }
    @Test
    public void checkInHist() {
        //assertEquals(true, hist.inHist("4"));
        //assertEquals(false, hist.inHist("1"));
    }
    @Test
    public void checkLength() {
        //assertEquals(1, hist.length());
    }
}