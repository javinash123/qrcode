package barcodescanner.core;


import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.simprosys.scan.qrcode.barcode.reader.R;
import com.simprosys.scan.qrcode.barcode.reader.utility.Utility;

public class ViewFinderView extends View implements IViewFinder {
    private static final String TAG = "ViewFinderView";
    private static final float PORTRAIT_WIDTH_RATIO = 6f / 8;
    private static final float PORTRAIT_WIDTH_HEIGHT_RATIO = 0.75f;
    private static final float LANDSCAPE_HEIGHT_RATIO = 5f / 8;
    private static final float LANDSCAPE_WIDTH_HEIGHT_RATIO = 1.4f;
    private static final int MIN_DIMENSION_DIFF = 50;
    private static final float SQUARE_DIMENSION_RATIO = 5f / 8;
    private static final int[] SCANNER_ALPHA = {0, 64, 128, 192, 255, 192, 128, 64};

    private static final int POINT_SIZE = 10;
    private static final long ANIMATION_DELAY = 00l;
    private static final int CORNER_RECT_WIDTH = 2;
    private static final int CORNER_RECT_HEIGHT = 40;

    private final int mDefaultLaserColor = Utility.fetchPrimaryColor(getContext());
    private final int mDefaultMaskColor = getResources().getColor(R.color.viewfinder_mask);
    private final int mDefaultBorderColor = Utility.fetchPrimaryColor(getContext());
    private final int mDefaultBorderStrokeWidth = getResources().getInteger(R.integer.viewfinder_border_width);
    private final int mDefaultBorderLineLength = getResources().getInteger(R.integer.viewfinder_border_length);

    public int scannerStart = 0;
    public int scannerEnd = 0;

    protected Paint mLaserPaint;
    protected Paint mFinderMaskPaint;
    protected Paint mBorderPaint;
    protected int mBorderLineLength;
    protected boolean mSquareViewFinder;
    private Rect mFramingRect;
    private int SCANNER_LINE_HEIGHT = 10;

    private final int animStep;
    private int scannerYPos;

    public ViewFinderView(Context context) {
        super(context);

        animStep = 5;
        scannerYPos = 0;

        init();
    }

    public ViewFinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        animStep = 5;
        scannerYPos = 0;

