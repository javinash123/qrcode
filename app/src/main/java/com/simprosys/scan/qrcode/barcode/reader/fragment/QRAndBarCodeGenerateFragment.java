package com.simprosys.scan.qrcode.barcode.reader.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.simprosys.scan.qrcode.barcode.reader.R;
import com.simprosys.scan.qrcode.barcode.reader.activity.HomeActivity;
import com.simprosys.scan.qrcode.barcode.reader.adapter.QRAndBarCodeGenerateAdapter;
import com.simprosys.scan.qrcode.barcode.reader.adapter.SectionedGridRecyclerViewAdapter;
import com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.barCodeFragment.Code128Fragment;
import com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.barCodeFragment.Code39Fragment;
import com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.barCodeFragment.EAN13Fragment;
import com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.barCodeFragment.EAN8Fragment;
import com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.barCodeFragment.UPCAFragment;
import com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.barCodeFragment.UPCEFragment;
import com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.qrCodeFragment.EmailFragment;
import com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.qrCodeFragment.EventFragment;
import com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.qrCodeFragment.FaceBookFragment;
import com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.qrCodeFragment.InstagramFragment;
import com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.qrCodeFragment.LinkedInFragment;
import com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.qrCodeFragment.LocationFragment;
import com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.qrCodeFragment.PhoneNumberFragment;
import com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.qrCodeFragment.SMSFragment;
import com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.qrCodeFragment.SnapChatFragment;
import com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.qrCodeFragment.TextFragment;
import com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.qrCodeFragment.TwitterFragment;
import com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.qrCodeFragment.VCardFragment;
import com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.qrCodeFragment.WebSiteFragment;
import com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.qrCodeFragment.WhatsAppFragment;
import com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.qrCodeFragment.WifiFragment;
import com.simprosys.scan.qrcode.barcode.reader.model.QRGenerateItem;
import com.simprosys.scan.qrcode.barcode.reader.utility.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QRAndBarCodeGenerateFragment extends BaseFragment {
    private static QRAndBarCodeGenerateFragment instance = null;

    @BindView(R.id.imgAppIconLeft)
    ImageView imgAppIconLeft;

    @BindView(R.id.txtAppName)
    TextView txtAppName;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    QRAndBarCodeGenerateAdapter mAdapter;

    SectionedGridRecyclerViewAdapter mSectionedAdapter;

    List<SectionedGridRecyclerViewAdapter.Section> mSectionsList = new ArrayList<SectionedGridRecyclerViewAdapter.Section>();

    SectionedGridRecyclerViewAdapter.Section[] dummy = new SectionedGridRecyclerViewAdapter.Section[mSectionsList.size()];

    ArrayList<QRGenerateItem> mItemList = new ArrayList<>();

    Fragment fragment;

    private HomeActivity activity;

    private Class TAG = QRAndBarCodeGenerateFragment.class;

    public QRAndBarCodeGenerateFragment() {

    }

    public static QRAndBarCodeGenerateFragment getInstance() {
        return instance;
    }

    public static QRAndBarCodeGenerateFragment newInstance() {
        Bundle args = new Bundle();
        QRAndBarCodeGenerateFragment fragment = new QRAndBarCodeGenerateFragment();
        args.putString("NAME", "QRAndBarCodeGenerate");
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.imgAppIconLeft)
    public void onClickBack() {
        activity.onBackPressed();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (HomeActivity) getActivity();
        instance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_qr_and_bar_code_generate, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        txtAppName.setText(activity.getResources().getString(R.string.txtAppBarNameQABCodeGenerate));

        Constant.isPopFragment = 0;

        activity.bottomNavViewEx.setVisibility(View.VISIBLE);

        setupListItem();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        mAdapter = new QRAndBarCodeGenerateAdapter(getActivity(), mItemList);

        mSectionsList.clear();
        mSectionsList.add(new SectionedGridRecyclerViewAdapter.Section(0, activity.getResources().getString(R.string.qrCodeSection)));
        mSectionsList.add(new SectionedGridRecyclerViewAdapter.Section(15, activity.getResources().getString(R.string.barCodeSection)));

        mSectionedAdapter = new SectionedGridRecyclerViewAdapter(activity, R.layout.section, R.id.section_text, recyclerView, mAdapter);
        mSectionedAdapter.setSections(mSectionsList.toArray(dummy));

        recyclerView.setAdapter(mSectionedAdapter);

        mAdapter.setOnItemClick(new QRAndBarCodeGenerateAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == 0) {
                    addFragment(new TextFragment());
                } else if (position == 1) {
                    addFragment(new PhoneNumberFragment());
                } else if (position == 2) {
                    addFragment(new SMSFragment());
                } else if (position == 3) {
                    addFragment(new EmailFragment());
                } else if (position == 4) {
                    addFragment(new WebSiteFragment());
                } else if (position == 5) {
                    addFragment(new LocationFragment());
                } else if (position == 6) {
                    addFragment(new WifiFragment());
                } else if (position == 7) {
                    addFragment(new VCardFragment());
                } else if (position == 8) {
                    addFragment(new EventFragment());
                } else if (position == 9) {
                    addFragment(new FaceBookFragment());
                } else if (position == 10) {
                    addFragment(new InstagramFragment());
                } else if (position == 11) {
                    addFragment(new WhatsAppFragment());
                } else if (position == 12) {
                    addFragment(new TwitterFragment());
                } else if (position == 13) {
                    addFragment(new LinkedInFragment());
                } else if (position == 14) {
                    addFragment(new SnapChatFragment());
                } else if (position == 15) {
                    addFragment(new EAN13Fragment());
                } else if (position == 16) {
                    addFragment(new EAN8Fragment());
                } else if (position == 17) {
                    addFragment(new UPCEFragment());
                } else if (position == 18) {
                    addFragment(new Code39Fragment());
                } else if (position == 19) {
                    addFragment(new Code128Fragment());
                } else if (position == 20) {
                    addFragment(new UPCAFragment());
                }
            }
        });
    }

    public void addFragment(Fragment fragment){
        if (mFragmentNavigation != null) {
            mFragmentNavigation.pushFragment(fragment);
        }
    }

    public void setupListItem() {
        mItemList.clear();
        mItemList.add(new QRGenerateItem(R.drawable.ic_text, activity.getResources().getString(R.string.qrCodeText)));
        mItemList.add(new QRGenerateItem(R.drawable.ic_telephone, activity.getResources().getString(R.string.qrCodeNumber)));
        mItemList.add(new QRGenerateItem(R.drawable.ic_sms, activity.getResources().getString(R.string.qrCodeSms)));
        mItemList.add(new QRGenerateItem(R.drawable.ic_mail, activity.getResources().getString(R.string.qrCodeEmail)));
        mItemList.add(new QRGenerateItem(R.drawable.ic_web, activity.getResources().getString(R.string.qrCodeWebSite)));
        mItemList.add(new QRGenerateItem(R.drawable.ic_location, activity.getResources().getString(R.string.qrCodeLocation)));
        mItemList.add(new QRGenerateItem(R.drawable.ic_wifi, activity.getResources().getString(R.string.qrCodeWifi)));
        mItemList.add(new QRGenerateItem(R.drawable.ic_vcard, activity.getResources().getString(R.string.qrCodeVCard)));
        mItemList.add(new QRGenerateItem(R.drawable.ic_calendar, activity.getResources().getString(R.string.qrCodeEvent)));
        mItemList.add(new QRGenerateItem(R.drawable.ic_facebook, activity.getResources().getString(R.string.qrCodeFacebook)));
        mItemList.add(new QRGenerateItem(R.mipmap.ic_instagram, activity.getResources().getString(R.string.qrCodeInstagram)));
        mItemList.add(new QRGenerateItem(R.drawable.ic_whatsapp, activity.getResources().getString(R.string.qrCodeWhatsApp)));
        mItemList.add(new QRGenerateItem(R.drawable.ic_twitter, activity.getResources().getString(R.string.qrCodeTwitter)));
        mItemList.add(new QRGenerateItem(R.drawable.ic_linked_in, activity.getResources().getString(R.string.qrCodeLinkedIn)));
        mItemList.add(new QRGenerateItem(R.drawable.ic_snapchat, activity.getResources().getString(R.string.qrCodeSnapChat)));
        mItemList.add(new QRGenerateItem(R.drawable.ic_barcode_border_black_24dp, activity.getResources().getString(R.string.barCodeEan13)));
        mItemList.add(new QRGenerateItem(R.drawable.ic_barcode_border_black_24dp, activity.getResources().getString(R.string.barCodeEan8)));
        mItemList.add(new QRGenerateItem(R.drawable.ic_barcode_border_black_24dp, activity.getResources().getString(R.string.barCodeUpce)));
        mItemList.add(new QRGenerateItem(R.drawable.ic_barcode_border_black_24dp, activity.getResources().getString(R.string.barCodeCode39)));
        mItemList.add(new QRGenerateItem(R.drawable.ic_barcode_border_black_24dp, activity.getResources().getString(R.string.barCodeCode128)));
        mItemList.add(new QRGenerateItem(R.drawable.ic_barcode_border_black_24dp, activity.getResources().getString(R.string.barCodeUpca)));
    }
}
