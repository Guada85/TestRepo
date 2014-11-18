package game;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * An entity represents any element that appears in the game. The entity is responsible for resolving collisions and movement
 * based on a set of properties defined either by subclass or externally.
 * 
 * Note that doubles are used for positions. This may seem strange given that pixels locations are integers. However, 
 * using double means that an entity can move a partial pixel. It doesn't of course mean that they will be display half 
 * way through a pixel but allows us not lose accuracy as we move.
 * 
 * @author Guada
 *
 */
public abstract class Entity {

	/** The current x location of this entity*/
	protected double x;
	/** The current y location of this entity*/
	protected double y;
	/** The Sprite that represents this entity*/
	protected Sprite sprite;
	/** The current speed of this entity horizontally (pixels/sec)*/
	protected double dx;
	/** The current speed of this entity vertically (pixels/sec)*/
	protected double dy;
	/** The rectangle used for this entity during collision resolution*/
	protected Rectangle me = new Rectangle();
	/** The rectangle used for other entities during collision resolution*/
	protected Rectangle him = new Rectangle();
	
	
	/**
	 * Construct a entity based on a sprite image and a location.
	 * 
	 * @param ref The reference to the image to be displayed for this entity
 	 * @param x The initial x location of this entity
	 * @param y The initial y location of this entity
	 */
	public Entity(String ref, int x,int y) {	
		this.sprite = SpriteStore.get().getSprite(ref);
		this.x = x;
		this.y = y;
	}

	/**
	 * Request that this entity move itself based on a certain amount of time passing.
	 * 
	 * @param delta The amount of time that has passed in milliseconds
	 */
	public void move(long delta){
		// Update the location of entity based on move speed
		x += (delta * dx) / 1000;
		y += (delta * dy) / 1000;
	}
	
	/**
	 * Set the vertical speed of this entity
	 * 
	 * @param dx The vertical speed of this entity (pixels/sec)
	 */
	public void setVerticalMovement(double dy) {
		this.dy = dy;
	}
	
	/**
	 * Get the horizontal speed of this entity
	 * 
	 * @return The horizontal speed of this entity (pixels/sec)
	 */
	public double getHorizontalMovement() {
		return dx;
	}

	/**
	 * Get the vertical speed of this entity
	 * 
	 * @return The vertical speed of this entity (pixels/sec)
	 */
	public double getVerticalMovement() {
		return dy;
	}
	
	/**
	 * Draw this entity to the graphic contest provided
	 * 
	 * @param g The graphic contest on which to draw
	 */
	public void draw(Graphics g){
		sprite.draw(g, (int) x, (int) y);
	}
	
	/**
	 * Get the x location of this entity
	 * 
	 * @return The x location of this entity
	 */
	public int getX() {
		return (int) x;
	}

	/**
	 * Get the y location of this entity
	 * 
	 * @return The y location of this entity
	 */
	public int getY() {
		return (int) y;
	}
	
	/**
	 * Check if this entity collides with other
	 * 
	 * @param other The other entity to check collision against
	 * @return True if entities collides each other
	 */	
	public boolean collidesWith(Entity other){
		me.setBounds((int) x, (int) y, sprite.getWidth(), sprite.getHeight());
		him.setBounds((int) other.x, (int) other.y, other.sprite.getWidth(), other.sprite.getHeight());
		
		return me.intersects(him);
	}
	
	/**
	 * Notification that this entity collided with another.
	 * 
	 * @param other The entity with which this entity collided.
	 */
	public abstract void collidedWith(Entity other);
}
