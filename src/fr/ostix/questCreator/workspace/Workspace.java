package fr.ostix.questCreator.workspace;

import fr.ostix.questCreator.quest.*;

public class Workspace {
    private QuestCategory category;

    public void newQuestCategory(){
        category = new QuestCategory();
    }

    public void openQuestCategory(String content){
        category = QuestCategory.load(content);
    }

    public String saveQuestCategory(){
        return category.save();
    }

    public QuestCategory getCategory() {
        return category;
    }
}
