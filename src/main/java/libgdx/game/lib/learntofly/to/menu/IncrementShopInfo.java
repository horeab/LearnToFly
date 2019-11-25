package libgdx.game.lib.learntofly.to.menu;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import libgdx.controls.button.MyButton;
import libgdx.controls.label.MyWrappedLabel;

public class IncrementShopInfo {

	private Table container;
	private MyButton buyBtn;
	private MyWrappedLabel priceLabel;
	private Image coinImage;

	public Table getContainer() {
		return container;
	}

	public void setContainer(Table container) {
		this.container = container;
	}

	public MyButton getBuyBtn() {
		return buyBtn;
	}

	public void setBuyBtn(MyButton buyBtn) {
		this.buyBtn = buyBtn;
	}

	public MyWrappedLabel getPriceLabel() {
		return priceLabel;
	}

	public void setPriceLabel(MyWrappedLabel priceLabel) {
		this.priceLabel = priceLabel;
	}

	public Image getCoinImage() {
		return coinImage;
	}

	public void setCoinImage(Image coinImage) {
		this.coinImage = coinImage;
	}

}
