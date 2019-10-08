package libgdx.game.lib.learntofly.to.menu;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class IncrementShopInfo {

	private Table container;
	private TextButton buyBtn;
	private Label priceLabel;
	private Image coinImage;

	public Table getContainer() {
		return container;
	}

	public void setContainer(Table container) {
		this.container = container;
	}

	public TextButton getBuyBtn() {
		return buyBtn;
	}

	public void setBuyBtn(TextButton buyBtn) {
		this.buyBtn = buyBtn;
	}

	public Label getPriceLabel() {
		return priceLabel;
	}

	public void setPriceLabel(Label priceLabel) {
		this.priceLabel = priceLabel;
	}

	public Image getCoinImage() {
		return coinImage;
	}

	public void setCoinImage(Image coinImage) {
		this.coinImage = coinImage;
	}

}
