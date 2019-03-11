package ir.sharif.uicomponents;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import java.util.HashMap;

public class Theme {

    public static Paint checkboxSquare_backgroundPaint;
    public static Paint checkboxSquare_eraserPaint;
    public static Paint checkboxSquare_checkPaint;

    public static final String key_dialogCheckboxSquareDisabled = "dialogCheckboxSquareDisabled";
    public static final String key_dialogCheckboxSquareUnchecked = "dialogCheckboxSquareUnchecked";
    public static final String key_checkboxSquareUnchecked = "checkboxSquareUnchecked";
    public static final String key_dialogCheckboxSquareBackground = "dialogCheckboxSquareBackground";
    public static final String key_checkboxSquareBackground = "checkboxSquareBackground";
    public static final String key_checkboxSquareDisabled = "checkboxSquareDisabled";
    public static final String key_dialogCheckboxSquareCheck = "dialogCheckboxSquareCheck";
    public static final String key_checkboxSquareCheck = "checkboxSquareCheck";

    private static HashMap<String, Integer> defaultColors = new HashMap<>();

    static {
        defaultColors.put(key_dialogCheckboxSquareDisabled, 0xffb0b0b0);
        defaultColors.put(key_dialogCheckboxSquareUnchecked, 0xff737373);
        defaultColors.put(key_checkboxSquareUnchecked, 0xff737373);
        defaultColors.put(key_dialogCheckboxSquareBackground, 0xff43a0df);
        defaultColors.put(key_checkboxSquareBackground, 0xff43a0df);
        defaultColors.put(key_checkboxSquareDisabled, 0xffb0b0b0);
        defaultColors.put(key_dialogCheckboxSquareCheck, 0xffffffff);
        defaultColors.put(key_checkboxSquareCheck, 0xffffffff);
    }

    public static int getColor(String key) {
        return getDefaultColor(key);
    }

    public static int getDefaultColor(String key) {
        Integer value = defaultColors.get(key);
        if (value == null) {
            return 0xffff0000;
        }
        return value;
    }

    public static void createCommonResources(Context context) {
//        if (dividerPaint == null) {
//            dividerPaint = new Paint();
//            dividerPaint.setStrokeWidth(1);
//
//            avatar_backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//
        checkboxSquare_checkPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        checkboxSquare_checkPaint.setStyle(Paint.Style.STROKE);
        checkboxSquare_checkPaint.setStrokeWidth(AndroidUtilities.dp(2));
        checkboxSquare_eraserPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        checkboxSquare_eraserPaint.setColor(0);
        checkboxSquare_eraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        checkboxSquare_backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

//            linkSelectionPaint = new Paint();
//
//            Resources resources = context.getResources();
//
//            avatar_broadcastDrawable = resources.getDrawable(R.drawable.broadcast_w);
//            avatar_savedDrawable = resources.getDrawable(R.drawable.bookmark_large);
//
//            applyCommonTheme();
    }
}
