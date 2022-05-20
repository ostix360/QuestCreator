package fr.ostix.questCreator.quest;

import com.google.gson.*;
import com.google.gson.annotations.*;
import com.sun.deploy.util.*;
import fr.ostix.questCreator.frame.*;
import fr.ostix.questCreator.json.*;
import fr.ostix.questCreator.quest.serialization.*;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.stream.*;

public class QuestDialog extends Quest {
    @Expose
    private final List<String> dialogs;

    private final JPanel panel = new JPanel();

    private final List<QuestText> questTexts = new ArrayList<>();
    private final JPanel mainPanel = new JPanel();

    private final JTextArea textArea = new JTextArea(3,30);

    private final JScrollPane scrollPane;
    private final List<DialogPanel> dialogPanels = new ArrayList<>();

    public QuestDialog() {
        this.dialogs = new ArrayList<>();
        mainPanel.setLayout(new GridBagLayout());
        scrollPane = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(480, 210));
    }

    public static QuestDialog load(String questData) {
        return JsonUtils.gsonInstance().fromJson(questData, QuestDialog.class);
    }

    @Override
    public String save() {
        dialogs.clear();
        questTexts.stream()
                .sorted(Comparator.comparingInt(QuestText::getIndex))
                .collect(Collectors.toList())
                .forEach((qt) -> dialogs.add(qt.getText()));
        Gson gson= new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(Rewards.class, new RewardsTypeAdapter())
                .create();

        return gson.toJson(this);
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
        panel.removeAll();
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        this.panel.setPreferredSize(new Dimension(700, 600));
        final JButton add = new JButton("Add");
        add.setFont(MainFrame.SMALL_FONT);
        add.addActionListener((e) -> {
            String s =  new String("Write here");
//            dialogs.add(s);
            questTexts.add(new QuestText(questTexts.size(), s));
            notifyAddingNewDialog();
            selecting(questTexts.get(questTexts.size()-1));
        });
        this.panel.add(add,gc);

        gc.gridy = 1;
        this.panel.add(scrollPane,gc);
        gc.gridy = 2;
        textArea.setLineWrap(true);
        textArea.setBorder(BorderFactory.createTitledBorder("Speech Bubble"));
        textArea.setWrapStyleWord(true);


        int charMax = 101;
        JLabel charCntLabel = new JLabel("100 Characters max");
        textArea.addKeyListener(new KeyListener() {
            boolean ignoreInput = false;
            @Override
            public void keyTyped(KeyEvent e) {
                return;
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // If we should be ignoring input then set make sure we
                // enforce max character count and remove the newly typed key.
                if(ignoreInput)
                    textArea.setText(textArea.getText().substring(0,
                            100));
                if (StringUtils.countMatches(textArea.getText(),"\n") > 2){
                    textArea.setText(textArea.getText().replaceFirst("\n",""));
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int newLen = 0;

                // The key has just been pressed so Swing hasn't updated
                // the text area with the new KeyEvent.
                int currLen = textArea.getText().length();

                // Adjust newLen depending on whether the user just pressed
                // the backspace key or not.

                if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    newLen = currLen - 1;
                    ignoreInput = false;
                }
                else
                    newLen = currLen + 1;
                charCntLabel.setText(newLen +"/100");
                if(newLen < 0)
                    newLen = 0;

               if(newLen >= charMax) {
                    ignoreInput = true;

                }
            }
        });
        this.panel.add(textArea,gc);
        gc.gridy = 3;
        this.panel.add(charCntLabel,gc);
        questTexts.clear();
        for (int i = 0; i < dialogs.size();i++){
            questTexts.add(new QuestText(i,dialogs.get(i)));
        }
        notifyAddingNewDialog();
        return panel;
    }

    private void selecting(QuestText s) {
        textArea.setDocument(new PlainDocument());
        textArea.setText(s.getText());
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            final QuestText qt = s;
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
                String text = textArea.getText();
                if (text.equals("")) {
                    return;
                }
//                dialogs.set(index,text);
                qt.setText(text);
            }
        });
    }

    private void initPanel() {
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        for (int i = 0; i < this.questTexts.size(); i++) {
            QuestText text = this.questTexts.get(i);
            gc.gridy = i;
            addQuestInList(text, gc);
        }
        dialogPanels.forEach((qp) -> mainPanel.add(qp.getPanel(),qp.getConstraints()));
    }

    private void notifyAddingNewDialog() {

        this.mainPanel.removeAll();

        this.dialogPanels.clear();
        initPanel();
        mainPanel.validate();
        mainPanel.repaint();
        panel.validate();
        panel.repaint();
    }

    public void refresh() throws ClassCastException {
        for (int i = 0; i < this.questTexts.size(); i++) {
            QuestText text = this.questTexts.get(i);
            JPanel qPanel = (JPanel) mainPanel.getComponent(i);
            JLabel label = (JLabel) qPanel.getComponent(0);
            if (text.getText().length() > 15){
                label.setText(text.getText().substring(0, 15));
            }else{
                label.setText(text.getText());
            }

            label.validate();
            label.repaint();
            qPanel.validate();
            qPanel.repaint();
        }
        mainPanel.validate();
        mainPanel.repaint();
        panel.validate();
        panel.repaint();
    }



    private void addQuestInList(QuestText text, GridBagConstraints gc) {
        GridBagConstraints gc2 = new GridBagConstraints();
        gc2.gridx = 0;
        gc2.gridy = 0;
        final JPanel qPanel = new JPanel();
        qPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        qPanel.setLayout(new GridBagLayout());
        qPanel.setPreferredSize(new Dimension(460, 50));

        final JLabel qName = new JLabel();
        if (text.getText().length() > 15){
            qName.setText(text.getText().substring(0, 15));
        }else{
            qName.setText(text.getText());
        }
        qName.setFont(MainFrame.SMALL_FONT);
        qPanel.add(qName, gc2);


        gc2.gridx = 1;
        final JButton select = new JButton("Select");
        select.setFont(MainFrame.SMALL_FONT);
        select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                QuestDialog.this.refresh();
                QuestDialog.this.selecting(text);
            }
        });
        qPanel.add(select, gc2);

        gc2.gridx = 2;
        final JButton delete = new JButton("delete");
        delete.setFont(MainFrame.SMALL_FONT);
        delete.addActionListener(e -> {
            questTexts.remove(text);
            //this.frame.notifyAddingNewDialog();
            this.mainPanel.remove(qPanel);
            mainPanel.validate();
            mainPanel.repaint();
            scrollPane.validate();
            scrollPane.repaint();
        });
        qPanel.add(delete, gc2);

        gc2.gridx = 3;
        final JButton up = new JButton("UP");
        up.setFont(MainFrame.SMALL_FONT);
        up.addActionListener(e -> {
            int i = questTexts.indexOf(text);
            if (i != 0) {
                Collections.swap(dialogPanels, i, i - 1);
                Collections.swap(questTexts, i, i - 1);
                text.setIndex(i-1);
                notifyAddingNewDialog();
            }
        });
        qPanel.add(up, gc2);

        gc2.gridx = 4;
        final JButton down = new JButton("DOWN");
        down.setFont(MainFrame.SMALL_FONT);
        down.addActionListener(e -> {
            int i = questTexts.indexOf(text);
            if (i != questTexts.size() - 1) {
                Collections.swap(dialogPanels, i, i + 1);
                Collections.swap(questTexts, i, i + 1);
                text.setIndex(i+1);
                notifyAddingNewDialog();
            }
        });
        qPanel.add(down, gc2);
        //this.mainPanel.add(qPanel,gc);
        this.dialogPanels.add(new DialogPanel(qPanel, (GridBagConstraints) gc.clone()));
    }


}

class QuestText{
    private int index;
    private String text;

    public QuestText(int index, String text) {
        this.index = index;
        this.text = text;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

class DialogPanel {
    private final JPanel panel;
    private final GridBagConstraints constraints;

    public DialogPanel(JPanel panel, GridBagConstraints constraints) {
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
