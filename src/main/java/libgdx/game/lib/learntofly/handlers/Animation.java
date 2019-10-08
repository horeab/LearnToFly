package libgdx.game.lib.learntofly.handlers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animation {

	private TextureRegion[] frames;
	private float time;
	private float delay;
	private int currentFrame;
	private Integer fixedFrame;
	private int timesDisplayed;
	public boolean fixedFrameSet;

	public Animation() {
	}

	public Animation(TextureRegion[] frames) {
		this(frames, 1 / 12f);
	}

	public Animation(TextureRegion[] frames, float delay) {
		setFrames(frames, delay);
	}

	public void setFrames(TextureRegion[] frames, float delay) {
		this.frames = frames;
		this.delay = delay;
		time = 0;
		currentFrame = 0;
		timesDisplayed = 0;
	}

	public void update(float dt) {
		if (delay < 0) {
			return;
		}
		time += dt;
		while (time >= delay) {
			step();

		}
	}

	private void step() {
		time -= delay;
		currentFrame++;
		if (currentFrame == frames.length) {
			currentFrame = 0;
			timesDisplayed++;
		}
		if (fixedFrame != null && currentFrame == fixedFrame && !fixedFrameSet) {
			currentFrame = fixedFrame;
			fixedFrameSet = true;
		}
		if (fixedFrameSet) {
			currentFrame = fixedFrame;
		}
	}

	public TextureRegion getFrame() {
		return frames[currentFrame];
	}

	public void setFixedFrame(Integer fixedFrame) {
		this.fixedFrame = fixedFrame;
	}

	public int getTimesDisplayed() {
		return timesDisplayed;
	}

}
