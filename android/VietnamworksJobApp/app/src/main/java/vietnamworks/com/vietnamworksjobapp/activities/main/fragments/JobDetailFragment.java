package vietnamworks.com.vietnamworksjobapp.activities.main.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import R.helper.BaseFragment;
import R.helper.Callback;
import R.helper.CallbackResult;
import R.helper.Common;
import butterknife.Bind;
import butterknife.ButterKnife;
import vietnamworks.com.vietnamworksjobapp.R;
import vietnamworks.com.vietnamworksjobapp.models.JobDetailModel;
import vietnamworks.com.vnwcore.entities.Category;
import vietnamworks.com.vnwcore.entities.Company;
import vietnamworks.com.vnwcore.entities.Configuration;
import vietnamworks.com.vnwcore.entities.Job;
import vietnamworks.com.vnwcore.entities.JobDetail;
import vietnamworks.com.vnwcore.entities.JobSummary;
import vietnamworks.com.vnwcore.entities.Language;
import vietnamworks.com.vnwcore.entities.Location;

/**
 * Created by duynk on 1/15/16.
 */
public class JobDetailFragment extends BaseFragment {
    @Bind(R.id.job_detail_job_title) TextView jobTitle;

    //head
    @Bind(R.id.job_detail_company_name) TextView companyName;
    @Bind(R.id.job_detail_company_image) ImageView companyImage;
    @Bind(R.id.job_detail_company_address) TextView companyAddress;

    //job summary
    @Bind(R.id.summary_job_detail) View summaryJobDetailView;
    @Bind(R.id.job_detail_working_location) TextView workingLocations;
    @Bind(R.id.job_detail_job_level) TextView jobLevel;
    @Bind(R.id.job_detail_job_industry) TextView jobIndustry;
    @Bind(R.id.job_detail_preferred_language) TextView preferredLanguage;

    //company detail
    @Bind(R.id.about_company) View aboutCompanyView;
    @Bind(R.id.company_detail_name) TextView companyDetailName;
    @Bind(R.id.company_detail_address) TextView companyDetailAddress;
    @Bind(R.id.company_detail_contact) TextView companyDetailContact;
    @Bind(R.id.company_detail_desc) TextView companyDetailDesc;
    @Bind(R.id.company_detail_size) TextView companyDetailSize;

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

        workingLocations.setText("");
        companyName.setText("");
        companyAddress.setText("");

        summaryJobDetailView.setVisibility(View.INVISIBLE);
        aboutCompanyView.setVisibility(View.INVISIBLE);

        progressBar.setVisibility(View.VISIBLE);
        JobDetailModel.load(getContext(), jobId, new Callback() {
            @Override
            public void onCompleted(Context context, CallbackResult result) {
                progressBar.setVisibility(View.GONE);
                summaryJobDetailView.setVisibility(View.VISIBLE);
                aboutCompanyView.setVisibility(View.VISIBLE);
                if (!result.hasError()) {
                    if (JobDetailFragment.this.getContext() == context) {
                        Job j = (Job) result.getData();
                        JobDetail jd = j.getJobDetail();

                        //job summary
                        JobSummary js = j.getJobSummary();

                        String locations = js.getLocations();
                        if (locations != null && !locations.isEmpty()) {
                            String[] arr = locations.split(",");
                            StringBuilder b = new StringBuilder();
                            String delim = "";
                            for (String s : arr) {
                                Location obj = Configuration.findLocation(s.trim());
                                if (obj != null) {
                                    b.append(delim);
                                    b.append(obj.getEn());
                                    delim = ", ";
                                }
                            }
                            workingLocations.setText(String.format(getString(R.string.work_location), b.toString()));
                        }


                        jobLevel.setText(Configuration.findJobLevel(js.getLevel() + "").getEn());

                        String industry = js.getCategories();
                        if (industry != null && !industry.isEmpty()) {
                            String[] industry_arr = industry.split(",");
                            StringBuilder b = new StringBuilder();
                            String delim = "";
                            for (String s : industry_arr) {
                                Category cat = Configuration.findCategory(s.trim());
                                if (cat != null) {
                                    b.append(delim);
                                    b.append(cat.getEn());
                                    delim = ", ";
                                }
                            }
                            jobIndustry.setText(b.toString());
                        }

                        Language lang = Configuration.findLanguage(js.getPreferLanguage() + "");
                        if (lang != null) {
                            preferredLanguage.setText(lang.getName());
                        }

                        //company
                        Company c = j.getCompany();
                        companyName.setText(c.getName());
                        companyAddress.setText(c.getAddress());
                        Picasso.with(context).load(c.getLogo()).into(companyImage);

                        //company detail
                        companyDetailName.setText(c.getName());
                        companyDetailAddress.setText(c.getAddress());
                        companyDetailContact.setText(String.format(getString(R.string.contact_person), c.getContactPerson()));
                        companyDetailDesc.setText(c.getProfile());
                        companyDetailSize.setText(String.format(getString(R.string.company_size), c.getSize()));
                    }
                }
            }
        });
        return rootView;
    }
}
