package com.simprosys.scan.qrcode.barcode.reader.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simprosys.scan.qrcode.barcode.reader.R;
import com.simprosys.scan.qrcode.barcode.reader.model.QRAndBarCode;
import com.simprosys.scan.qrcode.barcode.reader.utility.Utility;
import com.simprosys.scan.qrcode.barcode.reader.view.SwipeLayout;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.recyclerview.widget.RecyclerView.NO_POSITION;

/**
 * Created by simprosys on 1/2/19.
 */


public class QRAndBarCodeAdapter extends RecyclerView.Adapter<QRAndBarCodeAdapter.QRAndBarCodeViewHolder> {
    public ArrayList<QRAndBarCode> mItemList = new ArrayList<>();
    public ArrayList<QRAndBarCode> mSelectedItemList = new ArrayList<>();
    public boolean isImageSelectOrDeselect = false;
    private Context mContext;
    private OnItemClickListener mItemListener;
    private OnDeleteClickListener mDeleteListener;
    private OnDeleteSwipeLayoutListener mDeleteSwipeLayoutListener;
    public static SwipeLayout lastSwipeLayout;

    public QRAndBarCodeAdapter(Context mContext, ArrayList<QRAndBarCode> mItemList) {
        this.mContext = mContext;
        this.mItemList = mItemList;
    }

    public QRAndBarCodeAdapter(Context mContext, ArrayList<QRAndBarCode> mItemList, ArrayList<QRAndBarCode> mSelectedItemList) {
        this.mContext = mContext;
        this.mItemList = mItemList;
        this.mSelectedItemList = mSelectedItemList;
    }

    public boolean isImageSelectOrDeselect() {
        return isImageSelectOrDeselect;
    }

    public void setImageSelectOrDeselect(boolean imageSelectOrDeselect) {
        isImageSelectOrDeselect = imageSelectOrDeselect;
    }

