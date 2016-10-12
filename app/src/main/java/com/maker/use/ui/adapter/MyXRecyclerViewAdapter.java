package com.maker.use.ui.adapter;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class MyXRecyclerViewAdapter extends RecyclerView.Adapter<MyXRecyclerViewAdapter.MyViewHolder> {

    List<Commodity> CommodityList;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private OnRecyclerViewItemLongClickListener mOnItemLongClickListener;

    public MyXRecyclerViewAdapter(List<Commodity> list) {
        this.CommodityList = list;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_commoditylist, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
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

        //将数据保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(R.string.delete_commodity, CommodityList.get(position));
        holder.itemView.setTag(R.string.delete_position, position);
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return CommodityList.size();
    }

    public void add(List<Commodity> list) {
        if (CommodityList != null) {
            CommodityList.addAll(list);
            notifyItemInserted(CommodityList.size());
        }
    }

    public void add(int position, Commodity commodity) {
        if (CommodityList != null) {
            CommodityList.add(position, commodity);
            notifyItemInserted(position);
        }
    }

    public void delete(int position) {
        if (CommodityList != null) {
            CommodityList.remove(position);
            Log.e("index", position + "");
            notifyItemRangeRemoved(position,CommodityList.size());
//            notifyItemRangeChanged(1,CommodityList.size());
//            notifyItemRemoved(position);
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnRecyclerViewItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Commodity commodity);
    }

    public interface OnRecyclerViewItemLongClickListener {
        void onItemLongClick(View view, Commodity commodity, int position);
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
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

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                //注意这里使用getTag方法获取数据
                mOnItemClickListener.onItemClick(v, (Commodity) v.getTag(R.string.delete_commodity));
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mOnItemLongClickListener != null) {
                //注意这里使用getTag方法获取数据
                mOnItemLongClickListener.onItemLongClick(v, (Commodity) v.getTag(R.string.delete_commodity), (int) v.getTag(R.string.delete_position));
            }
            return true;
        }

    }

}