package graphproject.model.sessad;

// Evite de dupliquer certaines informations entre centre et missions car ils possèdent tous les 2 une position et un id
public class Place {

    public enum Type{CENTRE, MISSION}

    protected int id;
    protected double x;
    protected double y;
    protected String name;
    protected Type type;

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

    public Type getType(){return type;}
    public void setType(Type type){this.type = type;}
}
