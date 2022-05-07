package fr.ostix.questCreator.frame;

import fr.ostix.questCreator.quest.*;
import fr.ostix.questCreator.workspace.*;

import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

public class MainFrame {

    private final JFrame frame;

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    private JPanel mainPanel;
    private JPanel settingsPanel;

    private JPanel questEditor;
    private QuestSelector questSelector;
    private QuestSettings questSettings;
    private final Workspace workspace;

    public static final Font SMALL_FONT = new Font("Segoe UI", 1, 13);
    public static final Font MEDIUM_FONT = new Font("Segoe UI", 1, 20);

    public MainFrame() {
        this.frame = new JFrame("QuestEditor");
        workspace = new Workspace();
        this.initFrame();
        this.initIcon();
        this.initMainPanel();
        this.initInnerPanel();
        this.initMenuBar(workspace);
       // frame.pack();
        frame.setVisible(true);
    }

    private void initFrame() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());
        frame.setResizable(false);
    }

    private void initIcon() {
        BufferedImage image;
        try {
            image = ImageIO.read(Objects.requireNonNull(MainFrame.class.getResourceAsStream("/icon.png")));
            frame.setIconImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initMenuBar(Workspace workspace) {
        MenuBar menu = new MenuBar(this, workspace);
        frame.setJMenuBar(menu);
    }

    private void initMainPanel() {
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1.0D;
        gc.weighty = 1.0D;
        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(500, 650));
        frame.add(this.mainPanel, gc);
        gc.gridx = 1;
        settingsPanel = new JPanel();
        settingsPanel.setPreferredSize(new Dimension(750, 650));
        frame.add(this.settingsPanel, gc);
    }

    private void initInnerPanel() {
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        questSelector = new QuestSelector(this);
        questSelector.setPreferredSize(new Dimension(500,250));
        mainPanel.add(this.questSelector, gc);
        gc.gridy = 1;
        questSettings = new QuestSettings();
        questSettings.setPreferredSize(new Dimension(500, 380));
        mainPanel.add(this.questSettings, gc);


        gc.gridx = 1;
        questEditor = new JPanel();
        questEditor.setPreferredSize(new Dimension(730, 630));
        settingsPanel.add(this.questEditor, gc);
    }

    public void notifyOtherQuestSelected(Quest q){
        this.questSettings.refresh(q);
        this.questEditor.removeAll();
        this.questEditor.add(q.getPanel());
        this.frame.validate();
        this.frame.repaint();
        this.questSelector.refresh();
    }

    public void notifyAddingNewQuest() {
        this.questSelector.setNew(workspace.getCategory());
    }
}
