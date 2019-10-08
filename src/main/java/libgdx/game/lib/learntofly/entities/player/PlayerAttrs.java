package libgdx.game.lib.learntofly.entities.player;


import libgdx.game.lib.learntofly.util.CashCalculator;

public class PlayerAttrs {

	private int distance;
	private int altitude;
	private int duration;
	private int speed;
	private int stage;
	private boolean distanceRecord;
	private boolean altitudeRecord;
	private boolean durationRecord;
	private boolean speedRecord;

	private int gainedAchievementsReward;

	private CashCalculator cashCalculator = new CashCalculator();

	public PlayerAttrs(int stage) {
		this.stage = stage;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getAltitude() {
		return altitude;
	}

	public void setAltitude(int altitude) {
		this.altitude = altitude;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getDistanceCash() {
		return cashCalculator.getDistanceCashWon(distance, isDistanceRecord(), stage);
	}

	public int getAltitudeCash() {
		return cashCalculator.getAltitudeCashWon(altitude, isAltitudeRecord(), stage);
	}

	public int getDurationCash() {
		return cashCalculator.getDurationCashWon(duration, isDurationRecord(), stage);
	}

	public int getSpeedCash() {
		return cashCalculator.getSpeedCashWon(speed, isSpeedRecord(), stage);
	}

	public int getGainedAchievementsReward() {
		return gainedAchievementsReward;
	}

	public void addGainedAchievementsReward(int gainedAchievementsReward) {
		this.gainedAchievementsReward = this.gainedAchievementsReward + gainedAchievementsReward;
	}

	public int getTotalCash() {
		return getDistanceCash() + getAltitudeCash() + getDurationCash() + getSpeedCash() + getGainedAchievementsReward();
	}

	public void setDistanceRecord(boolean distanceRecord) {
		this.distanceRecord = distanceRecord;
	}

	public void setAltitudeRecord(boolean altitudeRecord) {
		this.altitudeRecord = altitudeRecord;
	}

	public void setDurationRecord(boolean durationRecord) {
		this.durationRecord = durationRecord;
	}

	public void setSpeedRecord(boolean speedRecord) {
		this.speedRecord = speedRecord;
	}

	public boolean isDistanceRecord() {
		return distanceRecord;
	}

	public boolean isAltitudeRecord() {
		return altitudeRecord;
	}

	public boolean isDurationRecord() {
		return durationRecord;
	}

	public boolean isSpeedRecord() {
		return speedRecord;
	}

}
