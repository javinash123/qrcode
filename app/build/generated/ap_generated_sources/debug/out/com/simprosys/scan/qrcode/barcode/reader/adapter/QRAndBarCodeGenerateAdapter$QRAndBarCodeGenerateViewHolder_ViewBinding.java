// Generated code from Butter Knife. Do not modify!
package com.simprosys.scan.qrcode.barcode.reader.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.simprosys.scan.qrcode.barcode.reader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class QRAndBarCodeGenerateAdapter$QRAndBarCodeGenerateViewHolder_ViewBinding implements Unbinder {
  private QRAndBarCodeGenerateAdapter.QRAndBarCodeGenerateViewHolder target;

  @UiThread
  public QRAndBarCodeGenerateAdapter$QRAndBarCodeGenerateViewHolder_ViewBinding(
      QRAndBarCodeGenerateAdapter.QRAndBarCodeGenerateViewHolder target, View source) {
    this.target = target;

    target.imgIcon = Utils.findRequiredViewAsType(source, R.id.imgIcon, "field 'imgIcon'", ImageView.class);
    target.txtTitle = Utils.findRequiredViewAsType(source, R.id.txtTitle, "field 'txtTitle'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    QRAndBarCodeGenerateAdapter.QRAndBarCodeGenerateViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imgIcon = null;
    target.txtTitle = null;
  }
}
