package vietnamworks.com.helper;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * Created by duynk on 12/29/15.
 */
public class BaseActivity extends AppCompatActivity {
    public static BaseActivity sInstance;
    private Handler handler = new Handler();

    public BaseActivity() {
        super();
        BaseActivity.sInstance = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void hideSystemUI() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void setTimeout(final Runnable f, long delay) {
        handler.postDelayed(f, delay);
    }

    public void setTimeout(final Runnable f) {
        handler.post(f);
    }


    public static void openActivity(Class<?> cls, @NonNull Bundle bundle) {
        Intent intent = new Intent(BaseActivity.sInstance, cls);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        BaseActivity.sInstance.startActivity(intent);
    }

    public static void openActivity(Class<?> cls) {
        Intent intent = new Intent(BaseActivity.sInstance, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        BaseActivity.sInstance.startActivity(intent);
    }

    public static void timeout(final Runnable f, long delay) {
        sInstance.setTimeout(f, delay);
    }

    public static void timeout(final Runnable f) {
        sInstance.setTimeout(f);
    }
}
