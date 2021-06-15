// Generated code from Butter Knife. Do not modify!
package com.simprosys.scan.qrcode.barcode.reader.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.simprosys.scan.qrcode.barcode.reader.R;
import com.simprosys.scan.qrcode.barcode.reader.view.SwitchButton;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SettingFragment_ViewBinding implements Unbinder {
  private SettingFragment target;

  private View view7f09010d;

  private View view7f0901de;

  private View view7f0901d8;

  private View view7f0901e4;

  private View view7f0901dc;

  private View view7f0901e0;

  private View view7f0901e2;

  @UiThread
  public SettingFragment_ViewBinding(final SettingFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.imgAppIconLeft, "field 'imgAppIconLeft' and method 'onClickBack'");
    target.imgAppIconLeft = Utils.castView(view, R.id.imgAppIconLeft, "field 'imgAppIconLeft'", ImageView.class);
    view7f09010d = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickBack();
      }
    });
    target.txtAppName = Utils.findRequiredViewAsType(source, R.id.txtAppName, "field 'txtAppName'", TextView.class);
    target.sbVibrate = Utils.findRequiredViewAsType(source, R.id.sbVibrate, "field 'sbVibrate'", SwitchButton.class);
    target.sbBeep = Utils.findRequiredViewAsType(source, R.id.sbBeep, "field 'sbBeep'", SwitchButton.class);
    view = Utils.findRequiredView(source, R.id.relLanguage, "field 'relLanguage' and method 'onClickLanguage'");
    target.relLanguage = Utils.castView(view, R.id.relLanguage, "field 'relLanguage'", RelativeLayout.class);
    view7f0901de = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickLanguage();
      }
    });
    view = Utils.findRequiredView(source, R.id.relApps, "field 'relApps' and method 'onClickApps'");
    target.relApps = Utils.castView(view, R.id.relApps, "field 'relApps'", RelativeLayout.class);
    view7f0901d8 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickApps();
      }
    });
    view = Utils.findRequiredView(source, R.id.relTheme, "method 'onClickTheme'");
    view7f0901e4 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickTheme();
      }
    });
    view = Utils.findRequiredView(source, R.id.relHelp, "method 'onClickHelp'");
    view7f0901dc = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickHelp();
      }
    });
    view = Utils.findRequiredView(source, R.id.relPrivacy, "method 'onClickPrivacy'");
    view7f0901e0 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickPrivacy();
      }
    });
    view = Utils.findRequiredView(source, R.id.relRateUs, "method 'onClickRateUs'");
    view7f0901e2 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickRateUs();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    SettingFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imgAppIconLeft = null;
    target.txtAppName = null;
    target.sbVibrate = null;
    target.sbBeep = null;
    target.relLanguage = null;
    target.relApps = null;

    view7f09010d.setOnClickListener(null);
    view7f09010d = null;
    view7f0901de.setOnClickListener(null);
    view7f0901de = null;
    view7f0901d8.setOnClickListener(null);
    view7f0901d8 = null;
    view7f0901e4.setOnClickListener(null);
    view7f0901e4 = null;
    view7f0901dc.setOnClickListener(null);
    view7f0901dc = null;
    view7f0901e0.setOnClickListener(null);
    view7f0901e0 = null;
    view7f0901e2.setOnClickListener(null);
    view7f0901e2 = null;
  }
}
