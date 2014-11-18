package game;

public class ShotEntity extends Entity {
	
	/** The vertical speed at which the players shot moves */
	private double moveSpeed = -300;
	/** The game in which this entity exists */
	private Game game;
	/** True if this shot has been "used", i.e. its hit something */
	private boolean used = false;

	public ShotEntity(String ref, int x, int y) {
		super(ref, x, y);
		// TODO Auto-generated constructor stub
	}

	public void move(long delta){
		// proceed with normal move
		super.move(delta);
		
		// if we shot off the screen, remove ourself
		if (y < -100){
			game.removeEntity(this);
		}
	}
	
	@Override
	public void collidedWith(Entity other) {
		// TODO Auto-generated method stub

	}

}
