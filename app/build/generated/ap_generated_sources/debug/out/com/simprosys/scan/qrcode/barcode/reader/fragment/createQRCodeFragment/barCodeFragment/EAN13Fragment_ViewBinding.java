// Generated code from Butter Knife. Do not modify!
package com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.barCodeFragment;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.simprosys.scan.qrcode.barcode.reader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class EAN13Fragment_ViewBinding implements Unbinder {
  private EAN13Fragment target;

  private View view7f09010f;

  private View view7f09011a;

  @UiThread
  public EAN13Fragment_ViewBinding(final EAN13Fragment target, View source) {
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
    target.edtBarCode = Utils.findRequiredViewAsType(source, R.id.edtBarCode, "field 'edtBarCode'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    EAN13Fragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imgBack = null;
    target.txtAppName = null;
    target.imgDone = null;
    target.edtBarCode = null;

    view7f09010f.setOnClickListener(null);
    view7f09010f = null;
    view7f09011a.setOnClickListener(null);
    view7f09011a = null;
  }
}
