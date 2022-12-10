
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SolutionDay5 {
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
        

        boolean BLockInputEnded=false;

        ArrayList<LinkedList<Character>> blocKStor = new ArrayList<>(10);

        for (int i = 0; i < 10; i++) {

            blocKStor.add(new LinkedList<>());
            
        }

        //ReadLine and then second loop reads the line chars
        while(sc.hasNextLine() && BLockInputEnded==false){
            String inputLine = sc.nextLine();

            
            int i =0;


            while(i<inputLine.length()){
                char block = inputLine.charAt(i+1);


                if(Character.isDigit(block)){
                    BLockInputEnded=true;
                    break;
                }
                else if(Character.isAlphabetic(block)){
                    int index = i/4;

                    blocKStor.get(index).addFirst(block);


                }

                i+=4;

            }


            
        }

        sc.nextLine();

        while(sc.hasNextLine()){
            String line = sc.nextLine();
            line = line.strip();

            if(line.length()<18 )
                continue;

            String split[] = line.split(" ");
            int count = Integer.parseInt(split[1]);
            int from = Integer.parseInt(split[3])-1;
            int to = Integer.parseInt(split[5])-1;

               var it = blocKStor.get(to).listIterator(blocKStor.get(to).size());
            while(count-- > 0){
                Character tarChar = blocKStor.get(from).pollLast();
                it.add(tarChar);
                it.previous();
            }

            
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            try {
                sb.append(blocKStor.get(i).getLast());
            } catch (NoSuchElementException e) {
                continue;
            }
            
            
        }
        System.out.println(sb);
        sc.close();
    }

    
}


