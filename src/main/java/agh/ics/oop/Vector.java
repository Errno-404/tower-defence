package agh.ics.oop;


public class Vector {

    private double x;
    private double y;

    public Vector(Vector v){
        this.x = v.x;
        this.y = v.y;
    }
    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void normalise(){
        double module = Math.sqrt(this.x*this.x + this.y*this.y);

        this.x = this.x/module;
        this.y = this.y/module;
    }

    public Vector getDirectionVector(Vector v2){
        Vector ret =new Vector(v2.getX() - this.getX(), v2.getY() - this.getY());
        ret.normalise();
        return ret;
    }

    public double distance(Vector other){
        return Math.sqrt((this.x - other.x)*(this.x - other.x) + (this.y - other.y)*(this.y - other.y));
    }

    public void multiplyScalar(double f){
        this.x*=f;
        this.y*=f;
    }

    public void addVector(Vector v){
        this.x += v.x;
        this.y += v.y;
    }

    public int getXindex(){
        return (int) (this.x/Constants.tileWidth);
    }

    public int getYindex(){
        return (int) (this.y/Constants.tileWidth);
    }
    @Override
    public String toString(){
        return "(" + this.x + ", " + this.y + ")";
    }


    public boolean follows (Vector v){
        return (this.x >= v.x && this.y >= v.y);
    }

    public boolean precedes (Vector v){
        return (this.x <= v.x && this.y <= v.y);
    }
}
