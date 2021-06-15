package com.simprosys.scan.qrcode.barcode.reader.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.core.os.ConfigurationCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simprosys.scan.qrcode.barcode.reader.R;
import com.simprosys.scan.qrcode.barcode.reader.activity.HomeActivity;
import com.simprosys.scan.qrcode.barcode.reader.model.Language;
import com.simprosys.scan.qrcode.barcode.reader.utility.LanguageHelper;
import com.simprosys.scan.qrcode.barcode.reader.utility.Utility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LanguageSelectionAdapter extends RecyclerView.Adapter<LanguageSelectionAdapter.LanguageSelectionViewHolder> {
    private ArrayList<Language> langAndLocales;
    private Activity activity;
    private int selectedPosition = -1;

    public LanguageSelectionAdapter(Activity activity, ArrayList<Language> langAndLocales) {
        this.activity = activity;
        this.langAndLocales = langAndLocales;
    }

    @NonNull
    @Override
    public LanguageSelectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_language_list, parent, false);
        return new LanguageSelectionViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LanguageSelectionViewHolder holder, int position) {
        final Language langAndLocale = langAndLocales.get(position);

        switch (langAndLocale.getLanguageCode()) {
            case "zh-rCN":
                holder.txtLanguage.setText(langAndLocale.getLanguageName());
                break;
            case "zh-rTW":
                holder.txtLanguage.setText(langAndLocale.getLanguageName());
                break;
            default:
                holder.txtLanguage.setText(langAndLocale.getLanguageName() + " ( " + langAndLocale.getLanguageNameInDefaultLocale() + " ) ");
                break;
        }

        String currentLanguage = LanguageHelper.getLanguage(activity);

        holder.leyLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utility.stopClick()) {
                    return;
                }

                if (selectedPosition == position || currentLanguage.equals(langAndLocale.getLanguageCode())) {
                    return;
                } else {
                    final Language langAndLocale = langAndLocales.get(position);
                    switchLanguage(activity, langAndLocale.getLanguageCode());

                    selectedPosition = position;
                }
            }
        });

        holder.imgLanguageSelected.setColorFilter(Utility.fetchPrimaryColor(activity), PorterDuff.Mode.SRC_IN);

        if (selectedPosition == position || currentLanguage.equals(langAndLocale.getLanguageCode())) {
            holder.imgLanguageSelected.setVisibility(View.VISIBLE);
        } else {
            holder.imgLanguageSelected.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return langAndLocales.size();
    }

    private void switchLanguage(Activity activity, String languageCode) {
        String language = (ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration())).toString().replaceAll("\\[", "").replaceAll("\\]", "");

        LanguageHelper.setLanguage(activity, languageCode);
       /* relaunch(activity);*/

        Intent dataRefresh = new Intent();
        dataRefresh.setAction("themeRefresh");
        dataRefresh.putExtra("languageCode",languageCode);
        LocalBroadcastManager.getInstance(activity).sendBroadcast(dataRefresh);
    }

    private void relaunch(Activity activity) {
        Intent intent = new Intent(activity, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        Runtime.getRuntime().exit(0);
        activity.finish();
    }

    class LanguageSelectionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.leyLanguage)
        LinearLayout leyLanguage;

        @BindView(R.id.txtLanguage)
        TextView txtLanguage;

        @BindView(R.id.imgLanguageSelected)
        ImageView imgLanguageSelected;

        LanguageSelectionViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
