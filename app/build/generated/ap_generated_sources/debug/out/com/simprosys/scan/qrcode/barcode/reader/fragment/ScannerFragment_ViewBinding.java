// Generated code from Butter Knife. Do not modify!
package com.simprosys.scan.qrcode.barcode.reader.fragment;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.simprosys.scan.qrcode.barcode.reader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ScannerFragment_ViewBinding implements Unbinder {
  private ScannerFragment target;

  private View view7f09011b;

  private View view7f09011c;

  @UiThread
  public ScannerFragment_ViewBinding(final ScannerFragment target, View source) {
    this.target = target;

    View view;
    target.container = Utils.findRequiredViewAsType(source, R.id.container, "field 'container'", FrameLayout.class);
    target.relPlaceHolder = Utils.findRequiredViewAsType(source, R.id.relPlaceHolder, "field 'relPlaceHolder'", RelativeLayout.class);
    view = Utils.findRequiredView(source, R.id.imgFlash, "field 'imgFlash' and method 'onClickFlash'");
    target.imgFlash = Utils.castView(view, R.id.imgFlash, "field 'imgFlash'", ImageView.class);
    view7f09011b = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickFlash();
      }
    });
    target.imgDivider = Utils.findRequiredViewAsType(source, R.id.imgDivider, "field 'imgDivider'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.imgGallery, "field 'imgGallery' and method 'onClickGallery'");
    target.imgGallery = Utils.castView(view, R.id.imgGallery, "field 'imgGallery'", ImageView.class);
    view7f09011c = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickGallery();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ScannerFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.container = null;
    target.relPlaceHolder = null;
    target.imgFlash = null;
    target.imgDivider = null;
    target.imgGallery = null;

    view7f09011b.setOnClickListener(null);
    view7f09011b = null;
    view7f09011c.setOnClickListener(null);
    view7f09011c = null;
  }
}
