package com.hcs.prototype.hcs_prototype;

import android.content.Intent;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.test.ActivityUnitTestCase;
import android.widget.Button;

/**
 * Created by mitch on 2015/09/21.
 */
public class LoginTest extends ActivityUnitTestCase<LoginActivity> {
    Intent mLaunchIntent;

    public LoginTest() {
        super(LoginActivity.class);
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
}
