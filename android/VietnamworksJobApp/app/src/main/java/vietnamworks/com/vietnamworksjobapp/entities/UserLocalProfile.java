package vietnamworks.com.vietnamworksjobapp.entities;

import java.util.ArrayList;
import java.util.Arrays;

import vietnamworks.com.helper.BaseEntity;

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
        set(WORKING_LOCATION, new ArrayList<String>());
    }

    public void setJobTitle(String value) {
        set(JOB_TITLE, value);
    }

    public String getJobTitle() {
        return getString(JOB_TITLE, "");
    }

    public void setIndustry(String value) {
        set(INDUSTRY, value);
    }

    public String getIndustry() {
        return getString(INDUSTRY, "");
    }

    public void setWorkingLocation(String... industries) {
        ArrayList<String> locations = new ArrayList<>(Arrays.asList(industries));
        set(WORKING_LOCATION, locations);
    }

    public void setWorkingLocation(ArrayList<String> locations) {
        set(WORKING_LOCATION, locations);
    }

    public ArrayList<String> getWorkingLocations() {
        return getArray(WORKING_LOCATION);
    }
}
