import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class SolutionDay12 {

    public static void main(String[] args) {

        Scanner sc;

        if (args.length > 0) {
            try {
                sc = new Scanner(new File(args[0]));
            } catch (FileNotFoundException e) {
                System.out.printf("The file with name %s not able to load\nExiting\n", args[0]);
                return;
            }
        } else {
            sc = new Scanner(System.in);
        }

        ArrayList<ArrayList<Character>> als = new ArrayList<>();
        PosPair end = new PosPair(0, 0);

        ArrayList<PosPair> startList = new ArrayList<>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();

            var locl = new ArrayList<Character>();
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (c == 'S') {
                    var start = new PosPair(als.size(), locl.size());
                    startList.add(start);
                    locl.add('a');
                } else if (c == 'E') {
                    end = new PosPair(als.size(), locl.size());
                    locl.add('z');
                } else {

                    if (c == 'a') {
                        var start = new PosPair(als.size(), locl.size());
                        startList.add(start);
                    }
                    locl.add(c);
                }
            }

            als.add(locl);

        }

        int ans = Integer.MAX_VALUE;

        for (PosPair posPair : startList) {
            ans = Math.min(ans, runDijKstra(posPair, end, als));
        }

        System.out.println(ans);
    }

    static int runDijKstra(PosPair start, PosPair end, ArrayList<ArrayList<Character>> als) {
        int ans = Integer.MAX_VALUE;
        int visit[][] = new int[als.size()][als.get(0).size()];
        PriorityQueue<PairStore> bfs = new PriorityQueue<>((a, b) -> {
            return a.step - b.step;
        });
        bfs.add(new PairStore(start, 0));

        while (!bfs.isEmpty()) {

            var curPair = bfs.poll();

            if (end.equals(curPair.loc)) {
                ans = Math.min(curPair.step, ans);
                continue;
            }

            if (curPair.step >= ans || visit[curPair.loc.x][curPair.loc.y] == 1) {
                continue;
            }

            var location = curPair.loc;
            var charAtLocation = als.get(location.x).get(location.y).charValue();
            visit[location.x][location.y] = 1;
            // up
            if (location.x - 1 >= 0 && als.get(location.x - 1).get(location.y).charValue() <= (charAtLocation + 1)) {
                bfs.add(new PairStore(new PosPair(location.x - 1, location.y), curPair.step + 1));
            }
            // down
            if (location.x + 1 < als.size()
                    && als.get(location.x + 1).get(location.y).charValue() <= (charAtLocation + 1)) {
                bfs.add(new PairStore(new PosPair(location.x + 1, location.y), curPair.step + 1));
            }
            // left
            if (location.y - 1 >= 0 && als.get(location.x).get(location.y - 1).charValue() <= (charAtLocation + 1)) {
                bfs.add(new PairStore(new PosPair(location.x, location.y - 1), curPair.step + 1));
            }
            // right
            if (location.y + 1 < als.get(0).size()
                    && als.get(location.x).get(location.y + 1).charValue() <= (charAtLocation + 1)) {
                bfs.add(new PairStore(new PosPair(location.x, location.y + 1), curPair.step + 1));
            }

        }

        return ans;
    }
}

class PairStore {
    PosPair loc;
    int step;

    PairStore(PosPair pair, int step) {
        this.loc = pair;
        this.step = step;
    }
}

class PosPair {
    int x;
    int y;

    PosPair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof PosPair)) {
            return false;
        }

        PosPair c = (PosPair) obj;

        return this.x == c.x && this.y == c.y;
    }

    @Override
    public int hashCode() {
        int p = 999;

        return p * x + y;
    }

}
