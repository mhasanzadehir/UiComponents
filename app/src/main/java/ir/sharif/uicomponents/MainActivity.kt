package ir.sharif.uicomponents

import android.animation.Animator
import android.animation.ObjectAnimator
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.widget.ImageView
import ir.sharif.uicomponents.components.AnimatedArrowDrawable
import ir.sharif.uicomponents.components.CheckBox
import ir.sharif.uicomponents.components.CheckBoxSquare
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var arrowUp = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidUtilities.checkDisplaySize(applicationContext.applicationContext, resources.configuration)

        val checkBox = CheckBox.Builder(this)
            .size(30)
            .checkOffset(CheckBox.DEFAULT_CHECK_OFFSET)
            .backgroundColor(CheckBox.DEFAULT_BACKGROUND)
            .checkColor(CheckBox.DEFAULT_CHECK_COLOR)
            .hasBorder(false)
            .isChecked(false)
            .drawBackground(true)
            .checkListener { isChecked ->
                Log.i("TAG", "CheckBox-isChecked:$isChecked")
            }
            .build()

        rootLayout.addView(
            checkBox,
            LayoutHelper.createFrame(30, 30, Gravity.TOP or Gravity.LEFT, 15F, 15F, 0F, 0F)
        )

        //////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////

        val checkBoxSquare = CheckBoxSquare.Builder(this)
            .isChecked(false)
            .isDisabled(false)
            .squareBackground(CheckBoxSquare.DEFAULT_SQUARE_BACKGROUND)
            .squareCheckedColor(CheckBoxSquare.DEFAULT_SQUARE_CHECKED_COLOR)
            .squareDisabledColor(CheckBoxSquare.SQUARE_DISABLED_COLOR)
            .squareUncheckedColor(CheckBoxSquare.DEFAULT_SQUARE_UNCHECKED_COLOR)
            .checkListener { isChecked ->
                Log.i("TAG", "checkBoxSquare-isChecked:$isChecked")
            }
            .build()

        rootLayout.addView(
            checkBoxSquare,
            LayoutHelper.createFrame(30, 30, Gravity.TOP or Gravity.LEFT, 20F, 60F, 0F, 0F)
        )

        //////////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////

        val arrowDrawable = AnimatedArrowDrawable(0xffffffff.toInt(), false)
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER
        imageView.setImageDrawable(arrowDrawable)
        imageView.colorFilter = PorterDuffColorFilter(
            Theme.getColor(Theme.key_chats_actionUnreadIcon),
            PorterDuff.Mode.MULTIPLY
        )
        imageView.setPadding(0, AndroidUtilities.dp(4f), 0, 0)
        rootLayout.addView(
            imageView,
            LayoutHelper.createFrame(30, 30, Gravity.TOP or Gravity.LEFT, 20F, 100F, 0F, 0F)
        )
        arrowDrawable.animationProgress = 1.0f

        imageView.setOnClickListener {
            val anim = ObjectAnimator.ofFloat(arrowDrawable, "animationProgress", if (arrowUp) 1F else 0F)
            anim.duration = 400
            anim.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(p0: Animator?) {
                }

                override fun onAnimationEnd(p0: Animator?) {
                    arrowUp = arrowDrawable.animationProgress == 0F
                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationStart(p0: Animator?) {
                }

            })
            anim.start()
        }

    }
}
