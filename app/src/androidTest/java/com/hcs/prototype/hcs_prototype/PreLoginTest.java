package com.hcs.prototype.hcs_prototype;

import android.content.Intent;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.util.Log;
import android.widget.Button;

/**
 * Created by mitch on 2015/09/21.
 */
public class PreLoginTest extends ActivityUnitTestCase<PreLoginActivity> {
    Intent mLaunchIntent;

    public PreLoginTest() {
        super(PreLoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ContextThemeWrapper context = new ContextThemeWrapper(getInstrumentation().getTargetContext(), R.style.AppTheme);
        setActivityContext(context);
        mLaunchIntent = new Intent(getInstrumentation()
                .getTargetContext(), PreLoginActivity.class);
        startActivity(mLaunchIntent, null, null);
        final Button launchLoginButton =
                (Button) getActivity()
                        .findViewById(R.id.mainpage_button_login);
        final Button launchRegisterButton =
                (Button) getActivity()
                        .findViewById(R.id.mainpage_button_register);
    }

    /**
     * Test that the login button opens the login activity
     */
    @MediumTest
    public void testLoginActivityWasLaunchedWithIntent() {
        //startActivity(mLaunchIntent, null, null);
        final Button launchLoginButton =
                (Button) getActivity()
                        .findViewById(R.id.mainpage_button_login);
        launchLoginButton.performClick();

        final Intent launchIntent = getStartedActivityIntent();
        assertNotNull("Intent was null", launchIntent);
        assertTrue("Wrong Activity Launched", launchIntent.resolveActivity(getInstrumentation().getTargetContext().getPackageManager()).getClassName().equals(LoginActivity.class.getCanonicalName()));
    }

    /**
     * Test that the register button opens the register activity
     */
    @MediumTest
    public void testRegisterActivityWasLaunchedWithIntent() {
        //startActivity(mLaunchIntent, null, null);
        final Button launchRegisterButton =
                (Button) getActivity()
                        .findViewById(R.id.mainpage_button_register);
        launchRegisterButton.performClick();

        final Intent launchIntent = getStartedActivityIntent();
        assertNotNull("Intent was null", launchIntent);
        assertTrue("Wrong Activity Launched", launchIntent.resolveActivity(getInstrumentation().getTargetContext().getPackageManager()).getClassName().equals(RegisterActivity.class.getCanonicalName()));
    }
}
