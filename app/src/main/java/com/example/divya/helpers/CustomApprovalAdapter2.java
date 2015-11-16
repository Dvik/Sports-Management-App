package com.example.divya.helpers;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.divya.models.Approval;
import com.example.divya.sepm.R;

import java.util.List;

/**
 * Created by Divya on 11/8/2015.
 */
public class CustomApprovalAdapter2 extends RecyclerView.Adapter<CustomApprovalAdapter2.ApprovalViewHolder> {
    String months[] ={"JAN","FEB","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEP","OCT","NOV","DEC"};

    List<Approval> approvals;

    public CustomApprovalAdapter2(List<Approval> approvals)
    {

        this.approvals = approvals;
    }

    public static class ApprovalViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView tt1,tt2,tt3,tt4,tt5;
        TextView personAge;

        ApprovalViewHolder(View itemView) {
            super(itemView);


            tt1 = (TextView) itemView.findViewById(R.id.approval_subject_tv);
            tt2 = (TextView) itemView.findViewById(R.id.approval_assigned_tv);

            tt3 = (TextView) itemView.findViewById(R.id.approval_day_tv);
            tt4 = (TextView) itemView.findViewById(R.id.approval_month_tv);



        }
    }

    @Override
    public int getItemCount()
    {
        return approvals.size();
    }
    @Override
    public void onBindViewHolder(ApprovalViewHolder appViewHolder, int position) {

        appViewHolder.tt1.setText((String) approvals.get(position).subject);
        appViewHolder.tt2.setText("Assigned to: "+(String) approvals.get(position).assigned);
        String date = (String) approvals.get(position).deadline;
        String odate = date;
        int i = date.indexOf('/');
        String newdate = date.substring(0,i);
        appViewHolder.tt3.setText(newdate);

        String mon = odate.substring(3,5);
        Integer m = Integer.parseInt(mon);
        appViewHolder.tt4.setText(months[m-1]);

    }


    @Override
    public ApprovalViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.approvalrow, viewGroup, false);
        ApprovalViewHolder pvh = new ApprovalViewHolder(v);
        return pvh;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}