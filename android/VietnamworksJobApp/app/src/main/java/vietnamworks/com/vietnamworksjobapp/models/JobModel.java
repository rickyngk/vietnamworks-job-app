package vietnamworks.com.vietnamworksjobapp.models;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import R.helper.Callback;
import R.helper.CallbackResult;
import R.helper.CallbackSuccess;
import vietnamworks.com.vietnamworksjobapp.entities.Job;
import vietnamworks.com.vietnamworksjobapp.entities.WorkingLocation;
import vietnamworks.com.vnwcore.VNWAPI;

/**
 * Created by duynk on 1/5/16.
 */
public class JobModel {
    public final static int MAX_JOBS = 20;
    static JobModel instance =  new JobModel();
    ArrayList<Job> data = new ArrayList<>();

    public static void load(final Context ctx, final Callback callback) {
        String jobTitle = UserLocalProfileModel.getEntity().getJobTitle();
        ArrayList<WorkingLocation> locations = UserLocalProfileModel.getEntity().getWorkingLocations();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < locations.size(); i++) {
            WorkingLocation l = locations.get(i);
            sb.append(l.getLocationId());
            if (i < locations.size() - 1) {
                sb.append(",");
            }
        }
        String jobIndustry = UserLocalProfileModel.getEntity().getIndustry();


        VNWAPI.searchJob(MAX_JOBS, jobTitle, sb.toString(), jobIndustry, new Callback() {
            @Override
            public void onCompleted(Context context, CallbackResult result) {
                if (result.hasError()) {
                    callback.onCompleted(context, new CallbackResult(result.getError()));
                } else {
                    try {
                        JSONObject res = (JSONObject) result.getData();
                        JSONObject data = res.optJSONObject("data");
                        JSONArray jobs = data.getJSONArray("jobs");
                        for (int i = 0; i < jobs.length(); i++) {
                            Job j = new Job();
                            j.setJobTitle(jobs.getJSONObject(i).getString("job_title"));
                            instance.data.add(j);
                        }
                    } catch (Exception E) {
                        callback.onCompleted(context, new CallbackResult(new CallbackResult.CallbackError(-1, E.getMessage())));
                    }
                    callback.onCompleted(context, new CallbackSuccess());
                }
            }
        });
    }

    public static JobModel getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        ArrayList<HashMap> a = new ArrayList<>();
        for (Job j:data) {
            a.add(j.exportToHashMap());
        }
        JSONArray array = new JSONArray(a);
        return array.toString();
    }

    public static int count() {
        return instance.data.size();
    }

    public static Job get(int index) {
        return instance.data.get(index);
    }
}
