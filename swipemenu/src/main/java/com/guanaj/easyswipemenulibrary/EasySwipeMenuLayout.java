package com.guanaj.easyswipemenulibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

import java.util.ArrayList;

import static com.guanaj.easyswipemenulibrary.State.CLOSE;

/**
 * Created by guanaj on 2017/6/5.
 */

public class EasySwipeMenuLayout extends ViewGroup {

    private static final String TAG = "EasySwipeMenuLayout";
    private static EasySwipeMenuLayout mViewCache;
    private static State mStateCache;
    private final ArrayList<View> mMatchParentChildren = new ArrayList<>(1);
    State result;
    private int mLeftViewResID;
    private int mRightViewResID;
    private int mContentViewResID;
    private View mLeftView;
    private View mRightView;
    private View mContentView;
    private MarginLayoutParams mContentViewLp;
    private boolean isSwiping;
    private PointF mLastP;
    private PointF mFirstP;
    private float mFraction = 0.3f;
    private boolean mCanLeftSwipe = true;
    private boolean mCanRightSwipe = true;
    private int mScaledTouchSlop;
    private Scroller mScroller;
    private float distanceX;
    private float finalDistanceX;
    private SwipeMenuListener swipeListener;

    public EasySwipeMenuLayout(Context context) {
        this(context, null);
    }

