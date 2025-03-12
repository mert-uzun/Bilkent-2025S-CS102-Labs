import java.util.Scanner;

/**
 * Rectangle class extending GeometricShape2D class, representing 2D rectangle shapes with 
 * height, width, and area.
 * 
 * @author Mert Uzun
 * Date: 3/12/2025
 */
public class Rectangle extends GeometricShape2D{
    protected static final Scanner sc = new Scanner(System.in);
    protected float height;
    protected float width;
    protected float area;

    public Rectangle(float height, float width){
        this.height = height;
        this.width = width;
        this.area = calculateArea();
    }
    
    @Override
    public float calculateArea(){
        return height * width;
    }

    @Override
    public void printInfo(){
        System.out.printf("Type: %s, height: %.2f, width: %.2f", 
                                    getType(), height, width);
    }

    @Override
    public void printDetailedInfo(){
        System.out.printf("Type: %s, height: %.2f, width: %.2f, area: %.2f", 
                                    getType(), height, width, area);
    }

    @Override
    public void editShape(){
        System.out.print("Enter new height: ");
        float newHeight = sc.nextFloat();
        sc.nextLine();
        setHeight(newHeight);

        System.out.print("Enter new width: ");
        float newWidth = sc.nextFloat();
        sc.nextLine();
        setWidth(newWidth);

        this.area = calculateArea();
    }

    //GETTERS AND SETTERS
    public float getHeight(){
        return height;
    }

    public float getWidth(){
        return width;
    }

    @Override
    public float getArea(){
        return area;
    }

    @Override
    public String getType(){
        return "Rectangle";
    }

    public void setHeight(float newHeight){
        this.height = newHeight;
    }

    public void setWidth(float newWidth){
        this.width = newWidth;
    }
}