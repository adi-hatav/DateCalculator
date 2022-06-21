import java.util.ArrayDeque;

public class LevelMostOccurrences {
    public static int getLevelWithMostOccurrences(BinNode<Integer> node, int num) {
        ArrayDeque<Integer> depths = new ArrayDeque<>();
        ArrayDeque<BinNode<Integer>> nodes = new ArrayDeque<>();
        ArrayDeque<Integer> depthsData = new ArrayDeque<>();//array that contain the depth of each occurrence of num
        int maxLevel = -1;
        if (node != null) {
            nodes.addLast(node);
            depths.addLast(0);
            if (node.getData() == num) {
                depthsData.addLast(0);
                maxLevel = 0;
            }
            while (!nodes.isEmpty()) {
                BinNode<Integer> currentNode = nodes.pop();
                int currentDepth = depths.pop();
                if (currentNode.getRight() != null) {
                    nodes.addLast(currentNode.getRight());
                    depths.addLast(currentDepth + 1);
                    if (currentNode.getRight().getData() == num) {
                        depthsData.addLast(currentDepth + 1);
                        maxLevel = (maxLevel < currentDepth + 1) ? currentDepth + 1 : maxLevel;
                    }
                }
                if (currentNode.getLeft() != null) {
                    nodes.addLast(currentNode.getLeft());
                    depths.addLast(currentDepth + 1);
                    if (currentNode.getLeft().getData() == num) {
                        depthsData.addLast(currentDepth + 1);
                        maxLevel = (maxLevel < currentDepth + 1) ? currentDepth + 1 : maxLevel;
                    }
                }

            }
        }
        int maxOccurrences = 0;
        if (maxLevel == -1)
            return maxLevel;

        int[] histogram = new int[maxLevel+1];
        for (int depth : depthsData) {
            histogram[depth]++;
        }
        for (int i = 0; i < histogram.length; i++) {
            if (histogram[i] > maxOccurrences) {
                maxOccurrences = histogram[i];
                maxLevel = i;
            }
        }
        return maxLevel;

    }
}
