/**
 * Abstract class of GeometricShape2D, with methods to be implemented by 2D and 3D shapes.
 * 
 * @author Mert Uzun
 * Date: 3/12/2025
 */
public abstract class GeometricShape2D {

    /**
     * This method calculates the total area of the specified shape.
     * This method is meant to be overriden by all shape subclasses
     * to make it accurate with varying values and calculation rules of different kinds of shapes.
     * @return returns the area of that shape
     */
    public abstract float calculateArea();

    /**
     * This method prints the values of the geometric shape.
     * This method is meant to be overriden to handle varying values and rules of different shape types.
     */
    public abstract void printInfo();

    /**
     * This method prints the values and specifications of the geometric shape.
     * This method is meant to be overriden to handle varying values and rules of different shape types.
     */
    public abstract void printDetailedInfo();

    /**
     * Provides a short interface for user to edit a specified geometric shape by re-defining its values.
     */
    public abstract void editShape();

    /**
     * Returns the type of geometric shape as a String
     * @return type of geometric shape
     */
    public abstract String getType();

    /**
     * Returns the area of specific type of geometric shape as a float
     * @return the area of geometric shape
     */
    public abstract float getArea();
}