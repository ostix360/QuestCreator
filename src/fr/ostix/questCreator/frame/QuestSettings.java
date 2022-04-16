package fr.ostix.questCreator.frame;

import fr.ostix.questCreator.*;
import fr.ostix.questCreator.items.*;
import fr.ostix.questCreator.quest.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.awt.*;
import java.text.*;
import java.util.*;

public class QuestSettings extends JPanel {

    private Quest q;

    private JPanel rewards;
    private JPanel mainSettings;

    private final GridBagConstraints gcRewards = getGC(1, 0);
    private final GridBagConstraints gcMainSettings = getGC(0, 0);

    public QuestSettings() {
        super.setLayout(new GridBagLayout());
        super.setBorder(BorderFactory.createTitledBorder("Quest main Settings"));
    }

    private void initMainSettings() {
        mainSettings = new JPanel();
        mainSettings.setLayout(new GridBagLayout());
        this.add(mainSettings, gcMainSettings);

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;

        final JLabel LTitle = new JLabel("Title : ");
        LTitle.setFont(MainFrame.MEDIUM_FONT);
        mainSettings.add(LTitle, gc);

        gc.gridx = 1;
        final JTextField title = new JTextField(q.getTitle(), 10);
        title.setFont(MainFrame.MEDIUM_FONT);
        title.getDocument().addDocumentListener(new DocumentListener() {
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
                String text = title.getText();
                if (text.equals("")) {
                    return;
                }
                q.setTitle(text);
            }
        });
        this.mainSettings.add(title, gc);

        gc.gridx = 0;
        gc.gridy = 1;

        final JLabel LDescription = new JLabel("Description : ");
        LDescription.setFont(MainFrame.MEDIUM_FONT);
        mainSettings.add(LDescription, gc);

        gc.gridx = 1;
        final JTextArea description = new JTextArea(q.getDescription());
        description.setFont(MainFrame.SMALL_FONT);
        description.getDocument().addDocumentListener(new DocumentListener() {
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
                String text = description.getText();
                if (text.equals("")) {
                    return;
                }
                q.setDescription(text);
            }
        });
        this.mainSettings.add(description, gc);

        gc.gridx = 0;
        gc.gridy = 2;

        final JLabel LID = new JLabel("ID : ");
        LID.setFont(MainFrame.MEDIUM_FONT);
        mainSettings.add(LID, gc);

        gc.gridx = 1;
        final JFormattedTextField id = createTextField();
        id.setValue(q.getId());
        id.getDocument().addDocumentListener(new DocumentListener() {
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
                int value = (int) id.getValue();
                if (value == -1) {
                    return;
                }
                q.setId(value);
            }
        });
        this.mainSettings.add(id, gc);

        gc.gridx = 0;
        gc.gridy = 3;

        final JLabel LNPC = new JLabel("NPC id : ");
        LNPC.setFont(MainFrame.MEDIUM_FONT);
        mainSettings.add(LNPC, gc);

        gc.gridx = 1;
        final JFormattedTextField npc = createTextField();
        npc.setValue(q.getNpc());
        npc.getDocument().addDocumentListener(new DocumentListener() {
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
                int value = (int) npc.getValue();
                if (value == -1) {
                    return;
                }
                q.setNpcID(value);
            }
        });
        this.mainSettings.add(npc, gc);

        gc.gridx = 0;
        gc.gridy = 4;

        final JLabel LStatue = new JLabel("Statue : ");
        LStatue.setFont(MainFrame.MEDIUM_FONT);
        mainSettings.add(LStatue, gc);

        gc.gridx = 1;
        final JComboBox<QuestStatus> status = new JComboBox<>(QuestStatus.values());
        status.setSelectedItem(q.getStatus());
        status.addActionListener((e) -> {
            q.setStatus((QuestStatus) status.getSelectedItem());
            status.validate();
            status.repaint();
        });

        this.mainSettings.add(status, gc);
    }

    //TODO rewards Settings
    private void initRewardsSettings() {
        Rewards r = q.getRewards();

        rewards = new JPanel();
        rewards.setLayout(new GridBagLayout());
        rewards.setBorder(BorderFactory.createTitledBorder("Rewards Settings"));
        this.add(rewards, gcRewards);

        GridBagConstraints gc = new GridBagConstraints();

        gc.weightx = 2;
        gc.gridx = 1;
        gc.gridy = 1;
        final JComboBox<ItemStack> items = new JComboBox<>(r.getRewardsItems().toArray(new ItemStack[]{}));
        items.setSelectedItem(q.getStatus());
        items.addActionListener((e) -> {
            q.setStatus((QuestStatus) items.getSelectedItem());
            items.validate();
            items.repaint();
        });
        rewards.add(items,gc);

        gc.weightx = 1;
        gc.gridx = 1;
        gc.gridy = 0;
        final JButton add = new JButton("Add Item");
        add.setFont(MainFrame.MEDIUM_FONT);
        add.addActionListener((e) -> {
            ItemChooser IC = new ItemChooser();
            items.addItem(IC.getSelectedItem());
            r.getRewardsItems().add(IC.getSelectedItem());
            items.setSelectedItem(IC.getSelectedItem());
        });
        rewards.add(add,gc);

        gc.gridx = 2;
        gc.gridy = 0;
        final JButton remove = new JButton("Remove Item");
        add.setFont(MainFrame.SMALL_FONT);
        add.addActionListener((e) -> {
            if (items.getSelectedItem() != null)
            r.getRewardsItems().remove(items.getSelectedItem());
            items.removeItemAt(items.getSelectedIndex());
        });
        rewards.add(remove,gc);

        //TODO add money text field in gx 0 gy 1 and money label in gx 0 gy 0

        gc.gridx = 0;
        gc.gridy = 2;
        final JLabel LItemCount = new JLabel("Item Count : ");
        LItemCount.setFont(MainFrame.MEDIUM_FONT);
        mainSettings.add(LItemCount, gc);

        gc.weightx = 2;
        gc.gridx = 1;
        gc.gridy = 2;
        final JFormattedTextField itemCount = createTextField();
        if (!r.getRewardsItems().isEmpty()) {
            itemCount.setValue(((ItemStack) Objects.requireNonNull(items.getSelectedItem())).getCount());
        }
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
                int value = (int) itemCount.getValue();
                if (value <= 0) {
                    items.removeItemAt(items.getSelectedIndex());
                }
                q.setNpcID(value);
            }
        });
        rewards.add(itemCount,gc);
    }


    private void refresh(Quest q) {
        this.q = q;
        initMainSettings();
        initRewardsSettings();
    }


    private JFormattedTextField createTextField() {
        NumberFormat floatFormat = NumberFormat.getNumberInstance();
        floatFormat.setMinimumFractionDigits(1);
        floatFormat.setMaximumFractionDigits(5);
        NumberFormatter numberFormatter = new NumberFormatter(floatFormat);
        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setAllowsInvalid(false);
        JFormattedTextField text = new JFormattedTextField(numberFormatter);
        text.setColumns(4);
        text.setFont(MainFrame.MEDIUM_FONT);
        text.setHorizontalAlignment(0);
        return text;
    }

    private GridBagConstraints getGC(int gridx, int gridy) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = gridx;
        gc.gridy = gridy;
        return gc;
    }
}
