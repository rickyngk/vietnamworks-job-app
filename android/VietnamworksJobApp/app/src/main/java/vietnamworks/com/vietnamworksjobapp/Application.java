package vietnamworks.com.vietnamworksjobapp;

import R.helper.BaseApplication;
import vietnamworks.com.vietnamworksjobapp.services.CloudinaryService;
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
        setDefaultFont(FontType.SERIF, "fonts/RobotoSlab-Regular.ttf");
        //VNWAPI.init("d54e4c1463d7709f8c6c49e93b3454e69befbdbe15a6723885bf399aa2a74bfe", "", false);
        VNWAPI.init("2ed19d9c84fa9280fe6fa1a9e58de807a9d076646de8327c53fc8ed64ca4e268", "", false);
        CloudinaryService.init(this);
    }
}
