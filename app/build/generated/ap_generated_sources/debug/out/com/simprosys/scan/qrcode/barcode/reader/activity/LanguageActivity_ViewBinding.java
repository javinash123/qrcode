// Generated code from Butter Knife. Do not modify!
package com.simprosys.scan.qrcode.barcode.reader.activity;

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

public class LanguageActivity_ViewBinding implements Unbinder {
  private LanguageActivity target;

  private View view7f09010d;

  @UiThread
  public LanguageActivity_ViewBinding(LanguageActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LanguageActivity_ViewBinding(final LanguageActivity target, View source) {
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
    target.recLanguage = Utils.findRequiredViewAsType(source, R.id.recLanguage, "field 'recLanguage'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LanguageActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imgAppIconLeft = null;
    target.txtAppName = null;
    target.recLanguage = null;

    view7f09010d.setOnClickListener(null);
    view7f09010d = null;
  }
}
