package com.hcs.prototype.hcs_prototype;

import android.content.Intent;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by mitch on 2015/09/22.
 */
public class RegisterTest extends ActivityUnitTestCase<RegisterActivity> {
    Intent mLaunchIntent;
    EditText username;
    EditText password;
    EditText name;
    EditText tel;

    public RegisterTest() {
        super(RegisterActivity.class);
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
        name = (EditText) getActivity().findViewById(R.id.name);
        tel = (EditText) getActivity().findViewById(R.id.tel_num);

    }
    /**
     * Test that the login button opens the main activity on correct input
     */
    @MediumTest
    public void testCorrectDetails() {
        //startActivity(mLaunchIntent, null, null);
        username.setText("test5");
        password.setText("test3");
        name.setText("bob");
        tel.setText("2");
        final Button launchLoginButton =
                (Button) getActivity()
                        .findViewById(R.id.email_sign_in_button);
        launchLoginButton.performClick();

        final Intent launchIntent = getStartedActivityIntent();
        assertNotNull("Intent was null", launchIntent);
        assertTrue("Wrong Activity Launched", launchIntent.resolveActivity(getInstrumentation().getTargetContext().getPackageManager()).getClassName().equals(MainActivity.class.getCanonicalName()));
        assertTrue("user incorrect1", User.getUser().getUsername().equals("test5"));
        assertTrue("user incorrect2", User.getUser().getName().equals("bob"));
        assertTrue("user incorrect3", User.getUser().getScore()== 0);
        assertTrue("user incorrect4", User.getUser().getTel().equals("2"));
    }

    /**
     * Test that the register button opens no activity when existing user
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
        assertNull("Intent was not null", launchIntent);
        //assertFalse("Wrong Activity Launched", launchIntent.resolveActivity(getInstrumentation().getTargetContext().getPackageManager()).getClassName().equals(MainActivity.class.getCanonicalName()));
    }
    /**
     * Test that the register button when no password
     */
    @MediumTest
    public void testIncorrectDetails2() {
        username.setText("test456778900");
        password.setText("");
        final Button launchLoginButton =
                (Button) getActivity()
                        .findViewById(R.id.email_sign_in_button);
        launchLoginButton.performClick();

        final Intent launchIntent = getStartedActivityIntent();
        assertNull("Intent was not null", launchIntent);
        //assertFalse("Wrong Activity Launched", launchIntent.resolveActivity(getInstrumentation().getTargetContext().getPackageManager()).getClassName().equals(MainActivity.class.getCanonicalName()));
    }
}
