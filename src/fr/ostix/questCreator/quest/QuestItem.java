package fr.ostix.questCreator.quest;


import fr.ostix.questCreator.items.*;
import fr.ostix.questCreator.json.*;

public class QuestItem extends Quest {

    private final ItemStack item;

    public QuestItem() {
        this.item = new ItemStack(null, 0);
    }

    public static QuestItem load(String questData) {
        return JsonUtils.gsonInstance().fromJson(questData, QuestItem.class);
    }

    public ItemStack getItem() {
        return item;
    }

    @Override
    public String save() {
        return JsonUtils.gsonInstance().toJson(this);
    }
}
