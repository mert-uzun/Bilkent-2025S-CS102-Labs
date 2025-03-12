/**
 * Abstract class of GeometricShape3D extending GeometricShape2D class, with methods to be implemented by 3D shapes.
 * 
 * @author Mert Uzun
 * Date: 3/12/2025
 */
public abstract class GeometricShape3D extends GeometricShape2D {

    /**
     * This method calculates the volume of the specified shape.
     * This method is meant to be overriden by all 3D shape subclasses
     * to make it accurate with varying values and calculation rules of different kinds of 3D shapes.
     * @return returns the volume of that 3D shape
     */
    public abstract float calculateVolume();
}
