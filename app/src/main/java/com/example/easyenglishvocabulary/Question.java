package com.example.easyenglishvocabulary;

import com.example.easyenglishvocabulary.DataBase.ActiveQuery;
import com.example.easyenglishvocabulary.DataBase.GlossaryModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Question {

    public static int countOfOpintos = 4;
    private GlossaryModel model;
    private String english;
    private String[] answers;
    private int rightAnswer;

    public static Question[] createQuestions(int count){
        ActiveQuery<GlossaryModel> query = new ActiveQuery<>(GlossaryModel.class);
        ArrayList<GlossaryModel> models = query.getFromDB(GlossaryModel.COLUMN_CORRECT_ANSWERS + " < 5");
        count = count - (countOfOpintos - 1) > models.size() ? models.size() - (countOfOpintos - 1) : count;
        return generateRandomQuestions(count, models);
    }

    protected static Question[] generateRandomQuestions(int count, ArrayList<GlossaryModel> models){
        Question[] questions = new Question[count];
        for (int i = 0; i < count; i++){
            GlossaryModel model = models.remove((int)(Math.random() * models.size()));
            GlossaryModel[] wrong = new GlossaryModel[countOfOpintos - 1];
            for (int j = 0; j < countOfOpintos - 1; j++){
                int random;
                do{
                    random = (int)(Math.random() * models.size());
                } while (Arrays.asList(wrong).contains(models.get(random)));
                wrong[j] = models.get(random);
            }
            questions[i] = generateQuestion(model, wrong);
        }
        return questions;
    }

    protected static Question generateQuestion(GlossaryModel right, GlossaryModel[] wrong){
        Question question = new Question();
        question.model = right;
        question.english = right.english;
        question.rightAnswer = (int)(Math.random() * countOfOpintos);
        question.answers = new String[countOfOpintos];
        for (int i = 0; i < countOfOpintos; i++){
            if (i == question.rightAnswer){
                question.answers[i] = right.russian;
            } else if (i > question.rightAnswer) {
                question.answers[i] = wrong[i - 1].russian;
            } else {
                question.answers[i] = wrong[i].russian;
            }
        }
        return question;
    }

    public String getEnglish() {
        return english;
    }

    public String[] getAnswers() {
        return answers;
    }

    public boolean isRightAnswer(int answer){
        if (answer == rightAnswer){
            model.correctAnswers++;
            model.save();
            return true;
        }
        return false;
    }
}
