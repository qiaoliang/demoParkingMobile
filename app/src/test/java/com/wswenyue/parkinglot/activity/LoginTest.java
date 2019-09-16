package com.wswenyue.parkinglot.activity;

import org.junit.Test;
import org.robolectric.Robolectric;

import static org.junit.Assert.*;

/**
 * Created by qiaoliang on 2/7/2019.
 */

public class LoginTest extends BaseRobolectricTestCase {
    @Test
    //@PrepareForTest({AppUtil.class, OAuthManager.class, NetUtil.class})
    public void checkUser() throws Exception {
        LoginActivity loginActivity = Robolectric.buildActivity(LoginActivity.class).create().get();
        assertNotNull(loginActivity);
        assertEquals(loginActivity.getTitle(), "SimpleActivity");
        loginActivity.CheckUser();
//        Intent expectedIntent = new Intent(loginActivity, HelpActivity.class);
//        ShadowActivity shadowActivity = Shadows.shadowOf(loginActivity);
//        Intent actualIntent = shadowActivity.getNextStartedActivity();
//        Assert.assertEquals(expectedIntent.getComponent().getClassName(), actualIntent.getComponent().getClassName());

    }
}
