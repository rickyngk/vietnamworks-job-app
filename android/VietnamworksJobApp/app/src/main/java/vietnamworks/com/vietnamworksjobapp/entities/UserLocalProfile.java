package vietnamworks.com.vietnamworksjobapp.entities;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import R.helper.BaseEntity;

/**
 * Created by duynk on 1/5/16.
 */
public class UserLocalProfile extends BaseEntity {
    public final static String JOB_TITLE = "jobTitle";
    public final static String INDUSTRY = "industry";
    public final static String WORKING_LOCATION = "workingLocation";

    public UserLocalProfile() {
        super(Arrays.asList(JOB_TITLE, INDUSTRY, WORKING_LOCATION));
        set(JOB_TITLE, "");
        set(INDUSTRY, "");
        set(WORKING_LOCATION, new ArrayList<WorkingLocation>());
    }

    public void setJobTitle(String value) {
        set(JOB_TITLE, value);
    }

    public String getJobTitle() {
        return getString(JOB_TITLE, "");
    }

    @Override
    public void importFromHashMap(@NonNull HashMap m) {
        for (String field:fields) {
            if (m.containsKey(field)) {
                if (field.compareTo(WORKING_LOCATION) == 0) {
                    data.put(field, convertArrayList(m.get(field), WorkingLocation.class));
                } else {
                    data.put(field, m.get(field));
                }
            }
        }
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

    public String getIndustry() {
        return getString(INDUSTRY, "");
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
