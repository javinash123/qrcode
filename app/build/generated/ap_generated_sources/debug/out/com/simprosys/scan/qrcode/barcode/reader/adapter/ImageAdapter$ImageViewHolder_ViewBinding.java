// Generated code from Butter Knife. Do not modify!
package com.simprosys.scan.qrcode.barcode.reader.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.simprosys.scan.qrcode.barcode.reader.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ImageAdapter$ImageViewHolder_ViewBinding implements Unbinder {
  private ImageAdapter.ImageViewHolder target;

  @UiThread
  public ImageAdapter$ImageViewHolder_ViewBinding(ImageAdapter.ImageViewHolder target,
      View source) {
    this.target = target;

    target.imgThumb = Utils.findRequiredViewAsType(source, R.id.imgThumb, "field 'imgThumb'", ImageView.class);
    target.leyImage = Utils.findRequiredViewAsType(source, R.id.leyImage, "field 'leyImage'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ImageAdapter.ImageViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imgThumb = null;
    target.leyImage = null;
  }
}
