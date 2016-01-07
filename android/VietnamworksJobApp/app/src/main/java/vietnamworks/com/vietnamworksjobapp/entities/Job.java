package vietnamworks.com.vietnamworksjobapp.entities;

import java.util.Arrays;

import R.helper.BaseEntity;

/**
 * Created by duynk on 1/5/16.
 */
public class Job extends BaseEntity {
    public final static String JOB_TITLE = "jobTitle";
    public final static String JOB_LEVEL = "jobLevel";

    public Job() {
        super(Arrays.asList(JOB_TITLE, JOB_LEVEL));
    }

    public void setJobTitle(String value) {
        set(JOB_TITLE, value);
    }

    public String getJobTitle() {
        return getString(JOB_TITLE, "");
    }

    public void setJobLevel(String value) {
        set(JOB_LEVEL, value);
    }

    public String getJobLevel() {
        return getString(JOB_TITLE, "");
    }
}
