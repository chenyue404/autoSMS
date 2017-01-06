package autosms.cy.autosms.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import autosms.cy.autosms.activity.ChatActivity;
import autosms.cy.autosms.bean1.SmsListBean;
import autosms.cy.autosms.R;

/**
 * Created by cy on 2016/7/7 0007.
 */
public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<SmsListBean> mList;

    public MainRecyclerViewAdapter(Context context, ArrayList list) {
        mContext = context;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item_view, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        SmsListBean smsListBean = mList.get(position);
        holder.senderTV.setText(smsListBean.getSenderName());
        holder.contentTV.setText(smsListBean.getSmsContext());
        holder.sendTimeTV.setText(smsListBean.getTime());

        holder.wholeRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ChatActivity.SMSBEANLIST, mList.get(position));
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout wholeRL;
        TextView senderTV, sendTimeTV, contentTV;

        public ViewHolder(View itemView) {
            super(itemView);
            senderTV = (TextView) itemView.findViewById(R.id.sender_tv);
            sendTimeTV = (TextView) itemView.findViewById(R.id.send_time_tv);
            contentTV = (TextView) itemView.findViewById(R.id.content_tv);
            wholeRL = (RelativeLayout) senderTV.getParent();
        }
    }
}
