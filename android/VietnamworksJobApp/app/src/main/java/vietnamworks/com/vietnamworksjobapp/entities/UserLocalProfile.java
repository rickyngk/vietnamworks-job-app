package vietnamworks.com.vietnamworksjobapp.entities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import R.helper.BaseEntity;
import R.helper.EntityField;

/**
 * Created by duynk on 1/5/16.
 */
public class UserLocalProfile extends BaseEntity {
    @EntityField("jobTitle") String JOB_TITLE;
    @EntityField("industry") String INDUSTRY;
    @EntityField("industry_code") String INDUSTRY_CODE;
    @EntityField(value = "workingLocation", type = WorkingLocation.class, collectionType = ArrayList.class) String WORKING_LOCATION;

    public UserLocalProfile() {
        super();
        set(JOB_TITLE, "");
        set(INDUSTRY, "");
        set(INDUSTRY_CODE, "");
        set(WORKING_LOCATION, new ArrayList<WorkingLocation>());
    }

    public void setJobTitle(String value) {
        set(JOB_TITLE, value);
    }

    public String getJobTitle() {
        return getString(JOB_TITLE, "");
    }

    @Override
    public JSONObject exportToJsonObject() {
        JSONObject obj = super.exportToJsonObject();
        obj.remove(WORKING_LOCATION);
        JSONArray array = new JSONArray();
        ArrayList<WorkingLocation> l = getWorkingLocations();

        for (int i = 0; i < l.size(); i++) {
            array.put(l.get(i).exportToJsonObject());
        }
        try {
            obj.put(WORKING_LOCATION, (Object)array);
        } catch (Exception E) {}
        return obj;
    }

    public void setIndustry(String value) {
        set(INDUSTRY, value);
    }

    public void setIndustryCode(String industryCode) {
        set(INDUSTRY_CODE, industryCode);
    }

    public String getIndustry() {
        return getString(INDUSTRY, "");
    }

    public String getIndustryCode() {
        return getString(INDUSTRY_CODE, "");
    }

    public void setWorkingLocation(WorkingLocation... l) {
        ArrayList<WorkingLocation> locations = new ArrayList<>(Arrays.asList(l));
        set(WORKING_LOCATION, locations);
    }

    public void setWorkingLocation(ArrayList<WorkingLocation> locations) {
        set(WORKING_LOCATION, locations);
    }

    public ArrayList<WorkingLocation> getWorkingLocations() {
        return getArray(WORKING_LOCATION);
    }
}
