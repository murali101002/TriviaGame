package android.com.group18_hw03;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by murali101002 on 9/22/2016.
 */
public class GetJsonDataAsync extends AsyncTask<String, Void, ArrayList<Question>> {
    BufferedReader reader;
    AccessUI accessUI;
    MainActivity activity;
    ProgressDialog progressDialog;
    static final int STATUS_START = 0;
    static final int STATUS_STEP = 1;
    static final int STATUS_STOP = 2;
    //    Handler handler = accessUI.getHandler();
    Bundle bundle;
    Message msg;

    @Override
    protected void onPostExecute(ArrayList<Question> questions) {
        super.onPostExecute(questions);
        progressDialog.dismiss();
    }

    public GetJsonDataAsync(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        accessUI.showProgressDialog();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Loading Trivia");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected ArrayList<Question> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                return JsonParsing.QuestionJsonParser.parseQuestion(stringBuilder.toString());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
