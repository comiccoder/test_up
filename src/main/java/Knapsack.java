
public class Knapsack {

    public static class Element implements Comparable {
        int id;//物品编号
        double d;

        public Element(int id, double d) {
            this.id = id;
            this.d = d;
        }

        public int compareTo(Object x) {
            double xd = ((Element) x).d;//递减顺序排列
            if (d < xd) return -1;
            if (d == xd) return 0;
            return 1;
        }
    }

    double c;//背包容量
    int n;//物品数
    double[] w;//物品重量数组
    double[] p;//物品价值数组
    double cw;//当前重量
    double cp;//当前价值
    double bestp;//最优价值
    int[] x;//当前装入背包顺序
    int[] bestx;//最优装入背包顺序
    Element[] q;//q为单位重量价值数组

    public double knapsack(double[] pp, double[] ww, double cc) {
        //初始化
        c = cc;
        n = pp.length - 1;
        cw = 0;
        cp = 0;
        bestp = 0;
        x = new int[n + 1];
        bestx = new int[n + 1];
        //q为单位重量价值数组
        q = new Element[n + 1];
        for (int i = 0; i <= n; i++) {
            q[i] = new Element(i, pp[i] / ww[i]);
        }
        //将个物品依单位重量价值从大到小排列
        java.util.Arrays.sort(q);
        p = new double[n + 1];
        w = new double[n + 1];
        for (int i = 1; i <= n; i++) {
            p[i] = pp[q[i].id];
            w[i] = ww[q[i].id];
        }
        backtrack(1);
        return bestp;
    }

    public void backtrack(int i) {
        if (i > n) {//到达叶子节点
            bestp = cp;
            for (int j = 1; j <= n; j++) {//保存最优值对应的包的编号
                bestx[j] = x[j];
            }
            return;
        }
        if (cw + w[i] <= c) {//左子树
            x[i] = 1;
            cw += w[i];
            cp += p[i];
            backtrack(i + 1);
            cw -= w[i];//恢复现场
            cp -= p[i];
        }
        if (bound(i + 1) > bestp) {
            x[i] = 0;
            backtrack(i + 1);
        }
    }

    public double bound(int i) {//上界函数
        double cleft = c - cw;
        double bound = cp;
        while (i <= n && w[i] <= cleft) {
            cleft -= w[i];
            bound += p[i];
            i++;
        }
        if (i <= n) {
            bound += p[i] * cleft / w[i];
        }
        return bound;
    }

    public static void main(String[] args) {
        double[] weight = {0, 71, 34, 82, 23, 1, 88, 12, 57, 10, 68, 5, 33, 37, 69, 98, 24, 26, 83, 16, 26, 18, 43, 52, 71, 22, 65, 68, 8, 40, 40, 24, 72, 16, 34, 10, 19, 28, 13, 34, 98, 29, 31, 79, 33, 60, 74, 44, 56, 54, 17};
        double[] price = {0, 26, 59, 30, 19, 66, 85, 94, 8, 3, 44, 5, 1, 41, 82, 76, 1, 12, 81, 73, 32, 74, 54, 62, 41, 19, 10, 65, 53, 56, 53, 70, 66, 58, 22, 72, 33, 96, 88, 68, 45, 44, 61, 78, 78, 6, 66, 11, 59, 83, 48};
        double cc = 300;
        //double[] weight={0,7,3,4,5};
        //double[] price={0,42,12,40,25};
        //double cc=10;
        Knapsack k = new Knapsack();
        double best = k.knapsack(price, weight, cc);
        System.out.println("最优值：" + best);
        System.out.println("选中的物品编号分别是：");
        for (int i = 1; i < k.bestx.length; i++) {
            if (k.bestx[i] == 1) {
                System.out.print(k.q[i].id + " ");
            }
        }
    }
}
