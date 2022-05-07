package fr.ostix.questCreator.quest;

import fr.ostix.questCreator.json.*;

import javax.swing.*;
import java.util.*;

public class QuestDialog extends Quest {
    private final List<String> dialogs;

    public QuestDialog() {

        this.dialogs = new ArrayList<>();
    }

    public static QuestDialog load(String questData) {
        return JsonUtils.gsonInstance().fromJson(questData, QuestDialog.class);
    }

    public List<String> getDialogs() {
        return dialogs;
    }

    @Override
    public String getType() {
        return "Dialogue";
    }

    @Override
    public JPanel getPanel() {
        return null;
    }

    @Override
    public String save() {
        return JsonUtils.gsonInstance().toJson(this);
    }
}
