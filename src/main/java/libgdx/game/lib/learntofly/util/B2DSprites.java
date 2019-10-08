package libgdx.game.lib.learntofly.util;

import libgdx.game.lib.learntofly.handlers.Animation;
import libgdx.game.lib.learntofly.handlers.B2DVars;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class B2DSprites {

	public static final float SPRITE_SCALE = 0.3f;

	protected Body body;

	private Animation animation;

	protected TextureRegion currentFrame;

	protected float width;
	protected float height;

	public B2DSprites(Body body) {
		this.body = body;
		animation = new Animation();
	}

	public void setAnimation(TextureRegion[] reg, float delay) {
		animation.setFrames(reg, delay);
		width = reg[0].getRegionWidth();
		height = reg[0].getRegionHeight();
	}

	public void update(float dt) {
		animation.update(dt);
	}

	public void render(SpriteBatch sb, float angle) {
		TextureRegion frame = animation.getFrame();
		int regionWidth = frame.getRegionWidth();
		int regionHeight = frame.getRegionHeight();
		render(sb, angle, frame, body.getPosition().x * B2DVars.WORLD_ACC - regionWidth / 2, body.getPosition().y * B2DVars.WORLD_ACC - regionHeight / 2);
	}

	public static void render(SpriteBatch sb, float angle, TextureRegion frame, float xPosition, float yPosition) {
		int regionWidth = frame.getRegionWidth();
		int regionHeight = frame.getRegionHeight();
		sb.draw(frame,
				xPosition,
				yPosition,
				regionWidth / 2,// origin
				regionHeight / 2,// origin
				regionWidth,
				regionHeight,
				SPRITE_SCALE,
				SPRITE_SCALE,
				angle);
	}

	public Animation getAnimation() {
		return animation;
	}

	public Body getBody() {
		return body;
	}

	public Vector2 getPosition() {
		return body.getPosition();
	}

}
