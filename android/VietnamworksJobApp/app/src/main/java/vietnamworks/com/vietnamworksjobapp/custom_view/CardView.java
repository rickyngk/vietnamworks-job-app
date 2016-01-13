package vietnamworks.com.vietnamworksjobapp.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import vietnamworks.com.vietnamworksjobapp.R;
import vietnamworks.com.vnwcore.entities.JobSearchResult;

/**
 * Created by duynk on 1/5/16.
 */
public class CardView extends FrameLayout {
    @Bind(R.id.job_title)
    TextView jobTitle;

    @Bind(R.id.company)
    TextView company;

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
        jobTitle.setText(j.getJobTitle());
        company.setText(j.getCompany());
    }
}
