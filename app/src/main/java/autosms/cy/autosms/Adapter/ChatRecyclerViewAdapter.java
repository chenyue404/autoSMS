package autosms.cy.autosms.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import autosms.cy.autosms.Bean.SmsBean;
import autosms.cy.autosms.R;

/**
 * Created by chenyue on 2016/12/2 0002.
 */

public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<SmsBean> mList;
    private final int LEFT = 0, RIGHT = 1;

    public ChatRecyclerViewAdapter(Context context, ArrayList list) {
        mContext = context;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == LEFT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_view, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_send_view, parent, false);
        }
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        int type = LEFT;
        if (mList.get(position).getSendOrReceive().equals("2")) {
            type = RIGHT;
        }
        return type;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SmsBean smsBean = mList.get(position);
        holder.message_tv.setText(smsBean.getSmsContext());
        holder.time_tv.setText(smsBean.getTime());
    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        } else {
            return mList.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView message_tv;
        public TextView time_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            message_tv = (TextView) itemView.findViewById(R.id.message_tv);
            time_tv = (TextView) itemView.findViewById(R.id.time_tv);
        }
    }
}
