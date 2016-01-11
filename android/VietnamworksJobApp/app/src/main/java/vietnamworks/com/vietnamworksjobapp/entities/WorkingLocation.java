package vietnamworks.com.vietnamworksjobapp.entities;

import R.helper.BaseEntity;
import R.helper.EntityField;

/**
 * Created by duynk on 1/6/16.
 */
public class WorkingLocation extends BaseEntity {

    @EntityField("location_id") String LOCATION_ID;
    @EntityField("lang_vn") String LANG_VN;
    @EntityField("lang_en") String LANG_EN;

    public WorkingLocation() {
        super();
    }

    public WorkingLocation(int locationID, String lang_vn, String lang_en) {
        super();
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
