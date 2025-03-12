import java.util.Scanner;

/**
 * EquilateralTriangle class extending GeometricShape2D class, representing 2D equilateral triangle shapes with 
 * side length and area.
 * 
 * @author Mert Uzun
 * Date: 3/12/2025
 */
public class EquilateralTriangle extends GeometricShape2D{
    protected static final Scanner sc = new Scanner(System.in);
    protected float sideLength;
    protected float area;

    public EquilateralTriangle(float sideLength){
        this.sideLength = sideLength;
        this.area = calculateArea();
    }

    @Override
    public float calculateArea(){
        return (float)Math.sqrt(3) * sideLength * sideLength / 4;
    }

    @Override
    public void printInfo(){
        System.out.printf("Type: %s, side length: %.2f", 
                                    getType(), sideLength);
    }
    
    @Override
    public void printDetailedInfo(){
        System.out.printf("Type: %s, side length: %.2f, area: %.2f", 
                                    getType(), sideLength, area);
    }

    @Override
    public void editShape(){
        System.out.print("Enter new side length: ");
        float newSideLength = sc.nextFloat();
        sc.nextLine();
        setSideLength(newSideLength);

        this.area = calculateArea();
    }

    //GETTERS AND SETTERS
    public float getSideLength() {
        return sideLength;
    }

    @Override
    public float getArea() {
        return area;
    }

    @Override
    public String getType(){
        return "Equilateral Triangle";
    }

    public void setSideLength(float newSideLength){
        this.sideLength = newSideLength;
    }
}