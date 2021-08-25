package com.example.animaciones

import android.animation.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.CycleInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Toast
import com.example.animaciones.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBarrel.setOnClickListener{
            barrelRoll()
        }

        binding.btnSin.setOnClickListener {
            esquivar()
        }

        binding.btnAlpha.setOnClickListener {
            blink()
        }

        binding.btnTiny.setOnClickListener {
            shink()
        }

        binding.btnStart.setOnClickListener {
            goToStart()
        }

        binding.btnPivot.setOnClickListener {
            pivot()
        }
    }

    private fun barrelRoll(){
        //rotacion inicial es 0 y rota hasta 72
        val valueAnimation = ValueAnimator.ofFloat(0f,720f)

        valueAnimation.addUpdateListener {
            val value = it.animatedValue as Float
            //ROTA
            //binding.arwing.rotation = value
            //ROTA CON RESPECTO AL EJE Y
            binding.arwing.rotationY = value
        }

        valueAnimation.duration = 1000
        //valueAnimation.interpolator = LinearInterpolator()
        valueAnimation.interpolator = AccelerateDecelerateInterpolator()
        valueAnimation.start()
    }

    private fun esquivar() {
        /*ObjectAnimator.ofFloat(binding.arwing, "translationX", 200f).apply {
            duration = 3000
            interpolator = CycleInterpolator(1f)
            start()
        }*/

        AnimatorInflater.loadAnimator(this, R.animator.esquivar).apply{
            setTarget(binding.arwing)
            start()
        }


    }

    private fun blink(){
        AnimatorInflater.loadAnimator(this, R.animator.blinking).apply {
            setTarget(binding.arwing)

            addListener(object: Animator.AnimatorListener{
                override fun onAnimationStart(p0: Animator?) {
                    Toast.makeText(applicationContext, "iniciando Parpadeo", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAnimationEnd(p0: Animator?) {
                    Toast.makeText(applicationContext, "Terminando Parpadeo", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAnimationCancel(p0: Animator?) {
                    Toast.makeText(applicationContext, "Cancelando Parpadeo", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAnimationRepeat(p0: Animator?) {
                    Toast.makeText(applicationContext, "Repitiendo Parpadeo", Toast.LENGTH_SHORT)
                        .show()
                }

            })
            start()
        }

    }

    private fun shink(){
        AnimatorInflater.loadAnimator(this, R.animator.shink).apply {
            setTarget(binding.arwing)
            start()
        }

    }

    private fun goToStart(){
        binding.arwing.animate().apply {
            translationX(0f)
            translationY(0f)
            duration = 1000
            interpolator = AccelerateDecelerateInterpolator()
            start()

        }
    }

    private fun pivot(){
        val initPivotX = PropertyValuesHolder.ofFloat("pivotX", 0f)
        val initPivotY = PropertyValuesHolder.ofFloat("pivotY", 0f)
        val transparent = PropertyValuesHolder.ofFloat("alpha", 0.6f)
        val animation1 = ObjectAnimator.ofPropertyValuesHolder(
            binding.arwing,
            initPivotX,
            initPivotY,
            transparent
        )
        animation1.duration = 500

        val pivotCenterX = binding.arwing.width.toFloat() / 2f
        val pivotCenterY = binding.arwing.height.toFloat() / 2f

        val centerPivotX = PropertyValuesHolder.ofFloat("pivotX", pivotCenterX)
        val centerPivotY = PropertyValuesHolder.ofFloat("pivotY", pivotCenterY)
        val opacy = PropertyValuesHolder.ofFloat("alpha", 1f)
        val animation2 = ObjectAnimator.ofPropertyValuesHolder(
            binding.arwing,
            centerPivotX,
            centerPivotY,
            opacy
        )
        animation2.duration = 500
        animation2.startDelay = 4000

        AnimatorSet().apply {
            playSequentially(animation1,animation2)
            start()
        }
    }
}