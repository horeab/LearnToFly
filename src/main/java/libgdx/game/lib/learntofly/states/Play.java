package libgdx.game.lib.learntofly.states;

import libgdx.game.lib.learntofly.achievements.Achievement;
import libgdx.game.lib.learntofly.creators.PlayerCreator;
import libgdx.game.lib.learntofly.creators.RampCreator;
import libgdx.game.lib.learntofly.creators.accesory.GliderCreator;
import libgdx.game.lib.learntofly.creators.accesory.PlayerOnRampProcessor;
import libgdx.game.lib.learntofly.creators.accesory.RocketCreator;
import libgdx.game.lib.learntofly.creators.accesory.SledCreator;
import libgdx.game.lib.learntofly.entities.player.Player;
import libgdx.game.lib.learntofly.entities.player.PlayerAttrs;
import libgdx.game.lib.learntofly.handlers.B2DVars;
import libgdx.game.lib.learntofly.handlers.BBInput;
import libgdx.game.lib.learntofly.handlers.Background;
import libgdx.game.lib.learntofly.handlers.GameStateManager;
import libgdx.game.lib.learntofly.handlers.MyContactListener;
import libgdx.game.lib.learntofly.handlers.MyInputProcessor;
import libgdx.game.lib.learntofly.hud.HUD;
import libgdx.game.lib.learntofly.levels.FuelLevel;
import libgdx.game.lib.learntofly.levels.GliderLevel;
import libgdx.game.lib.learntofly.levels.RampLevel;
import libgdx.game.lib.learntofly.levels.RocketLevel;
import libgdx.game.lib.learntofly.levels.SledLevel;
import libgdx.game.lib.learntofly.main.Game;
import libgdx.game.lib.learntofly.to.GameInfo;
import libgdx.game.lib.learntofly.util.BackgroundLoader;
import libgdx.game.lib.learntofly.util.CamPositionCalculator;
import libgdx.game.lib.learntofly.util.CashCalculator;
import libgdx.game.lib.learntofly.util.Resource;
import libgdx.game.lib.learntofly.util.Utils;

import static libgdx.game.lib.learntofly.handlers.B2DVars.*;
import static libgdx.game.lib.learntofly.util.Utils.*;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import java.util.List;

@SuppressWarnings("unused")
public class Play extends GameState {

    public enum GameRunState {
        RUN, GAME_FINISHED
    }

    private GameRunState gameRunState = GameRunState.RUN;
    private boolean finishUpdatesMade;
    private boolean screenDisposed;
    private boolean isWholeScreenPressed;

    // private boolean debug = true;
    private boolean debug = false;

    private Box2DDebugRenderer b2dr = new Box2DDebugRenderer();
    private OrthographicCamera b2dcam = new OrthographicCamera();

    private World world;

    private MyContactListener contactListener;
    private MyInputProcessor inputProcessor;
    private PlayerOnRampProcessor playerOnRampProcessor;

    private RampCreator rampCreator;
    private PlayerCreator playerCreator;

    private SledCreator sledCreator;
    private GliderCreator gliderCreator;
    private RocketCreator rocketCreator;

    private CamPositionCalculator camPositionCalculator;
    private BackgroundLoader backgroundLoader;

    private SledLevel sledLevelEnum;
    private RocketLevel rocketLevelEnum;
    private GliderLevel gliderLevelEnum;
    private FuelLevel fuelLevelEnum;

    private Player player;
    private PlayerAttrs playerAttrs;
    private HUD hud;

    private List<Background> backgrounds;

    private int currentAltitudeLevel = 0;
    private float availableFuel = 70f;
    private float topSpeedAllowed = 40f;

    private SpriteBatch spriteBatch = new SpriteBatch();
    private PolygonSpriteBatch polygonSpriteBatch = new PolygonSpriteBatch();

    private float secondsPassed = 0;

    private Sound rocketSound;
    private Sound achievementSound;
    private Sound stageUnlockedSound;
    private boolean rocketSoundStarted;

    public Play(GameStateManager gameStateManager, GameInfo gameInfo) {
        super(gameStateManager, gameInfo);
    }

    @Override
    public void buildStage() {
        changeGameInfo();
        initPlayFields();
        createWorldBodies();
        initDebugB2dr();
    }

    @Override
    public void onBackKeyPress() {
        gameStateManager.setUpgradeShopState(gameInfo);
    }

    private void changeGameInfo() {
        gameInfoManager.incrementCurrentDay(gameInfo);
    }

