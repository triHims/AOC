import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SolutionDay1 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("input2"));
        ArrayList<Integer> als = new ArrayList<>();
        als.add(0);
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            line = line.trim();

            if(line.length()==0){
                als.add(0);
                continue;
            }

            als.set(als.size()-1, als.get(als.size()-1)+Integer.parseInt(line));

            
        }

        als.sort((a,b)->b-a);
        int ans=0;
        for(int i=0;i<3 && i< als.size();i++){
            ans+=als.get(i);
        }

        System.out.println(ans);
    }
}
