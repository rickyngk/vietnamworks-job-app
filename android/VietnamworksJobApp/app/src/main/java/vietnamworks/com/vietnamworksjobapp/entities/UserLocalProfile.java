package vietnamworks.com.vietnamworksjobapp.entities;

import java.util.ArrayList;
import java.util.Arrays;

import vietnamworks.com.helper.BaseEntity;

/**
 * Created by duynk on 1/5/16.
 */
public class UserLocalProfile extends BaseEntity {
    public final static String F_JOB_TITLE = "jobTitle";
    public final static String F_INDUSTRY = "industry";
    public final static String F_WORKING_LOCATION = "workingLocation";

    public UserLocalProfile() {
        super(Arrays.asList(F_JOB_TITLE, F_INDUSTRY, F_WORKING_LOCATION));
        set(F_JOB_TITLE, "");
        set(F_INDUSTRY, "");
        set(F_WORKING_LOCATION, new ArrayList<String>());
    }

    public void setJobTitle(String value) {
        set(F_JOB_TITLE, value);
    }

    public void setIndustry(String value) {
        set(F_INDUSTRY, value);
    }

    public void setWorkingLocation(String... industries) {
        ArrayList<String> locations = new ArrayList<>(Arrays.asList(industries));
        set(F_WORKING_LOCATION, locations);
    }

    public void setWorkingLocation(ArrayList<String> locations) {
        set(F_WORKING_LOCATION, locations);
    }
}
