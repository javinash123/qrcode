package com.simprosys.scan.qrcode.barcode.reader.utility;

import android.text.TextPaint;
import android.text.style.URLSpan;

/**
 * Created by simprosys on 5/2/19.
 */

public class URLSpanNoUnderline extends URLSpan {
    public URLSpanNoUnderline(String url) {
        super(url);
    }

    public void updateDrawState(TextPaint drawState) {
        super.updateDrawState(drawState);
        drawState.setUnderlineText(false);
    }
}