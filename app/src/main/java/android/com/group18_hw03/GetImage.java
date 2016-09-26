package android.com.group18_hw03;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by murali101002 on 9/22/2016.
 */
public class GetImage extends AsyncTask<String,Void,Bitmap>{
    Trivia activity;
    ProgressDialog progressDialog;
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        activity.qstn_img.setImageBitmap(bitmap);
        progressDialog.dismiss();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Loading Trivia");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public GetImage(Trivia activity) {
        this.activity = activity;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int status_code = con.getResponseCode();
            if(status_code == HttpURLConnection.HTTP_OK){
                bitmap = BitmapFactory.decodeStream(con.getInputStream());
            }
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
