package libgdx.game.lib.learntofly.creators.accesory;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import libgdx.game.lib.learntofly.creators.TextureAnimation;
import libgdx.game.lib.learntofly.creators.WorldBodyCreator;
import libgdx.game.lib.learntofly.handlers.B2DVars;
import libgdx.game.lib.learntofly.util.B2DSprites;

public abstract class AccesoryCreator {

	protected WorldBodyCreator worldBodyCreator;
	protected World world;
	protected SpriteBatch sb;
	protected Joint playerAccesoryJoint;
	private int delayFrameSteps = 3;
	private int totalSteps = 0;
	protected Map<String, TextureAnimation> animations = new HashMap<String, TextureAnimation>();
	protected List<TextureAnimation> currentAnimation = new ArrayList<TextureAnimation>();
	protected Integer accesoryLevel;
	protected Body playerBody;
	protected String userData;

	public AccesoryCreator(World world, Body playerBody, Integer accesoryLevel, String userData) {
		worldBodyCreator = new WorldBodyCreator(world);
		this.world = world;
		this.accesoryLevel = accesoryLevel;
		this.playerBody = playerBody;
		this.userData = userData;
		if (hasAccesory()) {
			createAccesory();
		}
	}

	public abstract void createAccesory();

	public abstract void removeAccesory();

	public abstract Vector2 getAccesoryPosition();

	public void renderTexture(SpriteBatch sb, float movingAngle) {
		for (TextureAnimation textureAnimation : currentAnimation) {
			int framesLength = textureAnimation.getFrames().length;
			int frameToDisplay = framesLength - 1;
			if (framesLength >= textureAnimation.getCurrentFrameToDisplay() + 1) {
				frameToDisplay = textureAnimation.getFrameToPlay().get(textureAnimation.getCurrentFrameToDisplay());
			}

			TextureRegion[] frames = textureAnimation.getFrames();
			float tileWidth = textureAnimation.getTileWidth();
			float tileHeight = textureAnimation.getTileHeight();

			float yPosition = getAccesoryPosition().y * B2DVars.WORLD_ACC - tileHeight / 2f + textureAnimation.getyOffset();
			float xPosition = getAccesoryPosition().x * B2DVars.WORLD_ACC - tileWidth / 2f + textureAnimation.getxOffset();
			B2DSprites.render(sb, textureAnimation.getFixedAngle() != null ? textureAnimation.getFixedAngle() : movingAngle, frames[frameToDisplay], xPosition, yPosition);

			step(textureAnimation);
		}
	}

	public void setAnimationToPlay(String... animationKey) {
		if (currentAnimation.isEmpty() || !currentAnimationDoesNotContainGivenKeys(animationKey)) {
			currentAnimation.clear();
			for (String key : animationKey) {
				currentAnimation.add(animations.get(key));
			}
		}
	}

	private void step(TextureAnimation textureAnimation) {
		int nrOfFrames = textureAnimation.getFrameToPlay().size();
		if (nrOfFrames > 1) {
			totalSteps++;
			if (totalSteps % delayFrameSteps == 0) {
				textureAnimation.incrementCurrentFrameToDisplay();
			}
			if (textureAnimation.getCurrentFrameToDisplay() > nrOfFrames) {
				if (textureAnimation.isOneTimePlay()) {
					textureAnimation.setCurrentFrameToDisplay(nrOfFrames - 1);
				} else {
					textureAnimation.setCurrentFrameToDisplay(0);
				}
			}
		}
	}

	public boolean hasAccesory() {
		return accesoryLevel != null && accesoryLevel != 0;
	}

	public void dispose() {
	}

	private boolean currentAnimationDoesNotContainGivenKeys(String... animationKey) {
		List<String> animationKeyList = Arrays.asList(animationKey);
		if (currentAnimation.size() != animationKey.length) {
			return false;
		}
		for (TextureAnimation textureAnimation : currentAnimation) {
			if (!animationKeyList.contains(textureAnimation.getAnimationKey())) {
				return false;
			}
		}
		return true;
	}

}
