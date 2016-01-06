package vietnamworks.com.vietnamworksjobapp.models;

import vietnamworks.com.helper.LocalStorage;
import vietnamworks.com.vietnamworksjobapp.entities.UserLocalProfile;

/**
 * Created by duynk on 1/5/16.
 */
public class UserLocalProfileModel {
    public final static String LS_LOCAL_PROFILE_MODEL = "local_profile_model";

    static UserLocalProfile entity = new UserLocalProfile();

    public static boolean loadLocal() {
        return LocalStorage.loadExportableObject(LS_LOCAL_PROFILE_MODEL, entity);
    }

    public static void saveLocal() {
        LocalStorage.set(LS_LOCAL_PROFILE_MODEL, entity);
    }

    public static void removeLocal() {
        LocalStorage.remove(LS_LOCAL_PROFILE_MODEL);
    }

    public static UserLocalProfile getEntity() {
        return entity;
    }
}
