package fr.ostix.questCreator.quest;

import fr.ostix.questCreator.frame.*;

import java.util.*;

public class QuestCategory {

    public List<Quest> quests = new ArrayList<>();
    private int id;
    private String title;
    private QuestStatus status;
    private int nextQuest;

    public QuestCategory(int id, String title, QuestStatus status, int nextQuest) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.nextQuest = nextQuest;
    }

    public static QuestCategory createQuestCategory(){
        return new QuestCategoryFrame().getQuestCategory();
    }

    public QuestCategory(List<Quest> quests, int id, String title,QuestStatus status, int nextQuest) {
        this.quests = quests;
        this.id = id;
        this.title = title;
        this.status = status;
        this.nextQuest = nextQuest;
    }

    public static QuestCategory load(String content) {
        final List<Quest> quests = new ArrayList<>();
        String[] lines = content.split("\n");
        String[] values = lines[0].split(";");
        int id = Integer.parseInt(values[0]);
        String title = values[1];
        int nextQuest = Integer.parseInt(values[4]);
        QuestStatus status = QuestStatus.valueOf(values[3]);
        for (int i = 1; i < lines.length; i++) {
            Quest q;
            switch (lines[i]) {
                case "QuestItem":
                    q = QuestItem.load(lines[++i]);
                    break;
                case "QuestLocation":
                    q = QuestLocation.load(lines[++i]);
                    break;
                case "QuestDialog":
                    q = QuestDialog.load(lines[++i]);
                    break;
                default:
                    new Exception("Quest type not found");
                    i++;
                    continue;
            }
            quests.add(q);

        }
        return new QuestCategory(quests,id,title,status,nextQuest);
    }

    //1;Welcome;2;AVAILABLE
    //QuestDialog
    //{"dialogs":["Bonjour et bienvenue dans ce monde\nIci c\u0027est l\u0027apocalipse autrement tout va bien\nBonne journée ciao","hep hep hep pas si vite\nrevient ici vous allez venir sauver le monde\nBon alors déjà venez par là"],"id": 1,"npcID": 0,"title": "hello","description": "Que fait tu ici.\nA ce que je vois tu connais déjà le jeu.\nBon alors bon courage.","rewards": {"items": [],"money": 0},"status": "AVAILABLE" }
    //QuestLocation
    //{"pos":{"x":40.0,"y":10.0,"z":30.0},"range":5.0,"id":2,"npcID":0,"title":"teste de déplacemement","description":"Rendez vous par ici","rewards":{"items":[],"money":0},"status":"UNAVAILABLE"}

    public String save() {
        final StringBuilder content = new StringBuilder();
        content.append(this.id).append(";").append(this.title).append(";").append(this.quests.size()).append(';').append(status.toString()).append(";").append(nextQuest).append("\n");
        for (Quest q : this.quests) {
            if (q instanceof QuestItem) {
                content.append("QuestItem").append("\n");
            } else if (q instanceof QuestLocation) {
                content.append("QuestLocation").append("\n");
            } else {
                content.append("QuestDialog").append("\n");
            }
            content.append(q.save()).append("\n");
        }
        return content.toString();
    }

    public List<Quest> quests() {
        return this.quests;
    }

    public String getName() {
        return this.title;
    }

    public int getId() {
        return this.id;
    }
}
