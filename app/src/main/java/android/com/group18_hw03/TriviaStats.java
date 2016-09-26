package android.com.group18_hw03;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class TriviaStats extends AppCompatActivity implements View.OnClickListener {
    TextView lbl_percentage, text_tryAgain;
    ProgressBar progressBar;
    Button quit, tryAgain;
    MainActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_stats);
        double result = getIntent().getExtras().getInt(Trivia.SCORE);
        double percentage = (result/16)*100;
        lbl_percentage = (TextView)findViewById(R.id.lbl_progress);
        text_tryAgain = (TextView)findViewById(R.id.text_tryAgain);
        quit = (Button)findViewById(R.id.btn_quit_stats);
        tryAgain = (Button)findViewById(R.id.btn_tryAgain);
        quit.setOnClickListener(this);
        tryAgain.setOnClickListener(this);
        lbl_percentage.setText(String.valueOf((int)percentage)+" %");
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setProgress((int)percentage);
        if(percentage == 100){
            text_tryAgain.setText("Brilliant!!! You have answered all questions correct");
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_quit_stats:
                Intent mainIntent = new Intent(getApplicationContext(),MainActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mainIntent);
                break;
            case R.id.btn_tryAgain:
                Intent triviaIntent = new Intent(getApplicationContext(),Trivia.class);
                triviaIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ArrayList<Question> questions = (ArrayList<Question>) getIntent().getExtras().getSerializable(MainActivity.QSTNS);
                triviaIntent.putExtra(MainActivity.QSTNS,questions);
                startActivity(triviaIntent);
                break;
        }
    }
}
