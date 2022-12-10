import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Consumer;

public class SolutionDay9 {
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

        Pointer arr[] = new Pointer[10];

        for (int i = 0; i < 10; i++) {
            arr[i] = new Pointer(0, 0);
        }
        Set<Pointer> tailPos = new HashSet<>();
        Consumer<Pointer> myConsumer;

        tailPos.add(arr[0].clone());
        while (sc.hasNextLine()) {
            String line = sc.nextLine().strip();
            if (line.length() < 2) {
                continue;
            }

            String splitted[] = line.split(" ");

            int move = Integer.parseInt(splitted[1]);

            while (move-- > 0) {
                intFollowDirection(arr[0], splitted[0].charAt(0), 1);
                for (int i = 1; i < 10; i++) {
                    if (i == 9) {
                        myConsumer = (point) -> {
                            tailPos.add(point);
                        };
                    } else {
                        myConsumer = (point) -> {
                        };
                    }
                    squareOffHeadTail(arr[i - 1], arr[i], myConsumer);
                }

            }

        }

        System.out.println(tailPos.size());

    }

    /*
     * 
     *
     * 1 2 3 4 5
     * 0 1 2 3 4
     */
    static void intFollowDirection(Pointer head, char direction, int move) {
        switch (direction) {
            case 'L':
                head.y -= move;
                break;
            case 'R':
                head.y += move;
                break;
            case 'U':
                head.x += move;
                break;
            case 'D':
                head.x -= move;
                break;

            default:
                break;
        }
    }

    static boolean isSameRow(Pointer head, Pointer tail) {
        if (head.x == tail.x || head.y == tail.y)
            return true;
        return false;

    }

    static void squareOffHeadTail(Pointer head, Pointer tail, Consumer<Pointer> tailPos) {

        boolean change = false;
        if (Math.abs(head.x - tail.x) > 1) {
            tail.x = tail.x + ((head.x - tail.x > 0) ? 1 : -1);
            change |= true;

        } 

        if (Math.abs(head.y - tail.y) > 1) {
            tail.y = tail.y + ((head.y - tail.y > 0) ? 1 : -1);
            change |= true;

        }

        if(change){
            tailPos.accept(tail.clone());
        }

    }
}

class Pointer {
    int x;
    int y;

    Pointer(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Pointer clone() {
        return new Pointer(this.x, this.y);

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pointer other = (Pointer) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

}
