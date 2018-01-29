package com.devdaniel.avlsolver.Service;

import com.devdaniel.avlsolver.AVLTreeLogic;
import com.devdaniel.avlsolver.Model.AnswerStepModel;
import com.devdaniel.avlsolver.Model.GivenAnswerModel;
import com.devdaniel.avlsolver.Model.NodeModel;
import com.devdaniel.avlsolver.Model.QuestionModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AVLServiceTest {

    @Mock
    private QuestionModel questionModel;
    @Mock
    private GivenAnswerModel givenAnswerModel;

    private NodeModel beginTree;

    private AVLService avlService;
    private AVLTreeLogic logic;

    @Before
    public void setup () {
        this.avlService = new AVLService();
        this.logic = new AVLTreeLogic();
        this.beginTree = new NodeModel(3,
                new NodeModel(1, null, null),
                new NodeModel(4, null, null));
    }

    @Test
    public void testExaminationWithCorrectGivenAnswers () {
        when(questionModel.getTree()).thenReturn(beginTree);
        when(questionModel.getSteps()).thenReturn(new int[]{2,5});
        prepareCorrectGivenAnswer();

        avlService.examine(questionModel, givenAnswerModel);

        Assert.assertEquals(1, givenAnswerModel.getSteps().get(0).getCorrectStep());
        Assert.assertTrue(logic.equals(givenAnswerModel.getSteps().get(0).getCorrectAnswer(), givenAnswerModel.getSteps().get(0).getGivenStep()));
        Assert.assertEquals(1, givenAnswerModel.getSteps().get(1).getCorrectStep());
        Assert.assertTrue(logic.equals(givenAnswerModel.getSteps().get(1).getCorrectAnswer(), givenAnswerModel.getSteps().get(1).getGivenStep()));
    }

    @Test
    public void testExaminationWithSecondAnswerWrong () {
        when(questionModel.getTree()).thenReturn(beginTree);
        when(questionModel.getSteps()).thenReturn(new int[]{2,5});
        prepareSecondWrongGivenAnswer();

        avlService.examine(questionModel, givenAnswerModel);

        Assert.assertEquals(1, givenAnswerModel.getSteps().get(0).getCorrectStep());
        Assert.assertTrue(logic.equals(givenAnswerModel.getSteps().get(0).getCorrectAnswer(), givenAnswerModel.getSteps().get(0).getGivenStep()));
        Assert.assertEquals(-1, givenAnswerModel.getSteps().get(1).getCorrectStep());
        Assert.assertFalse(logic.equals(givenAnswerModel.getSteps().get(1).getCorrectAnswer(), givenAnswerModel.getSteps().get(1).getGivenStep()));
    }

    @Test
    public void testExaminationWithFirstAnswerWrongButAVLAndCorrectNext() {
        when(questionModel.getTree()).thenReturn(beginTree);
        when(questionModel.getSteps()).thenReturn(new int[]{2,5});
        prepareFirstWrongGivenAnswerButIsAVL();

        avlService.examine(questionModel, givenAnswerModel);

        Assert.assertEquals(-1, givenAnswerModel.getSteps().get(0).getCorrectStep());
        Assert.assertFalse(logic.equals(givenAnswerModel.getSteps().get(0).getCorrectAnswer(), givenAnswerModel.getSteps().get(0).getGivenStep()));
        Assert.assertEquals(1, givenAnswerModel.getSteps().get(1).getCorrectStep());
        Assert.assertTrue(logic.equals(givenAnswerModel.getSteps().get(1).getCorrectAnswer(), givenAnswerModel.getSteps().get(1).getGivenStep()));
    }

    @Test
    public void testExaminationWithFirstAnswerWrongAsAVLSecondCorrect() {
        when(questionModel.getTree()).thenReturn(beginTree);
        when(questionModel.getSteps()).thenReturn(new int[]{2,5});
        prepareFirstWrongGivenAnswerAndIsNoAVL();

        avlService.examine(questionModel, givenAnswerModel);

        Assert.assertEquals(-1, givenAnswerModel.getSteps().get(0).getCorrectStep());
        Assert.assertFalse(logic.equals(givenAnswerModel.getSteps().get(0).getCorrectAnswer(), givenAnswerModel.getSteps().get(0).getGivenStep()));
        Assert.assertEquals(1, givenAnswerModel.getSteps().get(1).getCorrectStep());
        Assert.assertTrue(logic.equals(givenAnswerModel.getSteps().get(1).getCorrectAnswer(), givenAnswerModel.getSteps().get(1).getGivenStep()));
    }

    @Test
    public void testExaminationWhereAnswerModelForFirstIsNotFilledIn () {
        when(questionModel.getTree()).thenReturn(beginTree);
        when(questionModel.getSteps()).thenReturn(new int[]{2,5});
        prepareCorrectSecondGivenAnswer();

        avlService.examine(questionModel, givenAnswerModel);

        Assert.assertEquals(1, givenAnswerModel.getSteps().get(0).getCorrectStep());
        Assert.assertTrue(logic.equals(givenAnswerModel.getSteps().get(0).getCorrectAnswer(), givenAnswerModel.getSteps().get(0).getGivenStep()));
        Assert.assertEquals(5, logic.size(givenAnswerModel.getSteps().get(0).getGivenStep()));
    }

    @Test
    public void testExaminationWhereAnswerModelForSecondIsNotFilledInButThirdIs () {
        beginTree.getRightChild().setValue(5);
        when(questionModel.getTree()).thenReturn(beginTree);
        when(questionModel.getSteps()).thenReturn(new int[]{2,4,6});
        prepareCorrectSecondGivenAnswerNotGiven();

        avlService.examine(questionModel, givenAnswerModel);

        Assert.assertEquals(1, givenAnswerModel.getSteps().get(0).getCorrectStep());
        Assert.assertTrue(logic.equals(givenAnswerModel.getSteps().get(0).getCorrectAnswer(), givenAnswerModel.getSteps().get(0).getGivenStep()));
        Assert.assertEquals(4, logic.size(givenAnswerModel.getSteps().get(0).getGivenStep()));
        Assert.assertEquals(1, givenAnswerModel.getSteps().get(1).getCorrectStep());
        Assert.assertTrue(logic.equals(givenAnswerModel.getSteps().get(1).getCorrectAnswer(), givenAnswerModel.getSteps().get(1).getGivenStep()));
        Assert.assertEquals(6, logic.size(givenAnswerModel.getSteps().get(1).getGivenStep()));
    }

    @Test
    public void testExaminationWhereAnswerModelForSecondIsNotFilledInButThirdIsAndWrong () {
        beginTree.getRightChild().setValue(5);
        when(questionModel.getTree()).thenReturn(beginTree);
        when(questionModel.getSteps()).thenReturn(new int[]{2,4,6});
        prepareCorrectSecondGivenAnswerNotGivenThirdIsWrong();

        avlService.examine(questionModel, givenAnswerModel);

        Assert.assertEquals(1, givenAnswerModel.getSteps().get(0).getCorrectStep());
        Assert.assertTrue(logic.equals(givenAnswerModel.getSteps().get(0).getCorrectAnswer(), givenAnswerModel.getSteps().get(0).getGivenStep()));
        Assert.assertEquals(4, logic.size(givenAnswerModel.getSteps().get(0).getGivenStep()));
        Assert.assertEquals(-1, givenAnswerModel.getSteps().get(1).getCorrectStep());
        Assert.assertFalse(logic.equals(givenAnswerModel.getSteps().get(1).getCorrectAnswer(), givenAnswerModel.getSteps().get(1).getGivenStep()));
        Assert.assertEquals(6, logic.size(givenAnswerModel.getSteps().get(1).getGivenStep()));
    }

    @Test
    public void testSolveTreeWithoutStartValue () {
        when(questionModel.getSteps()).thenReturn(new int[]{2,4,6});
        NodeModel nodeModel = avlService.solve(questionModel);

        Assert.assertEquals(4, nodeModel.getValue());
        Assert.assertEquals(2, nodeModel.getLeftChild().getValue());
        Assert.assertEquals(6, nodeModel.getRightChild().getValue());
    }

    @Test
    public void testSolveTreeWithStartValue () {
        when(questionModel.getTree()).thenReturn(beginTree);
        when(questionModel.getSteps()).thenReturn(new int[]{2,5,6});
        NodeModel nodeModel = avlService.solve(questionModel);

        Assert.assertEquals(3, nodeModel.getValue());
        Assert.assertEquals(1, nodeModel.getLeftChild().getValue());
        Assert.assertEquals(5, nodeModel.getRightChild().getValue());
        Assert.assertEquals(6, nodeModel.getRightChild().getRightChild().getValue());
        Assert.assertEquals(4, nodeModel.getRightChild().getLeftChild().getValue());
        Assert.assertEquals(2, nodeModel.getLeftChild().getRightChild().getValue());
    }

    /**
     *      3             3
     *   1    4  -->    1   4
     *    2              2    5
     */
    private void prepareCorrectGivenAnswer () {
        List<AnswerStepModel> list = new ArrayList<>();
        list.add(new AnswerStepModel(new NodeModel(3,
                new NodeModel(1, null, new NodeModel(2, null, null)),
                new NodeModel(4, null, null))));
        list.add(new AnswerStepModel(new NodeModel(3,
                new NodeModel(1, null, new NodeModel(2, null, null)),
                new NodeModel(4, null, new NodeModel(5, null, null)))));


        when(givenAnswerModel.getSteps()).thenReturn(list);
    }

    /**
     *      3             3
     *   1    4  -->    1   5
     *    2              2    4
     */
    private void prepareSecondWrongGivenAnswer () {
        List<AnswerStepModel> list = new ArrayList<>();
        list.add(new AnswerStepModel(new NodeModel(3,
                new NodeModel(1, null, new NodeModel(2, null, null)),
                new NodeModel(4, null, null))));
        list.add(new AnswerStepModel(new NodeModel(3,
                new NodeModel(1, null, new NodeModel(2, null, null)),
                new NodeModel(5, null, new NodeModel(4, null, null)))));


        when(givenAnswerModel.getSteps()).thenReturn(list);
    }

    /**
     *      3             3
     *    2   4  --->   2   4
     *  1             1       5
     *
     */
    private void prepareFirstWrongGivenAnswerButIsAVL () {
        List<AnswerStepModel> list = new ArrayList<>();
        list.add(new AnswerStepModel(new NodeModel(3,
                new NodeModel(2, new NodeModel(1, null, null),  null),
                new NodeModel(4, null, null))));
        list.add(new AnswerStepModel(new NodeModel(3,
                new NodeModel(2, new NodeModel(1, null, null),  null),
                new NodeModel(4, null, new NodeModel(5, null, null)))));

        when(givenAnswerModel.getSteps()).thenReturn(list);
    }

    /**
     *      3             3
     *   4    1  -->    1   4
     *    2              2    5
     */
    private void prepareFirstWrongGivenAnswerAndIsNoAVL () {
        List<AnswerStepModel> list = new ArrayList<>();
        list.add(new AnswerStepModel(new NodeModel(3,
                new NodeModel(4, null, new NodeModel(2, null, null)),
                new NodeModel(1, null, null))));
        list.add(new AnswerStepModel(new NodeModel(3,
                new NodeModel(1, null, new NodeModel(2, null, null)),
                new NodeModel(4, null, new NodeModel(5, null, null)))));


        when(givenAnswerModel.getSteps()).thenReturn(list);
    }

    /**
     *     3
     *   1   4
     *     2   5
     */
    private void prepareCorrectSecondGivenAnswer () {
        List<AnswerStepModel> list = new ArrayList<>();
        list.add(new AnswerStepModel(new NodeModel(3,
                new NodeModel(1, null, new NodeModel(2, null, null)),
                new NodeModel(4, null, new NodeModel(5, null, null)))));


        when(givenAnswerModel.getSteps()).thenReturn(list);
    }

    /**
     *      3             3
     *   1    5  -->    1   5
     *    2              2 4 6
     */
    private void prepareCorrectSecondGivenAnswerNotGiven () {
        List<AnswerStepModel> list = new ArrayList<>();
        list.add(new AnswerStepModel(new NodeModel(3,
                new NodeModel(1, null, new NodeModel(2, null, null)),
                new NodeModel(5, null, null))));
        list.add(new AnswerStepModel(new NodeModel(3,
                new NodeModel(1, null, new NodeModel(2, null, null)),
                new NodeModel(5, new NodeModel(4, null, null), new NodeModel(6, null, null)))));


        when(givenAnswerModel.getSteps()).thenReturn(list);
    }

    /**
     *      3             3
     *   1    5  -->    1   5
     *    2              2 4
     *                      6
     */
    private void prepareCorrectSecondGivenAnswerNotGivenThirdIsWrong() {
        List<AnswerStepModel> list = new ArrayList<>();
        list.add(new AnswerStepModel(new NodeModel(3,
                new NodeModel(1, null, new NodeModel(2, null, null)),
                new NodeModel(5, null, null))));
        list.add(new AnswerStepModel(new NodeModel(3,
                new NodeModel(1, null, new NodeModel(2, null, null)),
                new NodeModel(5, new NodeModel(4, null, new NodeModel(6, null, null)), null))));


        when(givenAnswerModel.getSteps()).thenReturn(list);
    }
}