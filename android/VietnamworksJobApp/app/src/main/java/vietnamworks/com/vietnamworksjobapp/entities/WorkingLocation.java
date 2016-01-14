package vietnamworks.com.vietnamworksjobapp.entities;

import R.helper.BindField;
import R.helper.EntityX;

/**
 * Created by duynk on 1/6/16.
 */
public class WorkingLocation extends EntityX {

    @BindField("location_id") int id = -1;
    @BindField("lang_vn") String langVn = "";
    @BindField("lang_en") String langEn = "";

    public WorkingLocation() {
        super();
    }

    public WorkingLocation(int locationID, String lang_vn, String lang_en) {
        super();
        setId(locationID);
        setLangEn(lang_en);
        setLangVn(lang_vn);
    }

    public static WorkingLocation HoChiMinh = new WorkingLocation(29, "Hồ Chí Minh", "Ho Chi Minh");
    public static WorkingLocation HaNoi = new WorkingLocation(24, "Hà Nội", "Ha Noi");
    public static WorkingLocation DaNang = new WorkingLocation(17, "Đà Nẵng", "Da Nang");
    public static WorkingLocation Other = new WorkingLocation(-1, "Other", "Other");

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLangVn() {
        return langVn;
    }

    public void setLangVn(String langVn) {
        this.langVn = langVn;
    }

    public String getLangEn() {
        return langEn;
    }

    public void setLangEn(String langEn) {
        this.langEn = langEn;
    }
}
