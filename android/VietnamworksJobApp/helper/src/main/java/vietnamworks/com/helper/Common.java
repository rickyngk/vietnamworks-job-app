package vietnamworks.com.helper;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

/**
 * Created by duynk on 12/29/15.
 */
public class Common {
    public static int apiVersion = Build.VERSION.SDK_INT;

    public static boolean isLollipopOrLater() {
        return apiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isMarshMallowOrLater() {
        return apiVersion >= Build.VERSION_CODES.M;
    }

    public static boolean isVersionOrLater(int version) {
        return apiVersion >= version;
    }

    public static int sign(float a) {
        return a > 0?1:(a<0?-1:0);
    }

    public static float lerp(float start, float end, float percent) {
        float dt = Math.abs(start - end);
        float min = Math.max(Math.abs(start) * 0.1f, Math.abs(end) * 0.1f);
        if (dt <= min) {
            start = end;
        }
        return (start + percent * (end - start));
    }

    public static int lerp(int start, int end, float percent) {
        float dt = Math.abs(start - end);
        float min = Math.max(Math.abs(start) * 0.1f, Math.abs(end) * 0.1f);
        if (dt <= min) {
            start = end;
        }
        return (int) (start + percent * (end - start));
    }

    public static float convertDpToPixel(float dp) {
        Resources resources = BaseApplication.sInstance.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * (metrics.densityDpi / 160f);
    }

    public static float convertPixelsToDp(float px, Context context){
        Resources resources = BaseApplication.sInstance.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return px / (metrics.densityDpi / 160f);
    }
}
