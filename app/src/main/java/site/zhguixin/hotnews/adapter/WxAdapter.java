package site.zhguixin.hotnews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import site.zhguixin.hotnews.R;
import site.zhguixin.hotnews.entity.WXNewsBean;
import site.zhguixin.hotnews.ui.DetailActivity;

/**
 * Created by zhguixin on 2017/12/4.
 */

public class WxAdapter extends RecyclerView.Adapter<WxAdapter.ViewHolder> {

    private Context mContext;
    private List<WXNewsBean> mList;

    public WxAdapter(Context context, List<WXNewsBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.wx_news_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titleView.setText(mList.get(position).getTitle());
        holder.descriptionView.setText(mList.get(position).getDescription());
        holder.dateView.setText(mList.get(position).getCtime());
        Glide.with(mContext)
                .load(mList.get(position).getPicUrl())
                .centerCrop()
                .crossFade()
                .into(holder.picView);
        final String url = mList.get(position).getUrl();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity.launch(new DetailActivity.Builder()
                        .setContext(mContext)
                        .setUrl(url));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.description)
        TextView descriptionView;

        @BindView(R.id.title)
        TextView titleView;

        @BindView(R.id.date)
        TextView dateView;

        @BindView(R.id.pic)
        ImageView picView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
