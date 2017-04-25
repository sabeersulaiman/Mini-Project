package com.a96group.appdup.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a96group.appdup.R;
import com.a96group.appdup.models.Plan;

import java.util.List;

/**
 * Created by ss on 25/4/17.
 */

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.MyViewHolder> {

    private List<Plan> planList;

    /**
     * View holder class
     * */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date;
        public TextView planName;

        public MyViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.date);
            planName = (TextView) view.findViewById(R.id.plan_name);
        }
    }

    public PlanAdapter(List<Plan> planList) {
        this.planList = planList;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Plan p = planList.get(position);
        holder.date.setText(p.getDate());
        holder.planName.setText(String.valueOf(p.getPlan()));
    }

    @Override
    public int getItemCount() {
        return planList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_single_diet,parent, false);
        return new MyViewHolder(v);
    }
}
