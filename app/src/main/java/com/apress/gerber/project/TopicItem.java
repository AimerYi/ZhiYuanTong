package com.apress.gerber.project;

/**
 * Created by Colonel on 2017/9/19.
 */

/**
 * Created by Colonel on 2017/9/18.
 */
import java.io.Serializable;
import java.util.List;

public class TopicItem implements Serializable {
    private String questionId;
    private String question;
    private String answerId;
    private String userAnswerId; //选择的答案选项
    private List<OptionItem> optionList;


    public String getQuestionId() {
        return questionId;
    }
    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public String getAnswerId() {
        return answerId;
    }
    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }
    public String getUserAnswerId() {
        return userAnswerId;
    }
    public void setUserAnswerId(String userAnswerId) {
        this.userAnswerId = userAnswerId;
    }
    public List<OptionItem> getOptionList() {
        return optionList;
    }
    public void setOptionList(List<OptionItem> optionList) {
        this.optionList = optionList;
    }
}