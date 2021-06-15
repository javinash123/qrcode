package com.simprosys.scan.qrcode.barcode.reader.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.simprosys.scan.qrcode.barcode.reader.R;

/**
 * Created by simprosys on 9/2/19.
 */

public class SwipeLayout extends FrameLayout{
    public static final int LEFT = 1;
    public static final int RIGHT = LEFT << 1;
    public static final int HORIZONTAL = LEFT | RIGHT;
    private static final int CLOSE_POSITION = 0;
    private static final int NO_POSITION = -1;
    private static final int DEFAULT_AUTO_OPEN_SPEED = 1000;

    private int currentDirection;

    private boolean isTogether;

    private boolean isContinuousSwipe;

    private boolean isFreeDragAfterOpen;

    private boolean isFreeHorizontalDrag;

    private int rightDragViewPadding;

    private int leftDragViewPadding;

    private double autoOpenSpeed;

    private int currentDraggingState = ViewDragHelper.STATE_IDLE;
    private ViewDragHelper dragHelper;
    private GestureDetectorCompat gestureDetector;
    private int draggingViewLeft;
    private int horizontalWidth;
    private boolean isLeftOpen;
    private boolean isRightOpen;
    private int staticRightViewId;
    private int staticLeftViewId;
    private int draggedViewId;
    private View draggedView;
    private View staticRightView;
    private View staticLeftView;
    private SwipeActionsListener actionsListener;

    public SwipeLayout(@NonNull Context context) {
        super(context);
    }

