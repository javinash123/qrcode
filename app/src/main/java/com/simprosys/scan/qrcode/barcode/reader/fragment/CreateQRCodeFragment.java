package com.simprosys.scan.qrcode.barcode.reader.fragment;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.simprosys.scan.qrcode.barcode.reader.R;
import com.simprosys.scan.qrcode.barcode.reader.activity.HomeActivity;
import com.simprosys.scan.qrcode.barcode.reader.adapter.QRAndBarCodeAdapter;
import com.simprosys.scan.qrcode.barcode.reader.database.query.Query;
import com.simprosys.scan.qrcode.barcode.reader.model.QRAndBarCode;
import com.simprosys.scan.qrcode.barcode.reader.utility.RecyclerItemClickListener;
import com.simprosys.scan.qrcode.barcode.reader.utility.Utility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.simprosys.scan.qrcode.barcode.reader.utility.Utility.removeProgress;
import static com.simprosys.scan.qrcode.barcode.reader.utility.Utility.showProgress;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateQRCodeFragment extends BaseFragment {
    private static CreateQRCodeFragment instance = null;


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.imgDeleteLeft)
    ImageView imgDeleteLeft;

    @BindView(R.id.imgClose)
    ImageView imgClose;

    @BindView(R.id.txtAppName)
    TextView txtAppName;

    @BindView(R.id.imgQrCodeAdd)
    ImageView imgQrCodeAdd;

    @BindView(R.id.imgDeleteRight)
    ImageView imgDeleteRight;

    @BindView(R.id.leyPlaceHolder)
    LinearLayout leyPlaceHolder;

    @BindView(R.id.recCreateQrCode)
    RecyclerView recCreateQrCode;

    boolean isMultiSelect = false;

    private HomeActivity activity;
    private QRAndBarCodeAdapter mAdapter;
    private ArrayList<QRAndBarCode> mItemList = new ArrayList<>();
    private ArrayList<QRAndBarCode> mMultiSelectItemList = new ArrayList<>();

    private Class TAG = CreateQRCodeFragment.class;

    @SuppressLint("HandlerLeak")
    Handler handlerDelete = new Handler() {
        public void handleMessage(Message msg) {
            try {
                if (mItemList.size() <= 0) {
                    imgClose.setVisibility(View.GONE);
                    imgDeleteRight.setVisibility(View.GONE);
                    imgQrCodeAdd.setVisibility(View.VISIBLE);
                    imgDeleteLeft.setVisibility(View.GONE);
                    recCreateQrCode.setVisibility(View.GONE);
                    leyPlaceHolder.setVisibility(View.VISIBLE);

                } else {
                    imgDeleteLeft.setVisibility(View.GONE);
                    recCreateQrCode.setVisibility(View.VISIBLE);
                    leyPlaceHolder.setVisibility(View.GONE);
                }

                removeProgress();
            } catch (Exception e) {
                e.getMessage();
                Utility.logE(TAG, " " + e.getMessage());
            }
            removeProgress();
        }
    };

    @SuppressLint("HandlerLeak")
    Handler handlerSelectedDelete = new Handler() {
        public void handleMessage(Message msg) {
            try {
                if (mItemList.size() <= 0) {
                    imgClose.setVisibility(View.GONE);
                    imgDeleteRight.setVisibility(View.GONE);
                    imgQrCodeAdd.setVisibility(View.VISIBLE);
                    imgDeleteLeft.setVisibility(View.GONE);
                    recCreateQrCode.setVisibility(View.GONE);
                    leyPlaceHolder.setVisibility(View.VISIBLE);

                } else {
                    imgDeleteLeft.setVisibility(View.GONE);
                    recCreateQrCode.setVisibility(View.VISIBLE);
                    leyPlaceHolder.setVisibility(View.GONE);
                }

            } catch (Exception e) {
                e.getMessage();
                Utility.logE(TAG, " " + e.getMessage());
            }
        }
    };

    public CreateQRCodeFragment() {
    }

    public static CreateQRCodeFragment getInstance() {
        return instance;
    }

    public static CreateQRCodeFragment newInstance() {
        Bundle args = new Bundle();
        CreateQRCodeFragment fragment = new CreateQRCodeFragment();
        args.putString("NAME", "CreateQRCode");
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.imgDeleteLeft)
    public void onClickDeleteLeft() {
        Toast.makeText(activity, "Click Done...", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.imgClose)
    public void onClickClose() {
        imgClose.setVisibility(View.GONE);
        imgDeleteRight.setVisibility(View.GONE);
        imgQrCodeAdd.setVisibility(View.VISIBLE);

        mAdapter.setImageSelectOrDeselect(false);
        isMultiSelect = false;
        mMultiSelectItemList = new ArrayList<>();

        refreshAdapter();
    }

    @OnClick(R.id.imgQrCodeAdd)
    public void onClickQrCodeAdd() {
        if (mFragmentNavigation != null) {
            mFragmentNavigation.pushFragment(QRAndBarCodeGenerateFragment.newInstance());
        }
    }

    @OnClick(R.id.imgDeleteRight)
    public void onClickDeleteRight() {
        if (mMultiSelectItemList.size() > 0) {
            for (int i = 0; i < mMultiSelectItemList.size(); i++) {
                int id = mMultiSelectItemList.get(i).getId();

                Query.createQrCodeDelete(id);
                new SelectedDeleteAsyncTask().execute();

                mItemList.remove(mMultiSelectItemList.get(i));

                /*if (mItemList.size() > 0) {
                    showProgress(activity);
                    new DeleteAsyncTask().execute();
                }*/

            }

            mAdapter.notifyDataSetChanged();

            //Toast.makeText(activity.getApplicationContext(), "Delete Click", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (HomeActivity) getActivity();
        instance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_qrcode, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        activity.bottomNavViewEx.setVisibility(View.VISIBLE);

        imgClose.setVisibility(View.GONE);
        imgDeleteRight.setVisibility(View.GONE);
        new LoadHistory().execute();

    }

    private class LoadHistory extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            setUpQrCodeList();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            leyPlaceHolder.setVisibility(View.GONE);
            Utility.showDefaultProgress(activity, getResources().getString(R.string.msgPbBarCode));
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Utility.removeProgress();
            if (mItemList.size() > 0) {
                imgDeleteLeft.setVisibility(View.GONE);
                recCreateQrCode.setVisibility(View.VISIBLE);
                leyPlaceHolder.setVisibility(View.GONE);

                mAdapter = new QRAndBarCodeAdapter(activity, mItemList, mMultiSelectItemList);

                recCreateQrCode.setLayoutManager(new LinearLayoutManager(activity));
                recCreateQrCode.setHasFixedSize(true);
                recCreateQrCode.setItemAnimator(new DefaultItemAnimator());

                recCreateQrCode.setAdapter(mAdapter);

                recCreateQrCode.addOnItemTouchListener(new RecyclerItemClickListener(activity, recCreateQrCode, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (isMultiSelect) {
                            multiSelect(position);
                        } else {
                        /*Fragment fragment = CreateQRCodeResultFragment.newInstance();

                        if (mItemList.get(position).getFormatType().equals("QR_CODE")) {

                            Bundle bundle = new Bundle();
                            bundle.putString("QrCodeData", mItemList.get(position).getDetails());
                            bundle.putString("QrCodeType", mItemList.get(position).getType());
                            bundle.putString("QrCodeFormat", mItemList.get(position).getFormatType());

                            Utility.setBundleFragment(fragment, bundle);

                            if (mFragmentNavigation != null) {
                                mFragmentNavigation.pushFragment(fragment);
                            }
                        } else {
                            Bundle bundle = new Bundle();
                            bundle.putString("BarCodeData", mItemList.get(position).getDetails());
                            bundle.putString("BarCodeType", mItemList.get(position).getType());
                            bundle.putString("BarCodeFormat", mItemList.get(position).getFormatType());

                            Utility.setBundleFragment(fragment, bundle);


                            if (mFragmentNavigation != null) {
                                mFragmentNavigation.pushFragment(fragment);
                            }
                        }*/

                            return;
                        }
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        if (!isMultiSelect) {
                            mMultiSelectItemList = new ArrayList<>();
                            isMultiSelect = true;
                        }

                        mAdapter.setImageSelectOrDeselect(true);

                        imgDeleteRight.setVisibility(View.VISIBLE);
                        imgClose.setVisibility(View.VISIBLE);
                        imgQrCodeAdd.setVisibility(View.GONE);

                        multiSelect(position);
                    }
                }));

                mAdapter.setOnItemClick(new QRAndBarCodeAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (!Utility.stopClick()) {
                            return;
                        }
                        Fragment fragment = CreateQRCodeResultFragment.newInstance();

                        if (mItemList.get(position).getFormatType().equals("QR_CODE")) {

                            Bundle bundle = new Bundle();
                            bundle.putString("QrCodeData", mItemList.get(position).getDetails());
                            bundle.putString("QrCodeType", mItemList.get(position).getType());
                            bundle.putString("QrCodeFormat", mItemList.get(position).getFormatType());

                            Utility.setBundleFragment(fragment, bundle);

                            if (mFragmentNavigation != null) {
                                mFragmentNavigation.pushFragment(fragment);
                            }
                        } else {
                            Bundle bundle = new Bundle();
                            bundle.putString("BarCodeData", mItemList.get(position).getDetails());
                            bundle.putString("BarCodeType", mItemList.get(position).getType());
                            bundle.putString("BarCodeFormat", mItemList.get(position).getFormatType());

                            Utility.setBundleFragment(fragment, bundle);


                            if (mFragmentNavigation != null) {
                                mFragmentNavigation.pushFragment(fragment);
                            }
                        }
                    }
                });

                mAdapter.setOnDeleteClick(new QRAndBarCodeAdapter.OnDeleteClickListener() {
                    @Override
                    public void onDeleteClick(View view, int position) {
                        int id = mItemList.get(position).getId();

                        if (mItemList.size() > 0) {
                            Query.createQrCodeDelete(id);

                            showProgress(activity);
                            new DeleteAsyncTask().execute();
                        }

                        mItemList.remove(position);
                        mAdapter.notifyDataSetChanged();
                    }
                });

                mAdapter.setOnDeleteSwipeLayout(new QRAndBarCodeAdapter.OnDeleteSwipeLayoutListener() {
                    @Override
                    public void onDeleteSwipeLayout(int position) {
                        int id = mItemList.get(position).getId();

                        if (mItemList.size() > 0) {
                            Query.createQrCodeDelete(id);

                            showProgress(activity);
                            new DeleteAsyncTask().execute();
                        }

                        mItemList.remove(position);
                        mAdapter.notifyDataSetChanged();
                    }
                });
            } else {
                imgDeleteLeft.setVisibility(View.GONE);
                recCreateQrCode.setVisibility(View.GONE);
                leyPlaceHolder.setVisibility(View.VISIBLE);
            }

        }
    }

    public void setUpQrCodeList() {
        mItemList.clear();
        if (Query.createQrCodeShow().size() > 0) {
            for (int i = 0; i < Query.createQrCodeShow().size(); i++) {
                int id = Query.createQrCodeShow().get(i).getId();
                String type = Query.createQrCodeShow().get(i).getType();
                String dateAndTime = Utility.getTimeStampToDate(Query.createQrCodeShow().get(i).getDateAndTime());
                String formatType = Query.createQrCodeShow().get(i).getFormatType();
                String details = Query.createQrCodeShow().get(i).getDetails();

                mItemList.add(new QRAndBarCode(id, type, dateAndTime, formatType, details));
            }
        }
    }

    public void multiSelect(int position) {
        if (mMultiSelectItemList.contains(mItemList.get(position))) {
            mMultiSelectItemList.remove(mItemList.get(position));
        } else {
            mMultiSelectItemList.add(mItemList.get(position));
        }

        refreshAdapter();
    }

    public void refreshAdapter() {
        mAdapter.mSelectedItemList = mMultiSelectItemList;
        mAdapter.mItemList = mItemList;
        mAdapter.notifyDataSetChanged();
    }

    @SuppressLint("StaticFieldLeak")
    private class DeleteAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            new DeleteThread().start();
            return null;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class SelectedDeleteAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            new SelectedDeleteThread().start();
            return null;
        }
    }

    private class DeleteThread extends Thread {

        @Override
        public void run() {
            handlerDelete.sendMessage(handlerDelete.obtainMessage());
        }
    }

    private class SelectedDeleteThread extends Thread {

        @Override
        public void run() {
            handlerSelectedDelete.sendMessage(handlerSelectedDelete.obtainMessage());
        }
    }
}
