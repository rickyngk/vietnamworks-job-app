package vietnamworks.com.vietnamworksjobapp.activities.main.fragments;
/**
 * Created by duynk on 1/25/16.

 */

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import R.helper.BaseActivity;
import R.helper.BaseFragment;
import R.helper.Callback;
import R.helper.CallbackResult;
import butterknife.Bind;
import butterknife.ButterKnife;
import vietnamworks.com.vietnamworksjobapp.R;
import vietnamworks.com.vnwcore.VNWAPI;
import vietnamworks.com.vnwcore.entities.AppliedJob;

public class AppliedJobsFragment extends BaseFragment {
    @Bind(R.id.progressBar_applied_jobs)
    ProgressBar progressBar;

    @Bind(R.id.applied_jobs_recycler_view)
    RecyclerView appliedJobsRecyclerView;
    MyRecyclerAdapter adapter;


    private static class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {
        private List<AppliedJob> items;
        private Context mContext;
        public MyRecyclerAdapter(Context context, ArrayList<AppliedJob> items) {
            this.items = items;
            this.mContext = context;
        }

        public void setItems(ArrayList<AppliedJob> items) {
            this.items = items;
            this.notifyDataSetChanged();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View view) {
                super(view);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.two_line_list_item, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            AppliedJob item = items.get(i);
            TextView lineOneView = (TextView)viewHolder.itemView.findViewById(android.R.id.text1);
            TextView lineTwoView = (TextView)viewHolder.itemView.findViewById(android.R.id.text2);

            lineOneView.setText(item.getJobTitle());
            lineTwoView.setText(item.getJobCompany());
        }

        @Override
        public int getItemCount() {
            return (null != items ? items.size() : 0);
        }
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_applied_jobs, container, false);
        ButterKnife.bind(this, rootView);

        progressBar.setVisibility(View.VISIBLE);
        adapter = new MyRecyclerAdapter(getContext(), null);
        appliedJobsRecyclerView.setAdapter(adapter);
        appliedJobsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        BaseActivity.timeout(new Runnable() {
            @Override
            public void run() {
                VNWAPI.getAppliedJobs(getContext(), new Callback() {
                    @Override
                    public void onCompleted(Context context, CallbackResult result) {
                        if (!result.hasError()) {
                            adapter.setItems((ArrayList<AppliedJob>)result.getData());
                            progressBar.setVisibility(View.GONE);
                        } else {
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        }, 1000);


        return rootView;
    }
}
