package com.simprosys.scan.qrcode.barcode.reader.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.simprosys.scan.qrcode.barcode.reader.R;
import com.simprosys.scan.qrcode.barcode.reader.model.QRGenerateItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by simprosys on 1/2/19.
 */

public class QRAndBarCodeGenerateAdapter extends RecyclerView.Adapter<QRAndBarCodeGenerateAdapter.QRAndBarCodeGenerateViewHolder> {
    private Context mContext;
    private ArrayList<QRGenerateItem> mItemList;
    private OnItemClickListener mListener;

    public QRAndBarCodeGenerateAdapter(Context mContext, ArrayList<QRGenerateItem> mItemList) {
        this.mContext = mContext;
        this.mItemList = mItemList;
    }

    @NonNull
    @Override
    public QRAndBarCodeGenerateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_qr_and_bar_code_generate, parent, false);

        return new QRAndBarCodeGenerateViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull QRAndBarCodeGenerateViewHolder holder,  int position) {
        QRGenerateItem item = mItemList.get(position);

        holder.imgIcon.setImageResource(item.Image);
        holder.txtTitle.setText(item.name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    public class QRAndBarCodeGenerateViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgIcon)
        public ImageView imgIcon;

        @BindView(R.id.txtTitle)
        TextView txtTitle;

        QRAndBarCodeGenerateViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClick(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
