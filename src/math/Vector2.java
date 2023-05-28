package math;

import java.io.Serializable;
import java.util.Objects;

import engine.input.InputManager;

/**
 * Vector2 is a vector class that provides Vector2 objects, their arithemtic operations and other functions for Vector mathematics.
 * @author David Šebesta
 * @version 1.0.2
 */
public class Vector2 implements Serializable{
	private static final transient long serialVersionUID = 1812445214L;
	
	private double x;
	private double y;
	
	//Static constants
	public static final transient Vector2 zero = new Vector2(0, 0);
	public static final transient Vector2 left = new Vector2(1, 0);
	public static final transient Vector2 right = new Vector2(-1, 0);
	public static final transient Vector2 up = new Vector2(0, 1);
	public static final transient Vector2 down = new Vector2(0, -1);
	
	/**
	 * Constant made for coordinates of spawned structures so they are not kept at 0,0
	 */
	public static final transient Vector2 templateSpawn = new Vector2(999999,999999);
	/**
	 * Constant made for coordinates of spawned ores so they are not kept at 0,0
	 */
	public static final transient Vector2 oreSpawn = new Vector2(123456,123456);

	/**
	 * Vector2 class constructor with float values for x and y
	 */
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Vector2 class constructor with double values for x and y
	 */
	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter method for X value
	 * @return X value of a vector
	 */
	public double getX() {
		return x;
	}
	/**
	 * Getter method for Y value
	 * @return Y value of a vector
	 */
	public double getY() {
		return y;
	}

	/**
	 * Setter method for X value
	 * @param new X
	 */
	public void setX(double x) {
		this.x = x;
	}
	
	/**
	 * Setter method for Y value
	 * @param new Y
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Magnitude is a method in vector math that returns a absolute lenght of a vector. This is basically a hypotenuse of a right angled triangle.
	 * @return Absolute length of a vector (scalar)
	 */
	public double magnitude() {
		return Math.sqrt(x * x + y * y);
	}

	/**
	 * A normalized vector maintains its direction but its Length becomes 1. The resulting vector is often called a unit vector. A vector is normalized by dividing the vector by its own Length.
	 */
	public void normalize() {
		double magnitude = this.magnitude();
		this.x /= magnitude;
		this.y /= magnitude;
	}

	/**
	 * Add method for two vectors, both values of vector are added up.
	 * @param other Vector2
	 * @return new Vector2
	 */
	public Vector2 add(Vector2 other) {
		return new Vector2(x + other.x, y + other.y);
	}

	/**
	 * Static method provided to sum multiple vector2 objects together, resulting in a single summed vector
	 * @param X amount of other Vector2 objects
	 * @return new Vector2
	 */
	public static Vector2 add(Vector2... vectors) {
		Vector2 sum = new Vector2(0, 0);
		for (Vector2 v : vectors) {
			sum = sum.add(v);
		}

		return sum;
	}

	/**
	 * Subtract method for two vectors, both values of vector are subtracted.s
	 * @param other Vector2
	 * @return new Vector2
	 */
	public Vector2 sub(Vector2 other) {
		return new Vector2(x - other.x, y - other.y);
	}


	/**
	 * Static method provided to subtract multiple vector2 objects together, resulting in a single subtracted vector
	 * @param X amount of other Vector2 objects
	 * @return new Vector2
	 */
	public static Vector2 subtract(Vector2... vectors) {
		Vector2 sum = new Vector2(0, 0);
		for (Vector2 v : vectors) {
			sum = sum.sub(v);
		}

		return sum;
	}

	/**
	 * Multiply method for Vector2 object
	 * @param scalar multiplier
	 * @return Multiplied Vector2
	 */
	public Vector2 mul(double scalar) {
		return new Vector2(x * scalar, y * scalar);
	}

	/**
	 * Divide method for Vector2 object
	 * @param scalar divider
	 * @return
	 */
	public Vector2 div(double scalar) {
		return new Vector2(x / scalar, y / scalar);
	}

	/**
	 * Creates new vector2 with absolute values of x and y in it
	 * @return new Vector2
	 */
	public Vector2 abs() {
		return new Vector2(Math.abs(x), Math.abs(y));
	}
	
	/**
	 * Linear interpolation for Vector2 math. Linearly interpolates between two Vector2 based on "t" value.
	 * @param other Vector2
	 * @param a double value between 0 and 1
	 * @return
	 */
	public Vector2 lerp(Vector2 other, double t) {
        double newX = this.x + (other.x - this.x) * t;
        double newY = this.y + (other.y - this.y) * t;
        return new Vector2(newX, newY);
    }

	@Override
	public String toString() {
		return "Vector2{" + "x=" + x + ", y=" + y + '}';
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector2 other = (Vector2) obj;
		return (int) x == (int) other.x && (int) y == (int) other.y;
	}
}
