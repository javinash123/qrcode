// Generated code from Butter Knife. Do not modify!
package com.simprosys.scan.qrcode.barcode.reader.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.simprosys.scan.qrcode.barcode.reader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class QRAndBarCodeGenerateFragment_ViewBinding implements Unbinder {
  private QRAndBarCodeGenerateFragment target;

  private View view7f09010d;

  @UiThread
  public QRAndBarCodeGenerateFragment_ViewBinding(final QRAndBarCodeGenerateFragment target,
      View source) {
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
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    QRAndBarCodeGenerateFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imgAppIconLeft = null;
    target.txtAppName = null;
    target.recyclerView = null;

    view7f09010d.setOnClickListener(null);
    view7f09010d = null;
  }
}
