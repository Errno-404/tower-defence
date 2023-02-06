package agh.ics.oop.maps;

public class DijkstraNode implements Comparable {
    public int val;
    public int x;
    public int y;

    public DijkstraNode(int val, int x, int y){
        this.val = val;
        this.x = x;
        this.y=y;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof DijkstraNode dn){
            return this.val - dn.val;
        }
        else return -1;
    }
}
