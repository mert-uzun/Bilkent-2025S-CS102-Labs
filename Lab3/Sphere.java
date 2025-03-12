import java.util.Scanner;

/**
 * Sphere class extending GeometricShape3D class, representing 3D sphere shapes with 
 * radius, area, and volume.
 * 
 * @author Mert Uzun
 * Date: 3/12/2025
 */
public class Sphere extends GeometricShape3D{
    protected static final Scanner sc = new Scanner(System.in);
    protected float radius;
    protected float area;
    protected float volume;

    public Sphere(float radius){
        this.radius = radius;
        this.area = calculateArea();
        this.volume = calculateVolume();
    }

    @Override
    public float calculateArea(){
        return 4 * (float)Math.PI * radius * radius;
    }

    @Override
    public float calculateVolume(){
        return (float)((4.0 / 3) * Math.PI * radius * radius * radius);
    }

    @Override
    public void printInfo(){
        System.out.printf("Type: %s, radius: %.2f", 
                                    getType(), radius);
    }

    @Override
    public void printDetailedInfo(){
        System.out.printf("Type: %s, radius: %.2f, area: %.2f, volume: %.2f", 
                                    getType(), radius, area, volume);
    }

    @Override
    public void editShape(){
        System.out.print("Enter new radius: ");
        float newRadius = sc.nextFloat();
        sc.nextLine();
        setRadius(newRadius);

        this.area = calculateArea();
        this.volume = calculateVolume();
    }

    //GETTERS AND SETTERS
    public float getRadius() {
        return radius;
    }

    @Override
    public float getArea() {
        return area;
    }

    public float getVolume() {
        return volume;
    }

    @Override
    public String getType(){
        return "Sphere";
    }

    public void setRadius(float newRadius){
        this.radius = newRadius;
    }
}
