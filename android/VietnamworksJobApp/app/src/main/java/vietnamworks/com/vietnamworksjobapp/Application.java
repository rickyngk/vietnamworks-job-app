package vietnamworks.com.vietnamworksjobapp;

import R.helper.BaseApplication;
import vietnamworks.com.vnwcore.VNWAPI;

/**
 * Created by duynk on 12/29/15.
 */
public class Application extends BaseApplication {

    public Application() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setDefaultFont(FontType.SERIF, "fonts/Montserrat-Regular.ttf");
        VNWAPI.init("d54e4c1463d7709f8c6c49e93b3454e69befbdbe15a6723885bf399aa2a74bfe", "", false);
    }
}
