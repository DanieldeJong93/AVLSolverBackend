package com.devdaniel.avlsolver;

import com.devdaniel.avlsolver.Model.NodeModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AVLTreeLogicTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    private AVLTreeLogic logic;

    @Before
    public void setup () {
        logic  = new AVLTreeLogic();
    }

    @Test
    public void testHeight () {
        NodeModel node = new NodeModel(3, new NodeModel (2, new NodeModel (1, null, null),null ), null);

        Assert.assertEquals(3, logic.height(node));
    }

    @Test
    public void testIfEquals () {
        NodeModel node1 = new NodeModel(3, new NodeModel (2, new NodeModel (1, null, null),null ), null);
        NodeModel node2 = new NodeModel(3, new NodeModel (2, new NodeModel (1, null, null),null ), null);

        Assert.assertTrue(logic.equals(node1, node2));
    }

    @Test
    public void testIfNotEqualsChild () {
        NodeModel node1 = new NodeModel(3, new NodeModel (2, new NodeModel (1, null, null),null ), null);
        NodeModel node2 = new NodeModel(3, new NodeModel (2, new NodeModel (10, null, null),null ), null);

        Assert.assertFalse(logic.equals(node1, node2));
    }

    @Test
    public void testIfNotEqualsRoot () {
        NodeModel node1 = new NodeModel(3, new NodeModel (2, new NodeModel (1, null, null),null ), null);
        NodeModel node2 = new NodeModel(4, new NodeModel (2, new NodeModel (1, null, null),null ), null);

        Assert.assertFalse(logic.equals(node1, node2));
    }

    @Test
    public void testSize () {
        NodeModel node = new NodeModel(4, new NodeModel (3, new NodeModel (1, null, new NodeModel(2, null, null)),null ), null);

        Assert.assertEquals(4, logic.size(node));
    }

    @Test
    public void testBalance () {
        NodeModel node = new NodeModel(3, new NodeModel (2, new NodeModel (1, null, null),null ), null);

        Assert.assertEquals(-2, logic.balance(node));
    }

    @Test
    public void testAddNodeFirstLeft () {
        NodeModel currentNode = new NodeModel(3, null, null);
        NodeModel node = new NodeModel (2, null, null);

        NodeModel newTree = logic.addNode(currentNode, node);

        Assert.assertEquals(node, newTree.getLeftChild());
        Assert.assertEquals(null, newTree.getRightChild());
    }

    @Test
    public void testAddNodeFirstRight () {
        NodeModel currentNode = new NodeModel(2, null, null);
        NodeModel node = new NodeModel (3, null, null);

        NodeModel newTree = logic.addNode(currentNode, node);

        Assert.assertEquals(node, newTree.getRightChild());
        Assert.assertEquals(null, newTree.getLeftChild());
    }

    @Test
    public void testAddNodeLeftLeft () {
        NodeModel currentNode = new NodeModel (4, new NodeModel (3, null, null), null);
        NodeModel node = new NodeModel (2, null, null);

        NodeModel newTree = logic.addNode(currentNode, node);

        Assert.assertEquals(node, newTree.getLeftChild().getLeftChild());
        Assert.assertEquals(null, newTree.getLeftChild().getRightChild());
        Assert.assertEquals(null, newTree.getRightChild());
    }

    @Test
    public void testAddNodeLeftRight () {
        NodeModel currentNode = new NodeModel (4, new NodeModel (2, null, null), null);
        NodeModel node = new NodeModel (3, null, null);

        NodeModel newTree = logic.addNode(currentNode, node);

        Assert.assertEquals(node, newTree.getLeftChild().getRightChild());
    }

    @Test
    public void testAddNodeRightRight () {
        NodeModel currentNode = new NodeModel (2, null, new NodeModel (3, null, null));
        NodeModel node = new NodeModel (4, null, null);

        NodeModel newTree = logic.addNode(currentNode, node);

        Assert.assertEquals(node, newTree.getRightChild().getRightChild());
        Assert.assertEquals(null, newTree.getRightChild().getLeftChild());
        Assert.assertEquals(null, newTree.getLeftChild());
    }

    @Test
    public void testAddNodeRightLeft () {
        NodeModel currentNode = new NodeModel (2, null, new NodeModel (4, null, null));
        NodeModel node = new NodeModel (3, null, null);

        NodeModel newTree = logic.addNode(currentNode, node);

        Assert.assertEquals(node, newTree.getRightChild().getLeftChild());
        Assert.assertEquals(null, newTree.getRightChild().getRightChild());
        Assert.assertEquals(null, newTree.getLeftChild());
    }

    @Test
    public void addNodeWithExistingValue () {
        NodeModel currentNode = new NodeModel(2, new NodeModel (1, null, null), new NodeModel (3, null, null));
        NodeModel node = new NodeModel (3, null, null);

        exception.expect(IllegalArgumentException.class);
        logic.addNode(currentNode, node);
    }

    @Test
    public void testLeftRotate () {
        NodeModel rightRight = new NodeModel (4, null, null);
        NodeModel right = new NodeModel (3, null, rightRight);
        NodeModel root = new NodeModel (2, null, right);

        NodeModel returnTree = logic.leftRotate(root);

        Assert.assertEquals(right.getValue(),returnTree.getValue());
        Assert.assertEquals(root.getValue(), returnTree.getLeftChild().getValue());
        Assert.assertEquals(rightRight.getValue(), returnTree.getRightChild().getValue());
    }

    @Test
    public void testLeftRotateWithInvalidNode () {
        NodeModel leftLeft = new NodeModel (2, null, null);
        NodeModel left = new NodeModel (3, leftLeft, null);
        NodeModel root = new NodeModel (4, left, null);

        exception.expect(IllegalArgumentException.class);
        logic.leftRotate(root);
    }

    @Test
    public void testRightRotate () {
        AVLTreeLogic logic = new AVLTreeLogic();
        NodeModel leftLeft = new NodeModel (2, null, null);
        NodeModel left = new NodeModel (3, leftLeft, null);
        NodeModel root = new NodeModel (4, left, null);

        NodeModel returnTree = logic.rightRotate(root);

        Assert.assertEquals(left.getValue(),returnTree.getValue());
        Assert.assertEquals(root.getValue(), returnTree.getRightChild().getValue());
        Assert.assertEquals(leftLeft.getValue(), returnTree.getLeftChild().getValue());
    }

    @Test
    public void testRightRotateWithInvalidNode () {
        NodeModel rightRight = new NodeModel (4, null, null);
        NodeModel right = new NodeModel (3, null, rightRight);
        NodeModel root = new NodeModel (2, null, right);

        exception.expect(IllegalArgumentException.class);
        logic.rightRotate(root);
    }

    @Test
    public void testIsAVLWithValidNode () {
        NodeModel left = new NodeModel (1, null, null);
        NodeModel right = new NodeModel (3, null, null);
        NodeModel root = new NodeModel (2, left, right);

        Assert.assertTrue(logic.isAVL(root));
    }

    @Test
    public void testIsAVLWithInvalidValueNode () {
        NodeModel left = new NodeModel (1, null, null);
        NodeModel right = new NodeModel (3, null, null);
        NodeModel root = new NodeModel (2, right, left);

        Assert.assertFalse(logic.isAVL(root));
    }

    @Test
    public void testIsAVLWithBalanceProblem () {
        NodeModel rightRight = new NodeModel (4, null, null);
        NodeModel right = new NodeModel (3, null, rightRight);
        NodeModel root = new NodeModel (2, null, right);

        Assert.assertFalse(logic.isAVL(root));
    }

    @Test
    public void testBalanceTreeSingleLeft () {
        NodeModel rightRight = new NodeModel (4, null, null);
        NodeModel right = new NodeModel (3, null, rightRight);
        NodeModel root = new NodeModel (2, null, right);

        NodeModel returnTree = logic.balanceTree(root);

        Assert.assertEquals(right.getValue(), returnTree.getValue());
        Assert.assertEquals(root.getValue(), returnTree.getLeftChild().getValue());
        Assert.assertEquals(right.getRightChild().getValue(), returnTree.getRightChild().getValue());
    }

    @Test
    public void testBalanceTreeSingleRight () {
        NodeModel leftLeft = new NodeModel (2, null, null);
        NodeModel left = new NodeModel (3, leftLeft, null);
        NodeModel root = new NodeModel (4, left, null);

        NodeModel returnTree = logic.balanceTree(root);

        Assert.assertEquals(left.getValue(), returnTree.getValue());
        Assert.assertEquals(root.getValue(),returnTree.getRightChild().getValue());
        Assert.assertEquals(leftLeft.getValue(), returnTree.getLeftChild().getValue());
    }

    @Test
    public void testBalanceTreeLeftRight () {
        NodeModel leftRight = new NodeModel (3, null, null);
        NodeModel left = new NodeModel (2, null, leftRight);
        NodeModel root = new NodeModel (4, left, null);

        NodeModel returnTree = logic.balanceTree(root);

        Assert.assertEquals(leftRight.getValue(), returnTree.getValue());
        Assert.assertEquals(root.getValue(), returnTree.getRightChild().getValue());
        Assert.assertEquals(left.getValue(), returnTree.getLeftChild().getValue());
    }

    @Test
    public void testBalanceTreeLeftRightWithChildren () {
        NodeModel leftRightRight = new NodeModel (4, null, null);
        NodeModel leftRight = new NodeModel (3, null, leftRightRight);
        NodeModel leftLeft = new NodeModel (1, null, null);
        NodeModel left = new NodeModel (2, leftLeft, leftRight);
        NodeModel right = new NodeModel (6, null, null);
        NodeModel root = new NodeModel (5, left, right);

        NodeModel returnTree = logic.balanceTree(root);

        Assert.assertEquals(leftRight.getValue(), returnTree.getValue());
        Assert.assertEquals(root.getValue(), returnTree.getRightChild().getValue());
        Assert.assertEquals(left.getValue(), returnTree.getLeftChild().getValue());
        Assert.assertEquals(leftLeft.getValue(), returnTree.getLeftChild().getLeftChild().getValue());
        Assert.assertEquals(leftRightRight.getValue(), returnTree.getRightChild().getLeftChild().getValue());
        Assert.assertEquals(right.getValue(), returnTree.getRightChild().getRightChild().getValue());
    }

    @Test
    public void testBalanceTreeRightLeft () {
        NodeModel rightLeft = new NodeModel (2, null, null);
        NodeModel right = new NodeModel (3, rightLeft, null);
        NodeModel root = new NodeModel (1, null, right);

        NodeModel returnTree = logic.balanceTree(root);

        Assert.assertEquals(rightLeft.getValue(), returnTree.getValue());
        Assert.assertEquals(root.getValue(), returnTree.getLeftChild().getValue());
        Assert.assertEquals(right.getValue(), returnTree.getRightChild().getValue());
    }

    @Test
    public void testBalanceTreeRightLeftWithChildren () {
        NodeModel rightLeftLeft = new NodeModel (7, null, null);
        NodeModel rightLeft = new NodeModel (8, rightLeftLeft, null);
        NodeModel rightRight = new NodeModel (10, null, null);
        NodeModel right = new NodeModel (9, rightLeft, rightRight);
        NodeModel left = new NodeModel (5, null, null);
        NodeModel root = new NodeModel (6, left, right);

        NodeModel returnTree = logic.balanceTree(root);

        Assert.assertEquals(rightLeft.getValue(), returnTree.getValue());
        Assert.assertEquals(root.getValue(), returnTree.getLeftChild().getValue());
        Assert.assertEquals(left.getValue(), returnTree.getLeftChild().getLeftChild().getValue());
        Assert.assertEquals(rightLeftLeft.getValue(), returnTree.getLeftChild().getRightChild().getValue());
        Assert.assertEquals(right.getValue(), returnTree.getRightChild().getValue());
        Assert.assertEquals(rightRight.getValue(), returnTree.getRightChild().getRightChild().getValue());
    }

    @Test
    public void testLeftRotationWithLeftSideBalanceProblem () {
        NodeModel leftLeftLeft = new NodeModel(1, null, null);
        NodeModel leftLeft = new NodeModel(2, leftLeftLeft, null);
        NodeModel left = new NodeModel (3, leftLeft, null);
        NodeModel right = new NodeModel (6, null, null);
        NodeModel root = new NodeModel (4, left, right);

        NodeModel returnTree = logic.balanceTree(root);

        Assert.assertEquals(root.getValue(), returnTree.getValue());
        Assert.assertEquals(leftLeft.getValue(), returnTree.getLeftChild().getValue());
        Assert.assertEquals(leftLeftLeft.getValue(), returnTree.getLeftChild().getLeftChild().getValue());
        Assert.assertEquals(left.getValue(), returnTree.getLeftChild().getRightChild().getValue());
        Assert.assertEquals(right.getValue(), returnTree.getRightChild().getValue());
    }

    @Test
    public void testRightRotationWithRightSideBalanceProblem () {
        AVLTreeLogic logic = new AVLTreeLogic();
        NodeModel rightRightRight = new NodeModel (5, null, null);
        NodeModel rightRight = new NodeModel (4, null, rightRightRight);
        NodeModel right = new NodeModel (3, null, rightRight);
        NodeModel left = new NodeModel (1, null, null);
        NodeModel root = new NodeModel (2, left, right);

        NodeModel returnTree = logic.balanceTree(root);

        Assert.assertEquals(root.getValue(), returnTree.getValue());
        Assert.assertEquals(left.getValue(), returnTree.getLeftChild().getValue());
        Assert.assertEquals(rightRight.getValue(), returnTree.getRightChild().getValue());
        Assert.assertEquals(right.getValue(), returnTree.getRightChild().getLeftChild().getValue());
        Assert.assertEquals(rightRightRight.getValue(), returnTree.getRightChild().getRightChild().getValue());
    }

    @Test
    public void testBalanceTreeOnTreeThatIsBalanced () {
        AVLTreeLogic logic = new AVLTreeLogic();
        NodeModel right = new NodeModel (3, null, null);
        NodeModel left = new NodeModel (1, null, null);
        NodeModel root = new NodeModel (2, left, right);

        Assert.assertTrue(logic.equals(root, logic.balanceTree(root)));
    }
}