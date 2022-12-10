import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class SolutionDay4 {
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

        int overLapRan =0;

        while(sc.hasNextLine()){
            String line = sc.nextLine();

            String[] sSpl = line.split("[,-]");

            int r1=Integer.valueOf(sSpl[0]);
            int e1=Integer.valueOf(sSpl[1]);
            int r2=Integer.valueOf(sSpl[2]);
            int e2=Integer.valueOf(sSpl[3]);

            if(( r1<r2 && r2<=e1 )  || ( r2<=r1 &&  r1<=e2 )){
                System.out.println(line);

                ++overLapRan;
            }
        }

        System.out.println(overLapRan);
        sc.close();


    }
}
