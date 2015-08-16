package com.hcs.prototype.hcs_prototype;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


/**
 * Created by mitch on 2015/08/16.
 */
public class UserNormalTest {
    private UserNormal user;

    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @Before
    public void setUp(){
        user = new UserNormal();
    }

    /**
     * Tears down the test fixture.
     * (Called after every test case method.)
     */
    @After
    public void tearDown() {
        user = null;
    }
    @Test
    public void testLogin() {
        assertEquals(false, user.login()); //because no context
    }
    @Test
    public void testRegister() {
        assertEquals(false, user.register()); //because no context
    }
    @Test
    public void testUsername(){
        assertEquals("username", user.getUsername());
    }
    @Test
    public void testScore() {
        assertEquals(100, user.getScore());
        user.setScore(123);
        assertEquals(123, user.getScore());
        user.incScore(123);
        assertEquals(246, user.getScore());
    }
}