//Austen Haymond
//This program allows the user to test an implementation of a Table using a Binary Search Tree

import java.util.*;

public class program7 {

    static Scanner kb = new Scanner(System.in);
    static Random rand = new Random();
    public static void main(String[] args) {
        System.out.println("Welcome to the Binary Search Tree Test Program");
        int mMenu, tMenu;
        do {
            mMenu = mainMenu();
            if (mMenu == 1) {
                Table testTable = new Table();
                do {
                    tMenu = tableMenu();
                    if (tMenu == 1) {
                        testTable = new Table();
                        System.out.println("\nTable initialized... _root = null");
                    }
                    else if (tMenu == 2) {
                        NBAPlayer player;
                        System.out.println("\nEnter player number");
                        int jersey = kb.nextInt();
                        System.out.println("Enter the player's team");
                        String team = kb.next();
                        System.out.println("Enter the player's name");
                        String name = kb.next();
                        System.out.println("Enter the player's points per game");
                        double avgPPG = kb.nextDouble();
                        player = new NBAPlayer(jersey, team, name, avgPPG);
                        System.out.println("Player added to Table");
                        System.out.println(player);
                        NBAPlayerKey pKey = player;
                        testTable.insert(pKey);
                    }
                    else if (tMenu == 3) {
                        NBAPlayerKey key;
                        System.out.println("\nEnter player number");
                        int jersey = kb.nextInt();
                        System.out.println("Enter the player's team");
                        String team = kb.next();
                        key = new NBAPlayerKey(jersey, team);
                        System.out.println("Search result: ");
                        NBAPlayerKey result = (NBAPlayerKey) testTable.search(key);
                        System.out.println(result);
                    }
                    else if (tMenu == 4) {
                        System.out.println("\nTree height: " + testTable.getHeight());
                    }
                    else if (tMenu == 5) {
                        System.out.println("\nTable size: " + testTable.getSize());
                    }
                    else if (tMenu == 6) {
                        testTable.showTree();
                    }
                    else if (tMenu == 7) {
                        System.out.println(testTable);
                    }
                }
                while (tMenu != 8);
            }
            else if (mMenu == 2) {
                System.out.println("\nCreating test tables...");
                int maxH = 0;
                for (int iy = 3; iy <= 15; ++iy) {
                    double p = (double) iy;
                    for (int ix = 0; ix < 10; ++ix) {
                        Table testSize = new Table();
                        for (double iz = 0; iz < Math.pow(2.0, p); ++iz) {
                            KeyableNumber key = new KeyableNumber(rand.nextInt());
                            testSize.insert(key);
                        }
                        if (testSize.getHeight() > maxH) {
                            maxH = testSize.getHeight();
                        }
                    }
                    System.out.println("Max Height for Table of size " + Math.pow(2.0, p) + " : " + maxH);
                }
            }
        }
        while (mMenu != 3);
    }
    public static int mainMenu() {
        int select;
        System.out.print("\f");
        System.out.println("\n=====Main Menu=====");
        System.out.println("Select the option you would like to test:");
        System.out.println("(1) Test a single table of NBA Players");
        System.out.println("(2) Test many tables for worst-case retrieval time");
        System.out.println("(3) Exit program");
        select = kb.nextInt();
        return select;
    }
    public static int tableMenu() {
        int select;
        System.out.print("\f");
        System.out.println("\n===Table Menu===");
        System.out.println("Select the operation you would like to perform:");
        System.out.println("(1) Initialize the table");
        System.out.println("(2) Insert an item into the table");
        System.out.println("(3) Search for an item by key");
        System.out.println("(4) Get the height of the tree");
        System.out.println("(5) Get the total number of items");
        System.out.println("(6) Display the entire tree");
        System.out.println("(7) Display the all items listed in order");
        System.out.println("(8) Return to Main Menu");
        select = kb.nextInt();
        return select;
    }
}

class Table {
    private Node _root;

