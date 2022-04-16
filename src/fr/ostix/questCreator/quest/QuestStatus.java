package fr.ostix.questCreator.quest;

public enum QuestStatus {
    AVAILABLE("AVAILABLE"),
    DONE("DONE"),
    QUESTING("QUESTING"),
    UNAVAILABLE("UNAVAILABLE");

    private final String id;

    QuestStatus(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }
}
