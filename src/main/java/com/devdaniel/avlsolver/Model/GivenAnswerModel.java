package com.devdaniel.avlsolver.Model;

import java.util.ArrayList;
import java.util.List;

public class GivenAnswerModel {
    private List<AnswerStepModel> steps;

    public GivenAnswerModel(List<AnswerStepModel> steps) {
        this.steps = steps;
    }

    public GivenAnswerModel() {
        this.steps = new ArrayList<>();
    }

    public List<AnswerStepModel> getSteps() {
        return steps;
    }
}