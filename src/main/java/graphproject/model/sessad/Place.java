package graphproject.model.sessad;

public class Place {

    protected int id;
    protected double x;
    protected double y;

    public Place(int id)
    {
        this.id = id;

    }

    public int getId(){return id;}
    public void setId(int id){this.id = id;}

    public double getX(){return x;}
    public void setX(double x){this.x = x;}

    public double getY(){return y;}

    public void setY(double y){this.y = y;}


}
