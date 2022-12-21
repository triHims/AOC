import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class SolutionDay11 {
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
        while(sc.hasNextLine()){
            String line = sc.nextLine();

            if(line.startsWith("Monkey ")){
                Monke saru = new Monke();
                String startingItems[] = sc.nextLine().trim().substring(16).split(", ");
                
                LinkedList<Integer> tempLst = new LinkedList<>();

                for (var it : startingItems) {
                    tempLst.add(Integer.parseInt(it));
                    
                }

                saru.listItem=tempLst;


                String operations = sc.nextLine().trim().substring(17);

                saru.operations=operations;

                saru.testDivide = Integer.parseInt(sc.nextLine().trim().substring(19));

                saru.toGiveToArr[1]=Integer.parseInt(sc.nextLine().trim().substring(25));
                saru.toGiveToArr[0]=Integer.parseInt(sc.nextLine().trim().substring(26));


                monkeList.add(saru);

            }
            
        }


        int rounds= 20;


        while(rounds-- > 0){
            for (var monkeEach : monkeList) {
                var monkeItemLst = monkeEach.listItem;

                var itLst = monkeItemLst.iterator();

                while(itLst.hasNext()){
                    int worry = itLst.next();
                    itLst.remove();

                    worry = processWorryOP(worry,monkeEach.operations);
                    
                    worry/=3;

                    if(worry%monkeEach.testDivide==0){
                        monkeList.get(monkeEach.toGiveToArr[1]).listItem.add(worry);
                    }else{
                        monkeList.get(monkeEach.toGiveToArr[0]).listItem.add(worry);
                    }
                    
                    ++monkeEach.activeCount;
                }

            }
        }


        int maximum =0;
        int secondmaximum = -1;

        for (var  monkeEach : monkeList) {
            if(monkeEach.activeCount>maximum){
                secondmaximum=maximum;
                maximum=monkeEach.activeCount;
            }else if(monkeEach.activeCount>secondmaximum){
                secondmaximum=monkeEach.activeCount;
            }
        }

        System.out.println(maximum*secondmaximum);
    }

static int processWorryOP(int worry, String operations ){
    String tokens[] = operations.split(" ");

    int op1,op2;

    if(tokens[0].equals("old")){
        op1 = worry;
    }else{
        op1 = Integer.parseInt(tokens[0]);
    }
    if(tokens[2].equals("old")){
        op2 = worry;
    }else{
        op2 = Integer.parseInt(tokens[2]);
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
            return 0;
        }


}

}

class Monke {
    LinkedList<Integer> listItem ;
    String operations;
    int testDivide;
    int activeCount=0;
    int toGiveToArr[]=new int[2];
}
