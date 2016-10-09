package com.maker.use.ui.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maker.use.R;
import com.maker.use.domain.Commodity;
import com.maker.use.global.UsedMarketURL;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;


/**
 * Created by XISEVEN on 2016/9/27.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    List<Commodity> CommodityList;

    public MyRecyclerViewAdapter(List<Commodity> list) {
        this.CommodityList = list;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_commoditylist, parent, false));
        return holder;
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setIgnoreGif(false)
                .setFailureDrawableId(R.drawable.error)
                .setLoadingDrawableId(R.drawable.loading)
                .build();
        x.image().bind(holder.iv_img, UsedMarketURL.server_heart + "//" + CommodityList.get(position).imgurl, imageOptions);
        holder.tv_name.setText(CommodityList.get(position).name);
        holder.tv_description.setText(CommodityList.get(position).description);
        holder.tv_price.setText(String.valueOf(CommodityList.get(position).price));
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return CommodityList.size();
    }

    public void add(Commodity commodity) {
        if (CommodityList != null) {
            CommodityList.add(commodity);
            notifyItemInserted(CommodityList.size());
        }
    }

    public void add(int position, Commodity commodity) {
        if (CommodityList != null) {
            CommodityList.add(position, commodity);
            notifyItemInserted(position);
        }
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_img;
        TextView tv_name;
        TextView tv_description;
        TextView tv_price;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            tv_description = (TextView) itemView.findViewById(R.id.tv_description);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
        }
    }
}