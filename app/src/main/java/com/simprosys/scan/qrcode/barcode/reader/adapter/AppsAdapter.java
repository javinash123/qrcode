package com.simprosys.scan.qrcode.barcode.reader.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.simprosys.scan.qrcode.barcode.reader.R;
import com.simprosys.scan.qrcode.barcode.reader.utility.barcodeUtils.AppsBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.MyViewHolder> {
    int status;
    MyViewHolder myViewHolder;
    private ArrayList<AppsBean> dataList;
    private ArrayList<AppsBean> arraylist;
    Context mContext;

    public AppsAdapter(Context contexts, ArrayList<AppsBean> myList) {
        this.mContext=contexts;
        this.dataList = myList;
        this.arraylist = new ArrayList<AppsBean>();
        this.arraylist.addAll(myList);
        status=1;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.app_card, parent, false);
        myViewHolder=new MyViewHolder(itemView);
        return myViewHolder;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final AppsBean viewList=dataList.get(position);
        Picasso.with(mContext).load(viewList.getIcon_url())
                .fit()
                .into(holder.imgMain);
        holder.tvName.setText(viewList.getName());
        holder.ratingBar.setRating(Float.parseFloat(viewList.getRating()));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + viewList.getPackageName())));
                } catch (android.content.ActivityNotFoundException anfe) {
                    mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + viewList.getPackageName())));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMain;
        TextView tvName;
        RatingBar ratingBar;
        LinearLayout linearLayout;
        public MyViewHolder(View view) {
            super(view);
            ratingBar = (RatingBar) view.findViewById(R.id.rateView);
            imgMain=(ImageView) view.findViewById(R.id.imageView);
            tvName=(TextView)view.findViewById(R.id.tvName);
            linearLayout=(LinearLayout) view.findViewById(R.id.linearLayout);

        }
    }
}
