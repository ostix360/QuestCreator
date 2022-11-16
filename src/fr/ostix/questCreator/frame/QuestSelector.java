package fr.ostix.questCreator.frame;

import fr.ostix.questCreator.quest.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class QuestSelector extends JPanel {

    private final JPanel mainPanel = new JPanel();

    private final JScrollPane scrollPane;
    private QuestCategory questCategory;

    private final List<QuestPanel> quests = new ArrayList<>();

    private final MainFrame frame;

    public QuestSelector(MainFrame frame) {
        this.frame = frame;
        super.setBorder(BorderFactory.createTitledBorder("Quest Selector"));
        mainPanel.setLayout(new GridBagLayout());
        scrollPane = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(480, 210));
        this.add(scrollPane);
    }

    public void setNew(QuestCategory questCategory) {
        this.questCategory = questCategory;

        this.mainPanel.removeAll();

        this.quests.clear();


        initPanel();
        mainPanel.validate();
        mainPanel.repaint();
        super.validate();
        super.repaint();
    }

    public void refresh() throws ClassCastException{
        for (int i = 0; i < this.questCategory.quests.size(); i++) {
            Quest quest = this.questCategory.quests.get(i);
            JPanel qPanel = (JPanel) mainPanel.getComponent(i);
            JLabel label = (JLabel) qPanel.getComponent(0);
            label.setText(quest.getTitle());

            label.validate();
            label.repaint();
            qPanel.validate();
            qPanel.repaint();
        }
        mainPanel.validate();
        mainPanel.repaint();
        super.validate();
        super.repaint();
    }

    private void initPanel() {
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        for (int i = 0; i < this.questCategory.quests.size(); i++) {
            Quest quest = this.questCategory.quests.get(i);
            gc.gridy = i;
            addQuestInList(quest, gc);
        }
        quests.forEach((qp) -> mainPanel.add(qp.getPanel(),qp.getConstraints()));
    }

    private void addQuestInList(Quest q, GridBagConstraints gc) {
        GridBagConstraints gc2 = new GridBagConstraints();
        gc2.gridx = 0;
        gc2.gridy = 0;
        final JPanel qPanel = new JPanel();
        qPanel.setBorder(BorderFactory.createTitledBorder(q.getType()));
        qPanel.setLayout(new GridBagLayout());
        qPanel.setPreferredSize(new Dimension(460, 50));

        final JLabel qName = new JLabel(q.getTitle());
        qName.setFont(MainFrame.SMALL_FONT);
        qPanel.add(qName, gc2);


        gc2.gridx = 1;
        final JButton select = new JButton("Select");
        select.setFont(MainFrame.SMALL_FONT);
        select.addActionListener(e -> frame.notifyOtherQuestSelected(q));
        qPanel.add(select, gc2);

        gc2.gridx = 2;
        final JButton delete = new JButton("delete");
        delete.setFont(MainFrame.SMALL_FONT);
        delete.addActionListener(e -> {
            questCategory.quests.remove(q);
            //this.frame.notifyAddingNewQuest();
            this.mainPanel.remove(qPanel);
            mainPanel.validate();
            mainPanel.repaint();
            scrollPane.validate();
            scrollPane.repaint();
            super.validate();
            super.repaint();
        });
        qPanel.add(delete, gc2);

        gc2.gridx = 3;
        final JButton up = new JButton("UP");
        up.setFont(MainFrame.SMALL_FONT);
        up.addActionListener(e -> {
            int i = questCategory.quests().indexOf(q);
            if (i != 0) {
                Collections.swap(quests, i, i - 1);
                Collections.swap(questCategory.quests, i, i - 1);
                frame.notifyAddingNewQuest();
            }
        });
        qPanel.add(up, gc2);

        gc2.gridx = 4;
        final JButton down = new JButton("DOWN");
        down.setFont(MainFrame.SMALL_FONT);
        down.addActionListener(e -> {
            int i = questCategory.quests().indexOf(q);
            if (i != questCategory.quests().size()-1) {
                Collections.swap(quests, i, i + 1);
                Collections.swap(questCategory.quests, i, i + 1);
                frame.notifyAddingNewQuest();
            }
        });
        qPanel.add(down, gc2);
        //this.mainPanel.add(qPanel,gc);
        this.quests.add(new QuestPanel(qPanel, (GridBagConstraints) gc.clone()));
    }


}
class QuestPanel{
    private final JPanel panel;
    private final GridBagConstraints constraints;


    public QuestPanel(JPanel panel, GridBagConstraints constraints) {
        this.panel = panel;
        this.constraints = constraints;
    }

    public GridBagConstraints getConstraints() {
        return constraints;
    }

    public JPanel getPanel() {
        return panel;
    }
}

