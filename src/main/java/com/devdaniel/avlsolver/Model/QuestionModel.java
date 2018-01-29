package com.devdaniel.avlsolver.Model;

public class QuestionModel {
    private NodeModel tree;
    private int [] steps;

    public QuestionModel() {
    }

    public QuestionModel(NodeModel tree, int [] steps) {
        this.tree = tree;
        this.steps = steps;
    }

    public NodeModel getTree() {
        return tree;
    }

    public int[] getSteps() {
        return steps;
    }
}
