package vietnamworks.com.vietnamworksjobapp.activities.main.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import vietnamworks.com.vietnamworksjobapp.models.JobSearchModel;
import vietnamworks.com.vnwcore.Auth;
import vietnamworks.com.vnwcore.matchingscore.MatchingScoreChangedListener;
import vietnamworks.com.vnwcore.matchingscore.MatchingScoreTable;

/**
 * Created by duynk on 1/5/16.
 */
public class CardsFragment extends BaseFragment {
    @Bind(R.id.cardview)
    CardStackView cardView;

    CardStackViewDelegate delegate = new CardStackViewDelegate() {
        @Override
        public void onStarted(final CardStackView v) {
            JobSearchModel.load(getContext(), new Callback<Object>() {
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
            if (v.getFrontViewHolder() != null) {
                ((CardView)v.getFrontView()).setConfidence(confidence);
            }
        }

        @Override
        public void onRollback(CardStackView v) {
            ((CardView)v.getFrontView()).setConfidence(0);
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
                TextView ele = (TextView) cardView.getFrontViewHolder().findViewById(R.id.job_card_job_title);

                Bundle bundle = new Bundle();
                bundle.putString("jobTitle", ele.getText().toString());
                bundle.putString("jobId", JobSearchModel.get(index).getId());

                BaseActivity.pushFragmentAnimateTransition(
                        new JobDetailFragment(),
                        R.id.fragment_holder,
                        bundle,
                        new BaseActivity.ShareAnimationView(ele, getString(R.string.transition_job_card_to_job_detail))
                );
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_cards, container, false);
        ButterKnife.bind(this, rootView);
        cardView.setDelegate(delegate);

        MatchingScoreTable.setOnMatchingScoreChangedListener(new MatchingScoreChangedListener() {
            @Override
            public void onChanged(String userId, String jobId, int score) {
                System.out.println(">>> " + userId + "  " + jobId + " " + score);
            }
        });

        return rootView;
    }

    public void reset() {
        if (cardView != null) {
            cardView.reset();
        }
    }
}
