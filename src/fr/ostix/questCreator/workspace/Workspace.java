package fr.ostix.questCreator.workspace;

import fr.ostix.questCreator.quest.*;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class Workspace {
    private QuestCategory category;
    private File questFile;

    public void newQuestCategory(){
        category = new QuestCategory();
    }

    public void openQuestCategory(){
        String quest = "";
        //TODO
        QuestCategory.load(quest);
    }

    public void saveQuestCategory() throws IOException {
        if (category != null) {
            String quest = category.save();
            openFile();
            try (FileOutputStream fos = new FileOutputStream(questFile);
                 FileChannel fc = fos.getChannel()) {
                byte[] data = quest.getBytes();
                ByteBuffer bytes = ByteBuffer.allocate(data.length);
                bytes.put(data);
                bytes.flip();
                fc.write(bytes);
            }
        }
    }

    private void openFile() throws IOException {
        questFile = new File(Config.OUTPUT_FOLDER + "\\" , category.getName()+"*"+category.getId()+".quest");
        if (!questFile.exists()){
            questFile.getParentFile().mkdirs();
            questFile.createNewFile();
        }
    }

    public QuestCategory getCategory() {
        return category;
    }
}
