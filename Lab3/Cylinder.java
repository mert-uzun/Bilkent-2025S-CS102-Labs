import java.util.Scanner;

/**
 * Cylinder class extending GeometricShape3D class, representing 3D cylinder shapes with 
 * base radius, height, area, and volume.
 * 
 * @author Mert Uzun
 * Date: 3/12/2025
 */
public class Cylinder extends GeometricShape3D{
    protected static final Scanner sc = new Scanner(System.in);
    protected float baseRadius;
    protected float height;
    protected float area;
    protected float volume;

    public Cylinder(float baseRadius, float height){
        this.baseRadius = baseRadius;
        this.height = height;
        this.area = calculateArea();
        this.volume = calculateVolume();
    }

    @Override
    public float calculateArea(){
        return (2 * (float)Math.PI * baseRadius * height) + (2 * (float)Math.PI * baseRadius * baseRadius);
    }

    @Override
    public float calculateVolume(){
        return (float)Math.PI * baseRadius * baseRadius * height;
    }

    @Override
    public void printInfo(){
        System.out.printf("Type: %s, base radius: %.2f, height: %.2f", 
                                    getType(), baseRadius, height);
    }

    @Override
    public void printDetailedInfo(){
        System.out.printf("Type: %s, base radius: %.2f, height: %.2f, area: %.2f, volume: %.2f", 
                                    getType(), baseRadius, height, area, volume);
    }

    @Override
    public void editShape(){
        System.out.print("Enter new base radius: ");
        float newBaseRadius = sc.nextFloat();
        sc.nextLine();
        setBaseRadius(newBaseRadius);

        System.out.print("Enter new height: ");
        float height = sc.nextFloat();
        sc.nextLine();
        setHeight(height);

        this.area = calculateArea();
        this.volume = calculateVolume();
    }

    //GETTERS AND SETTERS
    public float getBaseRadius() {
        return baseRadius;
    }

    public float getHeight() {
        return height;
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
        return "Cylinder";
    }

    public void setBaseRadius(float newBaseRadius){
        this.baseRadius = newBaseRadius;
    }

    public void setHeight(float newHeight){
        this.height = newHeight;
    }
}
