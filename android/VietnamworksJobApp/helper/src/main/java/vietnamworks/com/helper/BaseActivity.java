package vietnamworks.com.helper;

import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * Created by duynk on 12/29/15.
 */
public class BaseActivity extends AppCompatActivity {
    protected void hideSystemUI() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
