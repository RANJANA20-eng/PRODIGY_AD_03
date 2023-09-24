package com.example.stopwatchapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.stopwatchapp.ui.theme.StopwatchAppTheme
import android.widget.Button
import android.widget.TextView
import android.os.CountDownTimer
class MainActivity : ComponentActivity() {
    private lateinit var timerTextView: TextView
    private lateinit var startButton: Button
    private lateinit var pauseButton: Button
    private lateinit var resetButton: Button
    private var timer: CountDownTimer? = null
    private var running = false
    private var milliseconds = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timerTextView = findViewById(R.id.timerTextView)
        startButton = findViewById(R.id.startButton)
        pauseButton = findViewById(R.id.pauseButton)
        resetButton = findViewById(R.id.resetButton)

        startButton.setOnClickListener { startTimer() }
        pauseButton.setOnClickListener { pauseTimer() }
        resetButton.setOnClickListener { resetTimer() }
    }

    private fun startTimer() {
        if (!running) {
            timer = object : CountDownTimer(Long.MAX_VALUE, 1) {
                override fun onTick(millisUntilFinished: Long) {
                    milliseconds++
                    updateTimerUI()
                }

                override fun onFinish() {
                    // Not used in this example
                }
            }
            timer?.start()
            running = true
            startButton.isEnabled = false
            pauseButton.isEnabled = true
            resetButton.isEnabled = true
        }
    }

    private fun pauseTimer() {
        if (running) {
            timer?.cancel()
            running = false
            startButton.isEnabled = true
            pauseButton.isEnabled = false
            resetButton.isEnabled = true
        }
    }

    private fun resetTimer() {
        if (!running) {
            milliseconds = 0
            updateTimerUI()
            startButton.isEnabled = true
            pauseButton.isEnabled = false
            resetButton.isEnabled = false
        }
    }

    private fun updateTimerUI() {
        val minutes = (milliseconds / 60000).toString().padStart(2, '0')
        val seconds = ((milliseconds % 60000) / 1000).toString().padStart(2, '0')
        val millis = (milliseconds % 1000).toString().padStart(3, '0')
        timerTextView.text = "$minutes:$seconds:$millis"
    }
}
