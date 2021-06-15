// Generated code from Butter Knife. Do not modify!
package com.simprosys.scan.qrcode.barcode.reader;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AppsActivity_ViewBinding implements Unbinder {
  private AppsActivity target;

  private View view7f09010d;

  @UiThread
  public AppsActivity_ViewBinding(AppsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AppsActivity_ViewBinding(final AppsActivity target, View source) {
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
  }

  @Override
  @CallSuper
  public void unbind() {
    AppsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imgAppIconLeft = null;
    target.txtAppName = null;

    view7f09010d.setOnClickListener(null);
    view7f09010d = null;
  }
}
