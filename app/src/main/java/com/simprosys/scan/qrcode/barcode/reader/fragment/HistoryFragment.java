package com.simprosys.scan.qrcode.barcode.reader.fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.zxing.Result;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.simprosys.scan.qrcode.barcode.reader.BuildConfig;
import com.simprosys.scan.qrcode.barcode.reader.R;
import com.simprosys.scan.qrcode.barcode.reader.activity.HomeActivity;
import com.simprosys.scan.qrcode.barcode.reader.adapter.QRAndBarCodeAdapter;
import com.simprosys.scan.qrcode.barcode.reader.database.query.Query;
import com.simprosys.scan.qrcode.barcode.reader.database.table.HistoryTable;
import com.simprosys.scan.qrcode.barcode.reader.model.QRAndBarCode;
import com.simprosys.scan.qrcode.barcode.reader.utility.CsvFileWriter;
import com.simprosys.scan.qrcode.barcode.reader.utility.Utility;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.simprosys.scan.qrcode.barcode.reader.utility.Utility.getDirectoryFilePath;
import static com.simprosys.scan.qrcode.barcode.reader.utility.Utility.removeProgress;
import static com.simprosys.scan.qrcode.barcode.reader.utility.Utility.showProgress;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {
    private static final int REQ_CODE_STORAGE_PERM = 1;
    public static int id;
    private static HistoryFragment instance = null;
    public Boolean hasStoragePermGranted = false;
    @BindView(R.id.imgDelete)
    ImageView imgDelete;
    @BindView(R.id.txtAppName)
    TextView txtAppName;
    @BindView(R.id.imgCSV)
    ImageView imgCSV;
    @BindView(R.id.leyPlaceHolder)
    LinearLayout leyPlaceHolder;
    @BindView(R.id.recHistory)
    RecyclerView recHistory;

    QRAndBarCodeAdapter mAdapter;
    private HomeActivity activity;
    private ArrayList<QRAndBarCode> mItemList = new ArrayList<>();

    private Class TAG = HistoryFragment.class;

    @SuppressLint("HandlerLeak")
    Handler handlerDelete = new Handler() {
        public void handleMessage(Message msg) {
            try {
                if (mItemList.size() <= 0) {
                    imgDelete.setVisibility(View.GONE);
                    recHistory.setVisibility(View.GONE);
                    leyPlaceHolder.setVisibility(View.VISIBLE);
                } else {
                    imgDelete.setVisibility(View.VISIBLE);
                    recHistory.setVisibility(View.VISIBLE);
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
    Handler handlerCsv = new Handler() {
        public void handleMessage(android.os.Message msg) {
            try {
                if (msg.getData().getInt("what") == 0) {
                    removeProgress();
                } else {
                    removeProgress();
                }
            } catch (Exception e) {
                e.getMessage();
                Utility.logE(TAG, " " + e.getMessage());
            }
        }
    };

    public HistoryFragment() {

    }

    public static HistoryFragment getInstance() {
        return instance;
    }

    public static HistoryFragment newInstance() {
        Bundle args = new Bundle();
        HistoryFragment fragment = new HistoryFragment();
        args.putString("NAME", "History");
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.imgDelete)
    public void onClickDelete() {
        checkPermissionStorage();

        if (hasStoragePermGranted) {
            new MaterialDialog.Builder(activity)
                    .title(R.string.msgDeleteContent)
                    .positiveText(R.string.msgYes)
                    .negativeText(R.string.msgNo)
                    .itemsColor(Utility.fetchPrimaryColor(activity))
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            showProgress(activity);
                            new DeleteAsyncTask().execute();
                            setEmptyCsvFile();
                            dialog.dismiss();
                        }
                    })
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
    }

    private void setEmptyCsvFile() {
        PrintWriter mWriter = null;
        try {
            mWriter = new PrintWriter(Utility.getDirectoryFilePath(activity, "Document", "History.csv").toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assert mWriter != null;
        mWriter.print("");
        mWriter.close();
    }

    @OnClick(R.id.imgCSV)
    public void onClickCSV() {
        checkPermissionStorage();

        if (hasStoragePermGranted) {
            List<HistoryTable> mHistoryTables = SQLite.select().from(HistoryTable.class).queryList();

            int historySize = mHistoryTables.size();

            Utility.logE(TAG, "History Size of : " + historySize);
            if (historySize > 0) {
                showProgress(activity);
                new CSVFileAsyncTask().execute();
            } else {
                new MaterialDialog.Builder(activity)
                        .title(R.string.msgCsvContent)
                        .positiveText(R.string.msgOkay)
                        .itemsColor(Utility.fetchPrimaryColor(activity))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        }
    }

    private void shareCSVFile() {
        String fileName = Utility.getDirectoryFilePath(activity, "Document", "History.csv").getAbsolutePath();

        CsvFileWriter.writeCsvFile(fileName);

        Uri uri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".provider", getDirectoryFilePath(activity, "Document", "History.csv"));

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("text/csv");
        startActivity(intent);
    }

    public void checkPermissionStorage() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                final String[] LOCATION_PERMS = {
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                };

                hasStoragePermGranted = false;
                requestPermissions(LOCATION_PERMS, REQ_CODE_STORAGE_PERM);
            } else {
                hasStoragePermGranted = true;
            }
        } else {
            hasStoragePermGranted = true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(activity, perm));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQ_CODE_STORAGE_PERM:
                hasStoragePermGranted = hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (HomeActivity) getActivity();
        instance = this;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        } catch (Exception e) {
            e.getMessage();
            Utility.logE(TAG, " " + e.getMessage());
        }

        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        //setUpHistoryList();
        new LoadHistory().execute();


    }

    public void setUpHistoryList() {
        mItemList.clear();
        if (Query.historyShow().size() > 0) {
            for (int i = 0; i < Query.historyShow().size(); i++) {
                int id = Query.historyShow().get(i).getId();
                String type = Query.historyShow().get(i).getType();
                String dateAndTime = Utility.getTimeStampToDate(Query.historyShow().get(i).getDateAndTime());
                String formatType = Query.historyShow().get(i).getFormatType();
                String details = Query.historyShow().get(i).getDetails();

                byte[] bytes = Query.historyShow().get(i).getResult();
                String result = new String(bytes);
                Gson gson = new Gson();

                mItemList.add(new QRAndBarCode(id, type, dateAndTime, formatType, details, gson.fromJson(result, Result.class)));
            }
        }
    }

    private class LoadHistory extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            setUpHistoryList();
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
                imgDelete.setVisibility(View.VISIBLE);
                recHistory.setVisibility(View.VISIBLE);
                leyPlaceHolder.setVisibility(View.GONE);
            } else {
                imgDelete.setVisibility(View.GONE);
                recHistory.setVisibility(View.GONE);
                leyPlaceHolder.setVisibility(View.VISIBLE);
            }

            recHistory.setHasFixedSize(true);
            recHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
            recHistory.setItemAnimator(new DefaultItemAnimator());

            mAdapter = new QRAndBarCodeAdapter(getActivity(), mItemList);
            recHistory.setAdapter(mAdapter);

            mAdapter.setOnItemClick(new QRAndBarCodeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    if (!Utility.stopClick()) {
                        return;
                    }
                    //Utility.qrCodeResult = mItemList.get(position).getResult();
                    //Utility.isNewResult = false;

                    //Fragment fragment = CreateQRCodeResultFragment.newInstance("ScanResultDataForHistory");
                /*if (mFragmentNavigation != null) {
                    mFragmentNavigation.pushFragment(fragment);
                }*/

                    //activity.pushFragment(fragment);

                    Fragment fragment = CreateQRCodeResultFragment.newInstance();

                    if (mItemList.get(position).getFormatType().equals("QR_CODE")) {

                        Bundle bundle = new Bundle();
                        bundle.putString("QrCodeData", mItemList.get(position).getDetails());
                        bundle.putString("QrCodeType", mItemList.get(position).getType());
                        bundle.putString("QrCodeFormat", mItemList.get(position).getFormatType());

                        Utility.setBundleFragment(fragment, bundle);

                        activity.pushFragment(fragment);

                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString("BarCodeData", mItemList.get(position).getDetails());
                        bundle.putString("BarCodeType", mItemList.get(position).getType());
                        bundle.putString("BarCodeFormat", mItemList.get(position).getFormatType());

                        Utility.setBundleFragment(fragment, bundle);


                        activity.pushFragment(fragment);

                    }
                }
            });

            mAdapter.setOnDeleteClick(new QRAndBarCodeAdapter.OnDeleteClickListener() {
                @Override
                public void onDeleteClick(View view, int position) {
                    int id = mItemList.get(position).getId();

                    if (mItemList.size() > 0) {
                        Query.historyDelete(id);
                    } else {
                        imgDelete.setVisibility(View.GONE);
                        recHistory.setVisibility(View.GONE);
                        leyPlaceHolder.setVisibility(View.VISIBLE);
                    }

                    mItemList.remove(position);

                    mAdapter.notifyDataSetChanged();
                }
            });

            mAdapter.setOnDeleteSwipeLayout(new QRAndBarCodeAdapter.OnDeleteSwipeLayoutListener() {
                @Override
                public void onDeleteSwipeLayout(int position) {
                    id = mItemList.get(position).getId();

                    showProgress(activity);
                    new DeleteAsyncTask2().execute();

                    mItemList.remove(position);

                    mAdapter.notifyDataSetChanged();
                }
            });

        }
    }


    @SuppressLint("StaticFieldLeak")
    private class DeleteAsyncTask2 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            new DeleteThread2().start();
            return null;
        }
    }

    private class DeleteThread2 extends Thread {

        @Override
        public void run() {
            try {
                /*if (mItemList.size() > 0) {*/
                    Query.historyDelete(id);
                /*}*/
            } catch (Exception e) {
                e.getMessage();
                Utility.logE(TAG, " " + e.getMessage());
            }

            handlerDelete.sendMessage(handlerDelete.obtainMessage());
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class DeleteAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            new DeleteThread().start();
            return null;
        }
    }

    private class DeleteThread extends Thread {
        @Override
        public void run() {
            try {
                Query.historyAllDelete();
                mItemList.clear();
            } catch (Exception e) {
                e.getMessage();
                Utility.logE(TAG, " " + e.getMessage());
            }

            handlerDelete.sendMessage(handlerDelete.obtainMessage());
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class CSVFileAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            new CSVFileThread().start();
            return null;
        }
    }

    private class CSVFileThread extends Thread {
        @Override
        public void run() {
            try {
                shareCSVFile();
            } catch (Exception e) {
                e.getMessage();
                Utility.logE(TAG, " " + e.getMessage());

                Message m = new Message();
                Bundle b = new Bundle();
                b.putInt("what", 1);
                m.setData(b);
                handlerCsv.sendMessage(m);
            }

            Message m = new Message();
            Bundle b = new Bundle();
            b.putInt("what", 0);
            m.setData(b);
            handlerCsv.sendMessage(m);
        }
    }
}
