package com.example.stopwatch

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    //一度だけ代入はval
    val handler=Handler()
    //くり返し代入はvar
    var timeValue=0
    var status = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //view要素を変数に代入
        val timeText=findViewById(R.id.timeText) as TextView
        val startButton=findViewById(R.id.start) as Button
        val stopButton=findViewById(R.id.stop) as Button
        val resetButton=findViewById(R.id.reset) as Button

        //1秒ごとに処理を実行
        val runnable=object:Runnable{
            override fun run() {
                timeValue++

                //TextViewを更新、?.letを用いてnullではない場合のみ更新
                timeToText(timeValue)?.let{
                    //timeToText(timeValue)の値がlet内ではitとして使える
                    timeText.text= it
                }

                handler.postDelayed(this,1000)
            }
        }

        //startボタンを押した時の処理
        startButton.setOnClickListener {
            if(status == 0) {
                //handlerクラスのインスタンスのpostメソッド
                handler.post(runnable)
                //startButton.setEnabled(false)
                status = 1
            }

        }

        //そう言う事

        stopButton.setOnClickListener{
            handler.removeCallbacks(runnable)
            status=0

        }

        //reset
        resetButton.setOnClickListener {
            handler.removeCallbacks(runnable)
            status = 0
            timeValue=0
            timeToText()?.let{
                timeText.text=it
            }
        }


    }

    //数値を00:00:00形式の文字列に変換する関数
    //引数timeにはデフォルト値0を設定、返却する型はnullableならString?型
    private fun timeToText(time:Int=0):String? {
        //if式は値を返すため、そのままreturnできる
        return if (time < 0){
            null
        }else if(time==0){
            "00:00:00"
        }else{
            val h=time/3600
            val m=time%3600/60
            val s=time%60
            "%1$02d:%2$02d:%3$02d".format(h,m,s)
        }

    }

}
