package ir.sharif.uicomponents

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import ir.sharif.uicomponents.components.CheckBox
import android.view.Gravity
import android.view.View
import ir.sharif.uicomponents.components.CheckBoxSquare
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        AndroidUtilities.checkDisplaySize(applicationContext.applicationContext, resources.configuration)
        val checkBox = CheckBox(this, R.drawable.checkbig)
        checkBox.setSize(30)
        checkBox.setCheckOffset(AndroidUtilities.dp(1f))
        checkBox.setDrawBackground(true)
        checkBox.setColor(-0xc33511, -0x1)
        rootLayout.addView(checkBox, LayoutHelper.createFrame(30,30,Gravity.TOP or Gravity.LEFT, 15F,15F,0F,0F))
        checkBox.visibility = View.VISIBLE
        checkBox.setOnClickListener {
            if (checkBox.isChecked) {
                checkBox.setChecked(false, true)
            } else {
                checkBox.setChecked(true, true)
            }
        }


        val checkBoxSquare = CheckBoxSquare(this, true)
        rootLayout.addView(checkBoxSquare, LayoutHelper.createFrame(30,30,Gravity.TOP or Gravity.LEFT, 20F,60F,0F,0F))
        checkBoxSquare.setOnClickListener {
            if (checkBoxSquare.isChecked){
                checkBoxSquare.setChecked(false, true)
            } else{
                checkBoxSquare.setChecked(true, true)
            }
        }

    }
}
