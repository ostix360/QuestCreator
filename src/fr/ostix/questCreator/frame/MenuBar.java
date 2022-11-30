package fr.ostix.questCreator.frame;

import fr.ostix.questCreator.quest.*;
import fr.ostix.questCreator.utils.*;
import fr.ostix.questCreator.workspace.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class MenuBar extends JMenuBar{

    private final MainFrame frame;
    private final Workspace workspace;

    public MenuBar(MainFrame frame, Workspace workspace) {
        this.frame = frame;
        this.workspace = workspace;
        buildBar();
    }

    private void buildBar() {
        JMenu file = new JMenu("File");
        this.add(file);

        JMenuItem newStaticFile = new JMenuItem("New Quest");
        JMenuItem openFile = new JMenuItem("Open");
        JMenuItem save = new JMenuItem("Save");
        file.add(newStaticFile);
        file.add(openFile);
        file.add(save);

        addOpenFileFunction(openFile);
        addSaveFunction(save);
        addNewStaticFileFunction(newStaticFile);

        file.setFont(new Font("Segoe UI", 1, 12));

        newStaticFile.setFont(new Font("Segoe UI", 1, 12));
        openFile.setFont(new Font("Segoe UI", 1, 12));
        save.setFont(new Font("Segoe UI", 1, 12));


        JMenu others = new JMenu("Other");
        this.add(others);

        JMenuItem settings = new JMenuItem("Settings");
        others.add(settings);

        addSettingsFunction(settings);

        others.setFont(new Font("Segoe UI", 1, 12));

        settings.setFont(new Font("Segoe UI", 1, 12));


        JMenu quest = new JMenu("Quest");
        this.add(quest);

        JMenuItem add = new JMenuItem("Add");
        JMenuItem questCategorySettings = new JMenuItem("Quest Category Settings");
        quest.add(add);
        quest.add(questCategorySettings);

        addAddQuestFunction(add);
        addQuestCategorySettings(questCategorySettings);

        quest.setFont(new Font("Segoe UI", 1, 12));

        add.setFont(new Font("Segoe UI", 1, 12));
        questCategorySettings.setFont(new Font("Segoe UI", 1, 12));

    }

    private void addQuestCategorySettings(JMenuItem questCategorySettings) {
        questCategorySettings.addActionListener( e -> {
            new QuestCategoryFrame(workspace.getCategory()).setQuestCategory(workspace.getCategory());
        });
    }

    private void addAddQuestFunction(JMenuItem add){
        add.addActionListener(e -> {
            switch (new ChooseQuestType().getChosen()){
                case "item":
                    workspace.getCategory().quests().add(new QuestItem());
                    break;
                case "dialogue":
                    workspace.getCategory().quests().add(new QuestDialog());
                    break;
                case "location":
                    workspace.getCategory().quests().add(new QuestLocation());
                    break;
            }
            frame.notifyAddingNewQuest();
        });
    }





    private void addOpenFileFunction(JMenuItem open){
        open.addActionListener(e -> {
            try {
                workspace.saveQuestCategory();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            workspace.openQuestCategory();
//
//                new FileChooseScreen(MenuBar.this.workspace.getAvailableItems(), MenuBar.this.workspace, mainFrame);

        });
    }


    private void addSaveFunction(JMenuItem save) {
        save.addActionListener(e -> {
            try {
                workspace.saveQuestCategory();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void addNewStaticFileFunction(JMenuItem newStaticFile) {
        newStaticFile.addActionListener(e -> {
            try {
                workspace.saveQuestCategory();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            workspace.newQuestCategory();
        });
    }

    private void addSettingsFunction(JMenuItem settings){
        settings.addActionListener(e ->{
            new OptionPanel();
        });
    }
}
