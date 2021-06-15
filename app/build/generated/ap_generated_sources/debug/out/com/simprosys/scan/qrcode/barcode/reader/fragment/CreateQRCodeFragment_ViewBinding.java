// Generated code from Butter Knife. Do not modify!
package com.simprosys.scan.qrcode.barcode.reader.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.simprosys.scan.qrcode.barcode.reader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CreateQRCodeFragment_ViewBinding implements Unbinder {
  private CreateQRCodeFragment target;

  private View view7f090117;

  private View view7f090114;

  private View view7f090124;

  private View view7f090118;

  @UiThread
  public CreateQRCodeFragment_ViewBinding(final CreateQRCodeFragment target, View source) {
    this.target = target;

    View view;
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    view = Utils.findRequiredView(source, R.id.imgDeleteLeft, "field 'imgDeleteLeft' and method 'onClickDeleteLeft'");
    target.imgDeleteLeft = Utils.castView(view, R.id.imgDeleteLeft, "field 'imgDeleteLeft'", ImageView.class);
    view7f090117 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickDeleteLeft();
      }
    });
    view = Utils.findRequiredView(source, R.id.imgClose, "field 'imgClose' and method 'onClickClose'");
    target.imgClose = Utils.castView(view, R.id.imgClose, "field 'imgClose'", ImageView.class);
    view7f090114 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickClose();
      }
    });
    target.txtAppName = Utils.findRequiredViewAsType(source, R.id.txtAppName, "field 'txtAppName'", TextView.class);
    view = Utils.findRequiredView(source, R.id.imgQrCodeAdd, "field 'imgQrCodeAdd' and method 'onClickQrCodeAdd'");
    target.imgQrCodeAdd = Utils.castView(view, R.id.imgQrCodeAdd, "field 'imgQrCodeAdd'", ImageView.class);
    view7f090124 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickQrCodeAdd();
      }
    });
    view = Utils.findRequiredView(source, R.id.imgDeleteRight, "field 'imgDeleteRight' and method 'onClickDeleteRight'");
    target.imgDeleteRight = Utils.castView(view, R.id.imgDeleteRight, "field 'imgDeleteRight'", ImageView.class);
    view7f090118 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickDeleteRight();
      }
    });
    target.leyPlaceHolder = Utils.findRequiredViewAsType(source, R.id.leyPlaceHolder, "field 'leyPlaceHolder'", LinearLayout.class);
    target.recCreateQrCode = Utils.findRequiredViewAsType(source, R.id.recCreateQrCode, "field 'recCreateQrCode'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CreateQRCodeFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.imgDeleteLeft = null;
    target.imgClose = null;
    target.txtAppName = null;
    target.imgQrCodeAdd = null;
    target.imgDeleteRight = null;
    target.leyPlaceHolder = null;
    target.recCreateQrCode = null;

    view7f090117.setOnClickListener(null);
    view7f090117 = null;
    view7f090114.setOnClickListener(null);
    view7f090114 = null;
    view7f090124.setOnClickListener(null);
    view7f090124 = null;
    view7f090118.setOnClickListener(null);
    view7f090118 = null;
  }
}
