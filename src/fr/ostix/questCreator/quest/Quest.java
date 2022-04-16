package fr.ostix.questCreator.quest;

import fr.ostix.questCreator.quest.serialization.*;

public abstract class Quest implements IQuestSerializer {
    private int id;
    private int npcID;
    private String title;
    private String description;
    private Rewards rewards;
    private QuestStatus status;

    public Quest() {
        this.id = 0;
        this.npcID = -1;
        this.title = "Insert a Title";
        this.description = "Insert a description";
        this.rewards = new Rewards();
        this.status = QuestStatus.UNAVAILABLE;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNpcID(int npcID) {
        this.npcID = npcID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRewards(Rewards rewards) {
        this.rewards = rewards;
    }

    public void setStatus(QuestStatus status) {
        this.status = status;
    }

    public void done() {
        this.status = QuestStatus.DONE;
    }

    public int getNpc() {
        return npcID;
    }

    public String getDescription() {
        return description;
    }

    public Rewards getRewards() {
        return rewards;
    }

    public QuestStatus getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

}
