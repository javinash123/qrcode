// Generated code from Butter Knife. Do not modify!
package com.simprosys.scan.qrcode.barcode.reader.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.simprosys.scan.qrcode.barcode.reader.R;
import com.simprosys.scan.qrcode.barcode.reader.view.SwipeLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class QRAndBarCodeAdapter$QRAndBarCodeViewHolder_ViewBinding implements Unbinder {
  private QRAndBarCodeAdapter.QRAndBarCodeViewHolder target;

  @UiThread
  public QRAndBarCodeAdapter$QRAndBarCodeViewHolder_ViewBinding(
      QRAndBarCodeAdapter.QRAndBarCodeViewHolder target, View source) {
    this.target = target;

    target.swipeLayout = Utils.findRequiredViewAsType(source, R.id.swipeLayout, "field 'swipeLayout'", SwipeLayout.class);
    target.txtRightDelete = Utils.findRequiredViewAsType(source, R.id.txtRightDelete, "field 'txtRightDelete'", TextView.class);
    target.relView = Utils.findRequiredViewAsType(source, R.id.relView, "field 'relView'", RelativeLayout.class);
    target.imgType = Utils.findRequiredViewAsType(source, R.id.imgType, "field 'imgType'", ImageView.class);
    target.txtType = Utils.findRequiredViewAsType(source, R.id.txtType, "field 'txtType'", TextView.class);
    target.txtDateAndTime = Utils.findRequiredViewAsType(source, R.id.txtDateAndTime, "field 'txtDateAndTime'", TextView.class);
    target.relView2 = Utils.findRequiredViewAsType(source, R.id.relView2, "field 'relView2'", RelativeLayout.class);
    target.relBgSelected = Utils.findRequiredViewAsType(source, R.id.relBgSelected, "field 'relBgSelected'", RelativeLayout.class);
    target.imgDeSelect = Utils.findRequiredViewAsType(source, R.id.imgDeSelect, "field 'imgDeSelect'", ImageView.class);
    target.imgSelect = Utils.findRequiredViewAsType(source, R.id.imgSelect, "field 'imgSelect'", ImageView.class);
    target.imgType2 = Utils.findRequiredViewAsType(source, R.id.imgType2, "field 'imgType2'", ImageView.class);
    target.txtType2 = Utils.findRequiredViewAsType(source, R.id.txtType2, "field 'txtType2'", TextView.class);
    target.txtDateAndTime2 = Utils.findRequiredViewAsType(source, R.id.txtDateAndTime2, "field 'txtDateAndTime2'", TextView.class);
    target.viewDivider2 = Utils.findRequiredView(source, R.id.viewDivider2, "field 'viewDivider2'");
  }

  @Override
  @CallSuper
  public void unbind() {
    QRAndBarCodeAdapter.QRAndBarCodeViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.swipeLayout = null;
    target.txtRightDelete = null;
    target.relView = null;
    target.imgType = null;
    target.txtType = null;
    target.txtDateAndTime = null;
    target.relView2 = null;
    target.relBgSelected = null;
    target.imgDeSelect = null;
    target.imgSelect = null;
    target.imgType2 = null;
    target.txtType2 = null;
    target.txtDateAndTime2 = null;
    target.viewDivider2 = null;
  }
}
