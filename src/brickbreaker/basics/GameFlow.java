package brickbreaker.basics;


import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import brickbreaker.animation.AnimationRunner;
import brickbreaker.animation.GameLevel;
import brickbreaker.animation.HighScoresAnimation;
import brickbreaker.animation.MenuAnimation;
import brickbreaker.animation.WinScreen;
import brickbreaker.animation.GameOverScreen;
import brickbreaker.highscores.HighScoresTable;
import brickbreaker.highscores.ScoreInfo;
import brickbreaker.io.LevelData;
import brickbreaker.levels.LevelInformation;
import brickbreaker.listeners.ScoreTrackingListener;
import brickbreaker.sprites.LiveIndicator;
import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * this class holds data about the game flow.
 */
public class GameFlow {


    /**
     * number of lives to cegin with.
     */
    static final int    LIVES_IN_GAME = 2;
    static final String NEW_GAME = "Start a New Game";
    static final String HIGH_SCORE = "Check High-Score Table";
    static final String QUIT = "Quit Game";


    /**
     * fields of object.
     */
    private KeyboardSensor        keyboardSensor;
    private AnimationRunner       animationRunner;
    private ScoreTrackingListener scoreTrackingListener;
    private LiveIndicator         liveIndicator;
    private GUI                   gui;
    private HighScoresTable       highScoresTable;
    private File                  fileName;



    /**
     * constructor.
     *
     * @param ar AnimationRunner object to run animation on
     * @param ks KeyBoardSensor object - to control the game
     * @param gui gui window of the game
     * @param hst HighScoresTable of the game
     * @param fileName File object that store the highscore table
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, GUI gui,
                    HighScoresTable hst, File fileName) {
        this.animationRunner = ar;
        this.keyboardSensor = ks;
        this.gui = gui;
        this.highScoresTable = hst;
        this.fileName = fileName;
        this.resetCounters();
    }

    /**
     * method set all counters of object.
     */
    private void resetCounters() {
        this.scoreTrackingListener = new ScoreTrackingListener(new Counter());
        this.liveIndicator = new LiveIndicator(new Counter(LIVES_IN_GAME));
    }

    /**
     * this method run the whole cycle of game - menu, levels and high-scores.
     *
     * @param levelSets List of levelData objects
     */
    public void gameCycleLoop(List<LevelData> levelSets) {
        Menu<Task> menu = this.setUpMenu(levelSets);

        while (true) {
            doOneTask(menu);
        }
    }

    /**
     * helper method that run a menu and make a task according to menu.
     *
     * @param menu Menu of tasks objects.
     */
    public void doOneTask(Menu<Task> menu) {
        this.animationRunner.run(menu);
        menu.getStatus().run();
    }

    /**
     * method create a menu of tasks using anonynous classes.
     *
     * @param levelSets list of LevelData
     * @return Menu<Task> object
     */
    private Menu<Task> setUpMenu(List<LevelData> levelSets) {
        Menu<Task> menu = new MenuAnimation(this.keyboardSensor);
        Menu<Task> subMenu = this.setSubMenu(levelSets);
        Task task1;
        Task task2;
        Task task3;

        task1  = new Task() {

            @Override
            public Void run() {
                doOneTask(subMenu);
                return null;
            }
        };

        task2 = new Task() {

            @Override
            public Void run() {
                return highScoreAnimation();
            }
        };

        task3 = new Task() {

            @Override
            public Void run() {
                System.exit(0);
                return null;
            }
        };

        //parameters are key to wait for, line to print and a task to return
        menu.addSelection("s", NEW_GAME, task1);
        menu.addSelection("h", HIGH_SCORE, task2);
        menu.addSelection("q", QUIT, task3);
        menu.mainMenu(true);
        return menu;
    }

