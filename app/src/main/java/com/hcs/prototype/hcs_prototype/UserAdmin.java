package com.hcs.prototype.hcs_prototype;

import android.content.Context;

/**
 * Created by mitch on 2015/08/16.
 */
public class UserAdmin extends User {
    /**
     * Create a new user object
     */
    private UserAdmin (){
        super();
    }
    /**
     * Create a new user object
     * @param username the user's username
     * @param password the user's password
     * @param context the context which the data will be obtained from
     */
    private UserAdmin(String username, String password, Context context){
        super(username, password, context);
    }
}
