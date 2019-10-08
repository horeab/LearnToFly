package libgdx.game.lib.learntofly.storemanagers;

import libgdx.game.lib.learntofly.levels.FuelLevel;
import libgdx.game.lib.learntofly.levels.GliderLevel;
import libgdx.game.lib.learntofly.levels.RampLevel;
import libgdx.game.lib.learntofly.levels.RocketLevel;
import libgdx.game.lib.learntofly.levels.SledLevel;
import libgdx.game.lib.learntofly.to.GameInfo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

@SuppressWarnings("synthetic-access")
public class GameInfoManager {

	private static final String GLIDER_LEVEL_BOUGHT = "gliderLevelBought";
	private static final String ROCKET_LEVEL_BOUGHT = "rocketLevelBought";
	private static final String SLED_LEVEL_BOUGHT = "sledLevelBought";
	private static final String GLIDER_LEVEL = "gliderLevel";
	private static final String ROCKET_LEVEL = "rocketLevel";
	private static final String SLED_LEVEL = "sledLevel";
	private static final String RAMP_LEVEL = "rampLevel";
	private static final String FUEL_LEVEL = "fuelLevel";
	private static final String CASH = "cash";
	private static final String CURRENT_DAY = "currentDay";
	private static final String SELECTED_STAGE = "selectedStage";
	private static final String DISTANCE_MAX = "distanceMax";
	private static final String ALTITUDE_MAX = "altitudeMax";
	private static final String DURATION_MAX = "durationMax";
	private static final String SPEED_MAX = "speedMax";

	private static final String DISPLAY_ROCKET_TUTORIAL = "display_rocket_tutorial";
	private static final String DISPLAY_STAGE_SCREEN = "display_stage_screen";
	private static final String DISPLAY_GAME_FINISHED_SCREEN = "display_game_finished_screen";

	private Preferences prefs;

	public GameInfoManager() {
		prefs = Gdx.app.getPreferences("gameStatus");
		// resetAll();
	}

	private int getInteger(String prefKey) {
		return prefs.getInteger(prefKey, 0);
	}

	private void putInteger(int value, String prefKey) {
		prefs.putInteger(prefKey, value);
		prefs.flush();
	}

	public boolean displayRocketTutorial() {
		return prefs.getBoolean(DISPLAY_ROCKET_TUTORIAL, false);
	}

	public void setDisplayRocketTutorial(boolean value) {
		putBoolean(value, DISPLAY_ROCKET_TUTORIAL);
	}

	public boolean displayStageScreen() {
		return prefs.getBoolean(DISPLAY_STAGE_SCREEN, false);
	}

	public void setDisplayStageScreen(boolean value) {
		putBoolean(value, DISPLAY_STAGE_SCREEN);
	}

	public boolean displayGameFinishedScreen() {
		return prefs.getBoolean(DISPLAY_GAME_FINISHED_SCREEN, false);
	}

	public void setGameFinishedScreen(boolean value) {
		putBoolean(value, DISPLAY_GAME_FINISHED_SCREEN);
	}

	private void putBoolean(boolean value, String key) {
		prefs.putBoolean(key, value);
		prefs.flush();
	}

	public GameInfo createGameInfo() {
		int currentDay = getCurrentDay();
		int selectedStage = getSelectedStage();
		int cash = getCash();
		int fuelLevel = getFuelLevel();
		int rampLevel = getRampLevel();
		int sledLevel = getSledLevel();
		int rocketLevel = getRocketLevel();
		int gliderLevel = getGliderLevel();
		GameInfo gameInfo = new GameInfo(currentDay, selectedStage, cash, fuelLevel, rampLevel, sledLevel, rocketLevel, gliderLevel);
		return gameInfo;
	}

