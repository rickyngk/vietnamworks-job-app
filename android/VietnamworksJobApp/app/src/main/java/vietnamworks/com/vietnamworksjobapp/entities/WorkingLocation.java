package vietnamworks.com.vietnamworksjobapp.entities;

import java.util.Arrays;

import R.helper.BaseEntity;

/**
 * Created by duynk on 1/6/16.
 */
public class WorkingLocation extends BaseEntity {
    public final static String LOCATION_ID = "location_id";
    public final static String LANG_VN = "lang_vn";
    public final static String LANG_EN = "lang_en";

    public WorkingLocation() {
        super(Arrays.asList(LOCATION_ID, LANG_VN, LANG_EN));
    }

    public WorkingLocation(int locationID, String lang_vn, String lang_en) {
        super(Arrays.asList(LOCATION_ID, LANG_VN, LANG_EN));
        set(LOCATION_ID, locationID);
        set(LANG_VN, lang_vn);
        set(LANG_EN, lang_en);
    }

    public int getLocationId() {
        return getInt(LOCATION_ID, -1);
    }

    public String getEN() {
        return getString(LANG_EN, "");
    }

    public String getVN() {
        return getString(LANG_VN, "");
    }

    public static WorkingLocation HoChiMinh = new WorkingLocation(29, "Hồ Chí Minh", "Ho Chi Minh");
    public static WorkingLocation HaNoi = new WorkingLocation(24, "Hà Nội", "Ha Noi");
    public static WorkingLocation DaNang = new WorkingLocation(17, "Đà Nẵng", "Da Nang");
    public static WorkingLocation Other = new WorkingLocation(-1, "Other", "Other");
}
