package game;

public class AlienEntity extends Entity {

	/** The game in which this entity exists */
	private Game game;
	
	public AlienEntity(Game game, String ref, int x, int y) {
		super(ref, x, y);
		// TODO Auto-generated constructor stub
	}
	
	public void move(long delta){
		// if we have reached the left hand side of the screen and
		// are moving left then request a logic update 
		if ((dx < 0) && (x < 10)){
			game.updateLogic();
		}
		
		// and vice vesa, if we have reached the right hand side of 
		// the screen and are moving right, request a logic update
		if ((dx > 0) && (x > 750)) {
			game.updateLogic();
		}
		
		// proceed with normal move
		super.move(delta);
	}
	
	@Override
	public void collidedWith(Entity other) {
		// TODO Auto-generated method stub

	}

}
