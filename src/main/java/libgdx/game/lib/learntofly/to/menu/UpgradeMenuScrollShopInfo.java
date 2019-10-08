package libgdx.game.lib.learntofly.to.menu;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class UpgradeMenuScrollShopInfo {

	private List<Label> shopNameLabels = new ArrayList<Label>();

	private List<ImageButton> shopImgButtons = new ArrayList<ImageButton>();

	public List<Label> getShopNameLabels() {
		return shopNameLabels;
	}

	public List<ImageButton> getShopImgButtons() {
		return shopImgButtons;
	}

}
