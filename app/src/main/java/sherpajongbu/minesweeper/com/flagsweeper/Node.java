package sherpajongbu.minesweeper.com.flagsweeper;



public class Node {//Class Node with int Element
    private boolean isOpened;
    private boolean hasFlag;
    private boolean hasMine;
    private int mineCount;
    private Node next;
    private boolean isChecked;

    Node() {
        this(false,false,false,false,0, null);// returns a pointer
    }

    public Node(boolean o, boolean f, boolean m, boolean c,int mc, Node n) {
        isOpened=false;
        hasFlag=false;
        hasMine=false;
        isChecked=false;
        mineCount=mc;
        next = n;

    }

    // update methods
    void setFlag(boolean v) {
        hasFlag=v;
    }
    void setMine(boolean v) {
        hasMine=v;
    }
    void setOpened(boolean v) {
        isOpened=v;
    }
    void setMineCount(int count){
        mineCount=count;
    }
    void setNext(Node newNext) {
        next = newNext;
    }
    void setChecked(boolean check) { isChecked=check; }

    // access methods

    boolean getFlag() {
        return hasFlag;
    }

    boolean getMine() {
        return hasMine;
    }
    boolean getOpened() {
        return isOpened;
    }
    boolean getChecked() {
        return isChecked;
    }
    int getMineCount(){
        return mineCount;
    }

    Node getNext() {
        return next;
    }

}
