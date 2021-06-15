// Generated code from Butter Knife. Do not modify!
package com.simprosys.scan.qrcode.barcode.reader.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.simprosys.scan.qrcode.barcode.reader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ThemeAdapter$ThemeViewHolder_ViewBinding implements Unbinder {
  private ThemeAdapter.ThemeViewHolder target;

  @UiThread
  public ThemeAdapter$ThemeViewHolder_ViewBinding(ThemeAdapter.ThemeViewHolder target,
      View source) {
    this.target = target;

    target.leyTheme = Utils.findRequiredViewAsType(source, R.id.leyTheme, "field 'leyTheme'", LinearLayout.class);
    target.imgTheme = Utils.findRequiredViewAsType(source, R.id.imgTheme, "field 'imgTheme'", ImageView.class);
    target.txtTheme = Utils.findRequiredViewAsType(source, R.id.txtTheme, "field 'txtTheme'", TextView.class);
    target.imgThemeSelected = Utils.findRequiredViewAsType(source, R.id.imgThemeSelected, "field 'imgThemeSelected'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ThemeAdapter.ThemeViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.leyTheme = null;
    target.imgTheme = null;
    target.txtTheme = null;
    target.imgThemeSelected = null;
  }
}
