// Generated code from Butter Knife. Do not modify!
package com.simprosys.scan.qrcode.barcode.reader.fragment;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

public class CreateQRCodeResultFragment_ViewBinding implements Unbinder {
  private CreateQRCodeResultFragment target;

  private View view7f09010f;

  private View view7f09012a;

  private View view7f090141;

  private View view7f09015f;

  private View view7f090143;

  private View view7f09015a;

  private View view7f09014b;

  private View view7f090162;

  private View view7f09014a;

  private View view7f090163;

  private View view7f090146;

  private View view7f09014c;

  private View view7f09014d;

  private View view7f090150;

  private View view7f090165;

  private View view7f090161;

  private View view7f090152;

  private View view7f09015e;

  @UiThread
  public CreateQRCodeResultFragment_ViewBinding(final CreateQRCodeResultFragment target,
      View source) {
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
    view = Utils.findRequiredView(source, R.id.imgShare, "field 'imgShare' and method 'onClickShare'");
    target.imgShare = Utils.castView(view, R.id.imgShare, "field 'imgShare'", ImageView.class);
    view7f09012a = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickShare();
      }
    });
    target.imgIcon = Utils.findRequiredViewAsType(source, R.id.imgIcon, "field 'imgIcon'", ImageView.class);
    target.txtType = Utils.findRequiredViewAsType(source, R.id.txtType, "field 'txtType'", TextView.class);
    target.relQrCode = Utils.findRequiredViewAsType(source, R.id.relQrCode, "field 'relQrCode'", RelativeLayout.class);
    target.imgQrCodeBg = Utils.findRequiredViewAsType(source, R.id.imgQrCodeBg, "field 'imgQrCodeBg'", ImageView.class);
    target.imgQrCode = Utils.findRequiredViewAsType(source, R.id.imgQrCode, "field 'imgQrCode'", ImageView.class);
    target.frmQrCodeLogo = Utils.findRequiredViewAsType(source, R.id.frmQrCodeLogo, "field 'frmQrCodeLogo'", FrameLayout.class);
    target.imgQrCodeLogo = Utils.findRequiredViewAsType(source, R.id.imgQrCodeLogo, "field 'imgQrCodeLogo'", ImageView.class);
    target.relBarCode = Utils.findRequiredViewAsType(source, R.id.relBarCode, "field 'relBarCode'", RelativeLayout.class);
    target.imgBarCodeBg = Utils.findRequiredViewAsType(source, R.id.imgBarCodeBg, "field 'imgBarCodeBg'", ImageView.class);
    target.imgBarCode = Utils.findRequiredViewAsType(source, R.id.imgBarCode, "field 'imgBarCode'", ImageView.class);
    target.recImgHorizontal = Utils.findRequiredViewAsType(source, R.id.recImgHorizontal, "field 'recImgHorizontal'", RecyclerView.class);
    target.txtValue = Utils.findRequiredViewAsType(source, R.id.txtValue, "field 'txtValue'", TextView.class);
    target.txtValueWifi1 = Utils.findRequiredViewAsType(source, R.id.txtValueWifi1, "field 'txtValueWifi1'", TextView.class);
    target.leyPassWord = Utils.findRequiredViewAsType(source, R.id.leyPassWord, "field 'leyPassWord'", LinearLayout.class);
    target.txtValueWifi = Utils.findRequiredViewAsType(source, R.id.txtValueWifi, "field 'txtValueWifi'", TextView.class);
    target.txtValueWifi2 = Utils.findRequiredViewAsType(source, R.id.txtValueWifi2, "field 'txtValueWifi2'", TextView.class);
    target.txtValueWifi3 = Utils.findRequiredViewAsType(source, R.id.txtValueWifi3, "field 'txtValueWifi3'", TextView.class);
    view = Utils.findRequiredView(source, R.id.leyBarCode, "field 'leyBarCode' and method 'onClickBarCode'");
    target.leyBarCode = Utils.castView(view, R.id.leyBarCode, "field 'leyBarCode'", LinearLayout.class);
    view7f090141 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickBarCode();
      }
    });
    view = Utils.findRequiredView(source, R.id.leyText, "field 'leyText' and method 'onClickText'");
    target.leyText = Utils.castView(view, R.id.leyText, "field 'leyText'", LinearLayout.class);
    view7f09015f = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickText();
      }
    });
    view = Utils.findRequiredView(source, R.id.leyCall, "field 'leyCall' and method 'onClickCall'");
    target.leyCall = Utils.castView(view, R.id.leyCall, "field 'leyCall'", LinearLayout.class);
    view7f090143 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickCall();
      }
    });
    view = Utils.findRequiredView(source, R.id.leySMS, "field 'leySMS' and method 'onClickSMS'");
    target.leySMS = Utils.castView(view, R.id.leySMS, "field 'leySMS'", LinearLayout.class);
    view7f09015a = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickSMS();
      }
    });
    view = Utils.findRequiredView(source, R.id.leyEmail, "field 'leyEmail' and method 'onClickEmail'");
    target.leyEmail = Utils.castView(view, R.id.leyEmail, "field 'leyEmail'", LinearLayout.class);
    view7f09014b = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickEmail();
      }
    });
    view = Utils.findRequiredView(source, R.id.leyURL, "field 'leyURL' and method 'onClickURL'");
    target.leyURL = Utils.castView(view, R.id.leyURL, "field 'leyURL'", LinearLayout.class);
    view7f090162 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickURL();
      }
    });
    view = Utils.findRequiredView(source, R.id.leyDirection, "field 'leyDirection' and method 'onClickDirection'");
    target.leyDirection = Utils.castView(view, R.id.leyDirection, "field 'leyDirection'", LinearLayout.class);
    view7f09014a = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickDirection();
      }
    });
    view = Utils.findRequiredView(source, R.id.leyWIFI, "field 'leyWIFI' and method 'onClickWIFI'");
    target.leyWIFI = Utils.castView(view, R.id.leyWIFI, "field 'leyWIFI'", LinearLayout.class);
    view7f090163 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickWIFI();
      }
    });
    view = Utils.findRequiredView(source, R.id.leyContact, "field 'leyContact' and method 'onClickContact'");
    target.leyContact = Utils.castView(view, R.id.leyContact, "field 'leyContact'", LinearLayout.class);
    view7f090146 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickContact();
      }
    });
    view = Utils.findRequiredView(source, R.id.leyEvent, "field 'leyEvent' and method 'onClickEvent'");
    target.leyEvent = Utils.castView(view, R.id.leyEvent, "field 'leyEvent'", LinearLayout.class);
    view7f09014c = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickEvent();
      }
    });
    view = Utils.findRequiredView(source, R.id.leyFaceBook, "field 'leyFaceBook' and method 'onClickAllUrl'");
    target.leyFaceBook = Utils.castView(view, R.id.leyFaceBook, "field 'leyFaceBook'", LinearLayout.class);
    view7f09014d = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickAllUrl();
      }
    });
    view = Utils.findRequiredView(source, R.id.leyInstaGram, "field 'leyInstaGram' and method 'onClickAllUrl'");
    target.leyInstaGram = Utils.castView(view, R.id.leyInstaGram, "field 'leyInstaGram'", LinearLayout.class);
    view7f090150 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickAllUrl();
      }
    });
    view = Utils.findRequiredView(source, R.id.leyWhatsApp, "field 'leyWhatsApp' and method 'onClickAllUrl'");
    target.leyWhatsApp = Utils.castView(view, R.id.leyWhatsApp, "field 'leyWhatsApp'", LinearLayout.class);
    view7f090165 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickAllUrl();
      }
    });
    view = Utils.findRequiredView(source, R.id.leyTwitter, "field 'leyTwitter' and method 'onClickAllUrl'");
    target.leyTwitter = Utils.castView(view, R.id.leyTwitter, "field 'leyTwitter'", LinearLayout.class);
    view7f090161 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickAllUrl();
      }
    });
    view = Utils.findRequiredView(source, R.id.leyLinkedIn, "field 'leyLinkedIn' and method 'onClickAllUrl'");
    target.leyLinkedIn = Utils.castView(view, R.id.leyLinkedIn, "field 'leyLinkedIn'", LinearLayout.class);
    view7f090152 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickAllUrl();
      }
    });
    view = Utils.findRequiredView(source, R.id.leySnapChat, "field 'leySnapChat' and method 'onClickAllUrl'");
    target.leySnapChat = Utils.castView(view, R.id.leySnapChat, "field 'leySnapChat'", LinearLayout.class);
    view7f09015e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickAllUrl();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    CreateQRCodeResultFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imgBack = null;
    target.txtAppName = null;
    target.imgShare = null;
    target.imgIcon = null;
    target.txtType = null;
    target.relQrCode = null;
    target.imgQrCodeBg = null;
    target.imgQrCode = null;
    target.frmQrCodeLogo = null;
    target.imgQrCodeLogo = null;
    target.relBarCode = null;
    target.imgBarCodeBg = null;
    target.imgBarCode = null;
    target.recImgHorizontal = null;
    target.txtValue = null;
    target.txtValueWifi1 = null;
    target.leyPassWord = null;
    target.txtValueWifi = null;
    target.txtValueWifi2 = null;
    target.txtValueWifi3 = null;
    target.leyBarCode = null;
    target.leyText = null;
    target.leyCall = null;
    target.leySMS = null;
    target.leyEmail = null;
    target.leyURL = null;
    target.leyDirection = null;
    target.leyWIFI = null;
    target.leyContact = null;
    target.leyEvent = null;
    target.leyFaceBook = null;
    target.leyInstaGram = null;
    target.leyWhatsApp = null;
    target.leyTwitter = null;
    target.leyLinkedIn = null;
    target.leySnapChat = null;

    view7f09010f.setOnClickListener(null);
    view7f09010f = null;
    view7f09012a.setOnClickListener(null);
    view7f09012a = null;
    view7f090141.setOnClickListener(null);
    view7f090141 = null;
    view7f09015f.setOnClickListener(null);
    view7f09015f = null;
    view7f090143.setOnClickListener(null);
    view7f090143 = null;
    view7f09015a.setOnClickListener(null);
    view7f09015a = null;
    view7f09014b.setOnClickListener(null);
    view7f09014b = null;
    view7f090162.setOnClickListener(null);
    view7f090162 = null;
    view7f09014a.setOnClickListener(null);
    view7f09014a = null;
    view7f090163.setOnClickListener(null);
    view7f090163 = null;
    view7f090146.setOnClickListener(null);
    view7f090146 = null;
    view7f09014c.setOnClickListener(null);
    view7f09014c = null;
    view7f09014d.setOnClickListener(null);
    view7f09014d = null;
    view7f090150.setOnClickListener(null);
    view7f090150 = null;
    view7f090165.setOnClickListener(null);
    view7f090165 = null;
    view7f090161.setOnClickListener(null);
    view7f090161 = null;
    view7f090152.setOnClickListener(null);
    view7f090152 = null;
    view7f09015e.setOnClickListener(null);
    view7f09015e = null;
  }
}
