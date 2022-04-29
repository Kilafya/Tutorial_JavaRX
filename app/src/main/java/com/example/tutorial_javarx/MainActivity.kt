package com.example.tutorial_javarx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    lateinit var btn: Button
    lateinit var counterBtn: TextView
    lateinit var counterLog: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn = findViewById(R.id.btn)
        counterBtn = findViewById(R.id.counter_btn)
        counterLog = findViewById(R.id.counter_log)

        btn.setOnClickListener {
            val count = counterBtn.text.toString().toInt() + 1
            counterBtn.text = count.toString()
        }

        initObservable()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                        counterLog.text = it.toString()
                },
                {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                },
                {
                    Toast.makeText(applicationContext, "End", Toast.LENGTH_LONG).show()
                })
    }

    private fun initObservable(): Observable<Int> = Observable.create { subscriber ->
        for (i in 0..100) {
            Thread.sleep(1000)
            subscriber.onNext(i)
        }
    }
}
