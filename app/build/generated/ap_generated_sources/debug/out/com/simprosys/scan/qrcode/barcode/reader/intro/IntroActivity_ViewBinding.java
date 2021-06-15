// Generated code from Butter Knife. Do not modify!
package com.simprosys.scan.qrcode.barcode.reader.intro;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.viewpager.widget.ViewPager;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.simprosys.scan.qrcode.barcode.reader.R;
import com.viewpagerindicator.CirclePageIndicator;
import java.lang.IllegalStateException;
import java.lang.Override;

public class IntroActivity_ViewBinding implements Unbinder {
  private IntroActivity target;

  @UiThread
  public IntroActivity_ViewBinding(IntroActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public IntroActivity_ViewBinding(IntroActivity target, View source) {
    this.target = target;

    target.viewPager = Utils.findRequiredViewAsType(source, R.id.viewPager, "field 'viewPager'", ViewPager.class);
    target.circlePageIndicator = Utils.findRequiredViewAsType(source, R.id.circlePageIndicator, "field 'circlePageIndicator'", CirclePageIndicator.class);
    target.txtSkip = Utils.findRequiredViewAsType(source, R.id.txtSkip, "field 'txtSkip'", TextView.class);
    target.txtDone = Utils.findRequiredViewAsType(source, R.id.txtDone, "field 'txtDone'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    IntroActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.viewPager = null;
    target.circlePageIndicator = null;
    target.txtSkip = null;
    target.txtDone = null;
  }
}
