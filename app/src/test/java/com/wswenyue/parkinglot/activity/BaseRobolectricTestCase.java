package com.wswenyue.parkinglot.activity;

import android.app.Application;
import android.content.Context;

import com.wswenyue.parkinglot.BuildConfig;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

<<<<<<< HEAD
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

=======
>>>>>>> 6e0d5d5b045603eb42698b7203189dbc3c6a9a8d

@RunWith(RobolectricTestRunner.class)
@Config(shadows = {ShadowLog.class}, constants = BuildConfig.class, sdk = 23)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*", "org.json.*", "sun.security.*", "javax.net.*"})
public abstract class BaseRobolectricTestCase {

    @Rule
    public PowerMockRule rule = new PowerMockRule();

    private static boolean hasInited = false;

    @Before
    public void setUp() {
        ShadowLog.stream = System.out;
        if (!hasInited) {
            initRxJava();
            hasInited = true;
        }
        MockitoAnnotations.initMocks(this);
    }

    public Application getApplication() {
        return RuntimeEnvironment.application;
    }

    public Context getContext() {
        return RuntimeEnvironment.application;
    }

    private void initRxJava() {

<<<<<<< HEAD
        RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaSchedulersHook() {
            @Override
            public Scheduler getIOScheduler() {
                return Schedulers.immediate();
            }
        });
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
=======
//        RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaSchedulersHook() {
//            @Override
//            public Scheduler getIOScheduler() {
//                return Schedulers.immediate();
//            }
//        });
//        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
//            @Override
//            public Scheduler getMainThreadScheduler() {
//                return Schedulers.immediate();
//            }
//        });
>>>>>>> 6e0d5d5b045603eb42698b7203189dbc3c6a9a8d
    }

}

