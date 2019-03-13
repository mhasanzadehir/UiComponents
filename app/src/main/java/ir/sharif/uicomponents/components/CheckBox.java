package ir.sharif.uicomponents.components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.support.annotation.Keep;
import android.text.TextPaint;
import android.view.View;
import ir.sharif.uicomponents.AndroidUtilities;
import ir.sharif.uicomponents.R;

public class CheckBox extends View {

    public static final int DEFAULT_CHECK_COLOR = -0x1;
    public static final int CHECK_RESOURCE = R.drawable.checkbig;
    public static final int DEFAULT_BACKGROUND = -0xc33511;
    public static final int DEFAULT_CHECK_OFFSET = AndroidUtilities.dp(1f);
    public static final int DEFAULT_SIZE = 22;

    private Drawable checkDrawable;
    private static Paint paint;
    private static Paint eraser;
    private static Paint eraser2;
    private static Paint backgroundPaint;
    private TextPaint textPaint;

    private Bitmap drawBitmap;
    private Bitmap checkBitmap;
    private Canvas bitmapCanvas;
    private Canvas checkCanvas;

    private boolean drawBackground = true;
    private boolean hasBorder = false;

    private float progress;
    private ObjectAnimator checkAnimator;
    private boolean isCheckAnimation = true;

    private boolean attachedToWindow;
    private boolean isChecked;

    private int size = DEFAULT_SIZE;
    private int checkOffset = DEFAULT_CHECK_OFFSET;
    private int color = DEFAULT_BACKGROUND;
    private String checkedText;

    private CheckBoxListener listener;

    private final static float progressBounceDiff = 0.2f;