	public void resetAll() {
		putInteger(0, DISTANCE_MAX);
		putInteger(0, ALTITUDE_MAX);
		putInteger(0, DURATION_MAX);
		putInteger(0, SPEED_MAX);
		putInteger(0, GLIDER_LEVEL_BOUGHT);
		putInteger(0, ROCKET_LEVEL_BOUGHT);
		putInteger(0, SLED_LEVEL_BOUGHT);
		putInteger(0, GLIDER_LEVEL);
		putInteger(0, ROCKET_LEVEL);
		putInteger(0, SLED_LEVEL);
		putInteger(0, RAMP_LEVEL);
		putInteger(0, FUEL_LEVEL);
		putInteger(0, CASH);
		putInteger(0, CURRENT_DAY);
		putInteger(0, SELECTED_STAGE);
		putBoolean(false, DISPLAY_STAGE_SCREEN);
		putBoolean(false, DISPLAY_ROCKET_TUTORIAL);
	}

	public boolean updateDistanceMax(int newValue) {
		if (newValue > getDistanceMaxLevel()) {
			putInteger(newValue, DISTANCE_MAX);
			return true;
		}
		return false;
	}

	public int getDistanceMaxLevel() {
		return getInteger(DISTANCE_MAX);
	}

	public boolean updateAltitudeMax(int newValue) {
		if (newValue > getAltitudeMaxLevel()) {
			putInteger(newValue, ALTITUDE_MAX);
			return true;
		}
		return false;
	}

	public int getAltitudeMaxLevel() {
		return getInteger(ALTITUDE_MAX);
	}

	public boolean updateDurationMax(int newValue) {
		if (newValue > getDurationMaxLevel()) {
			putInteger(newValue, DURATION_MAX);
			return true;
		}
		return false;
	}

	public int getDurationMaxLevel() {
		return getInteger(DURATION_MAX);
	}

	public boolean updateSpeedMax(int newValue) {
		if (newValue > getSpeedMaxLevel()) {
			putInteger(newValue, SPEED_MAX);
			return true;
		}
		return false;
	}

	public int getSpeedMaxLevel() {
		return getInteger(SPEED_MAX);
	}

	public int getBoughtLevel(int shopId) {
		if (shopId == GliderLevel.SHOP_ID) {
			return getBoughtGliderLevel();
		} else if (shopId == SledLevel.SHOP_ID) {
			return getBoughtSledLevel();
		} else if (shopId == RocketLevel.SHOP_ID) {
			return getBoughtRocketLevel();
		}
		return -1;
	}

	public int getBoughtGliderLevel() {
		return getInteger(GLIDER_LEVEL_BOUGHT);
	}

	public int getBoughtRocketLevel() {
		return getInteger(ROCKET_LEVEL_BOUGHT);
	}

	public int getBoughtSledLevel() {
		return getInteger(SLED_LEVEL_BOUGHT);
	}

	private int getGliderLevel() {
		return getInteger(GLIDER_LEVEL);
	}

	private int getRocketLevel() {
		return getInteger(ROCKET_LEVEL);
	}

	private int getSledLevel() {
		return getInteger(SLED_LEVEL);
	}

	private int getRampLevel() {
		return getInteger(RAMP_LEVEL);
	}

	public int getCurrentDay() {
		return getInteger(CURRENT_DAY);
	}

	public GameInfo setSelectedStage(int selectedStage, GameInfo gameInfo) {
		putInteger(selectedStage, SELECTED_STAGE);
		gameInfo.setSelectedStage(getSelectedStage());
		return gameInfo;
	}

	private int getSelectedStage() {
		int selectedStage = getInteger(SELECTED_STAGE);
		return selectedStage > 0 ? selectedStage : 1;
	}

	private int getFuelLevel() {
		return getInteger(FUEL_LEVEL);
	}

	private int getCash() {
		return getInteger(CASH);
	}

	public GameInfo incrementCurrentDay(GameInfo gameInfo) {
		putInteger(gameInfo.getCurrentDay() + 1, CURRENT_DAY);
		gameInfo.setCurrentDay(getCurrentDay());
		return gameInfo;
	}

	public GameInfo updateCash(GameInfo gameInfo, int newCashValue) {
		putInteger(newCashValue, CASH);
		gameInfo.setCash(getCash());
		return gameInfo;
	}