    /**
     * a setter to sub menu.
     *
     * @param levelSets a List of LevelData, store data about levels.
     * @return a Menu of Task object
     */
    private Menu<Task> setSubMenu(List<LevelData> levelSets) {
        Menu<Task> subMenu = new MenuAnimation(this.keyboardSensor);
        subMenu.mainMenu(false);

        Task[] tasks = new Task[levelSets.size()];

        for (int i = 0; i < tasks.length; i++) {
            LevelData levelSet = levelSets.get(i);
            List<LevelInformation> levelList
                                        = levelSet.getLevelInformationList();

            tasks[i] = this.getTask(levelList);

            subMenu.addSelection(levelSet.getKey(), levelSet.getValue(),
                                 tasks[i]);
        }

        return subMenu;
    }

    /**
     * a helper - get a list of LevelInformation and return a task that run it.
     *
     * @param levelInformationList List of LevelInformation
     * @return Task that run loopOfLevels with input.
     */
    private Task getTask(List<LevelInformation> levelInformationList) {

        return new Task() {
            @Override
            public Void run() {
                loopOfLevels(levelInformationList);
                resetCounters();
                return null;
            }
        };
    }

    /**
     * method is a helper to gameCycleLoop - get a list of levels and run it.
     * @param levels List of LevelInformation - levels to run
     */
    private void loopOfLevels(List<LevelInformation> levels) {
        int currentLevel = 0;

        for (LevelInformation levelInfo : levels) {

            GameLevel level = new GameLevel(levelInfo, this.keyboardSensor,
                    this.scoreTrackingListener,
                    this.liveIndicator, this.gui,
                    this.animationRunner);

            level.initialize();

            while ((level.getBlockCounter().getValue() > 0)
                    && (liveIndicator.getLives().getValue() > 0)) {
                level.playOneTurn();
            }
            currentLevel++;

            if (liveIndicator.getLives().getValue() <= 0) {
                this.endGame(false);
                break;
            }

            //player won and passed all levels
            if (currentLevel == levels.size()) {
                this.endGame(true);
            }
        }

        this.highScoreAnimation();
    }

    /**
     * method run final screen - depends if the winner won or not.
     *
     * @param victory boolean - true if player win
     */
    private void endGame(boolean victory) {
        Counter score = this.scoreTrackingListener.getCurrentScore();
        this.endGameScreen(victory, score);
        this.updateHighScoreTable(score.getValue());
    }

    /**
     * method update high score table according to score of play.
     *
     * @param highScore int - amount of scores in this play
     */
    private void updateHighScoreTable(int highScore) {
        int rank = this.highScoresTable.getRank(highScore);

        if (rank <= this.highScoresTable.size()) {
            String name = this.getNameFromUser();
            ScoreInfo scoreInfo = new ScoreInfo(name, highScore);

            this.highScoresTable.add(scoreInfo);
        }

        try {
            this.highScoresTable.save(this.fileName);
        } catch (IOException exception) {
            System.out.println(exception.toString());
        }
    }
    /**
     * display an appropriate screen to user, depending on if he won or lost.
     *
     * @param victory boolean that tells if the player won or not
     * @param score Counter of the current score of user
     */
    private void endGameScreen(boolean victory, Counter score) {
        if (victory) {
            this.animationRunner.run(new WinScreen(this.keyboardSensor,
                                                   score));
        } else {
            this.animationRunner.run(new GameOverScreen(this.keyboardSensor,
                                                        score));
        }
    }

    /**
     * method runs an animation of high scores.
     *
     * @return Void - null
     */
    private Void highScoreAnimation() {
        HighScoresAnimation highScoresAnimation =
                new HighScoresAnimation(keyboardSensor,
                        highScoresTable);

        animationRunner.run(highScoresAnimation);
        return null;
    }

    /**
     * method make a window that asks the user for his name.
     *
     * @return String - name of the user, the input of user
     */
    private String getNameFromUser() {
        DialogManager dialog = this.gui.getDialogManager();
        String name = dialog.showQuestionDialog("Name",
                                            "What is your name?", "");

        return name;
    }
}
