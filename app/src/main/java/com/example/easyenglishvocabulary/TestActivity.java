package com.example.easyenglishvocabulary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class TestActivity extends AppCompatActivity {

    Question[] questions;
    int currentQuestion = 0;
    int countOfRightAnswers = 0;
    TextView questionField;
    RadioGroup answersField;
    RadioButton[] radioButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        questionField = (TextView)findViewById(R.id.question);
        answersField = (RadioGroup) findViewById(R.id.answers);

        questions = Question.createQuestions(10);
        showQuestion(currentQuestion);
    }

    protected void showQuestion(int number){
        Question question = questions[number];
        questionField.setText(question.getEnglish());

        answersField.removeAllViews();
        String[] answers = question.getAnswers();
        radioButtons = new RadioButton[answers.length];
        for (int i = 0; i < answers.length; i++){
            radioButtons[i] = new RadioButton(this);
            radioButtons[i].setText(answers[i]);
            radioButtons[i].setOnClickListener(radioButtonClickListener);
            answersField.addView(radioButtons[i]);
        }
    }

    View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton rb = (RadioButton)v;
            for (int i = 0; i < radioButtons.length; i++){
                if (rb == radioButtons[i]){
                    boolean right = questions[currentQuestion].isRightAnswer(i);
                    if(right) countOfRightAnswers++;

                    FragmentManager manager = getSupportFragmentManager();
                    MyDialogFragment myDialogFragment = new MyDialogFragment(right ? "Правильно" : "Неправильно", new Callback() {
                        @Override
                        public void callback() {
                            currentQuestion++;
                            if (currentQuestion < questions.length) {
                                showQuestion(currentQuestion);
                            } else {
                                float percentage = (float)(countOfRightAnswers) / questions.length;
                                String result;
                                if (percentage >= 0.9){ result = "Отлично";}
                                else if (percentage >= 0.7){ result = "Хорошо";}
                                else if (percentage >= 0.5){ result = "Неплохо";}
                                else { result = "Есть к чему стремиться";}

                                FragmentManager manager = getSupportFragmentManager();
                                MyDialogFragment myDialogFragment = new MyDialogFragment((countOfRightAnswers + "/" + questions.length + ". " + result), new Callback() {
                                    @Override
                                    public void callback() {
                                        Intent intent = new Intent(TestActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                });
                                myDialogFragment.show(manager, "myDialog");
                            }
                        }
                    });
                    myDialogFragment.show(manager, "myDialog");
                }
            }
        }
    };

    abstract class Callback{

        public abstract void callback();
    }
}