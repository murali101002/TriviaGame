package android.com.group18_hw03;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements AccessUI, View.OnClickListener {
    public static final String QSTNS = "questions";
    ImageView trivia_img;
    Button start_btn, exit_btn;
    String url;
    ProgressDialog progressDialog;
    Handler handler = new Handler();
    ArrayList<Question> questionsList;
    Question question;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        trivia_img = (ImageView)findViewById(R.id.trivia_img_id);
        exit_btn = (Button)findViewById(R.id.exit_btn);
        start_btn = (Button)findViewById(R.id.start_btn);
        start_btn.setEnabled(false);
        url = "http://dev.theappsdr.com/apis/trivia_json/index.php";
        AsyncTask<String, Void, ArrayList<Question>> myBgTask = new GetJsonDataAsync(this).execute(url);
        try {
            questionsList = myBgTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        trivia_img.setImageResource(R.drawable.trivia);
        start_btn.setEnabled(true);
        exit_btn.setOnClickListener(this);
        start_btn.setOnClickListener(this);
    }

    @Override
    public void showProgressDialog() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Loading Trivia");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    public Handler getHandler() {
//        this.handler = new Handler();
        return this.handler;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case(R.id.exit_btn):
                finish();
                break;
            case(R.id.start_btn):
                Intent triviaIntent = new Intent(getApplicationContext(),Trivia.class);
                triviaIntent.putExtra(QSTNS,questionsList);
                startActivity(triviaIntent);
                break;
        }
    }
}
