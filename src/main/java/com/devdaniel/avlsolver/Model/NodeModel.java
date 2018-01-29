package com.devdaniel.avlsolver.Model;

public class NodeModel {

    //Declare instance variables
    private int value;
    private NodeModel leftChild;
    private NodeModel rightChild;

    public NodeModel() {
        this.value = 0;
        this.leftChild = null;
        this.rightChild = null;
    }


    /**
     * Constructs a leaf node that stores an integer, data
     * @param value the integer to set the data value to for the new node
     */
    public NodeModel(int value) {
        this.value = value;
        this.leftChild = null;
        this.rightChild = null;
    }

    public NodeModel(int value, NodeModel leftChild, NodeModel rightChild) {
        this.value = value;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public NodeModel getLeftChild() {
        return this.leftChild;
    }

    public NodeModel getRightChild() {
        return this.rightChild;
    }

}
