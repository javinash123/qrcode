// Generated code from Butter Knife. Do not modify!
package com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.qrCodeFragment;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.simprosys.scan.qrcode.barcode.reader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WifiFragment_ViewBinding implements Unbinder {
  private WifiFragment target;

  private View view7f09010f;

  private View view7f09011a;

  private View view7f0901cf;

  private View view7f0901ce;

  private View view7f0901cd;

  @UiThread
  public WifiFragment_ViewBinding(final WifiFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.imgBack, "field 'imgBack' and method 'onClickBack'");
    target.imgBack = Utils.castView(view, R.id.imgBack, "field 'imgBack'", ImageView.class);
    view7f09010f = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickBack();
      }
    });
    target.txtAppName = Utils.findRequiredViewAsType(source, R.id.txtAppName, "field 'txtAppName'", TextView.class);
    view = Utils.findRequiredView(source, R.id.imgDone, "field 'imgDone' and method 'onClickDone'");
    target.imgDone = Utils.castView(view, R.id.imgDone, "field 'imgDone'", ImageView.class);
    view7f09011a = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickDone();
      }
    });
    target.rgToggle = Utils.findRequiredViewAsType(source, R.id.rgToggle, "field 'rgToggle'", RadioGroup.class);
    view = Utils.findRequiredView(source, R.id.rbWpaWpa2, "field 'rbWpaWpa2' and method 'onClickWPAWPA2'");
    target.rbWpaWpa2 = Utils.castView(view, R.id.rbWpaWpa2, "field 'rbWpaWpa2'", RadioButton.class);
    view7f0901cf = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickWPAWPA2();
      }
    });
    view = Utils.findRequiredView(source, R.id.rbWep, "field 'rbWep' and method 'onClickWep'");
    target.rbWep = Utils.castView(view, R.id.rbWep, "field 'rbWep'", RadioButton.class);
    view7f0901ce = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickWep();
      }
    });
    view = Utils.findRequiredView(source, R.id.rbNoEncryption, "field 'rbNoEncryption' and method 'onClickNoEncryption'");
    target.rbNoEncryption = Utils.castView(view, R.id.rbNoEncryption, "field 'rbNoEncryption'", RadioButton.class);
    view7f0901cd = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickNoEncryption();
      }
    });
    target.edtNetwork = Utils.findRequiredViewAsType(source, R.id.edtNetwork, "field 'edtNetwork'", EditText.class);
    target.txtPassword = Utils.findRequiredViewAsType(source, R.id.txtPassword, "field 'txtPassword'", TextView.class);
    target.edtPassword = Utils.findRequiredViewAsType(source, R.id.edtPassword, "field 'edtPassword'", EditText.class);
    target.viewLeft = Utils.findRequiredView(source, R.id.viewLeft, "field 'viewLeft'");
    target.viewRight = Utils.findRequiredView(source, R.id.viewRight, "field 'viewRight'");
  }

  @Override
  @CallSuper
  public void unbind() {
    WifiFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imgBack = null;
    target.txtAppName = null;
    target.imgDone = null;
    target.rgToggle = null;
    target.rbWpaWpa2 = null;
    target.rbWep = null;
    target.rbNoEncryption = null;
    target.edtNetwork = null;
    target.txtPassword = null;
    target.edtPassword = null;
    target.viewLeft = null;
    target.viewRight = null;

    view7f09010f.setOnClickListener(null);
    view7f09010f = null;
    view7f09011a.setOnClickListener(null);
    view7f09011a = null;
    view7f0901cf.setOnClickListener(null);
    view7f0901cf = null;
    view7f0901ce.setOnClickListener(null);
    view7f0901ce = null;
    view7f0901cd.setOnClickListener(null);
    view7f0901cd = null;
  }
}
