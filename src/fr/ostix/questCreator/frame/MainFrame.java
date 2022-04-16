package fr.ostix.questCreator.frame;

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

    private JPanel questEditor;
    private JPanel questSelector;
    private JPanel questSettings;
    private final Workspace workspace;

    public static final Font SMALL_FONT = new Font("Segoe UI", 1, 13);
    public static final Font MEDIUM_FONT = new Font("Segoe UI", 1, 20);

    public MainFrame() {
        this.frame = new JFrame("QuestEditor");
        this.initFrame();
        this.initIcon();
        this.initMainPanel();
        workspace = new Workspace();
        frame.pack();
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

    private void initMainPanel() {
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1.0D;
        gc.weighty = 1.0D;
        questSettings = new QuestSettings();
        questSettings.setPreferredSize(new Dimension(500, 650));
        frame.add(this.questSettings, gc);
        gc.gridx = 1;
        questEditor = new JPanel();
        questEditor.setPreferredSize(new Dimension(650, 650));
        frame.add(this.questEditor, gc);
    }
}