    @NonNull
    @Override
    public QRAndBarCodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_qr_and_bar_code, parent, false);
        return new QRAndBarCodeViewHolder(view);
    }

    @SuppressLint({"RecyclerView", "ResourceType"})
    @Override
    public void onBindViewHolder(@NonNull QRAndBarCodeViewHolder holder, int position) {
        QRAndBarCode item = mItemList.get(position);

        Configuration config = mContext.getResources().getConfiguration();

        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            holder.swipeLayout.setCurrentDirection(SwipeLayout.RIGHT);
        } else {
            holder.swipeLayout.setCurrentDirection(SwipeLayout.LEFT);
        }

        if(holder.swipeLayout.isRightOpen()){
            holder.swipeLayout.close(false);
        }else if(holder.swipeLayout.isLeftOpen()){
            holder.swipeLayout.close(false);
        }

        if (isImageSelectOrDeselect) {
            holder.swipeLayout.setVisibility(View.GONE);
            holder.relView2.setVisibility(View.VISIBLE);
            holder.relView.setVisibility(View.GONE);
        } else {
            holder.swipeLayout.setVisibility(View.VISIBLE);
            holder.relView2.setVisibility(View.GONE);
            holder.relView.setVisibility(View.VISIBLE);
        }

        if (mSelectedItemList.contains(mItemList.get(position))) {
            holder.imgDeSelect.setVisibility(View.GONE);
            holder.imgSelect.setVisibility(View.VISIBLE);
            holder.imgSelect.setColorFilter(Utility.fetchPrimaryColor(mContext), PorterDuff.Mode.SRC_IN);
            holder.relBgSelected.setVisibility(View.VISIBLE);
            holder.viewDivider2.setVisibility(View.GONE);

        } else {
            holder.imgDeSelect.setVisibility(View.VISIBLE);
            holder.imgSelect.setVisibility(View.GONE);
            holder.relBgSelected.setVisibility(View.GONE);
            holder.viewDivider2.setVisibility(View.VISIBLE);
        }

        setQrAndBarCodeType(holder, item);

        //holder.txtType.setText(item.getType());

        holder.txtDateAndTime.setText(item.getDateAndTime());
        holder.txtDateAndTime2.setText(item.getDateAndTime());

        holder.relView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemListener != null) {
                    mItemListener.onItemClick(view, position);
                }
            }
        });

        holder.txtRightDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDeleteListener != null) {
                    holder.swipeLayout.close();
                    mDeleteListener.onDeleteClick(view, position);
                }
            }
        });

        holder.swipeLayout.setOnActionsListener(new SwipeLayout.SwipeActionsListener() {
            @Override
            public void onOpen(int direction, boolean isContinuous) {
                Log.e("swipe","yes");
                if (lastSwipeLayout!=null)
                    lastSwipeLayout.close();
                lastSwipeLayout=holder.swipeLayout;
                if (direction == SwipeLayout.LEFT && isContinuous) {
                    if (position != NO_POSITION) {
                        if (mDeleteSwipeLayoutListener != null) {
                            holder.swipeLayout.close(false);
                            mDeleteSwipeLayoutListener.onDeleteSwipeLayout(position);

                        }

                    }
                } else if (direction == SwipeLayout.RIGHT && isContinuous) {
                    if (position != NO_POSITION) {
                        if (mDeleteSwipeLayoutListener != null) {
                            holder.swipeLayout.close(false);
                            mDeleteSwipeLayoutListener.onDeleteSwipeLayout(position);

                        }
                    }
                }
            }

            @Override
            public void onClose() {

            }
        });
    }
    private String getUriType(String type,String urlString) {
        URL url=null;
        int checkHost = 0;

        if (type.equals("URI")) {

            //URIParsedResult uriResult = (URIParsedResult) ResultParser.parseResult(Utility.qrCodeResult);

            String faceBook, instagram, whatsApp, twitter, linkedIn, snapChat;

            faceBook = "www.facebook.com";
            instagram = "www.instagram.com";
            whatsApp = "wa.me";
            twitter = "twitter.com";
            linkedIn = "www.linkedin.com";
            snapChat = "www.snapchat.com";

            try {
                url = new URL(urlString);
                Log.e("url description", url.getUserInfo() + " ");
                Log.e("url description", url.getRef() + " ");
                Log.e("url description", url.getProtocol() + " ");
                Log.e("url description", url.getHost() + " ");

                checkHost = 0;
            } catch (MalformedURLException e) {
                e.getMessage();
                //Utility.logE(TAG, " " + e.getMessage());

                checkHost = 1;
            }

            if (checkHost == 0) {
                if (faceBook.equals(url.getHost())) {
                    type = "FACEBOOK";
                    Log.e("TAG_", "Type : " + type);

                    return "FACEBOOK";
                } else if (instagram.equals(url.getHost())) {
                    type = "INSTAGRAM";
                    Log.e("TAG_", "Type : " + type);

                    return "INSTAGRAM";
                } else if (whatsApp.equals(url.getHost())) {
                    type = "WHATSAPP";
                    Log.e("TAG_", "Type : " + type);

                    return "WHATSAPP";
                } else if (twitter.equals(url.getHost())) {
                    type = "TWITTER";
                    Log.e("TAG_", "Type : " + type);

                    return "TWITTER";
                } else if (linkedIn.equals(url.getHost())) {
                    type = "LINKEDIN";
                    Log.e("TAG_", "Type : " + type);

                    return "LINKEDIN";
                } else if (snapChat.equals(url.getHost())) {
                    type = "SNAPCHAT";
                    Log.e("TAG_", "Type : " + type);

                    return "SNAPCHAT";
                } else if (snapChat.equals(url.getHost())) {
                    type = "SNAPCHAT";
                    Log.e("TAG_", "Type : " + type);

                    return "SNAPCHAT";
                } else {
                    Log.e("TAG_", "Type : " + type);

                    return type;
                }
            } else {

            }
        }
        return type;
    }
    private void setQrAndBarCodeType(QRAndBarCodeViewHolder holder, QRAndBarCode item) {
        holder.txtType.setText("");
        holder.imgType.setImageResource(0);
        holder.txtType2.setText("");
        holder.imgType2.setImageResource(0);
        String typeName;

        typeName = getUriType(item.getType(),item.getDetails());
        Log.e("typename",typeName);

        switch (typeName) {
            case "PRODUCT":
                if (item.getFormatType().equals("EAN_13")) {
                    holder.txtType.setText(mContext.getResources().getString(R.string.txtEan13Name));
                    holder.imgType.setImageResource(R.drawable.ic_barcode_border_black_24dp);
                    holder.txtType2.setText(mContext.getResources().getString(R.string.txtEan13Name));
                    holder.imgType2.setImageResource(R.drawable.ic_barcode_border_black_24dp);
                } else if (item.getFormatType().equals("EAN_8")) {
                    holder.txtType.setText(mContext.getResources().getString(R.string.txtEan8Name));
                    holder.imgType.setImageResource(R.drawable.ic_barcode_border_black_24dp);
                    holder.txtType2.setText(mContext.getResources().getString(R.string.txtEan8Name));
                    holder.imgType2.setImageResource(R.drawable.ic_barcode_border_black_24dp);
                } else if (item.getFormatType().equals("UPC_E")) {
                    holder.txtType.setText(mContext.getResources().getString(R.string.txtUpceName));
                    holder.imgType.setImageResource(R.drawable.ic_barcode_border_black_24dp);
                    holder.txtType2.setText(mContext.getResources().getString(R.string.txtUpceName));
                    holder.imgType2.setImageResource(R.drawable.ic_barcode_border_black_24dp);
                } else if (item.getFormatType().equals("UPC_A")) {
                    holder.txtType.setText(mContext.getResources().getString(R.string.txtUpcaName));
                    holder.imgType.setImageResource(R.drawable.ic_barcode_border_black_24dp);
                    holder.txtType2.setText(mContext.getResources().getString(R.string.txtUpcaName));
                    holder.imgType2.setImageResource(R.drawable.ic_barcode_border_black_24dp);
                } else {
                    holder.txtType.setText(mContext.getResources().getString(R.string.txtProductName));
                    holder.imgType.setImageResource(R.drawable.ic_barcode_border_black_24dp);
                    holder.txtType2.setText(mContext.getResources().getString(R.string.txtProductName));
                    holder.imgType2.setImageResource(R.drawable.ic_barcode_border_black_24dp);
                }

                break;

            case "TEXT":
                if (item.getFormatType().equals("CODE_39")) {
                    holder.txtType.setText(mContext.getResources().getString(R.string.txtCode39Name));
                    holder.imgType.setImageResource(R.drawable.ic_barcode_border_black_24dp);
                    holder.txtType2.setText(mContext.getResources().getString(R.string.txtCode39Name));
                    holder.imgType2.setImageResource(R.drawable.ic_barcode_border_black_24dp);
                } else if (item.getFormatType().equals("CODE_128")) {
                    holder.txtType.setText(mContext.getResources().getString(R.string.txtCode128Name));
                    holder.imgType.setImageResource(R.drawable.ic_barcode_border_black_24dp);
                    holder.txtType2.setText(mContext.getResources().getString(R.string.txtCode128Name));
                    holder.imgType2.setImageResource(R.drawable.ic_barcode_border_black_24dp);
                }else if (item.getFormatType().equals("CODE_93")) {
                    holder.txtType.setText(mContext.getResources().getString(R.string.txtCode93Name));
                    holder.imgType.setImageResource(R.drawable.ic_barcode_border_black_24dp);
                    holder.txtType2.setText(mContext.getResources().getString(R.string.txtCode93Name));
                    holder.imgType2.setImageResource(R.drawable.ic_barcode_border_black_24dp);
                } else {
                    holder.txtType.setText(mContext.getResources().getString(R.string.txtTextName));
                    holder.imgType.setImageResource(R.drawable.ic_text);
                    holder.txtType2.setText(mContext.getResources().getString(R.string.txtTextName));
                    holder.imgType2.setImageResource(R.drawable.ic_text);
                }

                break;

            case "TEL":
                holder.txtType.setText(mContext.getResources().getString(R.string.txtPhoneNumName));
                holder.imgType.setImageResource(R.drawable.ic_telephone);
                holder.txtType2.setText(mContext.getResources().getString(R.string.txtPhoneNumName));
                holder.imgType2.setImageResource(R.drawable.ic_telephone);
                break;

            case "SMS":
                holder.txtType.setText(mContext.getResources().getString(R.string.txtSmsName));
                holder.imgType.setImageResource(R.drawable.ic_sms);
                holder.txtType2.setText(mContext.getResources().getString(R.string.txtSmsName));
                holder.imgType2.setImageResource(R.drawable.ic_sms);

                break;

            case "EMAIL_ADDRESS":
                holder.txtType.setText(mContext.getResources().getString(R.string.txtEmailName));
                holder.imgType.setImageResource(R.drawable.ic_mail);
                holder.txtType2.setText(mContext.getResources().getString(R.string.txtEmailName));
                holder.imgType2.setImageResource(R.drawable.ic_mail);
                break;

            case "URL":
                holder.txtType.setText(mContext.getResources().getString(R.string.txtWebSiteName));
                holder.imgType.setImageResource(R.drawable.ic_web);
                holder.txtType2.setText(mContext.getResources().getString(R.string.txtWebSiteName));
                holder.imgType2.setImageResource(R.drawable.ic_web);
                break;

            case "GEO":
                holder.txtType.setText(mContext.getResources().getString(R.string.txtLocationName));
                holder.imgType.setImageResource(R.drawable.ic_location);
                holder.txtType2.setText(mContext.getResources().getString(R.string.txtLocationName));
                holder.imgType2.setImageResource(R.drawable.ic_location);
                break;

            case "WIFI":
                holder.txtType.setText(mContext.getResources().getString(R.string.txtWifiName));
                holder.imgType.setImageResource(R.drawable.ic_wifi);
                holder.txtType2.setText(mContext.getResources().getString(R.string.txtWifiName));
                holder.imgType2.setImageResource(R.drawable.ic_wifi);
                break;

            case "ADDRESSBOOK":
                holder.txtType.setText(mContext.getResources().getString(R.string.txtVCardName));
                holder.imgType.setImageResource(R.drawable.ic_vcard);
                holder.txtType2.setText(mContext.getResources().getString(R.string.txtVCardName));
                holder.imgType2.setImageResource(R.drawable.ic_vcard);
                break;

            case "CALENDAR":
                holder.txtType.setText(mContext.getResources().getString(R.string.txtEventName));
                holder.imgType.setImageResource(R.drawable.ic_calendar);
                holder.txtType2.setText(mContext.getResources().getString(R.string.txtEventName));
                holder.imgType2.setImageResource(R.drawable.ic_calendar);
                break;

            case "FACEBOOK":
                holder.txtType.setText(mContext.getResources().getString(R.string.txtFacebookName));
                holder.imgType.setImageResource(R.drawable.ic_facebook);
                holder.txtType2.setText(mContext.getResources().getString(R.string.txtFacebookName));
                holder.imgType2.setImageResource(R.drawable.ic_facebook);
                break;

            case "INSTAGRAM":
                holder.txtType.setText(mContext.getResources().getString(R.string.txtInstagramName));
                holder.imgType.setImageResource(R.mipmap.ic_instagram);
                holder.txtType2.setText(mContext.getResources().getString(R.string.txtInstagramName));
                holder.imgType2.setImageResource(R.mipmap.ic_instagram);
                break;

            case "WHATSAPP":
                holder.txtType.setText(mContext.getResources().getString(R.string.txtWhatsAppName));
                holder.imgType.setImageResource(R.drawable.ic_whatsapp);
                holder.txtType2.setText(mContext.getResources().getString(R.string.txtWhatsAppName));
                holder.imgType2.setImageResource(R.drawable.ic_whatsapp);
                break;

            case "TWITTER":
                holder.txtType.setText(mContext.getResources().getString(R.string.txtTwitterName));
                holder.imgType.setImageResource(R.drawable.ic_twitter);
                holder.txtType2.setText(mContext.getResources().getString(R.string.txtTwitterName));
                holder.imgType2.setImageResource(R.drawable.ic_twitter);
                break;

            case "LINKEDIN":
                holder.txtType.setText(mContext.getResources().getString(R.string.txtLinkedInName));
                holder.imgType.setImageResource(R.drawable.ic_linked_in);
                holder.txtType2.setText(mContext.getResources().getString(R.string.txtLinkedInName));
                holder.imgType2.setImageResource(R.drawable.ic_linked_in);
                break;

            case "SNAPCHAT":
                holder.txtType.setText(mContext.getResources().getString(R.string.txtSnapChatName));
                holder.imgType.setImageResource(R.drawable.ic_snapchat);
                holder.txtType2.setText(mContext.getResources().getString(R.string.txtSnapChatName));
                holder.imgType2.setImageResource(R.drawable.ic_snapchat);
                break;

            case "URI":
                holder.txtType.setText(mContext.getResources().getString(R.string.txtWebSiteName));
                holder.imgType.setImageResource(R.drawable.ic_web);
                holder.txtType2.setText(mContext.getResources().getString(R.string.txtWebSiteName));
                holder.imgType2.setImageResource(R.drawable.ic_web);
                break;

            case "ISBN":
                holder.txtType.setText(mContext.getResources().getString(R.string.txtIsbnName));
                holder.imgType.setImageResource(R.drawable.ic_barcode_border_black_24dp);
                holder.txtType2.setText(mContext.getResources().getString(R.string.txtIsbnName));
                holder.imgType2.setImageResource(R.drawable.ic_barcode_border_black_24dp);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public void setOnItemClick(OnItemClickListener mItemListener) {
        this.mItemListener = mItemListener;
    }

    public void setOnDeleteClick(OnDeleteClickListener mDeleteListener) {
        this.mDeleteListener = mDeleteListener;
    }

    public void setOnDeleteSwipeLayout(OnDeleteSwipeLayoutListener mDeleteSwipeLayoutListener) {
        this.mDeleteSwipeLayoutListener = mDeleteSwipeLayoutListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(View view, int position);
    }

    public interface OnDeleteSwipeLayoutListener {
        void onDeleteSwipeLayout(int position);
    }

    class QRAndBarCodeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.swipeLayout)
        SwipeLayout swipeLayout;

        @BindView(R.id.txtRightDelete)
        TextView txtRightDelete;

        @BindView(R.id.relView)
        RelativeLayout relView;

        @BindView(R.id.imgType)
        ImageView imgType;

        @BindView(R.id.txtType)
        TextView txtType;

        @BindView(R.id.txtDateAndTime)
        TextView txtDateAndTime;

        @BindView(R.id.relView2)
        RelativeLayout relView2;

        @BindView(R.id.relBgSelected)
        RelativeLayout relBgSelected;

        @BindView(R.id.imgDeSelect)
        ImageView imgDeSelect;

        @BindView(R.id.imgSelect)
        ImageView imgSelect;

        @BindView(R.id.imgType2)
        ImageView imgType2;

        @BindView(R.id.txtType2)
        TextView txtType2;

        @BindView(R.id.txtDateAndTime2)
        TextView txtDateAndTime2;

        @BindView(R.id.viewDivider2)
        View viewDivider2;

        QRAndBarCodeViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}

