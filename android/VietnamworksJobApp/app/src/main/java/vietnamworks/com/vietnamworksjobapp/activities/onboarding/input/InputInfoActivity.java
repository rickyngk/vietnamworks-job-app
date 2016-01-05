package vietnamworks.com.vietnamworksjobapp.activities.onboarding.input;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import vietnamworks.com.helper.BaseActivity;
import vietnamworks.com.vietnamworksjobapp.R;
import vietnamworks.com.vietnamworksjobapp.activities.onboarding.input.fragments.InputIndustryFragment;
import vietnamworks.com.vietnamworksjobapp.activities.onboarding.input.fragments.InputLocationFragment;
import vietnamworks.com.vietnamworksjobapp.activities.onboarding.input.fragments.InputTitleFragment;
import vietnamworks.com.vietnamworksjobapp.models.UserLocalProfileModel;

public class InputInfoActivity extends BaseActivity {
    int currentPageIndex = 0;
    public final static int MAX_PAGE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSystemUI();
        setContentView(R.layout.activity_input_info);
        setPageIndex(0);
    }

    public void onSkip(View v) {
        UserLocalProfileModel.saveLocal();
        System.out.println(UserLocalProfileModel.getEntity().exportToHashMap());
        //TODO: on skip input
    }

    public void setPageIndex(int index) {
        currentPageIndex = index;
        LinearLayout indicators = (LinearLayout) findViewById(R.id.input_steps);
        indicators.removeAllViewsInLayout();

        Drawable circle;
        Drawable filled_circle;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            circle = getResources().getDrawable(R.drawable.shape_onboarding_indicator, this.getTheme());
            filled_circle = getResources().getDrawable(R.drawable.shape_onboarding_selected_indicator, this.getTheme());
        } else {
            circle = getResources().getDrawable(R.drawable.shape_onboarding_indicator);
            filled_circle = getResources().getDrawable(R.drawable.shape_onboarding_selected_indicator);
        }

        for (int i = 0; i < MAX_PAGE; i++) {
            ImageView img = new ImageView(this);
            img.setImageDrawable(index != i?circle:filled_circle);
            img.setAlpha(0.75f);
            indicators.addView(img);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(30, 30);
            lp.setMargins(50, 0, 50, 0);
            img.setLayoutParams(lp);
        }
        switch (index) {
            case 0:
                openFragment(new InputTitleFragment(), R.id.fragment_holder);
                break;
            case 1:
                openFragment(new InputLocationFragment(), R.id.fragment_holder);
                break;
            case 2:
                openFragment(new InputIndustryFragment(), R.id.fragment_holder);
                break;
        }
    }
}
