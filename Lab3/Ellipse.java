import java.util.Scanner;

/**
 * Ellipse class GeometricShape2D class, representing 2D ellipse shapes with
 * major axis length, minor axis length, and area.
 * 
 * @author Mert Uzun
 * Date: 3/12/2025
 */
public class Ellipse extends GeometricShape2D{
    protected static final Scanner sc = new Scanner(System.in);
    protected float majorAxisLength;
    protected float minorAxisLength;
    protected float area;

    public Ellipse(float majorAxisLength, float minorAxisLength){
        this.majorAxisLength = majorAxisLength;
        this.minorAxisLength = minorAxisLength;
        this.area = calculateArea();
    }

    @Override
    public float calculateArea(){
        return (float)Math.PI * (majorAxisLength / 2) * (minorAxisLength / 2);
    }

    @Override
    public void printInfo(){
        System.out.printf("Type: %s, length of major axis: %.2f, length of minor axis: %.2f",
                                    getType(), majorAxisLength, minorAxisLength);
    }

    @Override
    public void printDetailedInfo(){
        System.out.printf("Type: %s, length of major axis: %.2f, length of minor axis: %.2f, area: %.2f",
                                    getType(), majorAxisLength, minorAxisLength, area);
    }

    @Override
    public void editShape(){
        System.out.print("Enter the new major axis length: ");
        float newMajorAxisLength = sc.nextFloat();
        sc.nextLine();
        setMajorAxisLength(newMajorAxisLength);

        System.out.print("Enter the new minor axis length: ");
        float newMinorAxisLength = sc.nextFloat();
        sc.nextLine();
        setMinorAxisLength(newMinorAxisLength);

        this.area = calculateArea();
    }

    //GETTERS AND SETTERS
    public float getMajorAxisLength() {
        return majorAxisLength;
    }

    public float getMinorAxisLength() {
        return minorAxisLength;
    }

    @Override
    public float getArea() {
        return area;
    }

    @Override
    public String getType(){
        return "Ellipse";
    }

    public void setMajorAxisLength(float newLength){
        this.majorAxisLength = newLength;
    }

    public void setMinorAxisLength(float newLength){
        this.minorAxisLength = newLength;
    }
}