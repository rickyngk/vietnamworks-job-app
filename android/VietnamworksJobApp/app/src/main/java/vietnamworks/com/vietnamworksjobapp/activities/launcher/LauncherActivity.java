package vietnamworks.com.vietnamworksjobapp.activities.launcher;

import android.content.Context;
import android.os.Bundle;

import R.helper.Callback;
import R.helper.CallbackResult;
import R.helper.Common;
import vietnamworks.com.vietnamworksjobapp.activities.main.MainActivity;
import vietnamworks.com.vietnamworksjobapp.activities.onboarding.WelcomeActivity;
import vietnamworks.com.vietnamworksjobapp.models.UserLocalProfileModel;
import vietnamworks.com.vnwcore.entities.Configuration;

public class LauncherActivity extends vietnamworks.com.vnwcore.LauncherActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuration.load(LauncherActivity.this, new Callback() {
            @Override
            public void onCompleted(Context context, CallbackResult result) {
                if (result.hasError()) {
                    //TODO: handle error here
                    Common.Dialog.alert(context, result.getError().getMessage(), new Callback() {
                        @Override
                        public void onCompleted(Context context, CallbackResult result) {
                            LauncherActivity.this.finish();
                        }
                    });
                } else {
                    if (UserLocalProfileModel.loadLocal()) {
                        timeout(new Runnable() {
                            @Override
                            public void run() {
                                openActivity(MainActivity.class);
                            }
                        }, 1000);
                    } else {
                        timeout(new Runnable() {
                            @Override
                            public void run() {
                                openActivity(WelcomeActivity.class);
                            }
                        }, 1000);
                    }
                }
            }
        });


    }
}
