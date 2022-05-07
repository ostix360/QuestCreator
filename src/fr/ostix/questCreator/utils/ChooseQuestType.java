package fr.ostix.questCreator.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ChooseQuestType {
    private JDialog frame;
    private String chosen = "dialogue";

    public ChooseQuestType() {
        setUpFrame();
        addLabel();
        addSpinner();
        addButton();
        this.frame.setVisible(true);
    }

    private void addSpinner() {
        final JPanel shapesPanel = new JPanel();
        shapesPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 191, 231)));
        shapesPanel.setLayout(new GridBagLayout());
        this.frame.add(shapesPanel);

        Types[] possible = Types.values();
        final JComboBox<Types> type = new JComboBox<>(possible);
        type.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chosen =  Objects.requireNonNull(type.getSelectedItem()).toString();
            }
        });
        shapesPanel.add(type);
    }

    public String getChosen() {
        return chosen;
    }

    private void setUpFrame() {
        this.frame = new JDialog();
        this.frame.setAlwaysOnTop(true);
        this.frame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        this.frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.frame.setSize(450, 600);
        this.frame.setResizable(false);
        this.frame.setLocationRelativeTo(null);
        this.frame.setLayout(new GridBagLayout());
    }

    private void addButton() {
        JButton confirm = new JButton("Select");
        confirm.setFont(new Font("Segoe UI", Font.BOLD, 15));
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 1;
        gc.gridy = 2;
        gc.weightx = 1.0D;
        gc.weighty = 0.4D;
        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                frame.setVisible(false);
                frame.dispose();

            }
        });
        this.frame.add(confirm, gc);
    }

    private void addLabel() {
        JLabel text = new JLabel("Choose a quest type!");
        text.setFont(new Font("Segoe UI", Font.BOLD, 15));
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1.0D;
        gc.weighty = 0.4D;
        this.frame.add(text, gc);
    }

    public enum Types {
        QUEST_DIALOGUE("dialogue"),
        QUEST_ITEM("item"),
        QUEST_LOCATION("location");

        private final String type;

        Types(String type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return type;
        }
    }
}