        init();
    }

    private void init() {

        //set up laser paint
        mLaserPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLaserPaint.setColor(mDefaultLaserColor);
        mLaserPaint.setStyle(Paint.Style.FILL);

        //finder mask paint
        mFinderMaskPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFinderMaskPaint.setColor(mDefaultMaskColor);

        //border paint
        mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBorderPaint.setColor(mDefaultBorderColor);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(mDefaultBorderStrokeWidth);

        mBorderLineLength = mDefaultBorderLineLength;
    }

    public void setLaserColor(int laserColor) {
        mLaserPaint.setColor(laserColor);
    }

    public void setMaskColor(int maskColor) {
        mFinderMaskPaint.setColor(maskColor);
    }

    public void setBorderColor(int borderColor) {
        mBorderPaint.setColor(borderColor);
    }

    public void setBorderStrokeWidth(int borderStrokeWidth) {
        mBorderPaint.setStrokeWidth(borderStrokeWidth);
    }

    public void setBorderLineLength(int borderLineLength) {
        mBorderLineLength = borderLineLength;
    }

    public void setSquareViewFinder(boolean set) {
        mSquareViewFinder = set;
    }

    public void setupViewFinder() {
        updateFramingRect();
        invalidate();
    }

    public Rect getFramingRect() {
        return mFramingRect;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (getFramingRect() == null) {
            return;
        }

        drawViewFinderMask(canvas);
        drawViewFinderBorder(canvas);
        drawLaser(canvas);
    }

    public void drawViewFinderMask(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        Rect framingRect = getFramingRect();

        if (scannerStart == 0 || scannerEnd == 0) {
            scannerStart = mFramingRect.top;
            scannerEnd = mFramingRect.bottom - SCANNER_LINE_HEIGHT;
        }

        canvas.drawRect(0, 0, width, framingRect.top, mFinderMaskPaint);
        canvas.drawRect(0, framingRect.top, framingRect.left, framingRect.bottom + 1, mFinderMaskPaint);
        canvas.drawRect(framingRect.right + 1, framingRect.top, width, framingRect.bottom + 1, mFinderMaskPaint);
        canvas.drawRect(0, framingRect.bottom + 1, width, height, mFinderMaskPaint);


    }

    public void drawViewFinderBorder(Canvas canvas) {
        Rect framingRect = getFramingRect();

        canvas.drawRect(framingRect.left, framingRect.top, framingRect.left + CORNER_RECT_WIDTH, framingRect.top + CORNER_RECT_HEIGHT, mBorderPaint);
        canvas.drawRect(framingRect.left, framingRect.top, framingRect.left + CORNER_RECT_HEIGHT, framingRect.top + CORNER_RECT_WIDTH, mBorderPaint);

        canvas.drawRect(framingRect.right - CORNER_RECT_WIDTH, framingRect.top, framingRect.right, framingRect.top + CORNER_RECT_HEIGHT, mBorderPaint);
        canvas.drawRect(framingRect.right - CORNER_RECT_HEIGHT, framingRect.top, framingRect.right, framingRect.top + CORNER_RECT_WIDTH, mBorderPaint);

        canvas.drawRect(framingRect.left, framingRect.bottom - CORNER_RECT_WIDTH, framingRect.left + CORNER_RECT_HEIGHT, framingRect.bottom, mBorderPaint);
        canvas.drawRect(framingRect.left, framingRect.bottom - CORNER_RECT_HEIGHT, framingRect.left + CORNER_RECT_WIDTH, framingRect.bottom, mBorderPaint);

        canvas.drawRect(framingRect.right - CORNER_RECT_WIDTH, framingRect.bottom - CORNER_RECT_HEIGHT, framingRect.right, framingRect.bottom, mBorderPaint);
        canvas.drawRect(framingRect.right - CORNER_RECT_HEIGHT, framingRect.bottom - CORNER_RECT_WIDTH, framingRect.right, framingRect.bottom, mBorderPaint);
    }

    public void drawLaser(Canvas canvas) {
       Rect framingRect = getFramingRect();

        mLaserPaint.setColor(mDefaultLaserColor);

        LinearGradient linearGradient = new LinearGradient(framingRect.left, scannerStart,
                framingRect.left, scannerStart + SCANNER_LINE_HEIGHT,
                shadeColor(mDefaultLaserColor),
                mDefaultLaserColor,
                Shader.TileMode.MIRROR);

        mLaserPaint.setShader(linearGradient);

        if (scannerStart <= scannerEnd) {
            RectF rectF = new RectF(framingRect.left + 2 * SCANNER_LINE_HEIGHT, scannerStart,
                    framingRect.right - 2 * SCANNER_LINE_HEIGHT, scannerStart + SCANNER_LINE_HEIGHT);
            //canvas.drawLine(rectF.left, rectF.top, rectF.right, rectF.bottom, mLaserPaint);
            canvas.drawOval(rectF, mLaserPaint);
            scannerStart += 4;
        } else {
            scannerStart = framingRect.top;
        }

        mLaserPaint.setShader(null);

        /*scannerYPos += animStep;

        if (scannerYPos > framingRect.bottom - 10/2 || scannerYPos < framingRect.top) {
            scannerYPos = framingRect.top;
        }

        Shader mShader = new LinearGradient(0, scannerYPos, 0, scannerYPos + 10 / 2, mDefaultLaserColor, mDefaultLaserColor, Shader.TileMode.MIRROR);
        mLaserPaint.setShader(mShader);
        RectF rectF = new RectF(framingRect.left + 10, scannerYPos, framingRect.right - 10, scannerYPos + 10);
        canvas.drawOval(rectF, mLaserPaint);

*/


        postInvalidateDelayed(ANIMATION_DELAY,
                framingRect.left - POINT_SIZE,
                framingRect.top - POINT_SIZE,
                framingRect.right + POINT_SIZE,
                framingRect.bottom + POINT_SIZE);


        /*// Draw a red "laser scanner" line through the middle to show decoding is active
        mLaserPaint.setAlpha(SCANNER_ALPHA[scannerAlpha]);

        scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
        int middle = framingRect.height() / 2 + framingRect.top;

        canvas.drawRect(framingRect.left + 2, middle - 1, framingRect.right - 1, middle + 2, mLaserPaint);

        postInvalidateDelayed(ANIMATION_DELAY,
                framingRect.left - POINT_SIZE,
                framingRect.top - POINT_SIZE,
                framingRect.right + POINT_SIZE,
                framingRect.bottom + POINT_SIZE);*/

      /*  mLaserPaint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
        scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
        int middle = mFramingRect.height() / 2 + mFramingRect.top;
        middle = middle + cntr;

        if ((cntr < 60) && (goingup == false)) {
            canvas.drawRect(mFramingRect.left + 3, middle - 5, mFramingRect.right - 3, middle + 5, mLaserPaint);
            cntr = cntr + 15;
        }
        if ((cntr >= 60) && (goingup == false))
            goingup = true;
        if ((cntr > -60) && (goingup == true)) {
            canvas.drawRect(mFramingRect.left + 3, middle - 5, mFramingRect.right - 3, middle + 5, mLaserPaint);
            cntr = cntr - 15;
        }
        if ((cntr <= -60) && (goingup == true))
            goingup = false;
        postInvalidateDelayed(ANIMATION_DELAY, mFramingRect.left - POINT_SIZE, mFramingRect.top - POINT_SIZE, mFramingRect.right + POINT_SIZE, mFramingRect.bottom + POINT_SIZE);
*/
    }

    public int shadeColor(int color) {
        String hax = Integer.toHexString(color);
        String result = "20" + hax.substring(2);
        return Integer.valueOf(result, 16);
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
      updateFramingRect();
    }

    public synchronized void updateFramingRect() {
        Point viewResolution = new Point(getWidth(), getHeight());
        int width;
        int height;
        int orientation = DisplayUtils.getScreenOrientation(getContext());

        if (mSquareViewFinder) {
            if (orientation != Configuration.ORIENTATION_PORTRAIT) {
                height = (int) (getHeight() * SQUARE_DIMENSION_RATIO);
                width = height;
            } else {
                width = (int) (getWidth() * SQUARE_DIMENSION_RATIO);
                height = width;
            }
        } else {
            if (orientation != Configuration.ORIENTATION_PORTRAIT) {
                height = (int) (getHeight() * LANDSCAPE_HEIGHT_RATIO);
                width = (int) (LANDSCAPE_WIDTH_HEIGHT_RATIO * height);
            } else {
                width = (int) (getWidth() * PORTRAIT_WIDTH_RATIO);
                height = (int) (PORTRAIT_WIDTH_HEIGHT_RATIO * width);
            }
        }

        if (width > getWidth()) {
            width = getWidth() - MIN_DIMENSION_DIFF;
        }

        if (height > getHeight()) {
            height = getHeight() - MIN_DIMENSION_DIFF;
        }

        int leftOffset = (viewResolution.x - width) / 2;
        int topOffset = (viewResolution.y - height) / 2;
        mFramingRect = new Rect(leftOffset, topOffset, leftOffset + width, topOffset + height);
    }
}
