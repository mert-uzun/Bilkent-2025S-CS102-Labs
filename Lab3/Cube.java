import java.util.Scanner;

/**
 * Cube class extending GeometricShape3D class, representing 3D cube shapes with side length, area, and volume.
 * 
 * @author Mert Uzun
 * Date: 3/12/2025
 */
public class Cube extends GeometricShape3D{
    protected static final Scanner sc = new Scanner(System.in);
    protected float sideLength;
    protected float area;
    protected float volume;

    public Cube(float sideLength){
        this.sideLength = sideLength;
        this.area = calculateArea();
        this.volume = calculateVolume();
    }

    @Override
    public float calculateArea(){
        return 6 * sideLength * sideLength;
    }

    @Override
    public float calculateVolume(){
        return sideLength * sideLength * sideLength;
    }

    @Override
    public void printInfo(){
        System.out.printf("Type: %s, side length: %.2f", 
                                    getType(), sideLength);
    }

    @Override
    public void printDetailedInfo(){
        System.out.printf("Type: %s, side length: %.2f, area: %.2f, volume %.2f", 
                                    getType(), sideLength, area, volume);
    }

    @Override
    public void editShape(){
        System.out.print("Enter new side length: ");
        float newSideLength = sc.nextFloat();
        sc.nextLine();
        setSideLength(newSideLength);

        this.area = calculateArea();
        this.volume = calculateVolume();
    }

    //GETTERS AND SETTERS
    public float getSideLength(){
        return sideLength;
    }

    @Override
    public float getArea(){
        return area;
    }

    public float getVolume(){
        return volume;
    }

    @Override
    public String getType(){
        return "Cube";
    }

    public void setSideLength(float newSideLength){
        this.sideLength = newSideLength;
    }
}