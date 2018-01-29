package com.devdaniel.avlsolver.Model;

public class QuestionRequest {

    private QuestionModel questionModel;
    private GivenAnswerModel givenAnswerModel;

    public QuestionRequest() {
        questionModel = new QuestionModel();
        givenAnswerModel = new GivenAnswerModel();
    }

    public QuestionRequest(QuestionModel questionModel, GivenAnswerModel givenAnswerModel) {
        this.questionModel = questionModel;
        this.givenAnswerModel = givenAnswerModel;
    }

    public QuestionModel getQuestionModel() {
        return questionModel;
    }

    public GivenAnswerModel getGivenAnswerModel() {
        return givenAnswerModel;
    }

    public void setQuestionModel(QuestionModel questionModel) {
        this.questionModel = questionModel;
    }

    public void setGivenAnswerModel(GivenAnswerModel givenAnswerModel) {
        this.givenAnswerModel = givenAnswerModel;
    }
}
