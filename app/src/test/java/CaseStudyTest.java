package com.hcs.prototype.hcs_prototype;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


/**
 * Created by mitch on 2015/08/16.
 */
public class CaseStudyTest {
    private CaseStudy cs;

    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @Before
    public void setUp(){
        cs = new CaseStudy(1, "id", "name", "desc", "loc", "type");
    }

    /**
     * Tears down the test fixture.
     * (Called after every test case method.)
     */
    @After
    public void tearDown() {
        cs = null;
    }
    @Test
    public void checkVars() {
        assertEquals(1, cs.getPrimaryKey());
        assertEquals("id", cs.getId());
        assertEquals("name", cs.getName());
        assertEquals("desc", cs.getDesc());
        assertEquals("loc", cs.getLocation());
        assertEquals("type", cs.getType());
    }
    @Test
    public void checkDB() {
        //This doesnt work due to the need for a context - needs more indepth android testing
        //CaseStudy.createDatabase(MainActivity.this);
        //CaseStudy.addCaseStudy("id", "name", "desc", "loc", "type");
    }
    @Test
    public void testToString(){
        assertEquals("id, name\ndesc", cs.toString());
    }
}