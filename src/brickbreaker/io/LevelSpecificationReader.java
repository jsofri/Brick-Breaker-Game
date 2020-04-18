package brickbreaker.io;


import brickbreaker.levels.LevelSetter;
import brickbreaker.levels.LevelInformation;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;


/**
 * Class read text file of data about levels and make list of levelInformation.
 */
public class LevelSpecificationReader {


    /**
     * following fields gather data about the level.
     * char array is for locations of blocks.
     */
    private List<LevelSetter> levelSetterList;


    /**
     * constructor.
     */
    public LevelSpecificationReader() {
        this.levelSetterList = new ArrayList<>();
    }

    /**
     * method runs a loop that call helper method to create the list.
     *
     * @param reader BufferedReader pointed to start of a text file
     * @return List of LevelInformation, as was specified in text file
     */
    public List<LevelInformation> fromReader(Reader reader) {
        BufferedReader br = null;

        try {
            br = new BufferedReader(reader);
            while ((br.readLine()) != null) {
                this.createOneLevel(br);
            }

            br.close();
        } catch (IOException exception) {
            System.out.println(exception.toString());
        }

        return this.createAllBlocks();
    }

    /**
     * method use helper methods to read the different sections in a file.
     *
     * @param reader BufferedReader, object to read data from text file
     */
    private void createOneLevel(BufferedReader reader) {
        try {

            LevelSetter newLevelSetter = new LevelSetter();

            //to ignore irrelevant lines, continue is for checkstyle
            while (!(reader.readLine().contains("START_LEVEL"))) {
                continue;
            }

            this.readProperties(reader, newLevelSetter);
            this.readBlocks(reader, newLevelSetter);
            this.addALevelSetterToList(newLevelSetter);
        } catch (IOException exception) {
            System.out.println(exception.toString());
        } catch (NullPointerException nullEx) {
            return;
        }
    }

    /**
     * read strings from file.
     * and set data about the levelSetter in fields of class.
     *
     * @param reader BufferedReader, positioned at beginning of data
     * @param levelSetter data about levelSetter will go in this object
     */
    private void readProperties(BufferedReader reader,
                                LevelSetter levelSetter) {
        String line;

        try {
            line = reader.readLine();

            while (!(line).contains("START_BLOCKS")) {
                this.makeFieldFromLine(line, levelSetter);
                line = reader.readLine();
            }
            levelSetter.setNumberOfBalls(
                    levelSetter.initialBallVelocities().size());
        } catch (IOException exception) {
            System.out.println(exception.toString());
        }
    }

    /**
     * method get a line and make a field out of it.
     *
     * @param subLine String with data in it in shape of "key:value"
     * @param levelSetter a LevelSetter object
     */
    private void makeFieldFromLine(String subLine, LevelSetter levelSetter) {
        String[] array = subLine.split(":");
        String   key = array[0];
        String   value = array[1];

        this.enterToMap(key, value, levelSetter);
    }

    /**
     * method get a key and a value strings and add data to the level setter.
     * @param key String - name of key
     * @param value String, contain value referenced to the key
     * @param levelSetter LevelSetter Object
     */
    private void enterToMap(String key, String value, LevelSetter levelSetter) {

        if (key.contains("level_name")) {
            levelSetter.setLevelName(value);
        } else if (key.contains("paddle_speed")) {
            levelSetter.setPaddleSpeed(Integer.parseInt(value));
        } else if (key.contains("paddle_width")) {
            levelSetter.setPaddleWidth(Integer.parseInt(value));
        } else if (key.contains("block_definitions")) {
            levelSetter.setBlockDefinitions(value);
        } else if (key.contains("num_blocks")) {
            levelSetter.setNumOfBlocks(Integer.parseInt(value));
        } else if (key.contains("row_height")) {
            levelSetter.setRowHeight(Integer.parseInt(value));
        } else if (key.contains("block_start_x")) {
            levelSetter.setBlockStartX(Integer.parseInt(value));
        } else if (key.contains("blocks_start_y")) {
            levelSetter.setBlockStartY(Integer.parseInt(value));
        } else if (key.contains("background")) {
            levelSetter.setBackground(BlockFromLine.getFill(value));
        } else if (key.contains("ball_velocities")) {
            int      angle;
            int      speed;
            String[] tmpArray;
            String[] tuples = value.split(" ");

            for (String tuple : tuples) {
                tmpArray = tuple.split(",");
                angle = Integer.parseInt(tmpArray[0]);
                speed = Integer.parseInt(tmpArray[1]);

                levelSetter.addVelocity(speed, angle);
            }
        }
    }

    /**
     * method get a buffered reader and a level setter.
     * using both, it can set the list of strings that describe the blocks.
     *
     * @param reader BufferedReader pointing to a file of block
     * @param levelSetter LevelSetter object that gather data about level
     */
    private void readBlocks(BufferedReader reader, LevelSetter levelSetter) {
        String line;

        try {
            line = reader.readLine();

            while (!line.contains("END_BLOCKS")) {
                levelSetter.addStringOfRowOfBlocks(line);
                line = reader.readLine();
            }
        } catch (IOException exception) {
            System.out.println(exception.toString());
        }
    }

    /**
     * method get a level setter object and enters it to the list if it's set.
     *
     * @param levelSetter  LevelSetter object to add to the instance's list
     */
    private void addALevelSetterToList(LevelSetter levelSetter) {
        if (levelSetter.allIsSet()) {
            this.levelSetterList.add(levelSetter);
        } else {
            throw new
                  RuntimeException("Reading Error of levelSetter description");
        }
    }

    /**
     * method run through all level setters in the list.
     * for each it sets all the blocks, and create a level object.
     * when loop ends it returns a list of level (implements LevelInformation).
     *
     * @return List of LevelInformation objects
     */
    private List<LevelInformation> createAllBlocks() {
        List<LevelInformation> levelInformationList = new ArrayList<>();

        if (this.levelSetterList.size() == 0) {
            throw new RuntimeException("0 levels were read from file");
        }

        for (LevelSetter levelSetter : this.levelSetterList) {
            levelSetter.createBlocks();
            levelInformationList.add(levelSetter.getLevel());
        }

        return levelInformationList;
    }
}
