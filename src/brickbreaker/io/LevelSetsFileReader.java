package brickbreaker.io;


import brickbreaker.levels.LevelInformation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * This class read files with data using level-sets file and it's references.
 * methods of class use helper classes. it's only field is a list.
 * list contain objects that contains a key, a String value and a levels list.
 * final is for checkStyle reasons.
 */
public final class LevelSetsFileReader {


    /**
     * all data that read from methods is stored in this list.
     */
    private List<LevelData> levelsSets;


    /**
     * constructor.
     */
    private LevelSetsFileReader() {
        this.levelsSets = new ArrayList<>();
    }

    /**
     * this method is called to get a list of key, value and list of levels.
     * it knows to read a level-set file and hold all relevant data in a list.
     *
     * @param fileName String - path to file
     * @return List of KeyValueLevelList object
     */
    public static List<LevelData> readLevelSetsFile(String fileName) {
        LevelSetsFileReader lsfr = new LevelSetsFileReader();

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            String[] strings;
            List<LevelInformation> levelInformationList;

            //2 lines are read in iteration: 1st is key and value, 2nd is path
            while ((line = br.readLine()) != null) {
                strings = line.split(":");
                levelInformationList = lsfr.getList(br.readLine());

                lsfr.levelsSets.add(new LevelData(strings[0],
                                   strings[1], levelInformationList));
            }

            br.close();
        } catch (IOException exception) {
            System.out.println(exception.toString());
        }

        return lsfr.levelsSets;
    }

    /**
     * method get a path to level definitions file and return a list of levels.
     *
     * @param fileName String - path to file of level definitions.
     * @return List of LevelInformation
     */
    private List<LevelInformation> getList(String fileName) {
        List<LevelInformation> list = null;

        try {
            LevelSpecificationReader lsr = new LevelSpecificationReader();
            Reader br
                 = new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(fileName));
             list = lsr.fromReader(br);

        } catch (Exception exception) {
            System.out.println(exception.toString());
        }

        return list;
    }
}
