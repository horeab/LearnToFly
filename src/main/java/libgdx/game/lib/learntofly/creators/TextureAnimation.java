package libgdx.game.lib.learntofly.creators;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.List;

import libgdx.game.lib.learntofly.util.Resource;
import libgdx.game.lib.learntofly.util.Utils;

public class TextureAnimation {

	private TextureRegion[] frames;
	private List<Integer> frameToPlay;
	private int tileWidth;
	private int tileHeight;
	private boolean oneTimePlay = true;
	private Resource resource;
	private String animationKey;
	private Float fixedAngle;
	private float xOffset = 0f;
	private float yOffset = 0f;
	private int currentFrameToDisplay;

	public TextureAnimation(Resource resource, String animationKey, List<Integer> frameToPlay, int tileWidth, int tileHeight, AssetManager assetManager) {
		Texture texture = Utils.getTextureWithFilter(assetManager, resource);
		this.frames = TextureRegion.split(texture, tileWidth, tileHeight)[0];
		this.frameToPlay = frameToPlay;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.resource = resource;
		this.animationKey = animationKey;
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public TextureRegion[] getFrames() {
		return frames;
	}

	public List<Integer> getFrameToPlay() {
		return frameToPlay;
	}

	public boolean isOneTimePlay() {
		return oneTimePlay;
	}

	public void setOneTimePlay(boolean oneTimePlay) {
		this.oneTimePlay = oneTimePlay;
	}

	public Resource getResource() {
		return resource;
	}

	public String getAnimationKey() {
		return animationKey;
	}

	public float getxOffset() {
		return xOffset;
	}

	public void setxOffset(float xOffset) {
		this.xOffset = xOffset;
	}

	public float getyOffset() {
		return yOffset;
	}

	public void setyOffset(float yOffset) {
		this.yOffset = yOffset;
	}

	public Float getFixedAngle() {
		return fixedAngle;
	}

	public void setFixedAngle(Float fixedAngle) {
		this.fixedAngle = fixedAngle;
	}

	public int getCurrentFrameToDisplay() {
		return currentFrameToDisplay;
	}

	public void setCurrentFrameToDisplay(int currentFrameToDisplay) {
		this.currentFrameToDisplay = currentFrameToDisplay;
	}

	public void incrementCurrentFrameToDisplay() {
		this.currentFrameToDisplay++;
	}

}