    public Table() {
        _root = null;
    }
    public void insert(Keyable key) {
        insert(key, _root);
    }
    private void insert(Keyable key, Node myRoot) {
        if (myRoot == null) {
            Node child = new Node(key);
            _root = child;
            child._left = child._right = null;
            return;
        }
        if (key.keyCompare(myRoot._data) == 0) {
            return;
        }
        else if (key.keyCompare(myRoot._data) == -1) {
            if (myRoot._left == null) {
                Node child = new Node(key);
                myRoot._left = child;
                child._left = child._right = null;
            }
            else {
                insert(key, myRoot._left);
            }
        }
        else if (key.keyCompare(myRoot._data) == 1) {
            if (myRoot._right == null) {
                Node child = new Node(key);
                myRoot._right = child;
                child._left = child._right = null;
            }
            else {
                insert(key, myRoot._right);
            }
        }
    }
    public Keyable search(Keyable key) {
        return search(key, _root);
    }
    private Keyable search(Keyable key, Node myRoot) {
        Keyable found = null;
        if (myRoot == null) {
            return null;
        }
        if (key.keyCompare(myRoot._data) == 0) {
            found = myRoot._data;
        }
        else {
            found = search(key, myRoot._left);
            found = search(key, myRoot._right);
        }
        return found;
    }
    public int getHeight() {
        return getHeight(_root);
    }
    private int getHeight(Node myRoot) {
        int heightL, heightR, heightMax;
        if (myRoot == null) {
            heightMax = 0;
        }
        else {
            heightL = getHeight(myRoot._left);
            heightR = getHeight(myRoot._right);
            heightMax = Math.max(heightL, heightR) + 1;
        }
        return heightMax;
    }
    public int getSize() {
        return getSize(_root);
    }
    private int getSize(Node myRoot) {
        int size = 0;
        if (myRoot == null) {
            size += 0;
        }
        else {
            ++size;
            size += getSize(myRoot._left);
            size += getSize(myRoot._right);
        }
        return size;
    }
    private String inOrder(Node myRoot) {
        String s = "";
        if (myRoot == null) {
            return "";
        }
        s += inOrder(myRoot._left);
        s += myRoot._data + "\n";
        s += inOrder(myRoot._right);
        return s;
    }
    public void showTree() {
        showTree(_root, 1);
    }
    private void showTree(Node myRoot, int level) {
        if (myRoot != null) {
            showTree(myRoot._right, level+1);
            for (int ix = 0; ix < level; ++ix) {
                System.out.print("\t");
            }
            System.out.println(myRoot._data.toKey());
            showTree(myRoot._left, level+1);
        }
    }
    public String toString() {
        return inOrder(_root);
    }
}

class Node {
    public Keyable _data;
    public Node _left;
    public Node _right;

    public Node(Keyable data) {
        _data = data;
    }
}

interface Keyable {
    public int keyCompare(Keyable other);
    public String toKey();
}

class NBAPlayerKey implements Keyable {
    private int _jersey;
    private String _team;

    public NBAPlayerKey(int jersey, String team) {
        _jersey = jersey;
        _team = team;
    }
    public int getJersey() {
        return _jersey;
    }
    public String getTeam() {
        return _team;
    }
    public String toString() {
        String s = "";
        s += "\nJersey #" + _jersey;
        s += "\nTeam: " + _team;
        return s;
    }
    public int keyCompare(Keyable other) {
        int comp = 999;
        if (other instanceof NBAPlayerKey) {
            NBAPlayerKey o = (NBAPlayerKey) other;
            if (_jersey < o.getJersey()) {
                comp = -1;
            }
            else if (_jersey > o.getJersey()) {
                comp = 1;
            }
            else {
                if (_team.compareTo(o.getTeam()) < 0) {
                    comp = -1;
                }
                else if (_team.compareTo(o.getTeam()) > 0) {
                    comp = 1;
                }
                else {
                    comp = 0;
                }
            }
        }
        return comp;
    }
    public String toKey() {
        String k = "";
        k += _jersey;
        k += " " + _team.substring(0, 3);
        return k;
    }
}

class NBAPlayer extends NBAPlayerKey {
    private String _name;
    private double _avgPPG;

    public NBAPlayer(int jersey, String team, String name, double avgPPG) {
        super(jersey, team);
        _name = name;
        _avgPPG = avgPPG;
    }
    public String getName() {
        return _name;
    }
    public double getAvgPPG() {
        return _avgPPG;
    }
    public String toString() {
        String s = "";
        s += "\nName: " + _name;
        s += super.toString();
        s += "\nAvg PPG: " + _avgPPG;
        return s;
    }
}

class KeyableNumber implements Keyable{
    private int _key;

    public KeyableNumber(int data) {
        _key = data;
    }
    public int getKey() {
        return _key;
    }
    public String toString() {
        String s = "";
        s += _key;
        return s;
    }
    public int keyCompare(Keyable other) {
        int comp = 999;
        if (other instanceof KeyableNumber) {
            KeyableNumber o = (KeyableNumber) other;
            if (_key < o._key) {
                comp = -1;
            }
            else if (_key > o._key) {
                comp = 1;
            }
            else {
                comp = 0;
            }
        }
        return comp;
    }
    public String toKey() {
        String k = "";
        k += _key;
        return k;
    }
}