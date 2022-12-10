import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SolutionDay8 {
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

        ArrayList<ArrayList<Integer>> arrList = new ArrayList<>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();

            ArrayList<Integer> temp = new ArrayList<>();
            for (int i = 0; i < line.length(); i++) {
                if (Character.isDigit(line.charAt(i))) {
                    temp.add(line.charAt(i) - '0');
                }
            }

            arrList.add(temp);

        }

        int rows = arrList.size();
        int cols = arrList.get(0).size();

        int ans = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                int currentHeight = arrList.get(i).get(j);
                // go top
                int k = i - 1, count = 0;

                while (k >= 0) {

                    count++;
                    if (arrList.get(k).get(j) >= currentHeight) {
                        break;
                    }
                    k--;

                }

                int top = count;
                //bottom
                k = i+1;
                count=0;
                while (k<rows) {

                    count++;
                    if (arrList.get(k).get(j) >= currentHeight) {
                        break;
                    }
                    k++;

                }

                int bottom = count;


                //left
                k = j-1;
                count=0;
                while (k>=0) {

                    count++;
                    if (arrList.get(i).get(k) >= currentHeight) {
                        break;
                    }
                    k--;

                }

                int left = count;
                //right
                k = j+1;
                count=0;
                while (k<cols) {

                    count++;
                    if (arrList.get(i).get(k) >= currentHeight) {
                        break;
                    }
                    k++;

                }

                int right = count;


                ans = Math.max(ans,top*bottom*left*right);

            }

        }

        System.out.println(ans);

    }
}
