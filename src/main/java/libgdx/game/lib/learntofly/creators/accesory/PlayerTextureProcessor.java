package libgdx.game.lib.learntofly.creators.accesory;

import com.badlogic.gdx.assets.AssetManager;

import libgdx.game.lib.learntofly.entities.player.Player;
import libgdx.game.lib.learntofly.util.Resource;
import libgdx.game.lib.learntofly.util.Utils;

class PlayerTextureProcessor {

	private Player player;
	private boolean alreadyTextureChanged;
	private AssetManager manager;

	public PlayerTextureProcessor(Player player, AssetManager manager) {
		this.player = player;
		this.manager = manager;
	}

	public void processPlayerGoesDownRamp() {
		if (!alreadyTextureChanged) {
			player.setAnimation(Utils.getTexture(manager, Resource.peng_slide), Player.PLAYER_SLIDING_TILE_WIDTH);
			player.getAnimation().setFixedFrame(1);
			alreadyTextureChanged = true;
		}
	}

}
