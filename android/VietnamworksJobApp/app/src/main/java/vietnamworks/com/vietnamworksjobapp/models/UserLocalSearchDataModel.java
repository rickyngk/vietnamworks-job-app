package vietnamworks.com.vietnamworksjobapp.models;

import R.helper.LocalStorage;
import vietnamworks.com.vietnamworksjobapp.entities.UserLocalSearchData;

/**
 * Created by duynk on 1/5/16.
 */
public class UserLocalSearchDataModel {
    public final static String LS_LOCAL_PROFILE_MODEL = "local_profile_model";

    static UserLocalSearchData entity = new UserLocalSearchData();

    public static boolean loadLocal() {
        try {
            return LocalStorage.loadEntityX(LS_LOCAL_PROFILE_MODEL, entity);
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

    public static UserLocalSearchData getEntity() {
        return entity;
    }
}
