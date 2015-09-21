package com.hcs.prototype.hcs_prototype;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


/**
 * Created by mitch on 2015/08/16.
 */
public class UserTest {
    private User user;

    /**
     * Sets up the test fixture.
     * (Called before every test case method.)
     */
    @Before
    public void setUp(){
        user = new User("username", "password", this);
        //User.createUser("UN", "pass", null);
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
        //Test the singleton
        //assertEquals(false, User.getUser().login());
    }
    @Test
    public void testRegister() {
        assertEquals(false, user.register()); //because no context
        //test the singleton
        //assertEquals(false, User.getUser().register());
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
        //test the singleton
        /*assertEquals(100, User.getUser().getScore());
        user.setScore(123);
        assertEquals(123, User.getUser().getScore());
        user.incScore(123);
        assertEquals(246, User.getUser().getScore());*/
    }
}