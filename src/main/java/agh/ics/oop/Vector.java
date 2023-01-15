package agh.ics.oop;

import agh.ics.oop.Hitboxes.RectangularHitbox;

public class Vector {

    private double x;
    private double y;

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
        this.x += v.getX();
        this.y += v.getY();
    }

    @Override
    public String toString(){
        return "(" + Double.toString(this.x) + ", " + Double.toString(this.y) + ")";
    }


}
