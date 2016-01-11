package vietnamworks.com.vietnamworksjobapp.activities.main.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import vietnamworks.com.cardstack.CardStackView;
import vietnamworks.com.cardstack.CardStackViewDelegate;
import R.helper.BaseFragment;
import R.helper.Callback;
import R.helper.CallbackResult;
import vietnamworks.com.vietnamworksjobapp.R;
import vietnamworks.com.vietnamworksjobapp.custom_view.CardView;
import vietnamworks.com.vietnamworksjobapp.models.JobModel;

/**
 * Created by duynk on 1/5/16.
 */
public class CardsFragment extends BaseFragment {
    @Bind(R.id.cardview)
    CardStackView cardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_cards, container, false);
        ButterKnife.bind(this, rootView);
        cardView.setDelegate(delegate);

        return rootView;
    }

    CardStackViewDelegate delegate = new CardStackViewDelegate() {
        @Override
        public void onStarted(final CardStackView v) {
            JobModel.load(null, new Callback() {
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
            cv.setViewModel(JobModel.get(index));
            return cv;
        }

        @Override
        public int getCount() {
            return JobModel.count();
        }

        @Override
        public void onDrag(CardStackView v, float confidence) {
        }

        @Override
        public void onActive(CardStackView v, int index) {}

        @Override
        public void onEndOfStack(CardStackView v) {
            v.reset();
        }

        @Override
        public void onOpen(CardStackView v, int index) {
        }
    };
}