    public EasySwipeMenuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EasySwipeMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);

    }

    public static EasySwipeMenuLayout getViewCache() {
        return mViewCache;
    }

    public static State getStateCache() {
        return mStateCache;
    }

    /**
     * 初始化方法
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        //创建辅助对象
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mScaledTouchSlop = viewConfiguration.getScaledTouchSlop();
        mScroller = new Scroller(context);
        //1、获取配置的属性值
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.EasySwipeMenuLayout, defStyleAttr, 0);

        try {
            int indexCount = typedArray.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int attr = typedArray.getIndex(i);
                if (attr == R.styleable.EasySwipeMenuLayout_leftMenuView) {
                    mLeftViewResID = typedArray.getResourceId(R.styleable.EasySwipeMenuLayout_leftMenuView, -1);
                } else if (attr == R.styleable.EasySwipeMenuLayout_rightMenuView) {
                    mRightViewResID = typedArray.getResourceId(R.styleable.EasySwipeMenuLayout_rightMenuView, -1);
                } else if (attr == R.styleable.EasySwipeMenuLayout_contentView) {
                    mContentViewResID = typedArray.getResourceId(R.styleable.EasySwipeMenuLayout_contentView, -1);
                } else if (attr == R.styleable.EasySwipeMenuLayout_canLeftSwipe) {
                    mCanLeftSwipe = typedArray.getBoolean(R.styleable.EasySwipeMenuLayout_canLeftSwipe, true);
                } else if (attr == R.styleable.EasySwipeMenuLayout_canRightSwipe) {
                    mCanRightSwipe = typedArray.getBoolean(R.styleable.EasySwipeMenuLayout_canRightSwipe, true);
                } else if (attr == R.styleable.EasySwipeMenuLayout_fraction) {
                    mFraction = typedArray.getFloat(R.styleable.EasySwipeMenuLayout_fraction, 0.5f);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取childView的个数
        setClickable(true);
        int count = getChildCount();
        //参考frameLayout测量代码
        final boolean measureMatchParentChildren =
                MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.EXACTLY ||
                        MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY;
        mMatchParentChildren.clear();
        int maxHeight = 0;
        int maxWidth = 0;
        int childState = 0;
        //遍历childViews
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);

            if (child.getVisibility() != GONE) {
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                maxWidth = Math.max(maxWidth,
                        child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin);
                maxHeight = Math.max(maxHeight,
                        child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);
                childState = combineMeasuredStates(childState, child.getMeasuredState());
                if (measureMatchParentChildren) {
                    if (lp.width == LayoutParams.MATCH_PARENT ||
                            lp.height == LayoutParams.MATCH_PARENT) {
                        mMatchParentChildren.add(child);
                    }
                }
            }
        }
        // Check against our minimum height and width
        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());
        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, childState),
                resolveSizeAndState(maxHeight, heightMeasureSpec,
                        childState << MEASURED_HEIGHT_STATE_SHIFT));

        count = mMatchParentChildren.size();
        if (count > 1) {
            for (int i = 0; i < count; i++) {
                final View child = mMatchParentChildren.get(i);
                final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

                final int childWidthMeasureSpec;
                if (lp.width == LayoutParams.MATCH_PARENT) {
                    final int width = Math.max(0, getMeasuredWidth()
                            - lp.leftMargin - lp.rightMargin);
                    childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                            width, MeasureSpec.EXACTLY);
                } else {
                    childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec,
                            lp.leftMargin + lp.rightMargin,
                            lp.width);
                }

                final int childHeightMeasureSpec;
                if (lp.height == FrameLayout.LayoutParams.MATCH_PARENT) {
                    final int height = Math.max(0, getMeasuredHeight()
                            - lp.topMargin - lp.bottomMargin);
                    childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                            height, MeasureSpec.EXACTLY);
                } else {
                    childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec,
                            lp.topMargin + lp.bottomMargin,
                            lp.height);
                }

                child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            }
        }

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int left = getPaddingLeft();
        int right = getPaddingLeft();
        int top = getPaddingTop();
        int bottom = getPaddingTop();

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (mLeftView == null && child.getId() == mLeftViewResID) {
                mLeftView = child;
                mLeftView.setClickable(true);
            } else if (mRightView == null && child.getId() == mRightViewResID) {
                mRightView = child;
                mRightView.setClickable(true);
            } else if (mContentView == null && child.getId() == mContentViewResID) {
                mContentView = child;
                mContentView.setClickable(true);
            }

        }
        //布局contentView
        int cRight = 0;
        if (mContentView != null) {
            mContentViewLp = (MarginLayoutParams) mContentView.getLayoutParams();
            int cTop = top + mContentViewLp.topMargin;
            int cLeft = left + mContentViewLp.leftMargin;
            cRight = left + mContentViewLp.leftMargin + mContentView.getMeasuredWidth();
            int cBottom = cTop + mContentView.getMeasuredHeight();
            mContentView.layout(cLeft, cTop, cRight, cBottom);
        }
        if (mLeftView != null) {
            MarginLayoutParams leftViewLp = (MarginLayoutParams) mLeftView.getLayoutParams();
            int lTop = top + leftViewLp.topMargin;
            int lLeft = -mLeftView.getMeasuredWidth() + leftViewLp.leftMargin + leftViewLp.rightMargin;
            int lRight = -leftViewLp.rightMargin;
            int lBottom = lTop + mLeftView.getMeasuredHeight();
            mLeftView.layout(lLeft, lTop, lRight, lBottom);
        }
        if (mRightView != null) {
            MarginLayoutParams rightViewLp = (MarginLayoutParams) mRightView.getLayoutParams();
            int lTop = top + rightViewLp.topMargin;
            int lLeft = mContentView.getRight() + mContentViewLp.rightMargin + rightViewLp.leftMargin;
            int lRight = lLeft + mRightView.getMeasuredWidth();
            int lBottom = lTop + mRightView.getMeasuredHeight();
            mRightView.layout(lLeft, lTop, lRight, lBottom);
        }


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                isSwiping = false;
                if (mLastP == null) {
                    mLastP = new PointF();
                }
                mLastP.set(ev.getRawX(), ev.getRawY());
                if (mFirstP == null) {
                    mFirstP = new PointF();
                }
                mFirstP.set(ev.getRawX(), ev.getRawY());
                if (mViewCache != null) {
                    if (mViewCache != this) {
                        mViewCache.handlerSwipeMenu(CLOSE);
                    }
                    getParent().requestDisallowInterceptTouchEvent(true);
                }

                break;
            }
            case MotionEvent.ACTION_MOVE: {
                float distanceX = mLastP.x - ev.getRawX();
                float distanceY = mLastP.y - ev.getRawY();
                if (Math.abs(distanceY) > mScaledTouchSlop && Math.abs(distanceY) > Math.abs(distanceX)) {
                    break;
                }

                scrollBy((int) (distanceX), 0);//scrollBy
                if (getScrollX() < 0) {
                    if (!mCanRightSwipe || mLeftView == null) {
                        scrollTo(0, 0);
                    } else {
                        if (getScrollX() < mLeftView.getLeft()) {

                            scrollTo(mLeftView.getLeft(), 0);
                        }

                    }
                } else if (getScrollX() > 0) {
                    if (!mCanLeftSwipe || mRightView == null) {
                        scrollTo(0, 0);
                    } else {
                        if (getScrollX() > mRightView.getRight() - mContentView.getRight() - mContentViewLp.rightMargin) {
                            scrollTo(mRightView.getRight() - mContentView.getRight() - mContentViewLp.rightMargin, 0);
                        }
                    }
                }
                if (Math.abs(distanceX) > mScaledTouchSlop
                ) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                mLastP.set(ev.getRawX(), ev.getRawY());


                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                finalDistanceX = mFirstP.x - ev.getRawX();
                if (Math.abs(finalDistanceX) > mScaledTouchSlop) {

                    isSwiping = true;
                }
                result = isShouldOpen(getScrollX());
                handlerSwipeMenu(result);


                break;
            }
            default: {
                break;
            }
        }

        return super.dispatchTouchEvent(ev);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (Math.abs(finalDistanceX) > mScaledTouchSlop) {
                    return true;
                }
                break;

            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                if (isSwiping) {
                    isSwiping = false;
                    finalDistanceX = 0;
                    return true;
                }
            }

        }
        return super.onInterceptTouchEvent(event);
    }

    private void handlerSwipeMenu(State result) {
        if (result == State.LEFTOPEN) {
            mScroller.startScroll(getScrollX(), 0, mLeftView.getLeft() - getScrollX(), 0);
            mViewCache = this;
            mStateCache = result;
            if (swipeListener != null) {
                swipeListener.onLeftMenuOpen();
            }
        } else if (result == State.RIGHTOPEN) {
            mViewCache = this;
            mScroller.startScroll(getScrollX(), 0, mRightView.getRight() - mContentView.getRight() - mContentViewLp.rightMargin - getScrollX(), 0);
            mStateCache = result;
            if (swipeListener != null) {
                swipeListener.onRightMenuOpen();
            }
        } else {
            mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0);
            mViewCache = null;
            mStateCache = null;
        }
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    private State isShouldOpen(int scrollX) {
        if (!(mScaledTouchSlop < Math.abs(finalDistanceX))) {
            return mStateCache;
        }
        Log.i(TAG, ">>>finalyDistanceX:" + finalDistanceX);
        if (finalDistanceX < 0) {
            if (getScrollX() < 0 && mLeftView != null) {
                if (Math.abs(mLeftView.getWidth() * mFraction) < Math.abs(getScrollX())) {
                    return State.LEFTOPEN;
                }
            }

            if (getScrollX() > 0 && mRightView != null) {
                return State.CLOSE;
            }
        } else if (finalDistanceX > 0) {
            if (getScrollX() > 0 && mRightView != null) {

                if (Math.abs(mRightView.getWidth() * mFraction) < Math.abs(getScrollX())) {
                    return State.RIGHTOPEN;
                }

            }
            if (getScrollX() < 0 && mLeftView != null) {
                return State.CLOSE;
            }
        }

        return State.CLOSE;

    }

    @Override
    protected void onDetachedFromWindow() {
        if (this == mViewCache) {
            mViewCache.handlerSwipeMenu(CLOSE);
        }
        super.onDetachedFromWindow();

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this == mViewCache) {
            mViewCache.handlerSwipeMenu(mStateCache);
        }
    }

    public void resetStatus() {
        if (mViewCache != null) {
            if (mStateCache != null && mStateCache != CLOSE && mScroller != null) {
                mScroller.startScroll(mViewCache.getScrollX(), 0, -mViewCache.getScrollX(), 0);
                mViewCache.invalidate();
                mViewCache = null;
                mStateCache = null;
            }
        }
    }

    public float getFraction() {
        return mFraction;
    }

    public void setFraction(float mFraction) {
        this.mFraction = mFraction;
    }

    public boolean isCanLeftSwipe() {
        return mCanLeftSwipe;
    }

    public void setCanLeftSwipe(boolean mCanLeftSwipe) {
        this.mCanLeftSwipe = mCanLeftSwipe;
    }

    public boolean isCanRightSwipe() {
        return mCanRightSwipe;
    }

    public void setCanRightSwipe(boolean mCanRightSwipe) {
        this.mCanRightSwipe = mCanRightSwipe;
    }

    public void setMenuSwipeListener(SwipeMenuListener listener) {
        this.swipeListener = listener;
    }

    private boolean isLeftToRight() {
        return distanceX < 0;

    }


}
