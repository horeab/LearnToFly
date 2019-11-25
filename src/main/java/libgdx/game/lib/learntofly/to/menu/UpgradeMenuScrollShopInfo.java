package libgdx.game.lib.learntofly.to.menu;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import libgdx.controls.label.MyWrappedLabel;

public class UpgradeMenuScrollShopInfo {

	private List<MyWrappedLabel> shopNameLabels = new ArrayList<MyWrappedLabel>();

	private List<ImageButton> shopImgButtons = new ArrayList<ImageButton>();

	public List<MyWrappedLabel> getShopNameLabels() {
		return shopNameLabels;
	}

	public List<ImageButton> getShopImgButtons() {
		return shopImgButtons;
	}

}
