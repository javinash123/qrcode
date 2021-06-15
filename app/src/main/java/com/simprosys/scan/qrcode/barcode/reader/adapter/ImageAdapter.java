package com.simprosys.scan.qrcode.barcode.reader.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.simprosys.scan.qrcode.barcode.reader.R;
import com.simprosys.scan.qrcode.barcode.reader.model.ImageItem;
import com.simprosys.scan.qrcode.barcode.reader.utility.Utility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by simprosys on 26/2/19.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context context;
    private ArrayList<ImageItem> mItemList;
    private OnItemClickListener mListener;

    public ImageAdapter(Context context, ArrayList<ImageItem> mItemList) {
        this.context = context;
        this.mItemList = mItemList;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_image, parent, false);

        return new ImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, final int position) {
        ImageItem item = mItemList.get(position);

        holder.imgThumb.setImageResource(item.imageId);

        holder.leyImage.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if(!Utility.stopClick())
                    return;
                if (mListener != null) {
                    mListener.onItemClick(view, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgThumb)
        ImageView imgThumb;

        @BindView(R.id.leyImage)
        LinearLayout leyImage;

        public ImageViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClick(OnItemClickListener mListener) {
        this.mListener = mListener;
    }
}
