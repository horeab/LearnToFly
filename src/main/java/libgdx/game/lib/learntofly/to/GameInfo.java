package libgdx.game.lib.learntofly.to;


import libgdx.game.lib.learntofly.levels.FuelLevel;
import libgdx.game.lib.learntofly.levels.GliderLevel;
import libgdx.game.lib.learntofly.levels.RampLevel;
import libgdx.game.lib.learntofly.levels.RocketLevel;
import libgdx.game.lib.learntofly.levels.SledLevel;

public class GameInfo {

	private int selectedStage;
	private int currentDay;
	private int cash;

	private int fuelLevel;
	private int rampLevel;
	private int sledLevel;
	private int rocketLevel;
	private int gliderLevel;

	public GameInfo(int currentDay, int selectedStage, int cash, int fuelLevel, int rampLevel, int sledLevel, int rocketLevel, int gliderLevel) {
		this.selectedStage = selectedStage;
		this.currentDay = currentDay;
		this.cash = cash;
		this.fuelLevel = fuelLevel;
		this.rampLevel = rampLevel;
		this.sledLevel = sledLevel;
		this.rocketLevel = rocketLevel;
		this.gliderLevel = gliderLevel;
	}

	public int getCurrentDay() {
		return currentDay;
	}

	public void setCurrentDay(int currentDay) {
		this.currentDay = currentDay;
	}

	public int getSelectedStage() {
		return selectedStage;
	}

	public void setSelectedStage(int selectedStage) {
		this.selectedStage = selectedStage;
	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}

	public int getFuelLevel() {
		return fuelLevel;
	}

	public void setFuelLevel(int fuelLevel) {
		this.fuelLevel = fuelLevel;
	}

	public int getRampLevel() {
		return rampLevel;
	}

	public void setRampLevel(int rampLevel) {
		this.rampLevel = rampLevel;
	}

	public int getSledLevel() {
		return sledLevel;
	}

	public void setSledLevel(int sledLevel) {
		this.sledLevel = sledLevel;
	}

	public int getRocketLevel() {
		return rocketLevel;
	}

	public void setRocketLevel(int rocketLevel) {
		this.rocketLevel = rocketLevel;
	}

	public int getGliderLevel() {
		return gliderLevel;
	}

	public void setGliderLevel(int gliderLevel) {
		this.gliderLevel = gliderLevel;
	}

	public int getLevelForShopId(int shopId) {
		if (shopId == GliderLevel.SHOP_ID) {
			return gliderLevel;
		} else if (shopId == SledLevel.SHOP_ID) {
			return sledLevel;
		} else if (shopId == RocketLevel.SHOP_ID) {
			return rocketLevel;
		} else if (shopId == FuelLevel.SHOP_ID) {
			return fuelLevel;
		} else if (shopId == RampLevel.SHOP_ID) {
			return rampLevel;
		}
		return -1;

	}

	public boolean gameExistsInMemory() {
		return getCurrentDay() > 0;
	}

}
