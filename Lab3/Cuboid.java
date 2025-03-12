import java.util.Scanner;

/**
 * Cuboid class extending GeometricShape3D class, representing 3D cuboid shapes 
 * with length, width, height, area, and volume.
 * 
 * @author Mert Uzun
 * Date: 3/12/2025
 */
public class Cuboid extends GeometricShape3D{
    protected static final Scanner sc = new Scanner(System.in);
    protected float length;
    protected float width;
    protected float height;
    protected float area;
    protected float volume;

    public Cuboid(float length, float width, float height){
        this.length = length;
        this.width = width;
        this.height = height;
        this.area = calculateArea();
        this.volume = calculateVolume();
    }

    @Override
    public float calculateArea(){
        return 2 * (height * width + height * length + length * width);
    }

    @Override
    public float calculateVolume(){
        return height * length * width;
    }

    @Override
    public void printInfo(){
        System.out.printf("Type: %s, height: %.2f, length: %.2f, width: %.2f",
                                    getType(), height, length, width);
    }

    @Override
    public void printDetailedInfo(){
        System.out.printf("Type: %s, height: %.2f, length: %.2f, width: %.2f, area: %.2f, volume: %.2f",
                                    getType(), height, length, width, area, volume);
    }

    @Override
    public void editShape(){
        System.out.print("Enter new length: ");
        float newLength = sc.nextFloat();
        sc.nextLine();
        setLength(newLength);

        System.out.print("Enter new width: ");
        float newWidth = sc.nextFloat();
        sc.nextLine();
        setWidth(newWidth);

        System.out.print("Enter new height: ");
        float newHeight = sc.nextFloat();
        sc.nextLine();
        setHeight(newHeight);

        this.area = calculateArea();
        this.volume = calculateVolume();
    }

    //GETTERS AND SETTERS
    public float getLength() {
        return length;
    }

    public float getWidth() {
        return width;
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
        return "Cuboid";
    }

    public void setLength(float newLength){
        this.length = newLength;
    }

    public void setWidth(float newWidth){
        this.width = newWidth;
    }

    public void setHeight(float newHeight){
        this.height = newHeight;
    }
}
