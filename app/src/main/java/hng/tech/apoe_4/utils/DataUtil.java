package hng.tech.apoe_4.utils;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import hng.tech.apoe_4.models.AnswerData;
import hng.tech.apoe_4.models.QuestionData;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DataUtil {

    //this static method loads json from asset folder
    public static String loadJsonFromAsset(Context context, String asset){
        String json = null;
        try {
            InputStream file = context.getAssets().open(asset);
            int size = file.available(); //here we check the size of bytes availabe
            byte [] fileRead = new byte[size]; //here i created an array
            file.read(fileRead); //here i read the bytes into the array
            json = new String(fileRead);
            Log.d("TAG", "sample :"+ size);
            file.close();
            Log.d("TAG", "sample2: " + json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;

    }


    public static List<QuestionData> openTheData(Context context, String option){
        List<QuestionData> questionDataArrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(loadJsonFromAsset(context, "questions.json"));
            JSONArray jsonArray = jsonObject.getJSONArray(option);

            Log.d("TAG", "array :"+ jsonArray );

            for(int i = 0; i< jsonArray.length(); ++i){
                Log.d("TAG", "jsonLength: " + jsonArray.length());
                JSONObject otherObject = jsonArray.getJSONObject(i);


             QuestionData questionData;
                //create a new object of the model class

                    questionData = new QuestionData(
                            otherObject.getString("qTitle"),
                            otherObject.getString("qAnswers")
                    );
                questionDataArrayList.add(questionData);

                Log.d("TAG", "sample 4: "+ otherObject.getString("qTitle")+ otherObject.getString("qAnswers"));


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return questionDataArrayList;
    }


   public   static List<AnswerData> loadAnswers(String arrayName) {
       List<AnswerData> answerDataList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(arrayName);
            Log.d("TAG", "newJsonObject "+ jsonObject.getJSONArray("qAnswers").get(1));
            JSONArray jsonArray = jsonObject.getJSONArray("qAnswers");
            for(int i = 0; i< jsonArray.length(); i++){
                JSONObject answer = jsonArray.getJSONObject(i);

                //create a new instance of verse model class and populate it with each verse
                AnswerData answerData = new AnswerData(answer.getString("title"),
                       answer.getString("answer")
                );

                Log.d("TAG", "Verses "+ answer.getString("title"));
                answerDataList.add(answerData);

                Log.d(TAG, "loadAnswers: " + answerDataList.size());

            }

//            return answerDataList;
//            verseAdapter.setVerses(verseList);

        } catch (JSONException e) {
            e.printStackTrace();
        }
       Log.d(TAG, "loadAnswers: " + answerDataList.size());
       return answerDataList;
    }
}
