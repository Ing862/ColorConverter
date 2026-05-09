package com.ingwoj.colorconverter

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.ingwoj.colorconverter.databinding.ActivityMainBinding
import kotlin.math.round
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // View binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val values = Values(0, 0, 0, 0f,0f,0f,0f)

        /** Protects slider and text input form updating each other in a loop.
         * If true - change was made, update the ui and displayed colour */
        var updating = false

        // RGB

        // R
        binding.sliderR.addOnChangeListener { _,  value, _ ->
            if (!updating){
                updating = true

                values.r = value.toInt()
                values.calcCMYK()

                updateEverything(values)

                updating = false
            }
        }
        binding.inputR.addTextChangedListener{ input ->
            if (!updating){
                updating = true

                val x = input.toString().toIntOrNull() ?: 0

                values.r = checkOutOfRange(x, "rgb")
                values.calcCMYK()

                updateEverything(values, "R")

                updating = false
            }
        }

        // G
        binding.sliderG.addOnChangeListener { _,  value, _ ->
            if (!updating){
                updating = true

                values.g = value.toInt()
                values.calcCMYK()

                updateEverything(values)

                updating = false
            }
        }
        binding.inputG.addTextChangedListener{ input ->
            if (!updating){
                updating = true

                val x = input.toString().toIntOrNull() ?: 0

                values.g = checkOutOfRange(x, "rgb")
                values.calcCMYK()

                updateEverything(values, "G")

                updating = false
            }
        }

        // B
        binding.sliderB.addOnChangeListener { _,  value, _ ->
            if (!updating){
                updating = true

                values.b = value.toInt()
                values.calcCMYK()

                updateEverything(values)

                updating = false
            }
        }
        binding.inputB.addTextChangedListener{ input ->
            if (!updating){
                updating = true

                val x = input.toString().toIntOrNull() ?: 0

                values.b = checkOutOfRange(x, "rgb")
                values.calcCMYK()

                updateEverything(values, "B")

                updating = false
            }
        }

        // CMYK

        // C
        binding.sliderC.addOnChangeListener { _,  value, _ ->
            if (!updating){
                updating = true

                values.c = value
                values.calcRGB()

                updateEverything(values)

                updating = false
            }
        }
        binding.inputC.addTextChangedListener{ input ->
            if (!updating){
                updating = true

                val x = input.toString().toIntOrNull() ?: 0

                values.c = checkOutOfRange(x, "cmyk").toFloat()
                values.calcRGB()

                updateEverything(values,"C")

                updating = false
            }
        }

        // M
        binding.sliderM.addOnChangeListener { _,  value, _ ->
            if (!updating){
                updating = true

                values.m = value
                values.calcRGB()

                updateEverything(values)

                updating = false
            }
        }
        binding.inputM.addTextChangedListener{ input ->
            if (!updating){
                updating = true

                val x = input.toString().toIntOrNull() ?: 0

                values.m = checkOutOfRange(x, "cmyk").toFloat()
                values.calcRGB()

                updateEverything(values, "M")

                updating = false
            }
        }

        // Y
        binding.sliderY.addOnChangeListener { _,  value, _ ->
            if (!updating){
                updating = true

                values.y = value
                values.calcRGB()

                updateEverything(values)

                updating = false
            }
        }
        binding.inputY.addTextChangedListener{ input ->
            if (!updating){
                updating = true

                val x = input.toString().toIntOrNull() ?: 0

                values.y = checkOutOfRange(x, "cmyk").toFloat()
                values.calcRGB()

                updateEverything(values, "Y")

                updating = false
            }
        }

        // K
        binding.sliderK.addOnChangeListener { _,  value, _ ->
            if (!updating){
                updating = true

                values.k = value
                values.calcRGB()

                updateEverything(values)

                updating = false
            }
        }
        binding.inputK.addTextChangedListener{ input ->
            if (!updating){
                updating = true

                val x = input.toString().toIntOrNull() ?: 0

                values.k = checkOutOfRange(x, "cmyk").toFloat()
                values.calcRGB()

                updateEverything(values, "K")

                updating = false
            }
        }
    }

    /**
     * Updates values displayed, sliders and displayed colour
     */
    fun updateEverything(values: Values, ignoreField: String? = null){
        updateValues(values, ignoreField)
        updateSliders(values)
        updateColorView(values)
    }

    /**
     * When one text input changed by the user, updates the rest with new calculated values
     * @param values all colour values
     * @param ignoreField letter of an input field which is ignored with changes of the UI
     */
    fun updateValues(values: Values, ignoreField: String? = null) {
        if (ignoreField != "R") binding.inputR.setText(values.r.toString())
        if (ignoreField != "G") binding.inputG.setText(values.g.toString())
        if (ignoreField != "B") binding.inputB.setText(values.b.toString())

        if (ignoreField != "C") binding.inputC.setText(values.c.roundToInt().toString())
        if (ignoreField != "M") binding.inputM.setText(values.m.roundToInt().toString())
        if (ignoreField != "Y") binding.inputY.setText(values.y.roundToInt().toString())
        if (ignoreField != "K") binding.inputK.setText(values.k.roundToInt().toString())
    }

    /**
     * Updates all sliders
     */
    fun updateSliders(values: Values) {
        binding.sliderR.value = round(values.r.toDouble()).toFloat()
        binding.sliderG.value = round(values.g.toDouble()).toFloat()
        binding.sliderB.value = round(values.b.toDouble()).toFloat()

        binding.sliderC.value = round(values.c.toDouble()).toFloat()
        binding.sliderM.value = round(values.m.toDouble()).toFloat()
        binding.sliderY.value = round(values.y.toDouble()).toFloat()
        binding.sliderK.value = round(values.k.toDouble()).toFloat()
    }

    /**
     * Updates displayed colour
     */
    fun updateColorView(values: Values) {
        binding.colorView.setBackgroundColor(Color.rgb(values.r, values.g, values.b))
    }

    /**
     * Checks if values added by user are in range
     */
    fun checkOutOfRange(input: Int, type: String): Int{
        when (type) {
            "rgb" -> if(input > 255) return 255 else if(input < 0) return 0
            "cmyk" -> if(input > 100) return 100 else if(input < 0) return 0
        }
        return input
    }
}