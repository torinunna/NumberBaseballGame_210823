package com.yujin.numberbaseballgame_210823

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.yujin.numberbaseballgame_210823.adapters.MessageAdapter
import com.yujin.numberbaseballgame_210823.datas.MessageData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val mMessageList = ArrayList<MessageData>()

    lateinit var mAdapter : MessageAdapter

    val mQuestionNumbers = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        makeQuestionNumbers()

        mAdapter = MessageAdapter(this, R.layout.message_list_item, mMessageList)

        messageListView.adapter = mAdapter

        okBtn.setOnClickListener {

            val inputNumStr = numberEdt.text.toString()

            val msg = MessageData( inputNumStr, "USER" )

            mMessageList.add(msg)

            mAdapter.notifyDataSetChanged()

            numberEdt.setText("")


            messageListView.smoothScrollToPosition( mMessageList.size - 1 )


            checkAnswer( inputNumStr.toInt() )

        }


    }

    fun checkAnswer(inputNum : Int) {


        val userInputNumArr = ArrayList<Int>()

        userInputNumArr.add(  inputNum / 100  )
        userInputNumArr.add( inputNum / 10 % 10 )
        userInputNumArr.add( inputNum % 10 )

        var strikeCount = 0
        var ballCount = 0

        for ( i   in 0..2  ) {

            for ( j   in 0..2 ) {

                if ( userInputNumArr[i] == mQuestionNumbers[j] ) {

                    if (i == j) {
                        strikeCount++
                    }
                    else {
                        ballCount++
                    }

                }

            }

        }

        mMessageList.add(  MessageData("${strikeCount}S ${ballCount}B 입니다.", "CPU")  )

        mAdapter.notifyDataSetChanged()

        messageListView.smoothScrollToPosition( mMessageList.size - 1 )

        if (strikeCount == 3) {
            mMessageList.add(  MessageData("축하합니다! 정답을 맞췄습니다.", "CPU")  )

            mAdapter.notifyDataSetChanged()

            messageListView.smoothScrollToPosition( mMessageList.size - 1 )

            Toast.makeText(this, "게임을 종료합니다.", Toast.LENGTH_SHORT).show()

            numberEdt.isEnabled = false

        }

    }

    fun makeQuestionNumbers() {

        for ( i  in 0..2) {

            while (true) {

                val randomNum = ( Math.random() * 9  + 1 ).toInt()


                var isDuplOk = true

                for ( num   in mQuestionNumbers ) {
                    if (num == randomNum) {

                        isDuplOk = false
                    }
                }

                if (isDuplOk) {
                    mQuestionNumbers.add( randomNum )

                    break
                }

            }

        }


        for (num  in mQuestionNumbers) {
            Log.d("출제된숫자", num.toString())
        }


        mMessageList.add(  MessageData("어서오세요.", "CPU")  )
        mMessageList.add( MessageData("숫자야구 게임입니다.", "CPU") )
        mMessageList.add( MessageData("세자리 숫자를 맞춰주세요.", "CPU") )


    }
}