package libgdx.game.lib.learntofly.to.menu;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class ScrollShopInfo {

	private List<Button> buyBtns = new ArrayList<Button>();
	private List<Table> buyBtnTextContainer = new ArrayList<Table>();
	private List<Label> priceLabels = new ArrayList<Label>();

	public List<Button> getBuyBtns() {
		return buyBtns;
	}

	public void setBuyBtns(List<Button> buyBtns) {
		this.buyBtns = buyBtns;
	}

	public List<Label> getPriceLabels() {
		return priceLabels;
	}

	public void setPriceLabels(List<Label> priceLabels) {
		this.priceLabels = priceLabels;
	}

	public List<Table> getBuyBtnTextContainer() {
		return buyBtnTextContainer;
	}

	public void setBuyBtnTextContainer(List<Table> buyBtnTextContainer) {
		this.buyBtnTextContainer = buyBtnTextContainer;
	}

}
