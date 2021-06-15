package com.simprosys.scan.qrcode.barcode.reader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.simprosys.scan.qrcode.barcode.reader.activity.BaseActivity;
import com.simprosys.scan.qrcode.barcode.reader.adapter.AppsAdapter;
import com.simprosys.scan.qrcode.barcode.reader.utility.LanguageHelper;
import com.simprosys.scan.qrcode.barcode.reader.utility.barcodeUtils.AppsBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AppsActivity extends BaseActivity {
    RecyclerView recApp;
    ImageView imgAppIconLeft1;
//    TextView txtAppName;
    AppsBean appsBean;
    ArrayList<AppsBean> appsBeanList;
    AppsAdapter appsAdapter;

    @BindView(R.id.imgAppIconLeft)
    ImageView imgAppIconLeft;

    @BindView(R.id.txtAppName)
    TextView txtAppName;


    @OnClick(R.id.imgAppIconLeft)
    public void onClickBack(){
        onBackPressed();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LanguageHelper.onAttach(base));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);
        recApp=(RecyclerView)findViewById(R.id.recApp);
        imgAppIconLeft1=(ImageView)findViewById(R.id.imgAppIconLeft1);
        ButterKnife.bind(this);
        txtAppName.setText(getResources().getString(R.string.txtMoreApp));
        imgAppIconLeft1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        requestGetData();
    }
    public void requestGetData(){
        appsBeanList=new ArrayList<>();
        try
        {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://storage.googleapis.com/zipo-config/more-apps-config.json",
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String result=response.toString();
                            try {
                                JSONObject jsonObject=new JSONObject(result);
                                JSONArray jsonArray=jsonObject.getJSONArray("apps");
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                    String packageName=jsonObject1.getString("package");
                                    String name=jsonObject1.getString("name");
                                    String description=jsonObject1.getString("description");
                                    String icon_url=jsonObject1.getString("icon_url");
                                    String rating=jsonObject1.getString("rating");
                                    String interstitial=jsonObject1.getString("interstitial");
                                    appsBean=new AppsBean();
                                    appsBean.setPackageName(packageName);
                                    appsBean.setName(name);
                                    appsBean.setDescription(description);
                                    appsBean.setIcon_url(icon_url);
                                    appsBean.setRating(rating);
                                    appsBean.setInterstitial(interstitial);
                                    appsBeanList.add(appsBean);
                                }
                                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(AppsActivity.this, 1);
                                mLayoutManager.setAutoMeasureEnabled(true);
                                recApp.setLayoutManager(mLayoutManager);
                                appsAdapter = new AppsAdapter(AppsActivity.this,appsBeanList);
                                recApp.setAdapter(appsAdapter);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            String errorMessage = error.getMessage();
                            if (error instanceof NoConnectionError) {
                                errorMessage = "No Internet Connection";
                            } else if (error instanceof ServerError) {
                                errorMessage = "Server Error";
                            } else if (error instanceof NetworkError) {
                                errorMessage = "Network Error";
                            }
                            Toast.makeText(AppsActivity.this, ""+errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }){

                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    return params;
                }

            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

        }catch (Exception e)
        {
            //App.handleUncaughtException(e);
        }
    }
}