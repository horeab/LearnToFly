package libgdx.game.lib.learntofly.to.menu;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import libgdx.controls.label.MyWrappedLabel;

public class HeaderInfo {

	private Table headerTable;
	private MyWrappedLabel currentDay;
	private MyWrappedLabel stage;
	private MyWrappedLabel cash;

	public HeaderInfo(Table headerTable, MyWrappedLabel currentDay, MyWrappedLabel stage, MyWrappedLabel cash) {
		super();
		this.headerTable = headerTable;
		this.currentDay = currentDay;
		this.stage = stage;
		this.cash = cash;
	}

	public Table getHeaderTable() {
		return headerTable;
	}

	public void setHeaderTable(Table headerTable) {
		this.headerTable = headerTable;
	}

	public MyWrappedLabel getCurrentDay() {
		return currentDay;
	}

	public void setCurrentDay(MyWrappedLabel currentDay) {
		this.currentDay = currentDay;
	}

	public MyWrappedLabel getCash() {
		return cash;
	}

	public void setCash(MyWrappedLabel cash) {
		this.cash = cash;
	}

	public MyWrappedLabel getStage() {
		return stage;
	}

	public void setStage(MyWrappedLabel stage) {
		this.stage = stage;
	}

}
