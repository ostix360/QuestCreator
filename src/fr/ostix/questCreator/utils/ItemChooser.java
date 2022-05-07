package fr.ostix.questCreator.utils;

import fr.ostix.questCreator.items.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ItemChooser {
    private JDialog frame;
    private JComboBox<Item> list;
    private Item chosen;

    public ItemChooser() {
        setUpFrame();
        addLabel();
        addButton();
        this.frame.setVisible(true);
    }

    public Item getSelectedItem() {
        return chosen;
    }


    private void setUpFrame() {
        this.frame = new JDialog();
        this.frame.setAlwaysOnTop(true);
        this.frame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        this.frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.frame.setSize(300, 500);
        this.frame.setResizable(false);
        this.frame.setLocationRelativeTo(null);
        this.frame.setLayout(new GridBagLayout());
        this.list = new JComboBox<>(Items.getItems().toArray(new Item[]{}));
    }

    private void addButton() {
        JButton confirm = new JButton("select");
        confirm.setFont(new Font("Segoe UI", Font.BOLD, 15));
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 1;
        this.frame.add(this.list,gc);
        gc.gridy = 2;
        gc.weightx = 1.0D;
        gc.weighty = 0.4D;
        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                chosen = (Item) list.getSelectedItem();
                frame.setVisible(false);
                frame.dispose();
            }
        });
        this.frame.add(confirm, gc);
    }

    private void addLabel() {
        JLabel text = new JLabel("Choose an Item!");
        text.setFont(new Font("Segoe UI", Font.BOLD, 15));
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1.0D;
        gc.weighty = 0.4D;
        this.frame.add(text, gc);
    }


}
