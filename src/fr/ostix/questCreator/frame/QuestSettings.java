package fr.ostix.questCreator.frame;

import be.pcl.swing.*;
import fr.ostix.questCreator.items.*;
import fr.ostix.questCreator.quest.*;
import fr.ostix.questCreator.utils.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.text.*;
import java.util.*;

import static fr.ostix.questCreator.utils.Utils.createIntTextField;

public class QuestSettings extends JPanel {

    private Quest q;

    private JPanel rewards;
    private JPanel mainSettings;

    private final GridBagConstraints gcRewards = getGC(1);
    private final GridBagConstraints gcMainSettings = getGC(0);

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
        LTitle.setFont(MainFrame.SMALL_FONT);
        mainSettings.add(LTitle, gc);

        gc.gridx = 1;
        final JTextField title = new JTextField(q.getTitle(), 20);
        title.setFont(MainFrame.SMALL_FONT);
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
        LDescription.setFont(MainFrame.SMALL_FONT);
        mainSettings.add(LDescription, gc);

        gc.gridx = 1;
        final JTextArea description = new JTextArea(q.getDescription(), 7, 22);
        description.setFont(MainFrame.SMALL_FONT);
        JScrollPane descPane = new JScrollPane(description,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
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
        this.mainSettings.add(descPane, gc);

        gc.gridx = 0;
        gc.gridy = 2;

        final JLabel LID = new JLabel("ID : ");
        LID.setFont(MainFrame.SMALL_FONT);
        mainSettings.add(LID, gc);

        gc.gridx = 1;
        final JFormattedTextField id = createIntTextField();
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
                if (id.getText().matches("\\d+")) {
                    int value = Integer.parseInt(id.getText());
                    if (value == -1) {
                        return;
                    }
                    q.setId(value);
                }
            }
        });
        this.mainSettings.add(id, gc);

        gc.gridx = 0;
        gc.gridy = 3;

        final JLabel LNPC = new JLabel("NPC id : ");
        LNPC.setFont(MainFrame.SMALL_FONT);
        mainSettings.add(LNPC, gc);

        gc.gridx = 1;
        final ImprovedFormattedTextField npc = createIntTextField();
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
                if (npc.getText().matches("\\d+")) {
                    int value = Integer.parseInt(npc.getText());
                    if (value == -1) {
                        return;
                    }
                    q.setNpcID(value);
                    System.out.println(value);
                }
            }
        });

        this.mainSettings.add(npc, gc);

        gc.gridx = 0;
        gc.gridy = 4;

        final JLabel LStatue = new JLabel("Statue : ");
        LStatue.setFont(MainFrame.SMALL_FONT);
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
        items.setSelectedItem(q.getRewards().getRewardsItems());
        items.addActionListener((e) -> {
            items.validate();
            items.repaint();
        });
        rewards.add(items, gc);

        gc.weightx = 1;
        gc.gridx = 2;
        gc.gridy = 1;
        final JButton add = new JButton("Add Item");
        add.setFont(MainFrame.SMALL_FONT);
        add.addActionListener((e) -> {
            ItemChooser IC = new ItemChooser();
            items.addItem(new ItemStack(IC.getSelectedItem(), 1));
            r.getRewardsItems().add(new ItemStack(IC.getSelectedItem(), 1));
            items.setSelectedItem(IC.getSelectedItem());
        });
        rewards.add(add, gc);

        gc.gridx = 2;
        gc.gridy = 2;
        final JButton remove = new JButton("Remove Item");
        remove.setFont(MainFrame.SMALL_FONT);
        remove.addActionListener((e) -> {
            if (items.getSelectedItem() != null) {
                r.getRewardsItems().remove(items.getSelectedItem());
                items.removeItemAt(items.getSelectedIndex());
            }
        });
        rewards.add(remove, gc);

        gc.gridx = 0;
        gc.gridy = 0;
        final JLabel LMoneyAmount = new JLabel("Money amount : ");
        LMoneyAmount.setFont(MainFrame.SMALL_FONT);
        rewards.add(LMoneyAmount, gc);


        gc.gridx = 1;

        final JFormattedTextField moneyAmount = createIntTextField();
        moneyAmount.setValue(r.getMoneyAmount());
        moneyAmount.getDocument().addDocumentListener(new DocumentListener() {
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
                    if (moneyAmount.getText().matches("\\d+")) {
                        int value = Integer.parseInt(moneyAmount.getText());
                        if (value == -1) {
                            return;
                        }
                        r.setMoneyAmount(value);
                    }
                }
        });
        rewards.add(moneyAmount, gc);



        gc.gridx = 0;
        gc.gridy = 2;
        final JLabel LItemCount = new JLabel("Item Count : ");
        LItemCount.setFont(MainFrame.SMALL_FONT);
        rewards.add(LItemCount, gc);

        gc.weightx = 2;
        gc.gridx = 1;
        gc.gridy = 2;
        final JFormattedTextField itemCount = createIntTextField();
        if (r.getRewardsItems().isEmpty()) {
            itemCount.setValue(0);
        }else{
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
                if (items.getSelectedItem() != null) {
                    if (itemCount.getText().matches("\\d+")) {
                        int value = Integer.parseInt(itemCount.getText());
                        if (value == -1) {
                            return;
                        }
                        ((ItemStack) Objects.requireNonNull(items.getSelectedItem())).setCount(value);
                    }
                }
            }
        });
        rewards.add(itemCount, gc);
    }


    public void refresh(Quest q) {
        this.q = q;
        if (mainSettings != null){
            this.remove(mainSettings);
        }
        if (rewards != null){
            this.remove(rewards);
        }
        initMainSettings();
        initRewardsSettings();
        super.validate();
        super.repaint();
    }




    private GridBagConstraints getGC(int gridy) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = gridy;
        return gc;
    }
}
