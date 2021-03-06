package bfs.bfsboot.bfs;

import java.util.*;

public class Surface {
    private int xSize;
    private int ySize;
    private List<List<Node>> elements = new ArrayList<>();
    private final LinkedList<Node> border = new LinkedList<>();
    private final LinkedList<Node> nodeQueue = new LinkedList<>();
    private int waterVolume = 0;
    // высота вершины, с которой начали обход
    private int currentHeight;

    public Surface(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
        Random r = new Random();
        for (int i = 0; i < ySize; i++) {
            List<Node> a = new ArrayList<>();
            elements.add(a);
            for (int j = 0; j < xSize; j++) {
                Node n = new Node(r.nextInt(10), j, i, this);
                a.add(n);
            }
        }
        makeBorderList();
    }

    /**
     * Кладем вершины с границы в отдельный массив. С него начинается обработка
     */
    private void makeBorderList() {
        Node node;

        for (int i = 0; i < elements.get(0).size(); i++) {
            node = elements.get(0).get(i);
            node.setBorder(true);
            border.add(node);

            node = elements.get(elements.size() - 1).get(i);
            node.setBorder(true);
            border.add(node);
        }
        for (int i = 1; i < elements.size() - 1; i++) {
            node = elements.get(i).get(0);
            node.setBorder(true);
            border.add(node);

            node = elements.get(i).get(elements.get(i).size() - 1);
            node.setBorder(true);
            border.add(node);
        }
        //border.sort(Comparator.comparingInt(Node::getHeight));
    }

    public int calculateVolume() {
        while (!border.isEmpty()) {
            Node borderNode = border.stream().min(Comparator.comparingInt(Node::getHeight)).get();
            currentHeight = borderNode.getHeight();
            border.remove(borderNode);
            borderNode.process();
        }
        return waterVolume;
    }

    public LinkedList<Node> getBorder() {
        return border;
    }

    public Node getNode(int y, int x) {
        return elements.get(y).get(x);
    }

    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }

    public void addVolume(int value) {
        this.waterVolume += value;
    }

    public LinkedList<Node> getNodeQueue() {
        return nodeQueue;
    }

    public int getCurrentHeight() {
        return currentHeight;
    }

    public void setCurrentHeight(int currentHeight) {
        this.currentHeight = currentHeight;
    }

    public int getWaterVolume() {
        return waterVolume;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (List<Node> l : elements) {
            sb.append(l).append("\n");
        }
        return sb.toString();
    }

    public String toWebString() {
        StringBuilder sb = new StringBuilder();
        for (List<Node> l : elements) {
            sb.append(l).append("<br>");
        }
        return sb.toString();
    }
}
