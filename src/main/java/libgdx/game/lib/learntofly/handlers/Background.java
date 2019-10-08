package libgdx.game.lib.learntofly.handlers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Background {

	private TextureRegion textureRegion;
	private OrthographicCamera gameCam;
	private float backgroundLevel;

	private Float hardcodedXPositon;
	private Float hardcodedYPosition;

	private float textureScale;
	private int numDrawX;

	private int heightLevel;

	@SuppressWarnings("unused")
	private String img;

	public Background(TextureRegion image, OrthographicCamera gameCam, float backDistance, String img, int level, float displayWidth) {
		this(image, gameCam, backDistance, img, level, null, null, 1f, displayWidth);
	}

	public Background(TextureRegion textureRegion, OrthographicCamera gameCam, float backDistance, String img, int level, Float hardcodedXPositon, Float hardcodedYPosition, float textureScale,
			float displayWidth) {
		this.textureRegion = textureRegion;
		this.gameCam = gameCam;
		this.backgroundLevel = backDistance;
		this.img = img;
		this.heightLevel = level;
		this.hardcodedXPositon = hardcodedXPositon;
		this.hardcodedYPosition = hardcodedYPosition;
		this.textureScale = textureScale;
		numDrawX = (int) (displayWidth / (textureRegion.getRegionWidth() * textureScale) + 2);
	}

	public void render(SpriteBatch sb) {

		float x = ((gameCam.viewportWidth / 2 - gameCam.position.x) * backgroundLevel) % getRegionWidth();

		float startingX = 0;
		if (gameCam.position.x > getRegionWidth()) {
			startingX = getRegionWidth() * (int) (gameCam.position.x / getRegionWidth());
		}
		float startingY = 0;
		if (gameCam.position.y > textureRegion.getRegionHeight()) {
			startingY = textureRegion.getRegionHeight() * (int) (gameCam.position.y / textureRegion.getRegionHeight());
		}

		int colOffset = x > 0 ? -1 : 0;
		for (int col = 0; col < numDrawX; col++) {
			float xPosition = x + (col + colOffset) * getRegionWidth() + startingX;
			if (startingY != 0 && heightLevel == -1) {
				drawSprite(sb,
						processHardcodedXPositon(xPosition - getRegionWidth()),
						processHardcodedYPositon(startingY - textureRegion.getRegionHeight()));
			} else if (heightLevel == 0 || heightLevel == 1) {
				int addHeight = heightLevel == 1 ? textureRegion.getRegionHeight() : 0;
				float sbY = processHardcodedYPositon(startingY + addHeight);
				// draw tile after
				if (startingX != 0) {
					drawSprite(sb, processHardcodedXPositon(xPosition - getRegionWidth()), sbY);
				}
				drawSprite(sb, processHardcodedXPositon(xPosition), sbY);
				drawSprite(sb, processHardcodedXPositon(xPosition + getRegionWidth()), sbY);
			}
		}
	}

	private void drawSprite(SpriteBatch sb, float sbX, float sbY) {
		float regionWidth = getRegionWidth();
		float regionHeight = textureRegion.getRegionHeight();
		sb.draw(textureRegion,
				sbX,// x
				sbY - sbY / 20,// y
				regionWidth,// origin
				regionHeight,// origin
				regionWidth,// width
				regionHeight,// height
				1f,// scale
				textureScale,// scale
				0);
	}

	private float getRegionWidth() {
		return textureRegion.getRegionWidth() * textureScale;
	}

	private float processHardcodedXPositon(float value) {
		return hardcodedXPositon == null ? value : hardcodedXPositon;
	}

	private float processHardcodedYPositon(float value) {
		return hardcodedYPosition == null ? value : hardcodedYPosition;
	}

}
