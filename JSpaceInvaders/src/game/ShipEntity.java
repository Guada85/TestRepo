package game;

public class ShipEntity extends Entity {

	public ShipEntity(Game game, String ref, int x, int y) {
		super(ref, x, y);
		// TODO Auto-generated constructor stub
	}
	
	public void move(long delta){
		// if we are moving left and have reached the left hand side of the screen, don't move
		if ((dx < 0) && (x > 10)){
			return;
		}
		
		// if we are moving right and have reached the right hand of the screen, don't move
		if ((dx > 0) && (x <750)){
			return;
		}
		
		super.move(delta);
	}

	@Override
	public void collidedWith(Entity other) {
		// TODO Auto-generated method stub

	}

}