    public SwipeLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        isLeftOpen = false;
        isRightOpen = false;

        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SwipeLayout);
        currentDirection = typedArray.getInteger(R.styleable.SwipeLayout_swipeDirection, LEFT);
        isFreeDragAfterOpen = typedArray.getBoolean(R.styleable.SwipeLayout_isFreeDragAfterOpen, false);
        isFreeHorizontalDrag = typedArray.getBoolean(R.styleable.SwipeLayout_isFreeHorizontalDrag, false);
        isContinuousSwipe = typedArray.getBoolean(R.styleable.SwipeLayout_isContinuousSwipe, false);
        isTogether = typedArray.getBoolean(R.styleable.SwipeLayout_isTogether, false);
        staticLeftViewId = typedArray.getResourceId(R.styleable.SwipeLayout_leftItem, 0);
        staticRightViewId = typedArray.getResourceId(R.styleable.SwipeLayout_rightItem, 0);
        draggedViewId = typedArray.getResourceId(R.styleable.SwipeLayout_draggedItem, 0);
        autoOpenSpeed = typedArray.getInt(R.styleable.SwipeLayout_autoMovingSensitivity, DEFAULT_AUTO_OPEN_SPEED);
        rightDragViewPadding = (int) typedArray.getDimension(R.styleable.SwipeLayout_rightDragViewPadding, 0);
        leftDragViewPadding = (int) typedArray.getDimension(R.styleable.SwipeLayout_leftDragViewPadding, 0);

        parametersAdjustment();
        typedArray.recycle();
    }

    public SwipeLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SwipeLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void updateState() {
        if (isClosed()) {
            isLeftOpen = false;
            isRightOpen = false;

            if (actionsListener != null) {
                actionsListener.onClose();
            }
        } else if (isLeftOpenCompletely() || isLeftViewOpen()) {
            isLeftOpen = true;
            isRightOpen = false;

            if (actionsListener != null) {
                actionsListener.onOpen(RIGHT, isLeftOpenCompletely());
            }
        } else if (isRightOpenCompletely() || isRightViewOpen()) {
            isLeftOpen = false;
            isRightOpen = true;

            if (actionsListener != null) {
                actionsListener.onOpen(LEFT, isRightOpenCompletely());
            }
        }
    }

    private final ViewDragHelper.Callback dragHelperCallback = new ViewDragHelper.Callback() {
        @Override
        public void onViewDragStateChanged(int state) {
            if (state == currentDraggingState)
                return;

            if (isIdleAfterMoving(state)) {
                updateState();
            }

            currentDraggingState = state;
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            draggingViewLeft = left;

            if (isTogether) {
                if (currentDirection == LEFT) {
                    staticRightView.offsetLeftAndRight(dx);
                } else if (currentDirection == RIGHT) {
                    staticLeftView.offsetLeftAndRight(dx);
                } else if (currentDirection == HORIZONTAL) {
                    staticLeftView.offsetLeftAndRight(dx);
                    staticRightView.offsetLeftAndRight(dx);
                }
            }
        }

        @Override
        public int getViewHorizontalDragRange(@NonNull View child) {
            return horizontalWidth;
        }

        @Override
        public boolean tryCaptureView(@NonNull View view, int pointerId) {
            return view.getId() == draggedView.getId();
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            switch (currentDirection) {
                case LEFT:
                    return clampLeftViewPosition(left);
                case RIGHT:
                    return clampRightViewPosition(left);
                case HORIZONTAL:
                    return clampHorizontalViewPosition(left, dx);
                default:
                    return CLOSE_POSITION;
            }
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xVel, float yVel) {
            int finalXDraggingView = CLOSE_POSITION;

            if (currentDirection == LEFT) {
                finalXDraggingView = getFinalXLeftDirection(xVel);
            } else if (currentDirection == RIGHT) {
                finalXDraggingView = getFinalXRightDirection(xVel);
            } else if (currentDirection == HORIZONTAL) {
                finalXDraggingView = getFinalXHorizontalDirection(xVel);

                if (finalXDraggingView == NO_POSITION) {
                    finalXDraggingView = getPreviousPosition();
                }
            }

            if (dragHelper.settleCapturedViewAt(finalXDraggingView, draggedView.getTop())) {
                ViewCompat.postInvalidateOnAnimation(SwipeLayout.this);
            }
        }
    };

    private int clampLeftViewPosition(int left) {
        if (isContinuousSwipe && isEmptyRightView()) {
            if (isFreeHorizontalDrag) {
                return left > horizontalWidth ? CLOSE_POSITION : Math.max(left, -horizontalWidth);
            } else {
                return left > CLOSE_POSITION ? CLOSE_POSITION : Math.max(left, -horizontalWidth);
            }
        }

        if (isFreeDragAfterOpen) {
            if (isFreeHorizontalDrag) {
                return left > horizontalWidth ? CLOSE_POSITION
                        : Math.max(left, leftDragViewPadding - horizontalWidth);
            }
            return left > CLOSE_POSITION ? CLOSE_POSITION
                    : Math.max(left, leftDragViewPadding - horizontalWidth);
        }

        if (isFreeHorizontalDrag) {
            return left > horizontalWidth ? CLOSE_POSITION : Math.max(left, -getRightViewWidth());
        }
        return left > CLOSE_POSITION ? CLOSE_POSITION : Math.max(left, -getRightViewWidth());
    }

    private int clampRightViewPosition(int left) {
        if (isContinuousSwipe && isEmptyLeftView()) {
            if (isFreeHorizontalDrag) {
                return left < -horizontalWidth ? -horizontalWidth : Math.min(left, horizontalWidth);
            } else {
                return left < CLOSE_POSITION ? CLOSE_POSITION : Math.min(left, horizontalWidth);
            }
        }

        if (isFreeDragAfterOpen) {
            if (isFreeHorizontalDrag) {
                return left < -horizontalWidth ? -horizontalWidth
                        : Math.min(left, horizontalWidth - rightDragViewPadding);
            }
            return left < CLOSE_POSITION ? CLOSE_POSITION
                    : Math.min(left, horizontalWidth - rightDragViewPadding);
        }

        if (isFreeHorizontalDrag) {
            return left < -horizontalWidth ? -horizontalWidth : Math.min(left, getLeftViewWidth());
        }
        return left < CLOSE_POSITION ? CLOSE_POSITION : Math.min(left, getLeftViewWidth());
    }

    private int clampHorizontalViewPosition(int left, int dx) {
        if (!isFreeHorizontalDrag && isLeftOpen && dx < 0) {
            return Math.max(left, CLOSE_POSITION);
        }

        if (!isFreeHorizontalDrag && isRightOpen && dx > 0) {
            return Math.min(left, CLOSE_POSITION);
        }

        if (!isFreeDragAfterOpen && left > CLOSE_POSITION) {
            return Math.min(left, getLeftViewWidth());
        }

        if (!isFreeDragAfterOpen && left < CLOSE_POSITION) {
            return Math.max(left, -getRightViewWidth());
        }

        return left < CLOSE_POSITION ? Math.max(left, leftDragViewPadding - horizontalWidth)
                : Math.min(left, horizontalWidth - rightDragViewPadding);
    }

    private int getPreviousPosition() {
        if (isLeftOpen) {
            return getLeftViewWidth();
        } else if (isRightOpen) {
            return -getRightViewWidth();
        } else {
            return CLOSE_POSITION;
        }
    }

    private int getFinalXLeftDirection(float xVel) {
        if (isContinuousSwipe) {
            if (isEmptyRightView()) {
                if ((draggingViewLeft < CLOSE_POSITION && Math.abs(draggingViewLeft) > horizontalWidth / 2)
                        || xVel < -autoOpenSpeed) {
                    return -horizontalWidth;
                }
                return CLOSE_POSITION;
            }

            if (isContinuousSwipeToLeft(xVel)) {
                return -horizontalWidth;
            }
        }

        final boolean settleToOpen;
        if (xVel > autoOpenSpeed) {
            settleToOpen = false;
        } else if (xVel < -autoOpenSpeed) {
            settleToOpen = true;
        } else if (draggingViewLeft < CLOSE_POSITION && Math.abs(draggingViewLeft) > getRightViewWidth() / 2) {
            settleToOpen = true;
        } else if (draggingViewLeft < CLOSE_POSITION && Math.abs(draggingViewLeft) < getRightViewWidth() / 2) {
            settleToOpen = false;
        } else {
            settleToOpen = false;
        }
        return settleToOpen ? -getRightViewWidth() : CLOSE_POSITION;
    }

    private int getFinalXRightDirection(float xVel) {
        if (isContinuousSwipe) {
            if (isEmptyLeftView()) {
                if ((draggingViewLeft > CLOSE_POSITION && Math.abs(draggingViewLeft) > horizontalWidth / 2)
                        || xVel > autoOpenSpeed) {
                    return horizontalWidth;
                }
                return CLOSE_POSITION;
            }

            if (isContinuousSwipeToRight(xVel)) {
                return horizontalWidth;
            }
        }

        final boolean settleToOpen;
        if (xVel > autoOpenSpeed) {
            settleToOpen = true;
        } else if (xVel < -autoOpenSpeed) {
            settleToOpen = false;
        } else if (draggingViewLeft > CLOSE_POSITION && Math.abs(draggingViewLeft) > getLeftViewWidth() / 2) {
            settleToOpen = true;
        } else if (draggingViewLeft > CLOSE_POSITION && Math.abs(draggingViewLeft) < getLeftViewWidth() / 2) {
            settleToOpen = false;
        } else {
            settleToOpen = false;
        }
        return settleToOpen ? getLeftViewWidth() : CLOSE_POSITION;
    }

    private int getFinalXHorizontalDirection(float xVel) {
        if (isSwipeToOpenLeft(xVel)) {
            return getLeftViewWidth();
        } else if (isSwipeToOpenRight(xVel)) {
            return -getRightViewWidth();
        } else if (isSwipeToClose(xVel)) {
            return CLOSE_POSITION;
        }

        return NO_POSITION;
    }

    private boolean isContinuousSwipeToRight(float xVel) {
        return (xVel > autoOpenSpeed && Math.abs(draggingViewLeft) > getLeftViewWidth())
                || (draggingViewLeft > CLOSE_POSITION && Math.abs(draggingViewLeft) > horizontalWidth / 2);

    }

    private boolean isContinuousSwipeToLeft(float xVel) {
        return (xVel < -autoOpenSpeed && Math.abs(draggingViewLeft) > getRightViewWidth())
                || (draggingViewLeft < CLOSE_POSITION && Math.abs(draggingViewLeft) > horizontalWidth / 2);
    }

    private boolean isSwipeToOpenRight(float xVel) {
        if (xVel > 0) {
            return false;
        }
        return (draggingViewLeft < CLOSE_POSITION && xVel < -autoOpenSpeed)
                || (draggingViewLeft < CLOSE_POSITION && Math.abs(draggingViewLeft) > getRightViewWidth() / 2);
    }

    private boolean isSwipeToOpenLeft(float xVel) {
        if (xVel < 0) {
            return false;
        }
        return (draggingViewLeft > CLOSE_POSITION && xVel > autoOpenSpeed)
                || (draggingViewLeft > CLOSE_POSITION && Math.abs(draggingViewLeft) > getLeftViewWidth() / 2);
    }

    private boolean isSwipeToClose(float xVel) {
        return (draggingViewLeft >= CLOSE_POSITION && xVel < -autoOpenSpeed)
                || (draggingViewLeft <= CLOSE_POSITION && xVel > autoOpenSpeed)
                || (draggingViewLeft >= CLOSE_POSITION && Math.abs(draggingViewLeft) < getLeftViewWidth() / 2)
                || (draggingViewLeft <= CLOSE_POSITION && Math.abs(draggingViewLeft) < getRightViewWidth() / 2);
    }

    @Override
    protected void onFinishInflate() {
        if (draggedViewId != 0) {
            draggedView = findViewById(draggedViewId);
        }

        if (staticLeftViewId != 0) {
            staticLeftView = findViewById(staticLeftViewId);
        }

        if (staticRightViewId != 0) {
            staticRightView = findViewById(staticRightViewId);
        }

        if (draggedView == null) {
            throw new RuntimeException("'draggedItem' must be specified");
        } else if (isTogether && currentDirection == LEFT && staticRightView == null) {
            throw new RuntimeException("If 'isTogether = true' 'rightItem' must be specified");
        } else if (isTogether && currentDirection == RIGHT && staticLeftView == null) {
            throw new RuntimeException("If 'isTogether = true' 'leftItem' must be specified");
        } else if (currentDirection == LEFT && !isContinuousSwipe && staticRightView == null) {
            throw new RuntimeException("Must be specified 'rightItem' or flag isContinuousSwipe = true");
        } else if (currentDirection == RIGHT && !isContinuousSwipe && staticLeftView == null) {
            throw new RuntimeException("Must be specified 'leftItem' or flag isContinuousSwipe = true");
        } else if (currentDirection == HORIZONTAL && (staticRightView == null || staticLeftView == null)) {
            throw new RuntimeException("'leftItem' and 'rightItem' must be specified");
        }

        dragHelper = ViewDragHelper.create(this, 1.0f, dragHelperCallback);
        gestureDetector = new GestureDetectorCompat(getContext(), gestureDetectorCallBack);

        setupPost();
        super.onFinishInflate();
    }

    private GestureDetector.OnGestureListener gestureDetectorCallBack = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (getParent() != null) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
            return false;
        }
    };

    private void setupPost() {
        if (isTogether) {
            post(new Runnable() {
                @Override
                public void run() {
                    if (currentDirection == LEFT) {
                        staticRightView.setX(horizontalWidth);
                    } else if (currentDirection == RIGHT) {
                        staticLeftView.setX(-staticLeftView.getWidth());
                    } else if (currentDirection == HORIZONTAL) {
                        staticRightView.setX(horizontalWidth);
                        staticLeftView.setX(-staticLeftView.getWidth());
                    }
                }
            });
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        horizontalWidth = w;
        super.onSizeChanged(w, h, oldW, oldH);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return isSwipeViewTarget(event) && dragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isSwipeViewTarget(event) || isMoving()) {
            gestureDetector.onTouchEvent(event);
            dragHelper.processTouchEvent(event);
            return true;
        } else {
            return super.onTouchEvent(event);
        }
    }

    private boolean isSwipeViewTarget(MotionEvent event) {
        final int[] swipeViewLocation = new int[2];
        draggedView.getLocationOnScreen(swipeViewLocation);
        final int upperLimit = swipeViewLocation[1] + draggedView.getMeasuredHeight();
        final int lowerLimit = swipeViewLocation[1];
        final int y = (int) event.getRawY();
        return (y > lowerLimit && y < upperLimit);
    }

    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private boolean isIdleAfterMoving(int state) {
        return (currentDraggingState == ViewDragHelper.STATE_DRAGGING
                || currentDraggingState == ViewDragHelper.STATE_SETTLING)
                && state == ViewDragHelper.STATE_IDLE;
    }

    private boolean isDragIdle(int state) {
        return state == ViewDragHelper.STATE_IDLE;
    }

    private boolean isRightViewOpen() {
        return staticRightView != null && draggingViewLeft == -getRightViewWidth();
    }

    private boolean isLeftViewOpen() {
        return staticLeftView != null && draggingViewLeft == getLeftViewWidth();
    }

    private boolean isRightOpenCompletely() {
        return draggingViewLeft == -horizontalWidth;
    }

    private boolean isLeftOpenCompletely() {
        return draggingViewLeft == horizontalWidth;
    }

    private int getLeftViewWidth() {
        return staticLeftView.getWidth();
    }

    private int getRightViewWidth() {
        return staticRightView.getWidth();
    }

    private boolean isEmptyLeftView() {
        return staticLeftView == null;
    }

    private boolean isEmptyRightView() {
        return staticRightView == null;
    }

    private void parametersAdjustment() {
        if (isContinuousSwipe && currentDirection != HORIZONTAL) {
            isFreeDragAfterOpen = true;
        }

        if (currentDirection == HORIZONTAL) {
            rightDragViewPadding = 0;
            leftDragViewPadding = 0;
        }
    }

    private void moveTo(int x) {
        dragHelper.smoothSlideViewTo(draggedView, x, draggedView.getTop());
        ViewCompat.postInvalidateOnAnimation(this);
    }

    public void openRight(boolean animated) {
        if (animated) {
            openRight();
        } else if (isDragIdle(currentDraggingState) && ((currentDirection == LEFT && !isEmptyRightView())
                || currentDirection == HORIZONTAL) && !isRightOpen) {
            if (isTogether) {
                staticRightView.offsetLeftAndRight(-1 * (isLeftOpen ? getRightViewWidth() * 2 : getRightViewWidth()));
            }

            draggedView.offsetLeftAndRight(-1 * (isLeftOpen ? getRightViewWidth() * 2 : getRightViewWidth()));
            draggingViewLeft -= (isLeftOpen ? getRightViewWidth() * 2 : getRightViewWidth());
            updateState();
        }
    }

    public void openRightCompletely(boolean animated) {
        if (animated) {
            openRightCompletely();
        } else {
            if (isDragIdle(currentDraggingState) && currentDirection == LEFT) {
                if (isTogether) {
                    staticRightView.offsetLeftAndRight(-horizontalWidth);
                }

                draggedView.offsetLeftAndRight(-horizontalWidth);
                draggingViewLeft -= horizontalWidth;
                updateState();
            }
        }
    }

    public void openLeft(boolean animated) {
        if (animated) {
            openLeft();
        } else if (isDragIdle(currentDraggingState) && ((currentDirection == RIGHT && !isEmptyLeftView())
                || currentDirection == HORIZONTAL) && !isLeftOpen) {
            if (isTogether) {
                staticLeftView.offsetLeftAndRight((isRightOpen ? getLeftViewWidth() * 2 : getLeftViewWidth()));
            }

            draggedView.offsetLeftAndRight((isRightOpen ? getLeftViewWidth() * 2 : getLeftViewWidth()));
            draggingViewLeft += (isRightOpen ? getLeftViewWidth() * 2 : getLeftViewWidth());
            updateState();
        }
    }

    public void openLeftCompletely(boolean animated) {
        if (animated) {
            openRightCompletely();
        } else {
            if (isDragIdle(currentDraggingState) && currentDirection == RIGHT) {
                if (isTogether) {
                    staticRightView.offsetLeftAndRight(horizontalWidth);
                }

                draggedView.offsetLeftAndRight(horizontalWidth);
                draggingViewLeft += horizontalWidth;
                updateState();
            }
        }
    }

    public void close(boolean animated) {
        if (animated) {
            close();
        } else {
            if (isTogether) {
                if (staticLeftView != null && currentDirection == RIGHT) {
                    staticLeftView.layout(CLOSE_POSITION, staticLeftView.getTop(),
                            staticLeftView.getWidth(), staticLeftView.getBottom());
                } else if (staticRightView != null && currentDirection == LEFT) {
                    staticRightView.layout(horizontalWidth - staticRightView.getWidth(), staticRightView.getTop(),
                            horizontalWidth, staticRightView.getBottom());
                } else if (currentDirection == HORIZONTAL && staticRightView != null && staticLeftView != null) {
                    staticLeftView.layout(CLOSE_POSITION, staticLeftView.getTop(),
                            staticLeftView.getWidth(), staticLeftView.getBottom());
                    staticRightView.layout(horizontalWidth - staticRightView.getWidth(), staticRightView.getTop(),
                            horizontalWidth, staticRightView.getBottom());
                }
            }

            draggedView.layout(CLOSE_POSITION,
                    draggedView.getTop(),
                    draggedView.getWidth(),
                    draggedView.getBottom());

            draggingViewLeft = CLOSE_POSITION;
            updateState();
        }
    }

    public void openLeft() {
        if (isDragIdle(currentDraggingState) && ((currentDirection == RIGHT && !isEmptyLeftView())
                || currentDirection == HORIZONTAL)) {
            moveTo(getLeftViewWidth());
        }
    }

    public void openRight() {
        if (isDragIdle(currentDraggingState) && ((currentDirection == LEFT && !isEmptyRightView())
                || currentDirection == HORIZONTAL)) {
            moveTo(-getRightViewWidth());
        }
    }

    public void openLeftCompletely() {
        if (isDragIdle(currentDraggingState) && currentDirection == RIGHT) {
            moveTo(horizontalWidth);
        }
    }

    public void openRightCompletely() {
        if (isDragIdle(currentDraggingState) && currentDirection == LEFT) {
            moveTo(-horizontalWidth);
        }
    }

    public void close() {
        moveTo(CLOSE_POSITION);
    }

    public boolean isMoving() {
        return (currentDraggingState == ViewDragHelper.STATE_DRAGGING ||
                currentDraggingState == ViewDragHelper.STATE_SETTLING);
    }

    public boolean isClosed() {
        return draggingViewLeft == CLOSE_POSITION;
    }

    public int getCurrentDirection() {
        return currentDirection;
    }

    public SwipeLayout setCurrentDirection(int currentDirection) {
        this.currentDirection = currentDirection;
        return this;
    }

    public boolean isTogether() {
        return isTogether;
    }

    public SwipeLayout setTogether(boolean together) {
        isTogether = together;
        return this;
    }

    public boolean isContinuousSwipe() {
        return isContinuousSwipe;
    }

    public SwipeLayout setContinuousSwipe(boolean continuousSwipe) {
        isContinuousSwipe = continuousSwipe;
        parametersAdjustment();
        return this;
    }

    public boolean isFreeDragAfterOpen() {
        return isFreeDragAfterOpen;
    }

    public SwipeLayout setFreeDragAfterOpen(boolean freeDragAfterOpen) {
        isFreeDragAfterOpen = freeDragAfterOpen;
        parametersAdjustment();
        return this;
    }

    public boolean isFreeHorizontalDrag() {
        return isFreeHorizontalDrag;
    }

    public SwipeLayout setFreeHorizontalDrag(boolean freeHorizontalDrag) {
        isFreeHorizontalDrag = freeHorizontalDrag;
        return this;
    }

    public boolean isRightOpen() {
        return isRightOpen;
    }

    public boolean isLeftOpen() {
        return isLeftOpen;
    }

    public SwipeLayout setOnActionsListener(SwipeActionsListener actionsListener) {
        this.actionsListener = actionsListener;
        return this;
    }

    public int getRightDragViewPadding() {
        return rightDragViewPadding;
    }

    public SwipeLayout setRightDragViewPadding(int minRightDragViewPadding) {
        this.rightDragViewPadding = minRightDragViewPadding;
        parametersAdjustment();
        return this;
    }

    public int getLeftDragViewPadding() {
        return leftDragViewPadding;
    }

    public SwipeLayout setLeftDragViewPadding(int minLeftDragViewPadding) {
        this.leftDragViewPadding = minLeftDragViewPadding;
        parametersAdjustment();
        return this;
    }

    public interface SwipeActionsListener {
        void onOpen(int direction, boolean isContinuous);
        void onClose();
    }
}
