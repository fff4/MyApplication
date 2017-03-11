package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.GurilyBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/10.
 */

public class MListAdapter extends BaseAdapter {

    private List<GurilyBean.ResultsBean> mList = new ArrayList<>();
    private Context mContext;

    public MListAdapter(List<GurilyBean.ResultsBean> list,Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        if(mList != null) {
            return mList.size();
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GurilHolder gh;
        if(convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_guril , null);
            gh = new GurilHolder(convertView);

            convertView.setTag(gh);

        }else {
            gh = (GurilHolder) convertView.getTag();
        }
        GurilyBean.ResultsBean resultsBean = mList.get(position);
        gh.tv.setText(resultsBean.getWho() + "发送的图片");
        Glide.with(mContext).load(resultsBean.getUrl()).centerCrop().into(gh.iv);

        return convertView;
    }

    static class GurilHolder{
        ImageView iv;
        TextView tv;

        public GurilHolder(View v) {
            iv = (ImageView) v.findViewById(R.id.iv_item);
            tv = (TextView) v.findViewById(R.id.tv_item);
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
