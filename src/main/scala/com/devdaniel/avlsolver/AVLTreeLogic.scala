package com.devdaniel.avlsolver

import com.devdaniel.avlsolver.Model.NodeModel

class AVLTreeLogic {

  /**
    * calculates the height of a node
    *
    * @param node: Node of which height is calculated
    * @return height as Int
    */
  def height(node:NodeModel): Int = node match {
    case null => 0
    case node: NodeModel =>
      1 + Math.max(height(node.getLeftChild), height(node.getRightChild))
  }

  /**
    * Compares two nodes if they have the same values at the same nodes.
    *
    * @param node1: Node that is compared to node2.
    * @param node2: Node that is compared to node1.
    * @return if nodes and children are equal true else false.
    */
  def equals(node1: NodeModel, node2: NodeModel): Boolean = {
    if (node1 == null && node2 == null) {
      true
    } else if (node1 == null || node2 == null) {
      false
    } else {
      node1.getValue == node2.getValue &&
        equals(node1.getLeftChild, node2.getLeftChild) &&
        equals(node1.getRightChild, node2.getRightChild)
    }
  }

  /**
    * Counts the amount of children of a given node.
    *
    * @param node: Node of which the size is being determined.
    * @return amount of children of given node.
    */
  def size(node: NodeModel): Int = node match {
    case null => 0
    case node: NodeModel =>
      1 + size(node.getLeftChild) + size(node.getRightChild)
  }

  /**
    * Determines the balance difference of a node (height of left node vs height of right node).
    *
    * @param node: Node of which the balance is determined.
    * @return balance factor as Int
    */
  def balance(node: NodeModel): Int = node match {
    case null => 0
    case node: NodeModel =>
      height(node.getRightChild) - height(node.getLeftChild)
  }

  /**
    * Adds node to the currentNode according binary search tree rules.
    *
    * @param currentNode: Node where node is being added to.
    * @param node: Node that is being added to the currentNode.
    * @return tree where node has been added to.
    */
  def addNode(currentNode: NodeModel, node: NodeModel): NodeModel =
    currentNode match {
      case null => node
      case x if x.getValue > node.getValue =>
        currentNode.getLeftChild match {
          case null =>
            new NodeModel(currentNode.getValue,
              node,
              currentNode.getRightChild)
          case n: NodeModel =>
            new NodeModel(currentNode.getValue,
              addNode(n, node),
              currentNode.getRightChild)
        }
      case x if x.getValue < node.getValue =>
        currentNode.getRightChild match {
          case null =>
            new NodeModel(currentNode.getValue, currentNode.getLeftChild, node)
          case n: NodeModel =>
            new NodeModel(currentNode.getValue,
              currentNode.getLeftChild,
              addNode(n, node))
        }
      case _ =>
        throw new IllegalArgumentException(
          "Value of node cannot be same value as existing node")
    }

  /**
    * Performs a AVL left rotation on given node.
    *
    * @param node: Node which is being rotated.
    * @return Rotated node
    */
  def leftRotate(node: NodeModel): NodeModel = {
    if (node.getRightChild != null && node != null) {
      new NodeModel(
        node.getRightChild.getValue,
        new NodeModel(node.getValue,
          node.getLeftChild,
          node.getRightChild.getLeftChild),
        node.getRightChild.getRightChild
      )
    } else {
      throw new IllegalArgumentException("Node or node.right cannot be null")
    }
  }

  /**
    * Performs a AVL right rotation on given node.
    *
    * @param node: Node which is being rotated.
    * @return Rotated node
    */
  def rightRotate(node: NodeModel): NodeModel = {
    if (node.getLeftChild != null && node != null) {
      new NodeModel(node.getLeftChild.getValue,
        node.getLeftChild.getLeftChild,
        new NodeModel(node.getValue,
          node.getLeftChild.getRightChild,
          node.getRightChild)
      )
    } else {
      throw new IllegalArgumentException("Node or node.left cannot be null")
    }
  }

  /**
    * Checks if given node is AVL.
    *
    * @param node: Node that is being checked for being valid AVLTree.
    * @return returns true if given Node is AVL else returns false.
    */
  def isAVL(node: NodeModel): Boolean = node match {
    case null => true
    case n: NodeModel =>
      (n.getLeftChild == null || n.getValue > n.getLeftChild.getValue) &&
        (n.getRightChild == null || n.getValue < n.getRightChild.getValue) &&
        balance(n) < 2 && balance(n) > -2 &&
        isAVL(n.getLeftChild) && isAVL(n.getRightChild)
  }

  /**
    * Balances a tree when necessary
    *
    * @param node: Node which is being balanced
    * @return Rotated node
    */
  def balanceTree(node: NodeModel): NodeModel = {
    balance(node) match {
      case 2 =>
        val b = balance(node.getRightChild)
        Math.abs(b) match {
          case 2 =>
            new NodeModel(node.getValue,
              node.getLeftChild,
              balanceTree(node.getRightChild));
          case _ =>
            b match {
              case x if x > 0 => leftRotate(node)
              case x if x < 0 =>
                val t = rightRotate(node.getRightChild)
                leftRotate(
                  new NodeModel(node.getValue,
                    node.getLeftChild,
                    t))
            }
        }
      case -2 =>
        val b = balance(node.getLeftChild)
        Math.abs(b) match {
          case 2 =>
            new NodeModel(node.getValue,
              balanceTree(node.getLeftChild),
              node.getRightChild);
          case _ =>
            b match {
              case x if x < 0 => rightRotate(node)
              case x if x > 0 =>
                val t = leftRotate(node.getLeftChild)
                rightRotate(
                  new NodeModel(node.getValue,
                    t,
                    node.getRightChild))
            }
        }
      case _ =>
        node match {
          case _: NodeModel => new NodeModel(node.getValue, balanceTree(node.getLeftChild), balanceTree(node.getRightChild))
          case _ => node
        }

    }
  }
}
