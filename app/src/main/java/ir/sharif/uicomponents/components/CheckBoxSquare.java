package ir.sharif.uicomponents.components;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.*;
import android.support.annotation.Keep;
import android.view.View;
import ir.sharif.uicomponents.AndroidUtilities;

public class CheckBoxSquare extends View {

    public static final int DEFAULT_SQUARE_UNCHECKED_COLOR = 0xff737373;
    public static final int DEFAULT_SQUARE_CHECKED_COLOR = 0xffffffff;
    public static final int DEFAULT_SQUARE_BACKGROUND = 0xff43a0df;
    public static final int SQUARE_DISABLED_COLOR = 0xffb0b0b0;

    public static Paint backgroundPaint;
    public static Paint eraserPaint;
    public static Paint checkPaint;

    private RectF rectF;

    private Bitmap drawBitmap;
    private Canvas drawCanvas;

    private float progress;
    private ObjectAnimator checkAnimator;

    private boolean attachedToWindow;
    private boolean isChecked;
    private boolean isDisabled;

    private int squareUncheckedColor = DEFAULT_SQUARE_UNCHECKED_COLOR;
    private int squareCheckedColor = DEFAULT_SQUARE_CHECKED_COLOR;
    private int squareBackground = DEFAULT_SQUARE_BACKGROUND;
    private int squareDisabledColor = SQUARE_DISABLED_COLOR;

    private CheckBoxSquareListener listener;

    public CheckBoxSquare(Context context) {
        super(context);
        if (backgroundPaint == null) {
            checkPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            checkPaint.setStyle(Paint.Style.STROKE);
            checkPaint.setStrokeWidth(AndroidUtilities.dp(2));
            eraserPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            eraserPaint.setColor(0);
            eraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        }
        rectF = new RectF();
        drawBitmap = Bitmap.createBitmap(AndroidUtilities.dp(18), AndroidUtilities.dp(18), Bitmap.Config.ARGB_4444);
        drawCanvas = new Canvas(drawBitmap);
    }

    @Keep
    public void setProgress(float value) {
        if (progress == value) {
            return;
        }
        progress = value;
        invalidate();
    }

    public float getProgress() {
        return progress;
    }

    private void cancelCheckAnimator() {
        if (checkAnimator != null) {
            checkAnimator.cancel();
        }
    }

    private void animateToCheckedState(boolean newCheckedState) {
        checkAnimator = ObjectAnimator.ofFloat(this, "progress", newCheckedState ? 1 : 0);
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

    public void setDisabled(boolean disabled) {
        isDisabled = disabled;
        invalidate();
    }

    public boolean isChecked() {
        return isChecked;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getVisibility() != VISIBLE) {
            return;
        }

        float checkProgress;
        float bounceProgress;
        if (progress <= 0.5f) {
            bounceProgress = checkProgress = progress / 0.5f;
            int rD = (int) ((Color.red(squareBackground) - Color.red(squareUncheckedColor)) * checkProgress);
            int gD = (int) ((Color.green(squareBackground) - Color.green(squareUncheckedColor)) * checkProgress);
            int bD = (int) ((Color.blue(squareBackground) - Color.blue(squareUncheckedColor)) * checkProgress);
            int c = Color.rgb(Color.red(squareUncheckedColor) + rD, Color.green(squareUncheckedColor) + gD, Color.blue(squareUncheckedColor) + bD);
            backgroundPaint.setColor(c);
        } else {
            bounceProgress = 2.0f - progress / 0.5f;
            checkProgress = 1.0f;
            backgroundPaint.setColor(squareBackground);
        }
        if (isDisabled) {
            backgroundPaint.setColor(squareDisabledColor);
        }
        float bounce = AndroidUtilities.dp(1) * bounceProgress;
        rectF.set(bounce, bounce, AndroidUtilities.dp(18) - bounce, AndroidUtilities.dp(18) - bounce);

        drawBitmap.eraseColor(0);
        drawCanvas.drawRoundRect(rectF, AndroidUtilities.dp(2), AndroidUtilities.dp(2), backgroundPaint);

        if (checkProgress != 1) {
            float rad = Math.min(AndroidUtilities.dp(7), AndroidUtilities.dp(7) * checkProgress + bounce);
            rectF.set(AndroidUtilities.dp(2) + rad, AndroidUtilities.dp(2) + rad, AndroidUtilities.dp(16) - rad, AndroidUtilities.dp(16) - rad);
            drawCanvas.drawRect(rectF, eraserPaint);
        }

        if (progress > 0.5f) {
            checkPaint.setColor(squareCheckedColor);
            int endX = (int) (AndroidUtilities.dp(7.5f) - AndroidUtilities.dp(5) * (1.0f - bounceProgress));
            int endY = (int) (AndroidUtilities.dpf2(13.5f) - AndroidUtilities.dp(5) * (1.0f - bounceProgress));
            drawCanvas.drawLine(AndroidUtilities.dp(7.5f), (int) AndroidUtilities.dpf2(13.5f), endX, endY, checkPaint);
            endX = (int) (AndroidUtilities.dpf2(6.5f) + AndroidUtilities.dp(9) * (1.0f - bounceProgress));
            endY = (int) (AndroidUtilities.dpf2(13.5f) - AndroidUtilities.dp(9) * (1.0f - bounceProgress));
            drawCanvas.drawLine((int) AndroidUtilities.dpf2(6.5f), (int) AndroidUtilities.dpf2(13.5f), endX, endY, checkPaint);
        }
        canvas.drawBitmap(drawBitmap, 0, 0, null);
    }

    public void setSquareUncheckedColor(int squareUncheckedColor) {
        this.squareUncheckedColor = squareUncheckedColor;
    }

    public void setSquareCheckedColor(int squareCheckedColor) {
        this.squareCheckedColor = squareCheckedColor;
    }

    public void setSquareBackground(int squareBackground) {
        this.squareBackground = squareBackground;
    }

    public void setSquareDisabledColor(int squareDisabledColor) {
        this.squareDisabledColor = squareDisabledColor;
    }

    public void setListener(CheckBoxSquareListener listener) {
        this.listener = listener;
    }

    public interface CheckBoxSquareListener {
        void checkChanged(boolean isChecked);
    }


    public static final class Builder {
        private CheckBoxSquare checkBoxSquare;

        public Builder(Context context) {
            checkBoxSquare = new CheckBoxSquare(context);
        }

        public Builder isChecked(boolean value) {
            checkBoxSquare.setChecked(value, false);
            return this;
        }

        public Builder isDisabled(boolean value) {
            checkBoxSquare.setDisabled(value);
            return this;
        }

        public Builder squareUncheckedColor(int value) {
            checkBoxSquare.setSquareUncheckedColor(value);
            return this;
        }

        public Builder squareCheckedColor(int value) {
            checkBoxSquare.setSquareCheckedColor(value);
            return this;
        }

        public Builder squareBackground(int value) {
            checkBoxSquare.setSquareBackground(value);
            return this;
        }

        public Builder squareDisabledColor(int value) {
            checkBoxSquare.setSquareDisabledColor(value);
            return this;
        }

        public Builder checkListener(CheckBoxSquareListener listener) {
            checkBoxSquare.setListener(listener);
            return this;
        }

        public CheckBoxSquare build() {
            checkBoxSquare.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkBoxSquare.setChecked(!checkBoxSquare.isChecked, true);
                    checkBoxSquare.listener.checkChanged(checkBoxSquare.isChecked);
                }
            });
            return checkBoxSquare;
        }
    }
}