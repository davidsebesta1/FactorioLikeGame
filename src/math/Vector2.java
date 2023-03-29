package math;

import java.io.Serializable;
import java.util.Objects;

public class Vector2 implements Serializable{
	private static final transient long serialVersionUID = 1812445214L;
	private float x;
	private float y;
	
	public static final transient Vector2 zero = new Vector2(0, 0);

	// Constructor
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	// Constructor with doubles
	public Vector2(double x, double y) {
		this.x = (float) x;
		this.y = (float) y;
	}

	// Setters, getters and methods
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	// Returns magnitude of a vector
	public float magnitude() {
		return (float) Math.sqrt(x * x + y * y);
	}

	// Returns normalized vector
	public void normalize() {
		float magnitude = this.magnitude();
		this.x /= magnitude;
		this.y /= magnitude;
	}

	// Adds up two vectors
	public Vector2 add(Vector2 other) {
		return new Vector2(x + other.x, y + other.y);
	}

	// Add static method
	public static Vector2 add(Vector2... vectors) {
		Vector2 sum = new Vector2(0, 0);
		for (Vector2 v : vectors) {
			sum = sum.add(v);
		}

		return sum;
	}

	// Subtracts vectors
	public Vector2 sub(Vector2 other) {
		return new Vector2(x - other.x, y - other.y);
	}

	// Subtract static method
	public static Vector2 subtract(Vector2... vectors) {
		Vector2 sum = new Vector2(0, 0);
		for (Vector2 v : vectors) {
			sum = sum.sub(v);
		}

		return sum;
	}

	// Multiplies vector by a scalar parameter
	public Vector2 mul(float scalar) {
		return new Vector2(x * scalar, y * scalar);
	}

	// Divides vector by a scalar parameter
	public Vector2 div(float scalar) {
		return new Vector2(x / scalar, y / scalar);
	}

	// Returns vector´s length
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	// Absolute value
	public Vector2 abs() {
		return new Vector2(Math.abs(x), Math.abs(y));
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
		return Float.floatToIntBits(x) == Float.floatToIntBits(other.x)
				&& Float.floatToIntBits(y) == Float.floatToIntBits(other.y);
	}
}
