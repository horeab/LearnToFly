package libgdx.game.lib.learntofly.util;

public class CashCalculator {

	public int getDistanceCashWon(int distanceCurrent, boolean isRecord, int stage) {
		return (int) (distanceCurrent * getRatio(stage)) + addRecordCash(isRecord, stage);
	}

	public int getAltitudeCashWon(int altitudeCurrent, boolean isRecord, int stage) {
		return (int) (altitudeCurrent * getRatio(stage)) + addRecordCash(isRecord, stage);
	}

	public int getDurationCashWon(int durationCurrent, boolean isRecord, int stage) {
		return (int) (durationCurrent * getRatio(stage)) + addRecordCash(isRecord, stage);
	}

	public int getSpeedCashWon(int speedCurrent, boolean isRecord, int stage) {
		return (int) (speedCurrent * getRatio(stage)) + addRecordCash(isRecord, stage);
	}

	public int getTotalCashWon(
			int distanceCurrent, boolean distanceRecord,
			int altitudeCurrent, boolean altitudeRecord,
			int durationCurrent, boolean durationRecord,
			int speedCurrent, boolean speedRecord,
			int gainedAchievementsReward,
			int stage) {
		return getDistanceCashWon(distanceCurrent, distanceRecord, stage) +
				getAltitudeCashWon(altitudeCurrent, altitudeRecord, stage) +
				getDurationCashWon(durationCurrent, durationRecord, stage) +
				getSpeedCashWon(speedCurrent, speedRecord, stage) +
				gainedAchievementsReward;
	}

	private int addRecordCash(boolean isRecord, int stage) {
		return isRecord ? getCashWonIfRecord(stage) : 0;
	}

	private int getCashWonIfRecord(int stage) {
		return stage * 75;
	}

	private float getRatio(int stage) {
		return (stage * 0.9f) + 1f;
	}
}
