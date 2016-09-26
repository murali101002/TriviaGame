package android.com.group18_hw03;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by murali101002 on 9/22/2016.
 */
public class Question implements Parcelable {
    String answer, question, imageUrl, id;
    ArrayList<String> choices;

    public Question() {

    }

    static public Question createQuestionObject(JSONObject jsonObject) throws JSONException {
        Question question = new Question();
        JSONObject choiceObjects = jsonObject.getJSONObject("choices");

        question.setAnswer(choiceObjects.getString("answer"));
        JSONArray jsonArray = choiceObjects.getJSONArray("choice");
        List<String> choiceList = new ArrayList<String>();
        for (int i = 0; i < jsonArray.length(); i++) {
            choiceList.add(jsonArray.getString(i));
        }
        question.setChoices((ArrayList<String>) choiceList);
        question.setId(jsonObject.getString("id"));
        if (jsonObject.has("image")) {
            question.setImageUrl(jsonObject.getString("image"));
        } else {
            question.setImageUrl("");
        }
        question.setQuestion(jsonObject.getString("text"));
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    protected Question(Parcel in) {
        answer = in.readString();
        question = in.readString();
        imageUrl = in.readString();
        id = in.readString();
        choices = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(answer);
        dest.writeString(question);
        dest.writeString(imageUrl);
        dest.writeString(id);
        dest.writeStringList(choices);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
}
