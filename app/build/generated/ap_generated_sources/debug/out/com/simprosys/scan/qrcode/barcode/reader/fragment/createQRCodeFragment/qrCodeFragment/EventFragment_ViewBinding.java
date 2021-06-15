// Generated code from Butter Knife. Do not modify!
package com.simprosys.scan.qrcode.barcode.reader.fragment.createQRCodeFragment.qrCodeFragment;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.simprosys.scan.qrcode.barcode.reader.R;
import com.simprosys.scan.qrcode.barcode.reader.view.SwitchButton;
import java.lang.IllegalStateException;
import java.lang.Override;

public class EventFragment_ViewBinding implements Unbinder {
  private EventFragment target;

  private View view7f09010f;

  private View view7f09011a;

  private View view7f0900b2;

  private View view7f0900b8;

  private View view7f0900b3;

  private View view7f0900b9;

  @UiThread
  public EventFragment_ViewBinding(final EventFragment target, View source) {
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
    target.switchButton = Utils.findRequiredViewAsType(source, R.id.switchButton, "field 'switchButton'", SwitchButton.class);
    target.edtTitle = Utils.findRequiredViewAsType(source, R.id.edtTitle, "field 'edtTitle'", EditText.class);
    target.edtLocation = Utils.findRequiredViewAsType(source, R.id.edtLocation, "field 'edtLocation'", EditText.class);
    target.leyDate = Utils.findRequiredViewAsType(source, R.id.leyDate, "field 'leyDate'", LinearLayout.class);
    target.leyDateAndTime = Utils.findRequiredViewAsType(source, R.id.leyDateAndTime, "field 'leyDateAndTime'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.edtBeginDate, "field 'edtBeginDate' and method 'onClickBeginDate'");
    target.edtBeginDate = Utils.castView(view, R.id.edtBeginDate, "field 'edtBeginDate'", EditText.class);
    view7f0900b2 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickBeginDate();
      }
    });
    view = Utils.findRequiredView(source, R.id.edtEndDate, "field 'edtEndDate' and method 'onClickEndDate'");
    target.edtEndDate = Utils.castView(view, R.id.edtEndDate, "field 'edtEndDate'", EditText.class);
    view7f0900b8 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickEndDate();
      }
    });
    view = Utils.findRequiredView(source, R.id.edtBeginDateAndTime, "field 'edtBeginDateAndTime' and method 'onClickBeginDateAndTime'");
    target.edtBeginDateAndTime = Utils.castView(view, R.id.edtBeginDateAndTime, "field 'edtBeginDateAndTime'", EditText.class);
    view7f0900b3 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickBeginDateAndTime();
      }
    });
    view = Utils.findRequiredView(source, R.id.edtEndDateAndTime, "field 'edtEndDateAndTime' and method 'onClickEndDateAndTime'");
    target.edtEndDateAndTime = Utils.castView(view, R.id.edtEndDateAndTime, "field 'edtEndDateAndTime'", EditText.class);
    view7f0900b9 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickEndDateAndTime();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    EventFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imgBack = null;
    target.txtAppName = null;
    target.imgDone = null;
    target.switchButton = null;
    target.edtTitle = null;
    target.edtLocation = null;
    target.leyDate = null;
    target.leyDateAndTime = null;
    target.edtBeginDate = null;
    target.edtEndDate = null;
    target.edtBeginDateAndTime = null;
    target.edtEndDateAndTime = null;

    view7f09010f.setOnClickListener(null);
    view7f09010f = null;
    view7f09011a.setOnClickListener(null);
    view7f09011a = null;
    view7f0900b2.setOnClickListener(null);
    view7f0900b2 = null;
    view7f0900b8.setOnClickListener(null);
    view7f0900b8 = null;
    view7f0900b3.setOnClickListener(null);
    view7f0900b3 = null;
    view7f0900b9.setOnClickListener(null);
    view7f0900b9 = null;
  }
}
