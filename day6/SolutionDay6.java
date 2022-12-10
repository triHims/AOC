
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class SolutionDay6 {
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

        String line = sc.nextLine();
        line = line.strip();
        HashMap<Character, Integer> hmp = new HashMap<>();
        if (line.length() < 14) {
            System.out.println("Not possible ");
            return;
        }

        for(int i=0;i<13;i++){
            increment(hmp,line.charAt(i));
        }




        int i = 13;
        for (; i < line.length(); i++) {
            increment(hmp, line.charAt(i));

            if (hmp.size() == 14) {
                break;
            }

            decrement(hmp, line.charAt(i - 13));

        }

        if (i == line.length())
            System.out.println("Not possible");
        else
            System.out.println("Char to be read are : " + (i + 1));

    }

    static void increment(HashMap<Character, Integer> hmp, char c) {
        hmp.put(c, hmp.getOrDefault(c, 0) + 1);
    }

    static void decrement(HashMap<Character, Integer> hmp, char c) {
        if (hmp.get(c) == null)
            return;

        if (hmp.get(c) == 1)
            hmp.remove(c);
        else
            hmp.put(c, hmp.get(c) - 1);

    }
}
