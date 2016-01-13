package vietnamworks.com.vietnamworksjobapp.models;

import R.helper.LocalStorage;
import vietnamworks.com.vietnamworksjobapp.entities.UserLocalProfile;

/**
 * Created by duynk on 1/5/16.
 */
public class UserLocalProfileModel {
    public final static String LS_LOCAL_PROFILE_MODEL = "local_profile_model";

    static UserLocalProfile entity = new UserLocalProfile();

    public static boolean loadLocal() {
        try {
            return LocalStorage.loadExportableObject(LS_LOCAL_PROFILE_MODEL, entity);
        } catch (Exception E) {
            E.printStackTrace();
            return false;
        }
    }

    public static void saveLocal() {
        try {
            LocalStorage.set(LS_LOCAL_PROFILE_MODEL, entity);
        }catch (Exception E) {
            LocalStorage.remove(LS_LOCAL_PROFILE_MODEL);
        }
    }

    public static void removeLocal() {
        LocalStorage.remove(LS_LOCAL_PROFILE_MODEL);
    }

    public static UserLocalProfile getEntity() {
        return entity;
    }
}
