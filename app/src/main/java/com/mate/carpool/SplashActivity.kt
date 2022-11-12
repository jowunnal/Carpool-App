package com.mate.carpool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //todo_sypark Handler() deprecated 된 소스 --표시가 보이 실텐데 일단 Loop.getMainLooper()로 해서 사용하시고 나중에 기회되시면 coroutine delay() 함수를 사용하시는게 직관적으로 보기 편할 듯합니다.
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }, 3000)    //todo_sypark 의미 없는 변수명 3초 지연시키고 실행시킨다는 것이기때문에 그냥 3000 이렇게 박아도 무방합니다.

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}