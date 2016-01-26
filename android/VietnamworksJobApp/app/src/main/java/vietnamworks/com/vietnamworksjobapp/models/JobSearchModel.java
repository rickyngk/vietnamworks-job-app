package vietnamworks.com.vietnamworksjobapp.models;

import android.content.Context;

import org.json.JSONArray;

import java.util.ArrayList;

import R.helper.Callback;
import R.helper.CallbackResult;
import vietnamworks.com.vietnamworksjobapp.entities.WorkingLocation;
import vietnamworks.com.vnwcore.VNWAPI;
import vietnamworks.com.vnwcore.entities.JobSearchResult;

/**
 * Created by duynk on 1/5/16.
 */
public class JobSearchModel {
    public final static int MAX_JOBS = 10;
    static JobSearchModel instance =  new JobSearchModel();
    ArrayList<JobSearchResult> data = new ArrayList<>();

    public static void load(final Context ctx, final Callback<Object> callback) {
        String jobTitle = UserLocalProfileModel.getEntity().getJobTitle();
        ArrayList<WorkingLocation> locations = UserLocalProfileModel.getEntity().getWorkingLocations();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < locations.size(); i++) {
            WorkingLocation l = locations.get(i);
            sb.append(l.getId());
            if (i < locations.size() - 1) {
                sb.append(",");
            }
        }
        String jobIndustry = UserLocalProfileModel.getEntity().getIndustryCode();


        VNWAPI.searchJob(ctx, MAX_JOBS, jobTitle, sb.toString(), jobIndustry, new Callback<ArrayList<JobSearchResult>>() {
            @Override
            public void onCompleted(Context context, CallbackResult<ArrayList<JobSearchResult>> result) {
                if (result.hasError()) {
                    callback.onCompleted(context, CallbackResult.error(result.getError()));
                } else {
                    try {
                        instance.data = result.getData();
                    } catch (Exception E) {
                        callback.onCompleted(context, CallbackResult.error(E.getMessage()));
                    }
                    callback.onCompleted(context, CallbackResult.success());
                }
            }
        });
    }

    public static JobSearchModel getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        try {
            JSONArray a = new JSONArray();
            for (JobSearchResult j : data) {
                a.put(j.exportToJson());
            }
            return a.toString();
        }catch (Exception E) {
            return E.toString();
        }
    }

    public static int count() {
        return instance.data.size();
    }

    public static JobSearchResult get(int index) {
        return instance.data.get(index);
    }

    public static String[] exportJobListAsArray() {
        ArrayList<String> ar = new ArrayList<>();
        for (JobSearchResult s:instance.data) {
            ar.add(s.getId());
        }
        return ar.toArray(new String[ar.size()]);
    }
}