    private void initDebugB2dr() {
        b2dcam.setToOrtho(false, scaleDownPPM(Game.getWidth()), scaleDownPPM(Game.getHeight()));
    }

    private void initPlayFields() {
        initSound();
        playerAttrs = new PlayerAttrs(gameInfo.getSelectedStage());
        inputProcessor = new MyInputProcessor();
        Gdx.input.setInputProcessor(inputProcessor);

        world = new World(new Vector2(0, -0.91f), true);
        world.setContactListener(contactListener = new MyContactListener());

        playerCreator = new PlayerCreator(world);
        camPositionCalculator = new CamPositionCalculator();
        backgroundLoader = new BackgroundLoader(gameInfo.getSelectedStage());
        backgrounds = backgroundLoader.loadBackgroundForAltitudeLevel(0, cam);
        sledLevelEnum = (SledLevel) SledLevel.NO_LEVEL.getItem(gameInfo.getSledLevel());
        rocketLevelEnum = (RocketLevel) RocketLevel.NO_LEVEL.getItem(gameInfo.getRocketLevel());
        gliderLevelEnum = (GliderLevel) GliderLevel.NO_LEVEL.getItem(gameInfo.getGliderLevel());
        fuelLevelEnum = FuelLevel.getFuel(gameInfo.getFuelLevel());
        gameInfoManager.setDisplayStageScreen(false);
    }

    private void initSound() {
        rocketSound = Utils.getSound(Resource.sound_rocket);
        achievementSound = Utils.getSound(Resource.sound_achiev);
        stageUnlockedSound = Utils.getSound(Resource.sound_stage_unlocked);
    }

    private void createWorldBodies() {
        RampLevel rampLevel = RampLevel.getRamp(gameInfo.getRampLevel());
        rampCreator = new RampCreator(world, gameInfo.getSelectedStage(), rampLevel.getBaseHeight(), rampLevel.getDownwardsRampLevel(), rampLevel.getUpwardsRampLevel());
        rampCreator.createRamps(10);
        player = playerCreator.createPlayer((int) rampCreator.getDownwardsRampHeight(), contactListener);
        createAccesories();
        hud = new HUD(player, playerAttrs, libgdxControlUtils);
    }

    private void createAccesories() {
        sledCreator = new SledCreator(world, player.getBody(), gameInfo.getSledLevel());
        playerOnRampProcessor = new PlayerOnRampProcessor(sledCreator.getAccesoryBody(), sledLevelEnum.getSpeedLevel(), sledLevelEnum.getTakeOffLevel(), player);
        gliderCreator = new GliderCreator(world, player.getBody(), gameInfo.getGliderLevel());
        rocketCreator = new RocketCreator(world, player.getBody(), gameInfo.getRocketLevel());
    }

