package engine.sprites;

/**
 * Interface for physics sprites
 * @author David Šebesta
 * @see PhysicsSprite
 *
 */
public interface IPhysicsBehaviour {
	public abstract void enteredCollision(PhysicsSprite sprite);
}
