package com.apress.gerber.project;

/**
 * Created by Colonel on 2017/9/19.
 */
import java.io.Serializable;

/**
 * Created by Colonel on 2017/9/18.
 */
public class OptionItem implements Serializable {
    private String answerOption;//答案选项
    private String answer;//答案
    public String getAnswerOption() {
        return answerOption;
    }
    public void setAnswerOption(String answerOption) {
        this.answerOption = answerOption;
    }
    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String anwer) {
        this.answer = anwer;
    }
}