    public void handleInput() {
        if ((BBInput.isWholeScreenPressed() || Gdx.input.isKeyPressed(Keys.ESCAPE))
                && gameRunState == GameRunState.GAME_FINISHED
                && !isWholeScreenPressed) {
            isWholeScreenPressed = true;
            if (hud.getCreateFinishPopup().areAllInfosShown()) {
                if (gameInfoManager.displayGameFinishedScreen()) {
                    gameStateManager.setGameFinishedState(gameInfo);
                } else {
                    onBackKeyPress();
                }
            } else {
                hud.getCreateFinishPopup().showAllInfos();
            }
        } else if (!BBInput.isWholeScreenPressed() && gameRunState == GameRunState.GAME_FINISHED) {
            isWholeScreenPressed = false;
        } else {
            if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
                onBackKeyPress();
            }
            if (BBInput.isGoUpPressed()) {
                player.pushUp();
            }
            if (BBInput.isGoDownPressed()) {
                player.pushDown();
            }
            if (BBInput.isRocketPressed()
                    && availableFuel > 0
                    && !player.isPlayerOnGround()
                    && player.hasLeftRamp()
                    && rocketCreator.hasAccesory()) {
                player.thrust(rocketLevelEnum.getThrustLevel(), rocketLevelEnum.getTopSpeedLevel());
                rocketCreator.setAnimationToPlay("rocketFire", "rocketHorizontal");
                availableFuel = availableFuel - fuelLevelEnum.getFuelLevel();
                if (!rocketSoundStarted) {
                    rocketSoundStarted = true;
                    libgdxControlUtils.loopSound(rocketSound);
                }
            } else {
                rocketSound.stop();
                if (player.hasLeftRamp() && rocketCreator.hasAccesory()) {
                    rocketCreator.setAnimationToPlay("rocketHorizontal");
                }
            }
            if (availableFuel <= 0) {
                rocketCreator.removeAccesory();
            }
        }
        if (!BBInput.isRocketPressed()) {
            rocketSound.stop();
            rocketSoundStarted = false;
        }
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if (!screenDisposed) {
            handleInput();
            player.setPreviousMovingAngle(player.getMovingAngle());
            player.setPreviousPlayerPoint();
            updateAccesoriesPreviousPlayerPoint();
            if (!screenDisposed) {
                world.step(dt, 8, 3);
            }
            player.update(dt);

            updatePlayerAttrs();
            loadBackgrounds();
            updateGameRunState();
            updateMaxAttrs();
            if (gameRunState == GameRunState.RUN && player.hasLeftRamp()) {
                secondsPassed = secondsPassed + Gdx.graphics.getDeltaTime();
            }
        }
    }

    @Override
    public void render(float delta) {
        // if (!debug) {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // }
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        if (!screenDisposed) {
            if (!debug) {
                spriteProcess();
            }
            cam.position.set(camPositionCalculator.xPosition(player.getPosition().x),
                    camPositionCalculator.yPosition(player.getPosition().y,
                            player.getPosition().x), 0);
            cam.update();
            if (debug) {
                b2dr.render(world, b2dcam.combined);
            }
        }
        /*******************************************/
    }

    private void updateGameRunState() {
        if (player.getDisplayAcceleration() <= 0 && (player.hasLeftRamp()) && player.isPlayerOnGround()) {
            gameRunState = GameRunState.GAME_FINISHED;
        }
    }

    private void updateAccesoriesPreviousPlayerPoint() {
        if (sledCreator.hasAccesory()) {
            sledCreator.setPreviousPlayerPoint();
        }
    }

    private void updateMaxAttrs() {
        if (!finishUpdatesMade && player.hasLeftRamp()) {
            if (player.getDisplayDistance() > playerAttrs.getDistance()) {
                playerAttrs.setDistance(player.getDisplayDistance());
            }
            if (player.getDisplayAltitude() > playerAttrs.getAltitude()) {
                playerAttrs.setAltitude(player.getDisplayAltitude());
            }
            if (secondsPassed > playerAttrs.getDuration()) {
                playerAttrs.setDuration((int) secondsPassed);
            }
            if (player.getDisplayAcceleration() > playerAttrs.getSpeed()) {
                playerAttrs.setSpeed((int) player.getDisplayAcceleration());
            }
        }
    }

    private void updatePlayerAttrs() {
        player.setGravity(gliderLevelEnum.getFloatingLevel());

        playerOnRampProcessor.processPlayerOnRamp();
        player.applyAirResistance(1f, gliderLevelEnum.getFloatingLevel());
        if (player.hasLeftRamp()) {
            if (sledCreator.hasAccesory()) {
                sledCreator.removeAccesory();
            }
            playerOnRampProcessor.processPlayerOnRamp();
        } else if (player.hasReachedRampPointAngleChanges()) {
            if (rocketCreator.hasAccesory()) {
                rocketCreator.setAnimationToPlay("rocketHorizontal");
            }
            if (gliderCreator.hasAccesory()) {
                gliderCreator.setAnimationToPlay("glider");
            }
        }
        if (player.isPlayerGoingBackwards()) {
            rampCreator.createBackwardsWall(scaleUpPPM(player.getPosition().x) - B2DVars.PLAYER_RADIUS * 1.1f, scaleUpPPM(player.getPosition().y + 100));
        }
    }

    private void loadBackgrounds() {
        int altLevel = player.getAltitudeLevelForBackgroundTexture(240, cam.position.y);
        if (altLevel != currentAltitudeLevel) {
            backgrounds = backgroundLoader.loadBackgroundForAltitudeLevel(altLevel, cam);
        }
        currentAltitudeLevel = altLevel;
    }

    private void spriteProcess() {
        spriteBatch.setProjectionMatrix(cam.combined);
        spriteBatch.begin();
        backgroundRender();
        spriteBatch.end();

        if (gameRunState == GameRunState.RUN) {
            polygonSpriteBatch.setProjectionMatrix(cam.combined);
            polygonSpriteBatch.begin();
            rampRender();
            polygonSpriteBatch.end();
        }

        spriteBatch.setProjectionMatrix(cam.combined);
        spriteBatch.begin();
        if (gameRunState == GameRunState.RUN) {
            playerRender();
        } else if (!finishUpdatesMade) {
            updateStoredData();
        }
        hudRender();
        spriteBatch.end();
    }

    private void updateStoredData() {
        if (!finishUpdatesMade) {
            int speed = playerAttrs.getSpeed();
            int altitude = playerAttrs.getAltitude();
            int distance = playerAttrs.getDistance();
            int duration = playerAttrs.getDuration();
            CashCalculator cashCalculator = new CashCalculator();
            playerAttrs.setAltitudeRecord(gameInfoManager.updateAltitudeMax(altitude));
            playerAttrs.setDistanceRecord(gameInfoManager.updateDistanceMax(distance));
            playerAttrs.setDurationRecord(gameInfoManager.updateDurationMax(duration));
            playerAttrs.setSpeedRecord(gameInfoManager.updateSpeedMax(speed));
            gameInfoManager.updateCash(gameInfo, gameInfo.getCash() + cashCalculator.getTotalCashWon(
                    distance, playerAttrs.isDistanceRecord(),
                    altitude, playerAttrs.isAltitudeRecord(),
                    duration, playerAttrs.isDurationRecord(),
                    speed, playerAttrs.isSpeedRecord(),
                    playerAttrs.getGainedAchievementsReward(),
                    gameInfo.getSelectedStage()));
            finishUpdatesMade = true;
        }
    }

    private void hudRender() {
        spriteBatch.setProjectionMatrix(hudCam.combined);
        Achievement achievement = processAchievement();
        Integer stageJustUnlocked = null;
        if (achievement != null) {
            stageJustUnlocked = achievementsManager.stageJustUnlocked(achievement.getAchievementId());
            libgdxControlUtils.playSound(stageJustUnlocked != null ? stageUnlockedSound : achievementSound);
            if (stageJustUnlocked != null) {
                if (stageJustUnlocked == StageScreen.NR_OF_STAGES + 1) {
                    gameInfoManager.setGameFinishedScreen(true);
                } else {
                    gameInfoManager.setDisplayStageScreen(true);
                }
            }
        }
        hud.render(spriteBatch, availableFuel, rocketCreator.hasAccesory(), gameRunState, achievement, stageJustUnlocked, player.getDisplayAltitude(), rocketLevelEnum.getTopSpeedLevel());
        spriteBatch.setProjectionMatrix(cam.combined);
    }

    private Achievement processAchievement() {
        int speed = playerAttrs.getSpeed();
        int altitude = playerAttrs.getAltitude();
        int distance = playerAttrs.getDistance();
        int duration = playerAttrs.getDuration();
        List<Achievement> achievements = achievementsManager.updateAchievementsAndGiveBackNewGainedAchievements(distance, altitude, duration, speed);
        for (Achievement achievement : achievements) {
            playerAttrs.addGainedAchievementsReward(achievement.getReward());
        }
        return !achievements.isEmpty() ? achievements.get(0) : null;
    }

    private void backgroundRender() {
        for (Background background : backgrounds) {
            background.render(spriteBatch);
        }
    }

    private void rampRender() {
        for (PolygonSprite polygonSprite : rampCreator.getPolygonSprites()) {
            PolygonRegion region = polygonSprite.getRegion();
            polygonSpriteBatch.draw(region,
                    0,// x
                    0,// y
                    0,// origin
                    0,// origin
                    region.getRegion().getRegionWidth(),// width
                    region.getRegion().getRegionHeight(),// height
                    RampCreator.RAMP_TEXTURE_SCALE,// scale
                    RampCreator.RAMP_TEXTURE_SCALE,// scale
                    0);
        }
    }

    private void playerRender() {
        float movingAngle = player.getMovingAngle();
        player.render(spriteBatch, movingAngle);
        if (gliderCreator.hasAccesory()) {
            gliderCreator.renderTexture(spriteBatch, movingAngle);
        }
        if (rocketCreator.hasAccesory() && rocketCreator.getAccesoryFixture() != null) {
            rocketCreator.renderTexture(spriteBatch, movingAngle);
        }
        if (player.isOnDownwardsRamp()) {
            if (sledCreator.hasAccesory()) {
                sledCreator.renderTexture(spriteBatch, movingAngle);
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        spriteBatch.dispose();
        polygonSpriteBatch.dispose();
        b2dr.dispose();
        gliderCreator.dispose();
        rocketCreator.dispose();
        sledCreator.dispose();
        inputProcessor.dispose();
        world.dispose();
        screenDisposed = true;
        rocketSound.stop();
    }
}
