package vietnamworks.com.vietnamworksjobapp;
import vietnamworks.com.helper.BaseApplication;

/**
 * Created by duynk on 12/29/15.
 */
public class Application extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();;
        setDefaultFont(FontType.SERIF, "fonts/Montserrat-Regular.ttf");
    }
}
