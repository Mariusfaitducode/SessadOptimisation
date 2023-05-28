package graphproject.model.sessad;

public class Place {

    protected int id;
    protected double x;
    protected double y;
    protected String name;

    public Place(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public int getId(){return id;}
    public void setId(int id){this.id = id;}

    public double getX(){return x;}
    public void setX(double x){this.x = x;}

    public double getY(){return y;}

    public void setY(double y){this.y = y;}

    public String getName(){return name;}

    public void setName(String name){this.name = name;}
}
