package com.devdaniel.avlsolver.Model;


public class AnswerStepModel {
    private NodeModel givenStep;
    private int correctStep;
    private NodeModel correctAnswer;

    public AnswerStepModel() {
        this.givenStep = null;
        this.correctStep = 0;
        this.correctAnswer = null;
    }

    public AnswerStepModel (NodeModel givenStep) {
        this.givenStep = givenStep;
        this.correctStep = 0;
        this.correctAnswer = null;
    }

    public NodeModel getGivenStep() {
        return givenStep;
    }

    public int getCorrectStep() {
        return correctStep;
    }

    public void setCorrectStep(int correctStep) {
        this.correctStep = correctStep;
    }

    public NodeModel getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(NodeModel correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
