import java.util.Scanner;

/**
 * Circle class extending Ellipse class, representing 2D cirlce shapes with radius and area.
 * 
 * @author Mert Uzun
 * Date: 3/12/2025
 */
public class Circle extends Ellipse{
    protected static final Scanner sc = new Scanner(System.in);
    protected float radius;

    public Circle(float diameter){
        super(diameter, diameter);
        radius = diameter / 2;
    }

    @Override
    public void printInfo(){
        System.out.printf("Type: %s, radius: %.2f", 
                                    getType(), radius);
    }

    @Override
    public void printDetailedInfo(){
        System.out.printf("Type: %s, radius: %.2f, area: %.2f", 
                                    getType(), radius, area);
    }

    @Override
    public void editShape(){
        System.out.print("Enter new length of radius: ");
        float newRadius = sc.nextFloat();
        sc.nextLine();
        setRadius(newRadius);

        this.area = calculateArea();
    }

    @Override
    public String getType(){
        return "Circle";
    }

    //SETTERS
    public void setRadius(float newRadius){
        this.radius = newRadius;
        this.majorAxisLength = 2 * newRadius;
        this.minorAxisLength = 2 * newRadius;
    }
}