package vietnamworks.com.vietnamworksjobapp.services;

import java.util.HashMap;

/**
 * Created by duynk on 1/21/16.
 */
public class ShareContext {
    public final static String SELECTED_JOB = "Selected_job";

    public static HashMap<String, Object> shared = new HashMap<>();
    public static Object get(String key) {
        return shared.get(key);
    }

    public static void set(String key, Object obj) {
        shared.put(key, obj);
    }
}
