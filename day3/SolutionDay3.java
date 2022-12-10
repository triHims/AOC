import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class SolutionDay3 {
    public static void main(String[] args) {

        Scanner sc;

        if (args.length > 0) {
            try {
                sc = new Scanner(new File(args[0]));
            } catch (FileNotFoundException e) {
                System.out.printf("The file with name %s not able to load\nExiting\n",args[0]);

                return ;
            }
        } else {
            sc = new Scanner(System.in);
        }

        int prisum = 0;
        HashMap<Character, Integer> hm = new HashMap<>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String line2 = sc.nextLine();
            String line3 = sc.nextLine();

            line.chars().boxed().map(r -> Character.valueOf((char) r.intValue())).collect(Collectors.toSet())
                    .forEach(r -> hm.put(r, hm.getOrDefault(r, 0) + 1));

            line2.chars().boxed().map(r -> Character.valueOf((char) r.intValue())).collect(Collectors.toSet())
                    .forEach(r -> hm.put(r, hm.getOrDefault(r, 0) + 1));
            line3.chars().boxed().map(r -> Character.valueOf((char) r.intValue())).collect(Collectors.toSet())
                    .forEach(r -> hm.put(r, hm.getOrDefault(r, 0) + 1));

            for (var st : hm.entrySet()) {
                if (st.getValue() == 3) {
                    prisum += charToPri(st.getKey());
                    break;
                }
            }

            hm.clear();

        }

        System.out.println(prisum);
        sc.close();

    }

    static int charToPri(char c) {
        if (c >= 'a') {
            return c - 'a' + 1;
        }
        return c - 'A' + 27;
    }
}
