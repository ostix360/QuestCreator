package fr.ostix.questCreator.workspace;

import javax.swing.filechooser.*;
import java.io.*;

public class Config {
    public static File OUTPUT_FOLDER = new File(FileSystemView.getFileSystemView().getDefaultDirectory().getAbsolutePath() + "\\questExporter");
    public static File optionFile = new File(System.getProperty("user.home", ".")+ "\\AppData\\Roaming\\QuestCreator.config");

}
