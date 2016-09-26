package android.com.group18_hw03;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by murali101002 on 9/22/2016.
 */
public class JsonParsing {
    static class QuestionJsonParser{
        static ArrayList<Question> parseQuestion(String jsonData) throws JSONException {
            ArrayList<Question> questionsData = new ArrayList<>();
            JSONObject rootObject = new JSONObject(jsonData);
            JSONArray rootArray = rootObject.getJSONArray("questions");
            for(int i=0;i<rootArray.length();i++){
                JSONObject childObject = rootArray.getJSONObject(i);
                Question questionObject = Question.createQuestionObject(childObject);
                questionsData.add(questionObject);
            }
            return questionsData;
        }
    }
}
