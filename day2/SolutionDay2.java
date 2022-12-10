import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SolutionDay2 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("input2.txt"));
        int ans =0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            line = line.trim();

            if (line.length() == 0) {
                continue;
            }

            char fist = line.charAt(0);
            char second = line.charAt(2);

            ans+=getDuelByB(fist-'A',second-'X');

        }
        System.out.println(ans);

    }


    static int getDuelByB(int A,int B){
        // 0 - roc , 1 pap , 2 sic
        if(B==1){
            return 3+A+1;
        }
        else if(B==0){
            return 0+(A-1+3)%3+1;
        }
        return 6+(A+1+3)%3+1;
            

    }

    static int getOutCome(char c) {
        switch (c) {
            case 'X':
                return 1;

            case 'Y':
                return 2;
            case 'Z':
                return 3;
            default:
                return 0;
        }
    }
}
