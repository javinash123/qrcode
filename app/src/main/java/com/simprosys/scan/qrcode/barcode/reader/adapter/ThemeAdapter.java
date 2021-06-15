package com.simprosys.scan.qrcode.barcode.reader.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simprosys.scan.qrcode.barcode.reader.R;
import com.simprosys.scan.qrcode.barcode.reader.model.Theme;
import com.simprosys.scan.qrcode.barcode.reader.utility.SharedPreferenceHelper;
import com.simprosys.scan.qrcode.barcode.reader.utility.Utility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ThemeViewHolder> {
    private Context context;
    private ArrayList<Theme> mItemList;
    private OnItemClickListener mListener;
    private int selectedPosition = -1;

    public ThemeAdapter(Context context, ArrayList<Theme> mItemList) {
        this.context = context;
        this.mItemList = mItemList;
    }

    @NonNull
    @Override
    public ThemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_theme_list, parent, false);
        return new ThemeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThemeViewHolder holder, int position) {
        Theme item = mItemList.get(position);

        //BaseActivity.mTheme = mItemList.get(position).getId();

        final int currentPosition = SharedPreferenceHelper.getSharedPreferenceInt(context, "SelectedTheme", 0);

        if (selectedPosition == position || currentPosition == position) {
            holder.imgThemeSelected.setVisibility(View.VISIBLE);
        } else {
            holder.imgThemeSelected.setVisibility(View.GONE);
        }

        holder.leyTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Utility.stopClick()) {
                    return;
                }

                if (selectedPosition == position || currentPosition == position) {
                    return;
                } else {
                    if (mListener != null) {
                        mListener.onItemClick(view, position);

                        selectedPosition = position;
                        ThemeAdapter.this.notifyDataSetChanged();
                    }
                }
            }
        });

        holder.imgTheme.setColorFilter(context.getResources().getColor(item.getPrimaryColor()), PorterDuff.Mode.SRC_IN);
        holder.txtTheme.setText(item.getColorName());
        holder.imgThemeSelected.setColorFilter(context.getResources().getColor(item.getPrimaryColor()), PorterDuff.Mode.SRC_IN);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public void setOnItemClick(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ThemeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.leyTheme)
        LinearLayout leyTheme;
        @BindView(R.id.imgTheme)
        ImageView imgTheme;
        @BindView(R.id.txtTheme)
        TextView txtTheme;
        @BindView(R.id.imgThemeSelected)
        ImageView imgThemeSelected;

        public ThemeViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}