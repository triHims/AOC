import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.List;

public class SolutionDay10Part2 {

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

        Set<Integer> match = new HashSet<>(List.of(20, 60, 100, 140, 180, 220));

        boolean inProcess = false;
        int xReg = 1, arg = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0;  i<240; i++) {
            int local = i%40;
            if(local>=xReg-1 && local<=xReg+1){
                sb.append('#');
            }else{
                sb.append('.');
            }
            if(sb.length()==40){
                System.out.println(sb);
                sb.setLength(0);
            }
            if (!inProcess) {
                String line = sc.nextLine().trim();
                if (line.length() == 0) {
                    i--;
                    continue;
                }

                if (line.startsWith("addx")) {
                    inProcess = true;
                    arg = Integer.parseInt(line.substring(5));
                }

            } else {
                xReg += arg;
                arg = 0;
                inProcess = false;
            }

            
        }

    }
}
