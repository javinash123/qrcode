// Generated code from Butter Knife. Do not modify!
package com.simprosys.scan.qrcode.barcode.reader.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class HistoryFragment_ViewBinding implements Unbinder {
  private HistoryFragment target;

  private View view7f090116;

  private View view7f090113;

  @UiThread
  public HistoryFragment_ViewBinding(final HistoryFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.imgDelete, "field 'imgDelete' and method 'onClickDelete'");
    target.imgDelete = Utils.castView(view, R.id.imgDelete, "field 'imgDelete'", ImageView.class);
    view7f090116 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickDelete();
      }
    });
    target.txtAppName = Utils.findRequiredViewAsType(source, R.id.txtAppName, "field 'txtAppName'", TextView.class);
    view = Utils.findRequiredView(source, R.id.imgCSV, "field 'imgCSV' and method 'onClickCSV'");
    target.imgCSV = Utils.castView(view, R.id.imgCSV, "field 'imgCSV'", ImageView.class);
    view7f090113 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickCSV();
      }
    });
    target.leyPlaceHolder = Utils.findRequiredViewAsType(source, R.id.leyPlaceHolder, "field 'leyPlaceHolder'", LinearLayout.class);
    target.recHistory = Utils.findRequiredViewAsType(source, R.id.recHistory, "field 'recHistory'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HistoryFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imgDelete = null;
    target.txtAppName = null;
    target.imgCSV = null;
    target.leyPlaceHolder = null;
    target.recHistory = null;

    view7f090116.setOnClickListener(null);
    view7f090116 = null;
    view7f090113.setOnClickListener(null);
    view7f090113 = null;
  }
}
