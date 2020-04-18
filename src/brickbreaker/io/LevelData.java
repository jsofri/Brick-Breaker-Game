package brickbreaker.io;


import brickbreaker.levels.LevelInformation;
import java.util.List;


/**
 * this class encapsulate 3 data types that present a level set in one object.
 * this is relevant for the file reading phase of program.
 * each object holds a key, a value and a list of level information.
 */
public class LevelData {


    /**
     * data of object.
     */
    private String key;
    private String value;
    private List<LevelInformation> levelInformationList;


    /**
     * constructor.
     *
     * @param key String - key of object
     * @param value String - value of object
     * @param list List of LevelInformation objects
     */
    public LevelData(String key, String value, List<LevelInformation> list) {
        this.key = key;
        this.value = value;
        this.levelInformationList = list;
    }

    /**
     * getter.
     *
     * @return String - key
     */
    public String getKey() {
        return this.key;
    }

    /**
     * getter.
     *
     * @return String - value
     */
    public String getValue() {
        return this.value;
    }

    /**
     * getter.
     *
     * @return Task of object
     */
    public List<LevelInformation> getLevelInformationList() {
        return this.levelInformationList;
    }
}
