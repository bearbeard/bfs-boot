package bfs.bfsboot.bfs;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

public class Node {
    private int height;
    private boolean isFilled = false;
    private boolean isBorder = false;
    private int x;
    private int y;
    private Surface surface;
    private LinkedList<Node> nodeQueue;
    private Node leftNode;
    private Node topNode;
    private Node rightNode;
    private Node bottomNode;

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
        leftNode = x == 0 ? null : surface.getNode(y, x - 1);
        topNode = y == 0 ? null : surface.getNode(y - 1, x);
        rightNode = x == surface.getXSize() - 1 ? null : surface.getNode(y, x + 1);
        bottomNode = y == surface.getYSize() - 1 ? null : surface.getNode(y + 1, x);

        addNodeToQ(leftNode);
        addNodeToQ(topNode);
        addNodeToQ(rightNode);
        addNodeToQ(bottomNode);
        System.out.println("Q: "+ surface.getNodeQueue());
        if (!isBorder) {
            /*if (surface.getCurrentHeight() > height) {
                surface.setCurrentHeight(getMinNeighbourNodeHeight());
            }*/
            int v = surface.getCurrentHeight() - height;
            surface.addVolume(v > 0 ? v : 0);
            setFilled(true);
        }
        while (!nodeQueue.isEmpty()) {
            Node nextNode;
            nextNode = nodeQueue.stream().min(Comparator.comparingInt(Node::getHeight)).get();
            if (nextNode.getHeight() > surface.getCurrentHeight() && surface.getBorder().stream().anyMatch(node -> node.getHeight() < nextNode.getHeight())) {
                break;
            } else {
                nodeQueue.remove(nextNode);
                if (nextNode.getHeight() < height && surface.getCurrentHeight() < height) {
                    surface.setCurrentHeight(height);
                }
                nextNode.process();
            }
        }
        System.out.println("V: "+ surface.getWaterVolume());
        System.out.println("-------");
    }

    private int getMinNeighbourNodeHeight() {
        Node[] nodes = {leftNode, topNode, rightNode, bottomNode};
        return Arrays.stream(nodes).filter(Objects::nonNull).map(node -> node.height).min(Integer::compareTo).get();
    }

    private void addNodeToQ(Node nodeToAdd) {
        //|| (!isBorder && height > nodeToAdd.getHeight() && height > surface.getCurrentHeight())
        if (nodeToAdd == null || nodeToAdd.isFilled()
                || nodeToAdd.isBorder()
                || nodeQueue.contains(nodeToAdd)) {
            return;
        }
        nodeQueue.add(nodeToAdd);
    }
}
