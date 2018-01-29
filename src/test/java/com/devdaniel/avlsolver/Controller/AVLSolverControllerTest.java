package com.devdaniel.avlsolver.Controller;

import com.devdaniel.avlsolver.Model.*;
import com.sun.istack.internal.Nullable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AVLSolverControllerTest {

    private AVLSolverController avlSolverController;

    @Before
    public void setup () {
        this.avlSolverController = new AVLSolverController() ;
    }

    @Test
    public void testGetNodeModel () {
        NodeModel nodeModel = this.avlSolverController.nodeModel();

        Assert.assertEquals(0, nodeModel.getValue());
    }

    @Test
    public void testSolveWithCorrectGivenAnswers () {
        QuestionRequest questionRequest = prepareGoodQuestion();

        GivenAnswerModel solved = this.avlSolverController.solve(questionRequest);

        for (AnswerStepModel answerStepModel : solved.getSteps()) {
            Assert.assertEquals(1, answerStepModel.getCorrectStep());
        }
    }

    @Test
    public void testSolveWithNoAnswersGiven () {
        QuestionModel questionModel = new QuestionModel(new NodeModel(1), new int [] {5,2,3});
        GivenAnswerModel givenAnswerModel = new GivenAnswerModel();

        GivenAnswerModel solved = this.avlSolverController.solve(new QuestionRequest(questionModel, givenAnswerModel));

        Assert.assertEquals(0, solved.getSteps().size());
    }

    public static QuestionRequest prepareGoodQuestion () {
        return getQuestionRequest(1, 3);
    }

    public static QuestionRequest prepareBadQuestion () {
        return getQuestionRequest(3, 1);
    }

    private static QuestionRequest getQuestionRequest(int i, int i2) {
        QuestionModel questionModel = new QuestionModel(new NodeModel(1), new int [] {5,2,3});

        List<AnswerStepModel> answerStepModels = new ArrayList<>();

        GivenAnswerModel givenAnswerModel = new GivenAnswerModel(answerStepModels);

        answerStepModels.add(new AnswerStepModel(new NodeModel(1, null, new NodeModel(5))));
        answerStepModels.add(new AnswerStepModel(new NodeModel(2, new NodeModel(1), new NodeModel(5))));
        answerStepModels.add(new AnswerStepModel(new NodeModel(2, new NodeModel(i), new NodeModel(5, new NodeModel(i2), null))));

        return new QuestionRequest(questionModel, givenAnswerModel);
    }

}