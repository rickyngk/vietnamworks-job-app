package vietnamworks.com.vietnamworksjobapp;
import vietnamworks.com.helper.BaseApplication;
import vietnamworks.com.vnwcore.VNWAPI;

/**
 * Created by duynk on 12/29/15.
 */
public class Application extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();;
        setDefaultFont(FontType.SERIF, "fonts/Montserrat-Regular.ttf");
        VNWAPI.init(this, "d54e4c1463d7709f8c6c49e93b3454e69befbdbe15a6723885bf399aa2a74bfe", "", false);
    }
}
