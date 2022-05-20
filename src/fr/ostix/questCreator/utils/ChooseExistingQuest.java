package fr.ostix.questCreator.utils;

import fr.ostix.questCreator.workspace.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class ChooseExistingQuest {
    private JDialog frame;
    private JList<FileInList> list;
    private File chosen;

    public ChooseExistingQuest() {
        setUpFrame();
        addLabel();
        addFileList(getAllIconFiles());
        addButton();
        this.frame.setVisible(true);
    }

    public File getChosen() {
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
    }

    private void addButton() {
        JButton confirm = new JButton("Open");
        confirm.setFont(new Font("Segoe UI", Font.BOLD, 15));
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 2;
        gc.weightx = 1.0D;
        gc.weighty = 0.4D;
        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                chosen = (list.getSelectedValue()).getFile();

                frame.setVisible(false);
                frame.dispose();
            }
        });
        this.frame.add(confirm, gc);
    }

    private void addLabel() {
        JLabel text = new JLabel("Choose an entity!");
        text.setFont(new Font("Segoe UI", Font.BOLD, 15));
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1.0D;
        gc.weighty = 0.4D;
        this.frame.add(text, gc);
    }


    private void addFileList(java.util.List<File> files) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 1;
        gc.weightx = 1.0D;
        gc.weighty = 1.0D;
        FileInList[] data = getAllFilesInList(files);
        this.list = new JList<>(data);
        this.list.setFont(new Font("Segoe UI", Font.BOLD, 12));
        this.list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        this.list.setLayoutOrientation(JList.VERTICAL);
        this.list.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(this.list);
        listScroller.setPreferredSize(new Dimension(250, 350));
        this.frame.add(listScroller, gc);
    }

    private FileInList[] getAllFilesInList(java.util.List<File> files) {
        FileInList[] listedFiles = new FileInList[files.size()];
        for (int i = 0; i < listedFiles.length; i++) {
            listedFiles[i] = new FileInList(files.get(i));
        }
        return listedFiles;
    }

    private java.util.List<File> getAllIconFiles() {
        File folder = new File(String.valueOf(Config.OUTPUT_FOLDER));
        File[] allFiles = folder.listFiles();
        List<File> goodFiles = new ArrayList<>();
        assert allFiles != null;
        for (File file : allFiles) {
            if (file.getName().endsWith(".quest")) {
                goodFiles.add(file);
            }
        }
        return goodFiles;
    }
}