	public GameInfo setShopLevel(final GameInfo gameInfo, int shopId, int newLevel) {
		if (shopId == GliderLevel.SHOP_ID) {
			updateShopLevel(gameInfo, GliderLevel.values().length, newLevel, GLIDER_LEVEL, new PrefShopLevel() {
				@Override
				public void setNewLevel() {
					gameInfo.setGliderLevel(getGliderLevel());
				}
			});
		} else if (shopId == SledLevel.SHOP_ID) {
			updateShopLevel(gameInfo, SledLevel.values().length, newLevel, SLED_LEVEL, new PrefShopLevel() {
				@Override
				public void setNewLevel() {
					gameInfo.setSledLevel(getSledLevel());
				}
			});
		} else if (shopId == RocketLevel.SHOP_ID) {
			updateShopLevel(gameInfo, RocketLevel.values().length, newLevel, ROCKET_LEVEL, new PrefShopLevel() {
				@Override
				public void setNewLevel() {
					gameInfo.setRocketLevel(getRocketLevel());
				}
			});
		}
		return gameInfo;
	}

	public GameInfo incrementShopLevel(final GameInfo gameInfo, int shopId) {
		if (shopId == GliderLevel.SHOP_ID) {
			incrementGliderShopLevel(gameInfo);
		} else if (shopId == SledLevel.SHOP_ID) {
			incrementSledShopLevel(gameInfo);
		} else if (shopId == RocketLevel.SHOP_ID) {
			incrementRocketShopLevel(gameInfo);
		} else if (shopId == FuelLevel.SHOP_ID) {
			incrementFuelShopLevel(gameInfo);
		} else if (shopId == RampLevel.SHOP_ID) {
			incrementRampShopLevel(gameInfo);
		}
		return gameInfo;
	}

	private GameInfo incrementGliderShopLevel(final GameInfo gameInfo) {
		putInteger(getBoughtGliderLevel() + 1, GLIDER_LEVEL_BOUGHT);
		return updateShopLevel(gameInfo, GliderLevel.values().length, gameInfo.getGliderLevel() + 1, GLIDER_LEVEL,
				new PrefShopLevel() {
					@Override
					public void setNewLevel() {
						gameInfo.setGliderLevel(getBoughtGliderLevel());
					}
				});
	}

	private GameInfo incrementRocketShopLevel(final GameInfo gameInfo) {
		putInteger(getBoughtRocketLevel() + 1, ROCKET_LEVEL_BOUGHT);
		return updateShopLevel(gameInfo, RocketLevel.values().length, gameInfo.getRocketLevel() + 1, ROCKET_LEVEL,
				new PrefShopLevel() {
					@Override
					public void setNewLevel() {
						gameInfo.setRocketLevel(getBoughtRocketLevel());
					}
				});
	}

	private GameInfo incrementSledShopLevel(final GameInfo gameInfo) {
		putInteger(getBoughtSledLevel() + 1, SLED_LEVEL_BOUGHT);
		return updateShopLevel(gameInfo, SledLevel.values().length, gameInfo.getSledLevel() + 1, SLED_LEVEL,
				new PrefShopLevel() {
					@Override
					public void setNewLevel() {
						gameInfo.setSledLevel(getBoughtSledLevel());
					}
				});
	}

	private GameInfo incrementRampShopLevel(final GameInfo gameInfo) {
		return updateShopLevel(gameInfo, RampLevel.values().length, gameInfo.getRampLevel() + 1, RAMP_LEVEL,
				new PrefShopLevel() {
					@Override
					public void setNewLevel() {
						gameInfo.setRampLevel(getRampLevel());
					}
				});
	}

	private GameInfo incrementFuelShopLevel(final GameInfo gameInfo) {
		return updateShopLevel(gameInfo, FuelLevel.values().length, gameInfo.getFuelLevel() + 1, FUEL_LEVEL,
				new PrefShopLevel() {
					@Override
					public void setNewLevel() {
						gameInfo.setFuelLevel(getFuelLevel());
					}
				});
	}

	private GameInfo updateShopLevel(GameInfo gameInfo, int totalLevels, int newLevel, String prefKey, PrefShopLevel shopLevel) {
		if (totalLevels - 1 >= newLevel) {
			putInteger(newLevel, prefKey);
			shopLevel.setNewLevel();
		}
		return gameInfo;
	}

	private abstract class PrefShopLevel {
		public abstract void setNewLevel();
	}
}
