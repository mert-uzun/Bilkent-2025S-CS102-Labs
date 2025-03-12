import java.util.Scanner;

/**
 * Pyramid class extending GeometricShape3D class, representing 3D pyramid shapes with base length, base width, heigth, 
 * area, volume, and heights of side surfaces.
 * 
 * @author Mert Uzun
 * Date: 3/12/2025
 */
public class Pyramid extends GeometricShape3D{
    protected static final Scanner sc = new Scanner(System.in);
    protected float baseLength;
    protected float baseWidth;
    protected float height;
    protected float area;
    protected float volume;
    protected float sideSurfaceHeightOne;
    protected float sideSurfaceHeightTwo;

    public Pyramid(float baseLength, float baseWidth, float height){
        this.baseLength = baseLength;
        this.baseWidth = baseWidth;
        this.height = height;
        this.sideSurfaceHeightOne = calculateHypotenuse(baseLength / 2, height);
        this.sideSurfaceHeightTwo = calculateHypotenuse(baseWidth / 2, height);
        this.area = calculateArea();
        this.volume = calculateVolume();
    }


    @Override
    public float calculateArea(){
        return (baseLength * baseWidth) + (baseLength * sideSurfaceHeightOne) + (baseWidth * sideSurfaceHeightTwo);
    }

    @Override
    public float calculateVolume(){
        return baseLength * baseWidth * height / 3;
    }

    @Override
    public void printInfo(){
        System.out.printf("Type: %s, base length: %.2f, base width: %.2f, height: %.2f", 
                                    getType(), baseLength, baseWidth, height);
    }

    @Override
    public void printDetailedInfo(){
        System.out.printf("Type: %s, base length: %.2f, base width: %.2f, height: %.2f, area: %.2f, volume: %.2f", 
                                    getType(), baseLength, baseWidth, height, area, volume);
    }

    @Override
    public void editShape(){
        System.out.print("Enter new base length: ");
        float newBaseLength = sc.nextFloat();
        sc.nextLine();
        setBaseLength(newBaseLength);

        System.out.print("Enter new base width: ");
        float newBaseWidth = sc.nextFloat();
        sc.nextLine();
        setBaseWidth(newBaseWidth);

        System.out.print("Enter new height: ");
        float newHeight = sc.nextFloat();
        sc.nextLine();
        setHeight(newHeight);

        this.area = calculateArea();
        this.volume = calculateVolume();
    }

    /**
     * A helper method to calculate heights of side surfaces
     * @param side1 one perpendicular side to consider in hypotenuse formula
     * @param side2 other perpendicular side in hypotenuse formula
     * @return length of hypotenuse as a float value
     */
    private float calculateHypotenuse(float side1, float side2){
        return (float)Math.sqrt(side1 * side1 + side2 * side2);
    }

    //GETTERS AND SETTERS
    public float getBaseLength() {
        return baseLength;
    }

    public float getBaseWidth() {
        return baseWidth;
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
        return "Pyramid";
    }

    public void setBaseLength(float newBaseLength){
        this.baseLength = newBaseLength;
    }

    public void setBaseWidth(float newBaseWidth){
        this.baseWidth = newBaseWidth;
    }

    public void setHeight(float newHeight){
        this.height = newHeight;
    }
}
