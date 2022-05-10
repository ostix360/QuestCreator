package fr.ostix.questCreator.quest;


import fr.ostix.questCreator.frame.*;
import fr.ostix.questCreator.items.*;
import fr.ostix.questCreator.json.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.util.*;

import static fr.ostix.questCreator.utils.Utils.createIntTextField;

public class QuestItem extends Quest {

    private final ItemStack item;

    public QuestItem() {
        this.item = new ItemStack(null, 0);
    }

    @Override
    public String getType() {
        return "Item";
    }

    @Override
    public JPanel getPanel() {
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        final JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(700, 600));
        panel.setLayout(new GridBagLayout());
        JComboBox<Item> items =  new JComboBox<>(Items.getItems().toArray(new Item[]{}));
        items.setSelectedItem(this.item.getItem());
        items.addActionListener((e) -> {
            this.item.setItem((Item) items.getSelectedItem());
            items.validate();
            items.repaint();
        });
        panel.add(items,gc);

        GridBagConstraints gc2 = new GridBagConstraints();
        gc2.gridx = 0;
        gc2.gridy = 0;
        JPanel tPanel = new JPanel();
        tPanel.setPreferredSize(new Dimension(700, 100));
        tPanel.setLayout(new GridBagLayout());
        final JLabel LItemCount = new JLabel("Item Count : ");
        LItemCount.setFont(MainFrame.SMALL_FONT);
        tPanel.add(LItemCount, gc2);

        final JFormattedTextField itemCount = createIntTextField();
        itemCount.setValue(item.getCount());
        itemCount.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                warn();
            }

            public void removeUpdate(DocumentEvent e) {
                warn();
            }

            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            public void warn() {
                if (items.getSelectedItem() != null) {
                    if (itemCount.getText().matches("\\d+")) {
                        int value = Integer.parseInt(itemCount.getText());
                        if (value == -1) {
                            return;
                        }
                        item.setCount(value);
                    }
                }
            }
        });
        gc2.gridx = 1;
        tPanel.add(itemCount,gc2);
        gc.gridy = 1;
        panel.add(tPanel,gc);
        return panel;
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
