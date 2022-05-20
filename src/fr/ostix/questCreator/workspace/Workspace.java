package fr.ostix.questCreator.workspace;

import fr.ostix.questCreator.frame.*;
import fr.ostix.questCreator.quest.*;
import fr.ostix.questCreator.utils.*;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class Workspace {
    private QuestCategory category;
    private File questFile;
    private final MainFrame frame;

    public Workspace(MainFrame frame) {
        this.frame = frame;
    }

    public void newQuestCategory(){
        category = QuestCategory.createQuestCategory();
    }

    public void openQuestCategory(){
        File file = new ChooseExistingQuest().getChosen();
        StringBuilder sb = new StringBuilder();
        BufferedReader reader;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.category = QuestCategory.load(sb.toString());
        frame.notifyAddingNewQuest();
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
        questFile = new File(Config.OUTPUT_FOLDER + "\\" , category.getName()+"."+category.getId()+".quest");
        if (!questFile.exists()){
            questFile.getParentFile().mkdirs();
            questFile.createNewFile();
        }
    }

    public QuestCategory getCategory() {
        return category;
    }
}
