/**
 * This class is for having a tree with five children
 *
 * Children 1-4 are top, bottom, left, right, respectively when traversing
 * Content is -1 child
 */
public class Node {
    Node top;
    Node bottom;
    Node right;
    Node left;

    Object content;

    public Node getTop() {
        return top;
    }

    public Node getBottom() {
        return bottom;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public Object getContent() {
        return content;
    }

    public void setTop(Node top) {
        this.top = top;
    }

    public void setBottom(Node bottom) {
        this.bottom = bottom;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}