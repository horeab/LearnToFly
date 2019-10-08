package libgdx.game.lib.learntofly.to.menu;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class HeaderInfo {

	private Table headerTable;
	private Label currentDay;
	private Label stage;
	private Label cash;

	public HeaderInfo(Table headerTable, Label currentDay, Label stage, Label cash) {
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

	public Label getCurrentDay() {
		return currentDay;
	}

	public void setCurrentDay(Label currentDay) {
		this.currentDay = currentDay;
	}

	public Label getCash() {
		return cash;
	}

	public void setCash(Label cash) {
		this.cash = cash;
	}

	public Label getStage() {
		return stage;
	}

	public void setStage(Label stage) {
		this.stage = stage;
	}

}
