package android.com.group18_hw03;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.concurrent.ExecutionException;

public class Trivia extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    public static final String SCORE = "SCORE";
    TextView qstn_num, timer, qstn;
    ImageView qstn_img;
    Button quit, next;
    ListIterator<Question> nextQstn;
    ArrayList<Question> questionsList;
    AsyncTask<String, Void, Bitmap> imageTask;
    Bitmap bitmap;
    int iterator = 1;
    int answer, selectedAnswer, totalChoices = 0;
    RadioGroup rGroup;
    static boolean isChecked = false;
    private static int correctAnswerCount = 0;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);
        questionsList = (ArrayList<Question>) getIntent().getExtras().getSerializable(MainActivity.QSTNS);
        qstn_num = (TextView) findViewById(R.id.lbl_qstnNum);
        timer = (TextView) findViewById(R.id.lbl_timer);
        qstn = (TextView) findViewById(R.id.text_qstn);
        qstn_img = (ImageView) findViewById(R.id.img_qstn);
        quit = (Button) findViewById(R.id.btn_quit);
        next = (Button) findViewById(R.id.btn_next);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        rGroup = (RadioGroup) findViewById(R.id.rGroup);
        qstn_num.setText("Q" + (Integer.parseInt(questionsList.get(0).getId()) + 1));
        qstn.setText(questionsList.get(0).getQuestion());
        new GetImage(this).execute(questionsList.get(0).getImageUrl());
        answer = Integer.parseInt(questionsList.get(0).getAnswer());
        totalChoices = questionsList.get(0).getChoices().size();
        final RadioButton[] rb = new RadioButton[questionsList.get(0).getChoices().size()];
        rGroup.setOrientation(RadioGroup.VERTICAL);
        for (int i = 0; i < questionsList.get(0).getChoices().size(); i++) {
            rb[i] = new RadioButton(this);
            rb[i].setId(i);
            rGroup.addView(rb[i]);
            rb[i].setText(questionsList.get(0).getChoices().get(i));
        }
        new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("Time Left: " + millisUntilFinished / 1000 + " seconds");
            }

            public void onFinish() {
                startTriviaStatsIntent();
            }
        }.start();

        rGroup.setOnCheckedChangeListener(this);
        quit.setOnClickListener(this);
        next.setOnClickListener(this);
        nextQstn = questionsList.listIterator();
    }

    private void startTriviaStatsIntent() {
        Intent statsIntent = new Intent(getApplicationContext(), TriviaStats.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MainActivity.QSTNS,questionsList);
        bundle.putInt(SCORE, correctAnswerCount);
        statsIntent.putExtras(bundle);
        startActivity(statsIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.btn_quit):
                finish();
                break;
            case R.id.btn_next:
                if (iterator < questionsList.size()) {
                    rGroup.removeAllViews();
                    if ((answer-1) == selectedAnswer) {
                        correctAnswerCount = correctAnswerCount+1;
                    }
                    Question question = questionsList.get(iterator);
                    answer = Integer.parseInt(question.getAnswer());
                    totalChoices = totalChoices+question.getChoices().size();
                    iterator++;
                    updateLayout(question);

                } else if (iterator == questionsList.size()) {
                    next.setText("Submit");
                    if ((answer-1) == selectedAnswer) {
                        correctAnswerCount = correctAnswerCount+1;
                    }
                    startTriviaStatsIntent();
                    correctAnswerCount = 0;
                }

                break;
        }
    }

    private void updateLayout(Question question) {
        qstn_num.setText("Q" + (Integer.parseInt(question.getId()) + 1));
        qstn.setText(question.getQuestion());
        if (question.getImageUrl() != null) {
            new GetImage(this).execute(question.getImageUrl());

        } else {
            qstn_img.setImageResource(R.drawable.no_image);
        }
        final RadioButton[] rb = new RadioButton[question.getChoices().size()];
        rGroup.setOrientation(RadioGroup.VERTICAL);
        for (int i = 0; i < question.getChoices().size(); i++) {
            rb[i] = new RadioButton(this);
            rb[i].setId(i);
            rGroup.addView(rb[i]);
            rb[i].setText(question.getChoices().get(i));

        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        selectedAnswer = checkedId ;
//        Toast.makeText(getApplicationContext(),"Checked",Toast.LENGTH_SHORT).show();
    }
}
