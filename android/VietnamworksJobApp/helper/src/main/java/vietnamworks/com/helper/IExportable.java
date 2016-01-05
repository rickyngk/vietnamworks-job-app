package vietnamworks.com.helper;

import java.util.HashMap;

/**
 * Created by duynk on 1/5/16.
 */
public interface IExportable {
    HashMap exportToHashMap();
    void importFromHashMap(HashMap m);
}
