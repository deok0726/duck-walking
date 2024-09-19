package com.austin.duckwalking

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewTreeObserver
import android.view.Window
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    // 오리 이미지 배열
    private val duckImages = arrayOf(
        R.drawable.duck,
        R.drawable.duck1,
        R.drawable.duck2,
        R.drawable.duck3,
        R.drawable.duck4 // 필요에 따라 더 추가 가능
    )

    @SuppressLint("WrongViewCast", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)

        // 오리 캐릭터 ImageView 가져오기
        val duckImageView = findViewById<ImageView>(R.id.duckImageView)

        // 초기 오리 이미지를 설정
        duckImageView.setImageResource(duckImages[0]) // 첫 번째 이미지를 기본으로 설정

        // 뷰가 레이아웃에 모두 그려진 후 애니메이션을 실행해야 하기 때문에 ViewTreeObserver 사용
        val viewTreeObserver = duckImageView.viewTreeObserver
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                // ViewTreeObserver 리스너 제거 (중복 실행 방지)
                duckImageView.viewTreeObserver.removeOnGlobalLayoutListener(this)

                // 화면 너비 가져오기
                val screenWidth = duckImageView.rootView.width

                // 오리 캐릭터의 초기 위치를 왼쪽 끝으로 설정
                duckImageView.translationX = -duckImageView.width.toFloat()

                // 오리 캐릭터가 화면의 왼쪽 끝에서 오른쪽 끝까지 이동하는 애니메이션
                val animator = ObjectAnimator.ofFloat(duckImageView, "translationX", -duckImageView.width.toFloat(), screenWidth.toFloat()).apply {
                    duration = 5000 // 5초 동안 이동
                    interpolator = LinearInterpolator() // 일정 속도로 이동
                    repeatCount = ObjectAnimator.INFINITE // 무한 반복
                    repeatMode = ObjectAnimator.RESTART // 끝에 도달하면 처음으로 돌아가 다시 시작
                }

                // 애니메이션 시작
                animator.start()
            }
        })

        // 전체 화면을 터치했을 때 이미지 랜덤 변경
        val rootView = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.root_layout)
        rootView.setOnClickListener {
            // 랜덤한 이미지를 선택하여 설정
            val randomImage = duckImages[Random.nextInt(duckImages.size)]
            duckImageView.setImageResource(randomImage)
        }
    }
}
