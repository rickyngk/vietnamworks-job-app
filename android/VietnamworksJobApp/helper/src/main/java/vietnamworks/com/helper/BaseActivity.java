package vietnamworks.com.helper;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

/**
 * Created by duynk on 12/29/15.
 */
public class BaseActivity extends AppCompatActivity {
    public static BaseActivity sInstance;
    private Handler handler = new Handler();

    //detect layout changed
    private boolean isKeyboardShown;
    private Rect screenRegion;

    public BaseActivity() {
        super();
        BaseActivity.sInstance = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isKeyboardShown = false;

        final View activityRootView = getWindow().getDecorView().findViewById(android.R.id.content);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                boolean isSoftKeyShown = r.height() < getScreenHeight()*0.8;
                isKeyboardShown = isSoftKeyShown;
                screenRegion = r;
                onLayoutChanged(r, isSoftKeyShown);
            }
        });
    }

    public void onLayoutChanged(Rect r, boolean isSoftKeyShown) {
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

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
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

    public static int[] getScreenSize() {
        Display display = BaseActivity.sInstance.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int s[] = new int[2];
        s[0] = size.x;
        s[1] = size.y;
        return s;
    }

    public static int getScreenHeight() {
        Display display = BaseActivity.sInstance.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    public static int getScreenWidth() {
        Display display = BaseActivity.sInstance.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = BaseActivity.sInstance.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = BaseActivity.sInstance.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
