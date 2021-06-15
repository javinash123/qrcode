// Generated code from Butter Knife. Do not modify!
package com.simprosys.scan.qrcode.barcode.reader.activity;

import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.simprosys.scan.qrcode.barcode.reader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HomeActivity_ViewBinding implements Unbinder {
  private HomeActivity target;

  @UiThread
  public HomeActivity_ViewBinding(HomeActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public HomeActivity_ViewBinding(HomeActivity target, View source) {
    this.target = target;

    target.bottomNavViewEx = Utils.findRequiredViewAsType(source, R.id.bottomNavViewEx, "field 'bottomNavViewEx'", BottomNavigationViewEx.class);
    target.frmContainer = Utils.findRequiredViewAsType(source, R.id.frmContainer, "field 'frmContainer'", FrameLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HomeActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.bottomNavViewEx = null;
    target.frmContainer = null;
  }
}
