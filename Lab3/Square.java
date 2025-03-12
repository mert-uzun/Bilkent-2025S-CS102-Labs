import java.util.Scanner;

/**
 * Square class extending Rectangle class, representing 2D square shapes with 
 * side length and area.
 * 
 * @author Mert Uzun
 * Date: 3/12/2025
 */
public class Square extends Rectangle{
    protected static final Scanner sc = new Scanner(System.in);
    public Square(float length){
        super(length, length);
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
        System.out.print("Enter new side length: ");
        float newSideLength = sc.nextFloat();
        sc.nextLine();
        setHeight(newSideLength);
        setWidth(newSideLength);

        this.area = calculateArea();
    }

    //GETTERS
    @Override
    public String getType(){
        return "Square";
    }
}
