package ir.sharif.uicomponents

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import ir.sharif.uicomponents.components.CheckBox
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import ir.sharif.uicomponents.components.AnimatedArrowDrawable
import ir.sharif.uicomponents.components.CheckBoxSquare
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.support.v4.view.ViewCompat.setLayerType
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.support.v4.view.ViewCompat.getTranslationY
import android.animation.ObjectAnimator
import android.animation.AnimatorSet




class MainActivity : AppCompatActivity() {

    var arrowUp = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        AndroidUtilities.checkDisplaySize(applicationContext.applicationContext, resources.configuration)
        val checkBox = CheckBox(this, R.drawable.checkbig)
        checkBox.setSize(30)
        checkBox.setCheckOffset(AndroidUtilities.dp(1f))
        checkBox.setDrawBackground(true)
        checkBox.setColor(-0xc33511, -0x1)
        rootLayout.addView(checkBox, LayoutHelper.createFrame(30, 30, Gravity.TOP or Gravity.LEFT, 15F, 15F, 0F, 0F))
        checkBox.visibility = View.VISIBLE
        checkBox.setOnClickListener {
            checkBox.setChecked(!checkBox.isChecked, true)
        }


        val checkBoxSquare = CheckBoxSquare(this, true)
        rootLayout.addView(
            checkBoxSquare,
            LayoutHelper.createFrame(30, 30, Gravity.TOP or Gravity.LEFT, 20F, 60F, 0F, 0F)
        )
        checkBoxSquare.setOnClickListener {
            checkBoxSquare.setChecked(!checkBoxSquare.isChecked, true)
        }

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
            anim.addListener(object : Animator.AnimatorListener{
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
