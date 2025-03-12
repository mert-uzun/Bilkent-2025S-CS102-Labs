import java.util.Scanner;

/**
 * Main class for Shape Organizing App, managing the user interface, and various method implementations 
 * to make the program work accordingly to the lab document.
 * 
 * @author Mert Uzun
 * Date: 3/12/2025
 */
public class ShapeProgram {
    private static final Scanner sc = new Scanner(System.in);
    private static GeometricShape2D[] arrayFor2D = new GeometricShape2D[0];
    private static GeometricShape3D[] arrayFor3D = new GeometricShape3D[0];

    public static void main(String[] args) {
        System.out.println("WELCOME TO THE SHAPE ORGANIZATION APP!");
        boolean appContinues = true;

        while (appContinues) {
            System.out.println("\n                MENU            ");
            System.out.println("1 - Create and Store a New Shape");
            System.out.println("2 - Add an Existing Shape to a Multi-Shape");
            System.out.println("3 - List All Shapes");
            System.out.println("4 - Merge Multi-Shapes");
            System.out.println("5 - Edit Shape");
            System.out.println("6 - Exit");
            System.out.println();
            System.out.print("Enter your choice: " );

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    createNewShapes();
                    break;
                case 2:
                    addToMultiShape2D();
                    break;
                case 3:
                    listAllShapes();
                    break;
                case 4:
                    mergeMultiShapes();
                    break;
                case 5:
                    editShapes();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    appContinues = false;
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Method to handle the creation and storing of new shapes, based on user interactions
     */
    public static void createNewShapes(){
        System.out.println("2D Shapes: \n" +
                            "\t1 - Rectangle\n" + 
                            "\t2 - Circle\n" + 
                            "\t3 - Square\n" + 
                            "\t4 - Ellipse\n" + 
                            "\t5 - Equilateral Triangle\n" + 
                            "\t6 - MultiShape2D");
        System.out.println("3D Shapes: \n" +
                            "\t7 - Cuboid\n" + 
                            "\t8 - Sphere\n" + 
                            "\t9 - Cylinder\n" + 
                            "\t10 - Cube\n" + 
                            "\t11 - Pyramid\n");
        
        System.out.print("Enter the shape type you want to create: ");
        int choiceOfShape = sc.nextInt();
        sc.nextLine();

        switch (choiceOfShape) {
            case 1:
                createRectangle();
                break;
            case 2:
                createCircle();
                break;
            case 3:
                createSquare();
                break;
            case 4:
                createEllipse();
                break;
            case 5:
                createEquilateralTriangle();
                break;
            case 6:
                createMultiShape2D();
                break;
            case 7:
                createCuboid();
                break;
            case 8:
                createSphere();
                break;
            case 9:
                createCylinder();
                break;
            case 10:
                createCube();
                break;
            case 11:
                createPyramid();
                break;
            default:
                break;
        }
    }

    /**
     * Creates a 2D rectangle shape based on user inputs and stores it in the main array of 2D shapes.
     */
    public static void createRectangle(){
        System.out.print("Enter the length: ");
        float length = sc.nextFloat();
        sc.nextLine();

        System.out.print("Enter the width: ");
        float width = sc.nextFloat();
        sc.nextLine();

        Rectangle newRectangle = new Rectangle(length, width);

        add2DShape(newRectangle);
    }

    /**
     * Creates a 2D circle shape based on user inputs and stores it in the main array of 2D shapes.
     */
    public static void createCircle(){
        System.out.print("Enter the length of diameter: ");
        float diameter = sc.nextFloat();
        sc.nextLine();

        Circle newCircle = new Circle(diameter);

        add2DShape(newCircle);
    }

    /**
     * Creates a 2D square shape based on user inputs and stores it in the main array of 2D shapes.
     */
    public static void createSquare(){
        System.out.print("Enter the side length: ");
        float sideLength = sc.nextFloat();
        sc.nextLine();

        Square newSquare = new Square(sideLength);

        add2DShape(newSquare);
    }

    /**
     * Creates a 2D ellipse shape based on user inputs and stores it in the main array of 2D shapes.
     */
    public static void createEllipse(){
        System.out.print("Enter the length of major axis: ");
        float majorAxisLength = sc.nextFloat();
        sc.nextLine();

        System.out.print("Enter the length of minor axis: ");
        float minorAxisLength = sc.nextFloat();
        sc.nextLine();

        Ellipse newEllipse = new Ellipse(majorAxisLength, minorAxisLength);

        add2DShape(newEllipse);
    }

    /**
     * Creates a 2D equilateral triangle shape based on user inputs and stores it in the main array of 2D shapes.
     */
    public static void createEquilateralTriangle(){
        System.out.print("Enter the side length: ");
        float sideLength = sc.nextFloat();
        sc.nextLine();

        EquilateralTriangle newEquilateralTriangle = new EquilateralTriangle(sideLength);

        add2DShape(newEquilateralTriangle);
    }

    /**
     * Creates a Multi-shape 2D object based on user inputs and stores it in the main array of 2D shapes.
     * Provides user with another interface to create and add another 2D objects to this Multi-shape 2D object upon creation.
     */
    public static void createMultiShape2D(){
        MultiShape2D newMultiShape2D = new MultiShape2D();
        boolean flag = true;

        while (flag) {
            System.out.println("2D Shapes: \n" +
                                "\t1 - Rectangle\n" + 
                                "\t2 - Circle\n" + 
                                "\t3 - Square\n" + 
                                "\t4 - Ellipse\n" + 
                                "\t5 - Equilateral Triangle\n" + 
                                "\t6 - Complete Creation");

            System.out.print("Enter the shape type you want to create and add to Multi-Shape 2D object: ");
            int choiceOfShape = sc.nextInt();
            sc.nextLine();
            switch (choiceOfShape) {
                case 1:
                    System.out.print("Enter the length: ");
                    float length = sc.nextFloat();
                    sc.nextLine();
            
                    System.out.print("Enter the width: ");
                    float width = sc.nextFloat();
                    sc.nextLine();
            
                    GeometricShape2D newRectangle = new Rectangle(length, width);
            
                    newMultiShape2D.addShape(newRectangle);
                    break;
                case 2:
                    System.out.print("Enter the length of diameter: ");
                    float diameter = sc.nextFloat();
                    sc.nextLine();
            
                    Circle newCircle = new Circle(diameter);

                    newMultiShape2D.addShape(newCircle);
                    break;
                case 3:
                    System.out.print("Enter the side length: ");
                    float sideLength = sc.nextFloat();
                    sc.nextLine();
            
                    Square newSquare = new Square(sideLength);

                    newMultiShape2D.addShape(newSquare);
                    break;
                case 4:
                    System.out.print("Enter the length of major axis: ");
                    float majorAxisLength = sc.nextFloat();
                    sc.nextLine();
            
                    System.out.print("Enter the length of minor axis: ");
                    float minorAxisLength = sc.nextFloat();
                    sc.nextLine();
            
                    Ellipse newEllipse = new Ellipse(majorAxisLength, minorAxisLength);

                    newMultiShape2D.addShape(newEllipse);
                    break;
                case 5:
                    System.out.print("Enter the side length: ");
                    float sideLengthOfTriangle = sc.nextFloat();
                    sc.nextLine();
            
                    EquilateralTriangle newEquilateralTriangle = new EquilateralTriangle(sideLengthOfTriangle);

                    newMultiShape2D.addShape(newEquilateralTriangle);
                    break;
                default:
                    System.out.println("Multi-Shape 2D object successfully created.");
                    flag = false;
                    break;
            }
        }

        add2DShape(newMultiShape2D);
    }
    
    /**
     * Creates a 3D cuboid shape based on user inputs and stores it in the main array of 3D shapes.
     */
    public static void createCuboid(){
        System.out.print("Enter the length: ");
        float length = sc.nextFloat();
        sc.nextLine();

        System.out.print("Enter the width: ");
        float width = sc.nextFloat();
        sc.nextLine();

        System.out.print("Enter the height: ");
        float height = sc.nextFloat();
        sc.nextLine();

        Cuboid newCuboid = new Cuboid(length, width, height);

        add3DShape(newCuboid);
    }

    /**
     * Creates a 3D sphere shape based on user inputs and stores it in the main array of 3D shapes.
     */
    public static void createSphere(){
        System.out.print("Enter the length of radius: ");
        float radius = sc.nextFloat();
        sc.nextLine();

        Sphere newSphere = new Sphere(radius);

        add3DShape(newSphere);
    }

    /**
     * Creates a 3D cylinder shape based on user inputs and stores it in the main array of 3D shapes.
     */
    public static void createCylinder(){
        System.out.print("Enter the length of base radius: ");
        float baseRadius = sc.nextFloat();
        sc.nextLine();

        System.out.print("Enter height: ");
        float height = sc.nextFloat();
        sc.nextLine();

        Cylinder newCylinder = new Cylinder(baseRadius, height);

        add3DShape(newCylinder);
    }

    /**
     * Creates a 3D cube shape based on user inputs and stores it in the main array of 3D shapes.
     */
    public static void createCube(){
        System.out.print("Enter side length: ");
        float sideLength = sc.nextFloat();
        sc.nextLine();

        Cube newCube = new Cube(sideLength);

        add3DShape(newCube);
    }

    /**
     * Creates a 3D pyramid shape based on user inputs and stores it in the main array of 3D shapes.
     */
    public static void createPyramid(){
        System.out.print("Enter base length: ");
        float baseLength = sc.nextFloat();
        sc.nextLine();

        System.out.print("Enter base width: ");
        float baseWidth = sc.nextFloat();
        sc.nextLine();

        System.out.print("Enter height: ");
        float height = sc.nextFloat();
        sc.nextLine();

        Pyramid newPyramid = new Pyramid(baseLength, baseWidth, height);

        add3DShape(newPyramid);
    }

    /**
     * A helper method to add a 2D shape to main array of 2D objects
     * @param newShape new 2D shape to be added to main array of 2D objects
     */
    private static void add2DShape(GeometricShape2D newShape){
        GeometricShape2D[] updated2DArray = new GeometricShape2D[arrayFor2D.length + 1];

        for(int i = 0; i < arrayFor2D.length; i++){
            updated2DArray[i] = arrayFor2D[i];
        }

        updated2DArray[arrayFor2D.length] = newShape;

        arrayFor2D = updated2DArray;
    }

    /**
     * A helper method to add a 3D shape to main array of 3D objects
     * @param newShape new 3D shape to be added to main array of 3D objects
     */
    private static void add3DShape(GeometricShape3D newShape){
        GeometricShape3D[] updated3DArray = new GeometricShape3D[arrayFor3D.length + 1];

        for(int i = 0; i < arrayFor3D.length; i++){
            updated3DArray[i] = arrayFor3D[i];
        }

        updated3DArray[arrayFor3D.length] = newShape;

        arrayFor3D = updated3DArray;
    }

    /**
     * A method to handle addition of previously created 2D objects to another previously created Multi-shape 2D object
     */
    public static void addToMultiShape2D(){

        if (arrayFor2D.length >= 2 && hasMultiShape2D(arrayFor2D)) {
            System.out.println("Below are the Multi-Shape 2D objects you can add to: ");
            for(int i = 0; i < arrayFor2D.length; i++){
                if (arrayFor2D[i] instanceof MultiShape2D) {
                    System.out.print(i + " - ");
                    arrayFor2D[i].printInfo();
                    System.out.println();
                }
            }

            System.out.print("Enter the index of the Multi-Shape 2D object you want to add to: ");
            int choice = sc.nextInt();
            sc.nextLine();

            MultiShape2D toBeAddMultiShape2D = (MultiShape2D)arrayFor2D[choice];

            boolean addingContinues = true;
            while (addingContinues) {
                System.out.println("Below are your choices to add to this Multi-Shape 2D object: ");

                for(int i = 0; i < arrayFor2D.length; i++){
                    if (arrayFor2D[i].equals(toBeAddMultiShape2D)) {
                        continue;
                    }
                    else{
                        System.out.print(i + " - ");
                        arrayFor2D[i].printInfo();
                        System.out.println();
                    }
                }

                System.out.print("Enter the index of the shape you want to add (Enter -1 to quit): ");
                int choiceOfIndex = sc.nextInt();
                sc.nextLine();

                if (choiceOfIndex == -1) {
                    addingContinues = false;
                }
                else{
                    toBeAddMultiShape2D.addShape(arrayFor2D[choiceOfIndex]);

                    GeometricShape2D[] temp = new GeometricShape2D[arrayFor2D.length - 1];
    
                    for(int i = 0; i < choiceOfIndex; i++){
                        temp[i] = arrayFor2D[i];
                    }
    
                    for(int i = choiceOfIndex + 1; i < arrayFor2D.length; i++){
                        temp[i - 1] = arrayFor2D[i];
                    }
    
                    arrayFor2D = temp;
                }
            }
        }
        else{
            System.out.println("2D shapes are not in required status to take such action.");
        }
    }

    /**
     * A helper method to check if a given array of GeometricShape2D objects containts any Multi-shape 2D objects
     * @param shapes given array of GeometricShape2D objects
     * @return true or false if given array contains any Multi-shape 2D objects or not
     */
    private static boolean hasMultiShape2D(GeometricShape2D[] shapes){
        for(GeometricShape2D shape : shapes){
            if (shape instanceof MultiShape2D) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * A method to list all 2D and 3D shapes with proper indentation.
     * This method also provides the user a choice to see the information about a shape in details.
     */
    public static void listAllShapes(){
        if (arrayFor2D.length == 0) {
            System.out.println("There are no 2D shapes stored!");
        }
        else{
            System.out.println("2D Shapes: ");
        
            for(int i = 0; i < arrayFor2D.length; i++){
                System.out.print("\t[" + i + "]");
                arrayFor2D[i].printInfo();
                System.out.println();
            }
        }

        if (arrayFor3D.length == 0) {
            System.out.println("There are no 3D shapes stored!");
        }
        else{
            System.out.println("3D Shapes: ");
        
            for(int i = arrayFor2D.length; i < arrayFor2D.length + arrayFor3D.length; i++){
                System.out.print("\t[" + i + "]");
                arrayFor3D[i - arrayFor2D.length].printInfo();
                System.out.println();
            }
        }

        if (arrayFor2D.length != 0 || arrayFor3D.length != 0) {
            System.out.println("Do you want details for a specific shape?");
            System.out.print("Enter shape index or -1 to return: ");

            int shapeIndex = sc.nextInt();
            sc.nextLine();

            if (shapeIndex == -1) {
                return;
            }
            else if (shapeIndex < arrayFor2D.length) {
                arrayFor2D[shapeIndex].printDetailedInfo();
            }
            else if (shapeIndex < arrayFor2D.length + arrayFor3D.length) {
                arrayFor3D[shapeIndex - arrayFor2D.length].printDetailedInfo();
            }
            else{
                System.out.println("Invalid index entered! Returning...");
                return;
            }
        }
        else{
            return;
        }
    }

    /**
     * A method to merge every previously created Multi-shape 2D objects within itself
     */
    public static void mergeMultiShapes(){
        System.out.println("Merging every Multi-shape object in itself...");
        
        for(GeometricShape2D shape : arrayFor2D){
            if (shape instanceof MultiShape2D) {
                ((MultiShape2D)shape).mergeShapes();
            }
        }
    }

    /**
     * A method to provide the user with an interface to choose a previously created shape to edit
     */
    public static void editShapes(){
        if (arrayFor2D.length != 0 || arrayFor3D.length != 0) {
            System.out.println("Below are editable shapes with their indices: ");

            if (arrayFor2D.length == 0) {
                System.out.println("There are no 2D shapes stored!");
            }
            else{
                System.out.println("Editable 2D Shapes: ");
                for(int i = 0; i < arrayFor2D.length; i++){
                    if (arrayFor2D[i] instanceof MultiShape2D) {
                        continue;
                    }
                    else{
                        System.out.print("[" + i + "]");
                        arrayFor2D[i].printInfo();
                        System.out.println();
                    }
                }
            }

            if (arrayFor3D.length == 0) {
                System.out.println("There are no 3D shapes stored!");
            }
            else{
                System.out.println("Editable 3D Shapes: ");
                for(int i = arrayFor2D.length; i < arrayFor2D.length + arrayFor3D.length; i++){
                    System.out.print("[" + i + "]");
                    arrayFor3D[i - arrayFor2D.length].printInfo();
                    System.out.println();
                }
            }

            System.out.print("Enter the index of the shape you want to edit: ");
            int editIndex = sc.nextInt();
            sc.nextLine();

            if (editIndex >= 0 && editIndex < arrayFor2D.length) {
                arrayFor2D[editIndex].editShape();
            }
            else if (editIndex < arrayFor2D.length + arrayFor3D.length) {
                arrayFor3D[editIndex - arrayFor2D.length].editShape();
            }
            else{
                System.out.println("You have entered an invalid index! Returning...\n");
                return;
            }
        }
        else{
            System.out.println("There are no shapes stored!");
        }
    }
}