    public CheckBox(Context context) {
        super(context);
        if (paint == null) {
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            eraser = new Paint(Paint.ANTI_ALIAS_FLAG);
            eraser.setColor(0);
            eraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            eraser2 = new Paint(Paint.ANTI_ALIAS_FLAG);
            eraser2.setColor(0);
            eraser2.setStyle(Paint.Style.STROKE);
            eraser2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            backgroundPaint.setColor(0xffffffff);
            backgroundPaint.setStyle(Paint.Style.STROKE);
        }
        eraser2.setStrokeWidth(AndroidUtilities.dp(28));
        backgroundPaint.setStrokeWidth(AndroidUtilities.dp(2));

        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(AndroidUtilities.dp(18));
        textPaint.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        textPaint.setColor(DEFAULT_CHECK_COLOR);

        checkDrawable = context.getResources().getDrawable(CHECK_RESOURCE).mutate();
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == VISIBLE && drawBitmap == null) {
            try {
                //android document point that ARGB_8888 is better than 4444. ARGB_4444 deprecated after API13.
                drawBitmap = Bitmap.createBitmap(AndroidUtilities.dp(size), AndroidUtilities.dp(size), Bitmap.Config.ARGB_8888);
                bitmapCanvas = new Canvas(drawBitmap);
                checkBitmap = Bitmap.createBitmap(AndroidUtilities.dp(size), AndroidUtilities.dp(size), Bitmap.Config.ARGB_8888);
                checkCanvas = new Canvas(checkBitmap);
            } catch (Throwable ignore) {

            }
        }
    }

    @Keep
    public void setProgress(float value) {
        if (progress == value) {
            return;
        }
        progress = value;
        invalidate();
    }

    public void setDrawBackground(boolean value) {
        drawBackground = value;
    }

    public void setHasBorder(boolean value) {
        hasBorder = value;
    }

    public void setCheckOffset(int value) {
        checkOffset = value;
    }

    public void setSize(int size) {
        this.size = size;
        if (size == 40) {
            textPaint.setTextSize(AndroidUtilities.dp(24));
        }
    }

    public float getProgress() {
        return progress;
    }

    public void setColor(int backgroundColor, int checkColor) {
        color = backgroundColor;
        checkDrawable.setColorFilter(new PorterDuffColorFilter(checkColor, PorterDuff.Mode.MULTIPLY));
        textPaint.setColor(checkColor);
        invalidate();
    }

    public void setBackgroundColor(int backgroundColor) {
        color = backgroundColor;
        invalidate();
    }

    public void setCheckColor(int checkColor) {
        checkDrawable.setColorFilter(new PorterDuffColorFilter(checkColor, PorterDuff.Mode.MULTIPLY));
        textPaint.setColor(checkColor);
        invalidate();
    }

    private void cancelCheckAnimator() {
        if (checkAnimator != null) {
            checkAnimator.cancel();
            checkAnimator = null;
        }
    }

    private void animateToCheckedState(boolean newCheckedState) {
        isCheckAnimation = newCheckedState;
        checkAnimator = ObjectAnimator.ofFloat(this, "progress", newCheckedState ? 1 : 0);
        checkAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (animation.equals(checkAnimator)) {
                    checkAnimator = null;
                }
                if (!isChecked) {
                    checkedText = null;
                }
            }
        });
        checkAnimator.setDuration(300);
        checkAnimator.start();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        attachedToWindow = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        attachedToWindow = false;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public void setChecked(boolean checked, boolean animated) {
        setChecked(-1, checked, animated);
    }

    public void setNum(int num) {
        if (num >= 0) {
            checkedText = "" + (num + 1);
        } else if (checkAnimator == null) {
            checkedText = null;
        }
        invalidate();
    }

    public void setChecked(int num, boolean checked, boolean animated) {
        if (num >= 0) {
            checkedText = "" + (num + 1);
            invalidate();
        }
        if (checked == isChecked) {
            return;
        }
        isChecked = checked;

        if (attachedToWindow && animated) {
            animateToCheckedState(checked);
        } else {
            cancelCheckAnimator();
            setProgress(checked ? 1.0f : 0.0f);
        }
    }

    public boolean isChecked() {
        return isChecked;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getVisibility() != VISIBLE || drawBitmap == null || checkBitmap == null) {
            return;
        }
        if (drawBackground || progress != 0) {
            eraser2.setStrokeWidth(AndroidUtilities.dp(size + 6));

            drawBitmap.eraseColor(0);
            float rad = getMeasuredWidth() / 2;

            float roundProgress = progress >= 0.5f ? 1.2f : progress / 0.5f;
            float checkProgress = progress < 0.5f ? 0.0f : (progress - 0.5f) / 0.5f;

            float roundProgressCheckState = isCheckAnimation ? progress : (1.0f - progress);
            if (roundProgressCheckState < progressBounceDiff) {
                rad -= AndroidUtilities.dp(2) * roundProgressCheckState / progressBounceDiff;
            } else if (roundProgressCheckState < progressBounceDiff * 2) {
                rad -= AndroidUtilities.dp(2) - AndroidUtilities.dp(2) * (roundProgressCheckState - progressBounceDiff) / progressBounceDiff;
            }
            if (drawBackground) {
                paint.setColor(0x44000000);
                canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, rad - AndroidUtilities.dp(1), paint);
                canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, rad - AndroidUtilities.dp(1), backgroundPaint);
            }

            paint.setColor(color);

            if (hasBorder) {
                rad -= AndroidUtilities.dp(2);
            }
            bitmapCanvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, rad, paint);
            bitmapCanvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, rad * (1 - roundProgress), eraser);
            canvas.drawBitmap(drawBitmap, 0, 0, null);

            checkBitmap.eraseColor(0);
            if (checkedText != null) {
                int w = (int) Math.ceil(textPaint.measureText(checkedText));
                checkCanvas.drawText(checkedText, (getMeasuredWidth() - w) / 2, AndroidUtilities.dp(size == 40 ? 28 : 21), textPaint);
            } else {
                int w = checkDrawable.getIntrinsicWidth();
                int h = checkDrawable.getIntrinsicHeight();
                int x = (getMeasuredWidth() - w) / 2;
                int y = (getMeasuredHeight() - h) / 2;

                checkDrawable.setBounds(x, y + checkOffset, x + w, y + h + checkOffset);
                checkDrawable.draw(checkCanvas);
            }
            checkCanvas.drawCircle(getMeasuredWidth() / 2 - AndroidUtilities.dp(2.5f), getMeasuredHeight() / 2 + AndroidUtilities.dp(4), ((getMeasuredWidth() + AndroidUtilities.dp(6)) / 2) * (1 - checkProgress), eraser2);

            canvas.drawBitmap(checkBitmap, 0, 0, null);
        }
    }

    public void setListener(CheckBoxListener listener) {
        this.listener = listener;
    }

    public interface CheckBoxListener {
        void checkChanged(boolean isChecked);
    }

    public static final class Builder {
        private CheckBox checkBox;

        public Builder(Context context) {
            checkBox = new CheckBox(context);
        }

        public Builder size(int size) {
            checkBox.setSize(size);
            return this;
        }

        public Builder checkOffset(int dp) {
            checkBox.setCheckOffset(AndroidUtilities.dp(dp));
            return this;
        }

        public Builder backgroundColor(int color) {
            checkBox.setBackgroundColor(color);
            return this;
        }

        public Builder checkColor(int color) {
            checkBox.setCheckColor(color);
            return this;
        }

        public Builder hasBorder(boolean has) {
            checkBox.setHasBorder(has);
            return this;
        }

        public Builder isChecked(boolean is) {
            checkBox.setChecked(is, false);
            return this;
        }

        public Builder drawBackground(boolean value) {
            checkBox.setDrawBackground(value);
            return this;
        }

        public Builder checkListener(CheckBoxListener listener) {
            checkBox.setListener(listener);
            return this;
        }

        public CheckBox build() {
            checkBox.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkBox.setChecked(!checkBox.isChecked, true);
                    checkBox.listener.checkChanged(checkBox.isChecked);
                }
            });
            checkBox.setVisibility(VISIBLE);
            return checkBox;
        }


    }

}