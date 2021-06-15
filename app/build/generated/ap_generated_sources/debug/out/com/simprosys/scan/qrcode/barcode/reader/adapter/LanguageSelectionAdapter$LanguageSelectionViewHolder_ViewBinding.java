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

public class LanguageSelectionAdapter$LanguageSelectionViewHolder_ViewBinding implements Unbinder {
  private LanguageSelectionAdapter.LanguageSelectionViewHolder target;

  @UiThread
  public LanguageSelectionAdapter$LanguageSelectionViewHolder_ViewBinding(
      LanguageSelectionAdapter.LanguageSelectionViewHolder target, View source) {
    this.target = target;

    target.leyLanguage = Utils.findRequiredViewAsType(source, R.id.leyLanguage, "field 'leyLanguage'", LinearLayout.class);
    target.txtLanguage = Utils.findRequiredViewAsType(source, R.id.txtLanguage, "field 'txtLanguage'", TextView.class);
    target.imgLanguageSelected = Utils.findRequiredViewAsType(source, R.id.imgLanguageSelected, "field 'imgLanguageSelected'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LanguageSelectionAdapter.LanguageSelectionViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.leyLanguage = null;
    target.txtLanguage = null;
    target.imgLanguageSelected = null;
  }
}
