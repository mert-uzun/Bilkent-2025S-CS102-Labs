import java.util.Scanner;

/**
 * MultiShape2D class extending GeometricShape2D class, representing Multi-shape objects that can store multiple 2D shapes within.
 * These objects can merge all of the shapes they are consisting of to a single square.
 * 
 * @author Mert Uzun
 * Date: 3/12/2025
 */
public class MultiShape2D extends GeometricShape2D{
    protected static final Scanner sc = new Scanner(System.in);
    protected int shapeCount;
    protected GeometricShape2D[] containedShapes;
    protected float area;
    protected int id;
    public static int idCounter = 0;

    public MultiShape2D(){
        this.id = idCounter++;
        shapeCount = 0;
        containedShapes = new GeometricShape2D[shapeCount];
        area = calculateArea();
    }

    @Override
    public float calculateArea(){
        float totalArea  = 0;

        if (shapeCount == 0) {
            return totalArea;
        }
        else{
            for (GeometricShape2D shape : containedShapes) {
                totalArea += shape.getArea();
            }
            
            return totalArea;
        }
    }

    @Override
    public void printInfo(){
        System.out.printf("Type: %s, area: %.2f", 
                                    getType(), area);
    }

    @Override
    public void printDetailedInfo(){
        System.out.print("\t");
        this.printInfo();
        System.out.println();

        if (shapeCount == 0) {
            System.out.println("There are no shapes in this Multi-Shape 2D object.");
        }
        else{
            for(GeometricShape2D shape : containedShapes){
                System.out.print("\t\t- ");
                shape.printDetailedInfo();
                System.out.println();
            }
        }
    }

    @Override
    public void editShape(){
        System.out.println("Multi-shape objects are not eligible for editing!");
    }

    /**
     * Updated the contained shapes array, increments its size by one and adds the new element
     * @param newShape new 2D geometric shape to be added
     */
    public void addShape(GeometricShape2D newShape){
        this.shapeCount++;

        GeometricShape2D[] updatedArrayOfShapes = new GeometricShape2D[shapeCount];

        for(int i = 0; i < shapeCount - 1; i++){
            updatedArrayOfShapes[i] = containedShapes[i];
        } 

        updatedArrayOfShapes[shapeCount - 1] = newShape;

        this.containedShapes = updatedArrayOfShapes;

        this.area = calculateArea();
    }

    /**
     * Merges all the shapes contained in this Multi-Shape 2D object into a single square 
     * with same total area with all those geometric shapes
     */
    public void mergeShapes(){
        shapeCount = 1;
        this.containedShapes = new GeometricShape2D[shapeCount];

        float sideLengthOfMergedSquare = (float)Math.sqrt(this.area);

        Square mergedSquare = new Square(sideLengthOfMergedSquare);

        this.containedShapes[0] = mergedSquare;

        this.area = calculateArea();
    }

    /**
     * Equals method considering the ID number unique to each Multi-shape 2D object
     * @param other other Multi-shape object to be compared with
     * @return true or false based on if ID numbers match or not
     */
    public boolean equals(MultiShape2D other){
        return this.getId() == other.getId();
    }

    //GETTERS AND SETTERS
    public int getShapeCount() {
        return shapeCount;
    }

    public GeometricShape2D[] getContainedShapes() {
        return containedShapes;
    }

    @Override
    public float getArea() {
        return area;
    }

    @Override
    public String getType(){
        return "Multi-Shape 2D";
    }

    public int getId(){
        return id;
    }
}
