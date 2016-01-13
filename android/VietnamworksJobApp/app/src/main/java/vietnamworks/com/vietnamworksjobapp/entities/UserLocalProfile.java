package vietnamworks.com.vietnamworksjobapp.entities;

import java.util.ArrayList;

import R.helper.BaseEntity;
import R.helper.EntityArrayField;
import R.helper.EntityField;

/**
 * Created by duynk on 1/5/16.
 */
public class UserLocalProfile extends BaseEntity {
    @EntityField("jobTitle") String JOB_TITLE;
    @EntityField("industry") String INDUSTRY;
    @EntityField("industry_code") String INDUSTRY_CODE;
    @EntityArrayField(value = "workingLocation", type = WorkingLocation.class) String WORKING_LOCATION;

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

    public void setWorkingLocation(ArrayList<WorkingLocation> locations) {
        set(WORKING_LOCATION, locations);
    }

    public ArrayList<WorkingLocation> getWorkingLocations() {
        return (ArrayList)getArray(WORKING_LOCATION);
    }
}
