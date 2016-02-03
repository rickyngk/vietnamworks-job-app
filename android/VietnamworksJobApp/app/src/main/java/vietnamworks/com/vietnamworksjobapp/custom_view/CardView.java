package vietnamworks.com.vietnamworksjobapp.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import R.helper.BaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import vietnamworks.com.vietnamworksjobapp.R;
import vietnamworks.com.vietnamworksjobapp.entities.WorkingLocation;
import vietnamworks.com.vnwcore.entities.JobSearchResult;
import vietnamworks.com.vnwcore.entities.Skill;

/**
 * Created by duynk on 1/5/16.
 */
public class CardView extends FrameLayout {
    @Bind(R.id.job_card_job_title)
    TextView jobTitle;

    @Bind(R.id.job_card_company)
    TextView company;

    @Bind(R.id.job_card_job_level)
    TextView jobLevel;

    @Bind(R.id.job_card_location)
    TextView location;

    @Bind(R.id.job_card_salary)
    TextView salary;

    @Bind(R.id.job_card_skills)
    TextView skills;

    @Bind(R.id.card_skip_icon)
    ImageView cardSkipIcon;

    @Bind(R.id.card_accept_icon)
    ImageView cardAcceptIcon;

    public CardView(Context context) {
        super(context);
        initializeViews(context);
    }

    public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public CardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    public void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.cv_card_view, this);

        ButterKnife.bind(this, this);
    }

    public void setViewModel(JobSearchResult j) {
        StringBuilder sb = new StringBuilder();
        String delimiter = "";

        jobTitle.setText(j.getTitle());
        company.setText(j.getCompany());
        jobLevel.setText(j.getJobLevel());


        String locations = j.getLocations();
        String[] location_array = locations.split(",");
        sb = new StringBuilder();
        delimiter = "";
        for (String l: location_array) {
            l = l.trim();
            int i_l = Integer.parseInt(l);
            String addedLoc = "";
            if (i_l == WorkingLocation.DaNang.getId()) {
                addedLoc = WorkingLocation.DaNang.getLangEn();
            } else if (i_l == WorkingLocation.HoChiMinh.getId()) {
                addedLoc = WorkingLocation.HoChiMinh.getLangEn();
            } else if (i_l == WorkingLocation.DaNang.getId()) {
                addedLoc = WorkingLocation.DaNang.getLangEn();
            }
            if (!addedLoc.isEmpty()) {
                sb.append(delimiter);
                sb.append(addedLoc);
                delimiter = ", ";
            }
        }
        location.setText(sb.toString());

        salary.setText(j.getSalary());

        ArrayList<Skill> sks = j.getSkills();
        sb = new StringBuilder();
        delimiter = "";
        int max = 3;
        if (skills != null && sks.size() > 0) {
            for (Skill sk: sks) {
                if (max == 0) {
                    sb.append("...");
                    break;
                } else {
                    max--;
                    sb.append(delimiter);
                    sb.append(sk.getName());
                    delimiter = ", ";
                }
            }
        }
        skills.setText(sb.toString());
    }

    public void setConfidence(final float f) {
        BaseActivity.timeout(new Runnable() {
            @Override
            public void run() {
                if (f > 0) {
                    cardSkipIcon.setVisibility(INVISIBLE);
                    cardAcceptIcon.setVisibility(VISIBLE);
                    cardAcceptIcon.setAlpha(f);
                } else if (f < 0) {
                    cardAcceptIcon.setVisibility(INVISIBLE);
                    cardSkipIcon.setVisibility(VISIBLE);
                    cardSkipIcon.setAlpha(-f);
                } else {
                    cardSkipIcon.setVisibility(INVISIBLE);
                    cardAcceptIcon.setVisibility(INVISIBLE);
                }
            }
        });
    }
}
