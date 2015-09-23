package com.hcs.prototype.hcs_prototype;

import android.content.Intent;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by mitch on 2015/09/21.
 */
public class LoginTest extends ActivityUnitTestCase<LoginActivity> {
    Intent mLaunchIntent;
    EditText username;
    EditText password;

    public LoginTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ContextThemeWrapper context = new ContextThemeWrapper(getInstrumentation().getTargetContext(), R.style.AppTheme);
        setActivityContext(context);
        mLaunchIntent = new Intent(getInstrumentation()
                .getTargetContext(), LoginActivity.class);
        startActivity(mLaunchIntent, null, null);
        username = (EditText) getActivity().findViewById(R.id.email);
        password = (EditText) getActivity().findViewById(R.id.password);

    }
    /**
     * Test that the login button opens the main activity on correct input
     */
    @MediumTest
    public void testCorrectDetails() {
        //startActivity(mLaunchIntent, null, null);
        username.setText("test");
        password.setText("test");
        final Button launchLoginButton =
                (Button) getActivity()
                        .findViewById(R.id.email_sign_in_button);
        launchLoginButton.performClick();

        final Intent launchIntent = getStartedActivityIntent();
        assertNotNull("Intent was null", launchIntent);
        assertTrue("Wrong Activity Launched", launchIntent.resolveActivity(getInstrumentation().getTargetContext().getPackageManager()).getClassName().equals(MainActivity.class.getCanonicalName()));
        assertTrue("user incorrect", User.getUser().getUsername().equals("test"));
    }

    /**
     * Test that the login button opens no activity when incorrect password
     */
    @MediumTest
    public void testIncorrectDetails() {
        username.setText("test");
        password.setText("bob");
        final Button launchLoginButton =
                (Button) getActivity()
                        .findViewById(R.id.email_sign_in_button);
        launchLoginButton.performClick();

        final Intent launchIntent = getStartedActivityIntent();
        assertNull("Intent was not null2", launchIntent);
        //assertFalse("Wrong Activity Launched", launchIntent.resolveActivity(getInstrumentation().getTargetContext().getPackageManager()).getClassName().equals(MainActivity.class.getCanonicalName()));
    }
    /**
     * Test that the login button opens no activity when incorrect username
     */
    @MediumTest
    public void testIncorrectDetails2() {
        username.setText("bdugsuydg");
        password.setText("test");
        final Button launchLoginButton =
                (Button) getActivity()
                        .findViewById(R.id.email_sign_in_button);
        launchLoginButton.performClick();

        final Intent launchIntent = getStartedActivityIntent();
        //Log.v("FUCK", launchIntent.resolveActivity(getInstrumentation().getTargetContext().getPackageManager()).getClassName());
        assertNull("Intent was not null3", launchIntent);

        //assertFalse("Wrong Activity Launched", launchIntent.resolveActivity(getInstrumentation().getTargetContext().getPackageManager()).getClassName().equals(MainActivity.class.getCanonicalName()));
    }
}
