package vietnamworks.com.vietnamworksjobapp.activities.main.fragments;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import java.util.ArrayList;

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
import vietnamworks.com.vietnamworksjobapp.models.JobSearchModel;
import vietnamworks.com.vietnamworksjobapp.models.UserLocalProfileModel;
import vietnamworks.com.vnwcore.Auth;
import vietnamworks.com.vnwcore.VNWAPI;
import vietnamworks.com.vnwcore.matchingscore.MatchingScoreChangedListener;
import vietnamworks.com.vnwcore.matchingscore.MatchingScoreTable;

/**
 * Created by duynk on 1/5/16.
 */
public class CardsFragment extends BaseFragment {
    @Bind(R.id.cardview)
    CardStackView cardView;

    @Bind(R.id.job_title)
    AutoCompleteTextView jobTitle;
    ArrayAdapter<String> adapter;

    boolean preventTextChangedEvent = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_cards, container, false);
        ButterKnife.bind(this, rootView);
        cardView.setDelegate(delegate);

        preventTextChangedEvent = true;
        jobTitle.setText(UserLocalProfileModel.getEntity().getJobTitle());
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line);
        jobTitle.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == getResources().getInteger(R.integer.ime_job_title) || actionId == EditorInfo.IME_NULL) && event == null) {
                    UserLocalProfileModel.getEntity().setJobTitle(jobTitle.getText().toString());
                    UserLocalProfileModel.saveLocal();
                    BaseActivity.hideKeyboard();
                    adapter.clear();
                    adapter.getFilter().filter(jobTitle.getText(), null);
                    cardView.reset();
                    jobTitle.clearFocus();
                    return true;
                }
                return false;
            }
        });
        jobTitle.setAdapter(adapter);
        jobTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (preventTextChangedEvent) {
                    preventTextChangedEvent = false;
                    return;
                }
                VNWAPI.jobTitleSuggestion(getContext(), jobTitle.getText().toString(), new Callback() {
                    @Override
                    public void onCompleted(Context context, CallbackResult result) {
                        adapter.clear();
                        if (!result.hasError()) {
                            try {
                                ArrayList<String> data = (ArrayList<String>) result.getData();
                                if (data != null) {
                                    for (int i = 0; i < data.size(); i++) {
                                        adapter.add(data.get(i));
                                    }
                                }
                                System.out.println(data);
                                adapter.getFilter().filter(jobTitle.getText(), null);
                            } catch (Exception E) {
                                E.printStackTrace();
                            }
                        }
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        MatchingScoreTable.setOnMatchingScoreChangedListener(new MatchingScoreChangedListener() {
            @Override
            public void onChanged(String userId, String jobId, int score) {
                System.out.println(">>> " + userId + "  " + jobId + " " + score);
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
                    if (Auth.getAuthData() != null) {
                        MatchingScoreTable.get(getContext(), Auth.getAuthData().getProfile().getUserId(), JobSearchModel.exportJobListAsArray());
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
                BaseActivity act = getActivityRef(BaseActivity.class);
                TextView ele = (TextView)cardView.getFront().findViewById(R.id.job_card_job_title);

                Bundle bundle = new Bundle();
                bundle.putString("jobTitle", ele.getText().toString());
                bundle.putString("jobId", JobSearchModel.get(index).getId());

                act.pushFragmentAnimateTransition(
                        new JobDetailFragment(),
                        R.id.fragment_holder,
                        bundle,
                        new BaseActivity.ShareAnimationView(ele, getString(R.string.transition_job_card_to_job_detail))
                );
            }
        }
    };

    public void onLayoutChanged(Rect r, boolean isSoftKeyShown) {
        if (!isSoftKeyShown) {
            preventTextChangedEvent = true;
            jobTitle.setText(UserLocalProfileModel.getEntity().getJobTitle());
            jobTitle.clearFocus();
        } else {
            jobTitle.requestFocus();
            jobTitle.setSelection(jobTitle.getText().length());
        }
    }
}
