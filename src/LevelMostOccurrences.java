import java.util.ArrayDeque;

/**
 * represents LevelMostOccurrences calculator
 */
public class LevelMostOccurrences {
    /**
     * calculates the level with most occurrences of given number in a tree
     * calculates the level with most occurrences of given number in a tree
     *
     * @param node root of binary tree to search on
     * @param num  number to search on tree
     * @return the level with most occurrences on this tree
     */
    public static int getLevelWithMostOccurrences(BinNode<Integer> node, int num) {
        ArrayDeque<BinNode<Integer>> nodes = new ArrayDeque<>(); //array that contain the nodes
        ArrayDeque<Integer> depths = new ArrayDeque<>(); //array that contain the depth of each node
        ArrayDeque<Integer> depthsData = new ArrayDeque<>(); //array that contain the depth of each occurrence of num
        int maxLevel = -1; // the level to return. if it is not updated we will return -1 that indicates
        // the num does not exist in node

        // iterate over the node if the node is not  empty
        if (node != null) {
            nodes.addLast(node);
            depths.addLast(0);
            if (node.getData() == num) {
                depthsData.addLast(0);
                maxLevel = 0;
            }
            // adds each node to the end of nodes array, add its depth to the end of the depths array
            // iterate over the nodes array until empty
            while (!nodes.isEmpty()) {
                BinNode<Integer> currentNode = nodes.pop();
                int currentDepth = depths.pop();
                // if current node has right child then adds it to the arrays
                if (currentNode.getRight() != null) {
                    nodes.addLast(currentNode.getRight());
                    depths.addLast(currentDepth + 1);
                    // if child's data is equal to the given number than adds its depth to the depthsData
                    if (currentNode.getRight().getData() == num) {
                        depthsData.addLast(currentDepth + 1);
                        // saves the max level in the tree
                        maxLevel = (maxLevel < currentDepth + 1) ? currentDepth + 1 : maxLevel;
                    }
                }
                // if current node has left child then adds it to the arrays
                if (currentNode.getLeft() != null) {
                    nodes.addLast(currentNode.getLeft());
                    depths.addLast(currentDepth + 1);
                    // if child's data is equal to the given number than adds its depth to the depthsData
                    if (currentNode.getLeft().getData() == num) {
                        depthsData.addLast(currentDepth + 1);
                        // saves the max level in the tree
                        maxLevel = (maxLevel < currentDepth + 1) ? currentDepth + 1 : maxLevel;
                    }
                }

            }
        }
        // saves the max occurrence in the tree
        int maxOccurrences = 0;
        if (maxLevel == -1)
            return maxLevel;

        // create histogram to count the number of appearances of the given num in each level
        int[] histogram = new int[maxLevel + 1];
        for (int depth : depthsData) {
            histogram[depth]++;
        }
        // iterate over the histogram to find the level with most occurrences
        for (int i = 0; i < histogram.length; i++) {
            if (histogram[i] > maxOccurrences) {
                maxOccurrences = histogram[i];
                maxLevel = i;
            }
        }
        return maxLevel;

    }
}
