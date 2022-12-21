import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SolutionDay13 {
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
:
        Object parsedLine1 = parseLine(l,0);

    }


    Object parseLine(String l,int index){
       int endInd =index; 

        if(l.charAt(index)=='['){
            endInd++;

            var als = new ArrayList<Object>();


        }

        
        
    }
}
