package com.simprosys.scan.qrcode.barcode.reader.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by f22labs on 07/03/17.
 */

public abstract class BaseFragment extends Fragment {
    public static final String ARGS_INSTANCE = "com.simprosys.scan.qrcode.barcode.reader";

    FragmentNavigation mFragmentNavigation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentNavigation) {
            mFragmentNavigation = (FragmentNavigation) context;
        }
    }

    public interface FragmentNavigation {
         void pushFragment(Fragment fragment);
    }
}
