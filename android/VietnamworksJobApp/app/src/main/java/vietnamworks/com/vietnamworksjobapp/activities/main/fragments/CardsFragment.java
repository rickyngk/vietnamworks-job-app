package vietnamworks.com.vietnamworksjobapp.activities.main.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import R.cardstack.CardStackView;
import R.cardstack.CardStackViewDelegate;
import R.helper.BaseActivity;
import R.helper.BaseFragment;
import R.helper.Callback;
import R.helper.CallbackResult;
import butterknife.Bind;
import butterknife.ButterKnife;
import vietnamworks.com.vietnamworksjobapp.R;
import vietnamworks.com.vietnamworksjobapp.custom_view.CardView;
import vietnamworks.com.vietnamworksjobapp.custom_view.EmptyCardView;
import vietnamworks.com.vietnamworksjobapp.models.JobDetailModel;
import vietnamworks.com.vietnamworksjobapp.models.JobSearchModel;
import vietnamworks.com.vietnamworksjobapp.models.UserLocalProfileModel;
import vietnamworks.com.vnwcore.entities.Job;

/**
 * Created by duynk on 1/5/16.
 */
public class CardsFragment extends BaseFragment {
    @Bind(R.id.cardview)
    CardStackView cardView;

    @Bind(R.id.industry_spinner)
    Spinner industrySpinner;

    boolean isFirstInit = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_cards, container, false);
        ButterKnife.bind(this, rootView);
        cardView.setDelegate(delegate);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.industry_array, R.layout.cv_dropdown_simple);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        industrySpinner.setAdapter(adapter);

        String currentIndustry = UserLocalProfileModel.getEntity().getIndustry();
        for (int i = 0; i < adapter.getCount(); i++) {
            String adapterItem = adapter.getItem(i).toString();
            if (currentIndustry.equals(adapterItem)) {
                industrySpinner.setSelection(i);
                break;
            }
        }
        isFirstInit = true;

        industrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!isFirstInit) {
                    String industry = "";
                    String industry_code = "";
                    if (position > 0) {
                        industry = industrySpinner.getSelectedItem().toString();
                        industry_code = getResources().getStringArray(R.array.industry_code)[position];
                    }
                    UserLocalProfileModel.getEntity().setIndustry(industry);
                    UserLocalProfileModel.getEntity().setIndustryCode(industry_code);
                    UserLocalProfileModel.saveLocal();
                    cardView.reset();
                } else {
                    isFirstInit = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return rootView;
    }

    CardStackViewDelegate delegate = new CardStackViewDelegate() {
        @Override
        public void onStarted(final CardStackView v) {
            JobSearchModel.load(getContext(), new Callback() {
                @Override
                public void onCompleted(Context context, CallbackResult result) {
                    if (result.hasError()) {
                        System.out.println("ERROR: " + result.getError().getMessage());
                    }
                    v.ready();
                }
            });
        }

        @Override
        public View onLoadView(CardStackView v, int index) {
            CardView cv = new CardView(CardsFragment.this.getContext());
            cv.setViewModel(JobSearchModel.get(index));
            return cv;
        }

        @Override
        public View onLoadEmptyView(CardStackView v) {
            return new EmptyCardView(CardsFragment.this.getContext());
        }

        @Override
        public int getCount() {
            return JobSearchModel.count();
        }

        @Override
        public void onDrag(CardStackView v, float confidence) {
        }

        @Override
        public void onActive(CardStackView v, int index) {
        }

        @Override
        public void onEndOfStack(CardStackView v) {
            v.reset();
        }

        @Override
        public void onOpen(CardStackView v, int index) {
            if (index == -1) { //click on empty card
                v.reset();
            } else {
                JobDetailModel.load(getContext(), JobSearchModel.get(index).getId(), new Callback() {
                    @Override
                    public void onCompleted(Context context, CallbackResult result) {
                        if (!result.hasError()) {
                            Job job = (Job) result.getData();
                            BaseActivity act = getActivityRef(BaseActivity.class);
                            TextView ele = (TextView)cardView.getFront().findViewById(R.id.job_card_job_title);

                            Bundle bundle = new Bundle();
                            bundle.putString("jobTitle", ele.getText().toString());

                            act.pushFragmentWithShareAnimation(
                                    new JobDetailFragment(),
                                    R.id.fragment_holder,
                                    bundle,
                                    0, 0,
                                    new BaseActivity.ShareAnimationView(ele, getString(R.string.transition_job_card_to_job_detail))
                            );
                        }
                    }
                });
            }
        }
    };
}
