package vietnamworks.com.vietnamworksjobapp.activities.main.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import R.helper.BaseFragment;
import R.helper.Callback;
import R.helper.CallbackResult;
import R.helper.Common;
import butterknife.Bind;
import butterknife.ButterKnife;
import vietnamworks.com.vietnamworksjobapp.R;
import vietnamworks.com.vietnamworksjobapp.models.JobDetailModel;
import vietnamworks.com.vnwcore.entities.Company;
import vietnamworks.com.vnwcore.entities.Job;
import vietnamworks.com.vnwcore.entities.JobDetail;
import vietnamworks.com.vnwcore.entities.JobSummary;

/**
 * Created by duynk on 1/15/16.
 */
public class JobDetailFragment extends BaseFragment {
    @Bind(R.id.job_detail_job_title)
    TextView jobTitle;

    @Bind(R.id.job_detail_job_level)
    TextView jobLevel;

    @Bind(R.id.job_detail_job_industry)
    TextView jobIndustry;

    @Bind(R.id.job_detail_company_name)
    TextView companyName;

    @Bind(R.id.job_detail_company_profile)
    TextView companyProfile;


    @Bind(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();

        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_job_detail, container, false);
        ButterKnife.bind(this, rootView);

        if (Common.isLollipopOrLater()) {
            jobTitle.setTransitionName(getString(R.string.transition_job_card_to_job_detail));
        }
        jobTitle.setText(bundle.getString("jobTitle"));
        String jobId = bundle.getString("jobId");

        jobLevel.setText("");
        jobIndustry.setText("");
        companyName.setText("");
        companyProfile.setText("");

        progressBar.setVisibility(View.VISIBLE);
        JobDetailModel.load(getContext(), jobId, new Callback() {
            @Override
            public void onCompleted(Context context, CallbackResult result) {
                progressBar.setVisibility(View.GONE);
                if (!result.hasError()) {
                    Job j = (Job)result.getData();
                    JobDetail jd = j.getJobDetail();
                    JobSummary js = j.getJobSummary();
                    Company c = j.getCompany();
                    if (JobDetailFragment.this.getContext() == context) {
                        jobLevel.setText(js.getLevel() + "");
                        jobIndustry.setText(js.getCategories());
                        companyName.setText(c.getName());
                        companyProfile.setText(c.getProfile());
                    }
                }
            }
        });
        return rootView;
    }
}
