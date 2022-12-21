package part2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class SolutionDay11Part2 {
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


        ArrayList<Monke> monkeList = new ArrayList<>();
        int MOD =1;
        while(sc.hasNextLine()){
            String line = sc.nextLine();

            if(line.startsWith("Monkey ")){
                Monke saru = new Monke();
                String startingItems[] = sc.nextLine().trim().substring(16).split(", ");
                
                LinkedList<Long> tempLst = new LinkedList<>();

                for (var it : startingItems) {
                    tempLst.add(Long.parseLong(it));
                    
                }

                saru.listItem=tempLst;


                String operations = sc.nextLine().trim().substring(17);

                saru.operations=operations;

                saru.testDivide = Integer.parseInt(sc.nextLine().trim().substring(19));
                MOD*= saru.testDivide;

                saru.toGiveToArr[1]=Integer.parseInt(sc.nextLine().trim().substring(25));
                saru.toGiveToArr[0]=Integer.parseInt(sc.nextLine().trim().substring(26));


                monkeList.add(saru);

            }
            
        }


        int rounds= 10000;


        while(rounds-- > 0){
            for (var monkeEach : monkeList) {
                var monkeItemLst = monkeEach.listItem;

                var itLst = monkeItemLst.iterator();
                long ops = 0;
                while(itLst.hasNext()){
                    long worry = itLst.next();
                    itLst.remove();

                    worry = processWorryOP(worry,monkeEach.operations)%MOD;
                    
                    // worry/=3;

                    if(worry%monkeEach.testDivide==0){
                        monkeList.get(monkeEach.toGiveToArr[1]).listItem.add(worry);
                    }else{
                        monkeList.get(monkeEach.toGiveToArr[0]).listItem.add(worry);
                    }
                    
                    ++ops;
                }

                monkeEach.active+=ops*1L;

            }
        }


        long maximum =0;
        long secondmaximum = -1;

        for (var  monkeEach : monkeList) {
            if(monkeEach.active>maximum){
                secondmaximum=maximum;
                maximum=monkeEach.active;
            }else if(monkeEach.active>secondmaximum){
                secondmaximum=monkeEach.active;
            }
        }
        int tmk =0;
        for (var  monkeEach : monkeList) {
            System.out.printf("Monke %d, Activity %d \n",tmk++,monkeEach.active);
        }
        System.out.printf("max: %d, secondMax : %d \n",maximum,secondmaximum);
        System.out.println(1L*maximum*secondmaximum);
    }

static long processWorryOP(long worry, String operations){
    String tokens[] = operations.split(" ");

    long op1,op2;

    if(tokens[0].equals("old")){
        op1 = worry;
    }else{
        op1 = Long.parseLong(tokens[0]);
    }
    if(tokens[2].equals("old")){
        op2 = worry;
    }else{
        op2 = Long.parseLong(tokens[2]);
    }


        switch (tokens[1]) {
            case "+":
            return op1 + op2;
            case "-":
            return op1 - op2;
            case "/":
            return op1 / op2;
            case "*":
            return op1 * op2;

            default:
            return 0L;
        }


}

}

class Monke {
    LinkedList<Long> listItem;
    String operations;
    int testDivide;
    public long active=0;

    int toGiveToArr[]=new int[2];
}
