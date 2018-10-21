package bfs.bfsboot.bfs;

import java.util.LinkedList;

public class Node {
    private int height;
    private boolean isFilled = false;
    private boolean isBorder = false;
    private int x;
    private int y;
    private Surface surface;
    private LinkedList<Node> nodeQueue;

    Node(int height, int x, int y, Surface surface) {
        this.height = height;
        this.x = x;
        this.y = y;
        this.surface = surface;
        nodeQueue = surface.getNodeQueue();
    }

    public int getHeight() {
        return height;
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }

    public boolean isBorder() {
        return isBorder;
    }

    public void setBorder(boolean border) {
        isBorder = border;
    }

    @Override
    public String toString() {
        return height + "";
    }

    public void process() {
        System.out.println("Node: "+ height);
        Node leftNode = x == 0 ? null : surface.getNode(y, x - 1);
        Node topNode = y == 0 ? null : surface.getNode(y - 1, x);
        Node rightNode = x == surface.getXSize() - 1 ? null : surface.getNode(y, x + 1);
        Node bottomNode = y == surface.getYSize() - 1 ? null : surface.getNode(y + 1, x);

        addNodeToQ(leftNode);
        addNodeToQ(topNode);
        addNodeToQ(rightNode);
        addNodeToQ(bottomNode);
        System.out.println("Q: "+ surface.getNodeQueue());
        if (!isBorder) {
            int v = surface.getCurrentHeight() - height;
            surface.addVolume(v > 0 ? v : 0);
            setFilled(true);
        }
        System.out.println("V: "+ surface.getWaterVolume());
        System.out.println("-------");
        if (!nodeQueue.isEmpty()) {
            Node nextNode = nodeQueue.removeFirst();
            nextNode.process();
        }
    }

    private void addNodeToQ(Node node) {
        if (node == null || node.isFilled()
                || node.isBorder()
                || nodeQueue.contains(node)
                || (!isBorder && height > node.getHeight() && height > surface.getCurrentHeight())) {
            return;
        }
        nodeQueue.add(node);
    }
}
