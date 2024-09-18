package com.austin.duckwalking

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.ViewTreeObserver
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 오리 캐릭터 ImageView 가져오기
        val duckImageView = findViewById<ImageView>(R.id.duckImageView)

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
                    duration = 5000 // 3초 동안 이동
                    interpolator = LinearInterpolator() // 일정 속도로 이동
                    repeatCount = ObjectAnimator.INFINITE // 무한 반복
                    repeatMode = ObjectAnimator.RESTART // 끝에 도달하면 처음으로 돌아가 다시 시작
                }

                // 애니메이션 시작
                animator.start()
            }
        })
    }
}
