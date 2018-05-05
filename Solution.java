package solution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import javafx.scene.layout.Priority;
import jigsaw.Jigsaw;
import jigsaw.JigsawNode;


/**
 * 鍦ㄦ绫讳腑濉厖绠楁硶锛屽畬鎴愰噸鎷煎浘娓告垙锛圢-鏁扮爜闂锛�
 */
public class Solution extends Jigsaw {

    /**
     * 鎷煎浘鏋勯�犲嚱鏁�
     */
    public Solution() {
    	
    }

    /**
     * 鎷煎浘鏋勯�犲嚱鏁�
     * @param bNode - 鍒濆鐘舵�佽妭鐐�
     * @param eNode - 鐩爣鐘舵�佽妭鐐�
     */
    public Solution(JigsawNode bNode, JigsawNode eNode) {
        super(bNode, eNode);
    }

    /**
     *锛堝疄楠屼竴锛夊箍搴︿紭鍏堟悳绱㈢畻娉曪紝姹傛寚瀹�5*5鎷煎浘锛�24-鏁扮爜闂锛夌殑鏈�浼樿В
     * 濉厖姝ゅ嚱鏁帮紝鍙湪Solution绫讳腑娣诲姞鍏朵粬鍑芥暟锛屽睘鎬�
     * @param bNode - 鍒濆鐘舵�佽妭鐐�
     * @param eNode - 鐩爣鐘舵�佽妭鐐�
     * @return 鎼滅储鎴愬姛鏃朵负true,澶辫触涓篺
     */
    public boolean BFSearch(JigsawNode bNode, JigsawNode eNode) {
    	int searchedNodesNum;
    	HashSet<JigsawNode> visitedList = new HashSet<>(1000);
    	Queue<JigsawNode> exploreList = new LinkedList<>();
    	
        this.beginJNode = new JigsawNode(bNode);
        this.endJNode = new JigsawNode(eNode);
        this.currentJNode = null;

        // 访问节点数大于29000个则认为搜索失败
        final int MAX_NODE_NUM = 29000;
        final int DIRS = 4;

        // 重置求解标记
        searchedNodesNum = 0;
        // (1)将起始节点放入exploreList中
        visitedList.add(this.beginJNode);
        exploreList.add(this.beginJNode);

        // (2) 如果exploreList为空，或者访问节点数大于MAX_NODE_NUM个，则搜索失败，问题无解;否则循环直到求解成功
        while (searchedNodesNum < MAX_NODE_NUM && !exploreList.isEmpty()) {
            searchedNodesNum++;

            // (2-1)取出exploreList的第一个节点N，置为当前节点currentJNode
            //      若currentJNode为目标节点，则搜索成功，计算解路径，退出
            this.currentJNode = exploreList.poll();
            if (this.currentJNode.equals(eNode)) {
                this.getPath();
                break;
            }

            // 记录并显示搜索过程
            // System.out.println("Searching.....Number of searched nodes:" + searchedNodesNum +
            //     "    Est:" + this.currentJNode.getEstimatedValue() +
            //     "    Current state:" + this.currentJNode.toString());

            JigsawNode[] nextNodes = new JigsawNode[]{
                new JigsawNode(this.currentJNode), new JigsawNode(this.currentJNode),
                new JigsawNode(this.currentJNode), new JigsawNode(this.currentJNode)
            };

            // (2-2)寻找所有与currentJNode邻接且未曾被发现的节点，将它们按代价估值从小到大排序插入exploreList中
            //         并加入visitedList中，表示已发现
            for (int i = 0; i < DIRS; i++) {
                if (nextNodes[i].move(i) && !visitedList.contains(nextNodes[i])) {
                    JigsawNode tempJNode = new JigsawNode(nextNodes[i]);
                    visitedList.add(nextNodes[i]);
                    exploreList.add(nextNodes[i]);
                }
            }
        }

        System.out.println("Jigsaw AStar Search Result:");
        System.out.println("Begin state:" + this.getBeginJNode().toString());
        System.out.println("End state:" + this.getEndJNode().toString());
        System.out.println("Solution Path: ");
        System.out.println(this.getSolutionPath());
        System.out.println("Total number of searched nodes:" + searchedNodesNum);
        System.out.println("Depth of the current node is:" + this.getCurrentJNode().getNodeDepth());
        return this.isCompleted();
    }


    /**
     *锛圖emo+瀹為獙浜岋級璁＄畻骞朵慨鏀圭姸鎬佽妭鐐筳Node鐨勪唬浠蜂及璁″��:f(n)
     * 濡� f(n) = s(n). s(n)浠ｈ〃鍚庣画鑺傜偣涓嶆纭殑鏁扮爜涓暟
     * 姝ゅ嚱鏁颁細鏀瑰彉璇ヨ妭鐐圭殑estimatedValue灞炴�у��
     * 淇敼姝ゅ嚱鏁帮紝鍙湪Solution绫讳腑娣诲姞鍏朵粬鍑芥暟锛屽睘鎬�
     * @param jNode - 瑕佽绠椾唬浠蜂及璁″�肩殑鑺傜偣
     */
    public void estimateValue(JigsawNode jNode) {
        int s = 0; // 鍚庣画鑺傜偣涓嶆纭殑鏁扮爜涓暟
        int dimension = JigsawNode.getDimension();
        for (int index = 1; index < dimension * dimension; index++) {
            if (jNode.getNodesState()[index] + 1 != jNode.getNodesState()[index + 1]) {
                s++;
            }
        }
        jNode.setEstimatedValue(s);
    }
}
