package vietnamworks.com.cardstack;

import android.view.View;

/**
 * Created by duynk on 1/5/16.
 */
public interface CardStackViewDelegate {
    void onStarted();
    View onLoadView(int index);
    int getCount();
}
