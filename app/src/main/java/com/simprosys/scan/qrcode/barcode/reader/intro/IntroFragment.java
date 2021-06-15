package com.simprosys.scan.qrcode.barcode.reader.intro;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.simprosys.scan.qrcode.barcode.reader.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntroFragment extends Fragment {
    private static final String ARGS_RES_IMAGES = "res_images";
    private static final String ARGS_RES_DETAILS = "res_details";
    private static final String ARGS_RES_TITLES = "res_titles";

    private int images;
    private String titles;
    private String details;

    public IntroFragment() {

    }

    public static IntroFragment newInstance(String titles, int images, String details) {
        IntroFragment fragment = new IntroFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_RES_IMAGES, images);
        args.putString(ARGS_RES_TITLES, titles);
        args.putString(ARGS_RES_DETAILS, details);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.images = getArguments().getInt(ARGS_RES_IMAGES);
        this.titles = getArguments().getString(ARGS_RES_TITLES);
        this.details = getArguments().getString(ARGS_RES_DETAILS);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_intro, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ImageView imgIntro = view.findViewById(R.id.imgIntro);
        final TextView txtTitle = view.findViewById(R.id.txtTitle);
        final TextView txtDetail = view.findViewById(R.id.txtDetail);

        imgIntro.setImageResource(images);
        txtTitle.setText(titles);
        txtDetail.setText(details);
    }
}
