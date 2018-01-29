package com.devdaniel.avlsolver.Service;


import com.devdaniel.avlsolver.AVLTreeLogic;
import com.devdaniel.avlsolver.Model.AnswerStepModel;
import com.devdaniel.avlsolver.Model.GivenAnswerModel;
import com.devdaniel.avlsolver.Model.NodeModel;
import com.devdaniel.avlsolver.Model.QuestionModel;

public class AVLService {
    private AVLTreeLogic logic;
    private int lastStep = 0;

    public AVLService() {
        this.logic = new AVLTreeLogic();
    }

    /**
     * Goes through all given steps and sets the correct steps and trees for all steps.
     *
     * @param questionModel model that is used for starting point.
     * @param givenAnswerModel answer that has been given for the question.
     * @return true if question has been examined else false
     */
    public GivenAnswerModel examine (QuestionModel questionModel, GivenAnswerModel givenAnswerModel) {
        NodeModel tree = questionModel.getTree();
        int stepCounter = 1;
        int startSize = logic.size(tree);
        lastStep = 0;

        for (int step : questionModel.getSteps()) {
            AnswerStepModel answerStepModel = findLastValidStep(givenAnswerModel,stepCounter + startSize);

            if (answerStepModel != null) {
                tree = this.validateStep(answerStepModel, tree, step);
            } else {
                tree = logic.balanceTree(logic.addNode(tree, new NodeModel(step)));
            }

            stepCounter++;
        }

        return givenAnswerModel;
    }

    public NodeModel solve (QuestionModel questionModel) {
        NodeModel nodeModel = questionModel.getTree();

        for (int num : questionModel.getSteps()) {
            nodeModel = logic.balanceTree(logic.addNode(nodeModel, new NodeModel(num)));
        }

        return nodeModel;
    }

    /**
     * Checks if givenAnswer is same as made step by automatic made step.
     *
     * @param givenAnswer step of the givenAnswer of a student.
     * @param startPosition tree representation on which a value is being added.
     * @param addingValue value that is being added to tree
     * @return Correct tree that can be used for next step validation
     */
    private NodeModel validateStep (AnswerStepModel givenAnswer, NodeModel startPosition, int addingValue) {
        NodeModel correctTree = logic.balanceTree(logic.addNode(startPosition, new NodeModel(addingValue)));
        boolean isAVL = logic.isAVL(givenAnswer.getGivenStep());

        givenAnswer.setCorrectStep(isAVL &&
                                    logic.equals(givenAnswer.getGivenStep(), correctTree) ? 1 : -1);

        givenAnswer.setCorrectAnswer(correctTree);

        return isAVL ? givenAnswer.getGivenStep() : correctTree;
    }

    /**
     * find the last step that has the size of the stepSize.
     *
     * @param givenAnswerModel answer that has been given for the question.
     * @param stepSize size of the tree used in the givenStep.
     * @return the model that can be used for grading is null if can't find a step.
     */
    private AnswerStepModel findLastValidStep (GivenAnswerModel givenAnswerModel, int stepSize) {
        AnswerStepModel answerStepModel = null;
        for (int j = lastStep; j < givenAnswerModel.getSteps().size(); j++) {
            if (logic.size(givenAnswerModel.getSteps().get(j).getGivenStep()) > stepSize) {
                lastStep = j;
                return answerStepModel;
            }
            answerStepModel = givenAnswerModel.getSteps().get(j);
        }

        return answerStepModel;
    }
}
