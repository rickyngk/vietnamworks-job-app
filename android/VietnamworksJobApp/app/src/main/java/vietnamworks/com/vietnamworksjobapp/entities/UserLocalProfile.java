package vietnamworks.com.vietnamworksjobapp.entities;

import java.util.ArrayList;

import R.helper.BindField;
import R.helper.EntityX;

/**
 * Created by duynk on 1/5/16.
 */
public class UserLocalProfile extends EntityX {
    @BindField("jobTitle") String jobTitle = "";
    @BindField("industry") String industry = "";
    @BindField("industry_code") String industryCode = "";
    @BindField("workingLocation") ArrayList<WorkingLocation> workingLocations = new ArrayList<>();

    public UserLocalProfile() {
        super();
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
    }

    public ArrayList<WorkingLocation> getWorkingLocations() {
        return workingLocations;
    }

    public void setWorkingLocations(ArrayList<WorkingLocation> workingLocations) {
        this.workingLocations = workingLocations;
    }
}
