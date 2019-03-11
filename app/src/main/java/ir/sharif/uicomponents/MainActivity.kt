package ir.sharif.uicomponents

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import ir.sharif.uicomponents.components.CheckBox
import android.view.Gravity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val checkBox = CheckBox(this, R.drawable.checkbig)
        checkBox.setSize(100)
        checkBox.setCheckOffset(AndroidUtilities.dp(1f))
        checkBox.setDrawBackground(true)
        checkBox.setColor(-0xc33511, -0x1)
        rootLayout.addView(checkBox, LayoutHelper.createFrame(100, 100, Gravity.LEFT or Gravity.TOP, 46F, 4F, 0F, 0F));
        checkBox.visibility = View.VISIBLE
        checkBox.setOnClickListener {
            if (checkBox.isChecked) {
                checkBox.setChecked(false, true)
            } else {
                checkBox.setChecked(true, true)
            }
        }

    }
}
