package fr.ostix.questCreator.quest;

import fr.ostix.questCreator.json.*;

import java.util.*;

public class QuestCategory {

    public HashMap<Integer, Quest> quests;
    public int id;
    public String title;
    private QuestStatus status;

    public QuestCategory() {
        this.id = -1;
        this.title = "";
        this.quests = new HashMap<>();
        status = QuestStatus.UNAVAILABLE;
    }

    public QuestCategory(HashMap<Integer, Quest> quests, int id, String title) {
        this.quests = quests;
        this.id = id;
        this.title = title;
    }

    public static QuestCategory load(String questFile) {
        final HashMap<Integer, Quest> quests = new HashMap<>();
        String content = JsonUtils.loadJson(questFile);
        String[] lines = content.split("\n");
        String[] values = lines[0].split(";");
        int id = Integer.parseInt(values[0]);
        String title = values[1];
        int nbQuest = Integer.parseInt(values[2]) + 1;
        for (int i = 1; i < nbQuest; i++) {
            Quest q;
            switch (lines[i]) {
                case "QuestItem":
                    q = QuestItem.load(lines[i++]);
                    break;
                case "QuestLocation":
                    q = QuestLocation.load(lines[i++]);
                    break;
                case "QuestDialog":
                    q = QuestDialog.load(lines[i++]);
                    break;
                default:
                    new Exception("Quest type not found");
                    i++;
                    continue;
            }
            quests.put(q.getId(), q);

        }
        return new QuestCategory(quests,id,title);
    }

    public String save() {
        final StringBuilder content = new StringBuilder();
        content.append(this.id).append(";").append(this.title).append(";").append(this.quests.values().size()).append("\n");
        for (Quest q : this.quests.values()) {
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
        return new ArrayList<>(this.quests.values());
    }

    public String getName() {
        return this.title;
    }

}
