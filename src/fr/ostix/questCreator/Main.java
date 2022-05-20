package fr.ostix.questCreator;

import fr.ostix.questCreator.frame.*;
import fr.ostix.questCreator.workspace.*;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        readConfig();
    }

    private static void readConfig(){
        try (FileReader fr = new FileReader(Config.optionFile);
             BufferedReader reader = new BufferedReader(fr)){
            String config = reader.readLine();
            Config.OUTPUT_FOLDER = new File(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
