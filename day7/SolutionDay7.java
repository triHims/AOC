import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.Iterator;

public class SolutionDay7 {
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

        Directory root = new Directory("/", null);
        Directory current = root;
        String lastCommand = "";

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();

            if (line.charAt(0) == '$') {
                String command = line.substring(2, 4);
                lastCommand = command;
                if ("cd".equals(command)) {
                    String arg = line.substring(5);
                    if (arg.equals("/")) {
                        current = root;
                    } else if (arg.equals("..")) {
                        current = current.parent;
                    } else {
                        assert current.type == Type.Directory;

                        var it = current.getChildrenIterator();

                        while (it.hasNext()) {
                            Node child = it.next();
                            if (child.type == Type.Directory && child.name.equals(arg)) {
                                current = (Directory) child;
                                break;
                            }

                        }

                    }

                } else if ("ls".equals(command)) {
                    lastCommand = command;
                }
            } else {
                if (lastCommand.equals("ls")) {
                    String[] tokens = line.split(" ");
                    if (tokens[0].equals("dir")) {
                        current.addChild(new Directory(tokens[1], current));
                    } else {
                        current.addChild(new FileN(tokens[1], Integer.parseInt(tokens[0]), current));
                    }

                }
            }

        }

        int spaceLeft = 70_000_000-root.size;

        int spaceToFree = 30_000_000 - spaceLeft;

        System.out.printf("spaceToFree: %d, spaceLeft: %d\n",spaceToFree,spaceLeft);

        System.out.println(findSumWithLessThen100000(root,spaceToFree));

    }

    static int findSumWithLessThen100000(Directory current,int spaceToFree) {
        if (current == null) {
            return Integer.MAX_VALUE;
        }

        int childsum = Integer.MAX_VALUE;

        var it = current.getChildrenIterator();

        while (it.hasNext()) {
            Node child = it.next();
            if (child.type == Type.Directory) {
                childsum=Math.min( childsum , findSumWithLessThen100000((Directory) child,spaceToFree) );
            }

        }

        if (current.size >= spaceToFree) {
            childsum = Math.min(childsum, current.size);
        }

        return childsum;

    }
}

enum Type {
    Directory, File
}

class Node {
    int size;
    Type type;
    String name;
}

class Directory extends Node {
    private Set<Node> children;
    Directory parent;

    Directory(String name, Directory parent) {
        this.name = name;
        type = Type.Directory;
        children = new TreeSet<>((Node n1, Node n2) -> {
            if(n1.type == n2.type ){
                return n1.name.compareTo(n2.name);
            }
            else if(n1.type==Type.File){
                return -1;
            }
            return 1;

        });
        this.parent = parent;
        this.size = 0;
    }

    void addChild(Node node) {
        children.add(node);

        var sizeNode = this;

        while (sizeNode != null) {
            sizeNode.size += node.size;
            sizeNode = sizeNode.parent;
        }
    }

    Iterator<Node> getChildrenIterator(){
        return this.children.iterator();
    }

}

class FileN extends Node {
    FileN(String name, int size, Node parent) {
        this.name = name;
        type = Type.File;
        this.size = size;
    }
}
