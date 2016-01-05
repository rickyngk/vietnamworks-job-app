package vietnamworks.com.helper;

/**
 * Created by duynk on 12/29/15.
 */
public class Common {
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
}
