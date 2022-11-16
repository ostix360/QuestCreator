package fr.ostix.questCreator.frame;

import fr.ostix.questCreator.quest.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

import static fr.ostix.questCreator.utils.Utils.createIntTextField;

public class QuestCategoryFrame {

    private JDialog frame;

    private String title = "";

    private int id = -1;

    private QuestStatus status = QuestStatus.AVAILABLE;

    private final GridBagConstraints gc = new GridBagConstraints();
    private int nextQuest = -1;


    public QuestCategoryFrame() {
        setUpFrame();
        addLabel();
        addComponents();
        addButton();
        this.frame.setVisible(true);
    }

    private void addComponents() {
        gc.gridy = 1;
        gc.gridx = 0;

        final JLabel LTitle = new JLabel("Title : ");
        LTitle.setFont(MainFrame.SMALL_FONT);
        frame.add(LTitle, gc);

        gc.gridx = 1;
        final JTextField title = new JTextField("", 20);
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
                QuestCategoryFrame.this.title = text;
            }
        });
        frame.add(title, gc);

        gc.gridx = 0;
        gc.gridy = 2;

        final JLabel LID = new JLabel("ID : ");
        LID.setFont(MainFrame.SMALL_FONT);
        frame.add(LID, gc);

        gc.gridx = 1;
        final JFormattedTextField id = createIntTextField();
        id.setValue(-1);
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
                    QuestCategoryFrame.this.id = value;
                }
            }
        });
        this.frame.add(id, gc);

        gc.gridx = 1;
        gc.gridy = 3;

        final JPanel shapesPanel = new JPanel();
        shapesPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 191, 231)));
        shapesPanel.setLayout(new GridBagLayout());
        this.frame.add(shapesPanel,gc);

        QuestStatus[] possible = QuestStatus.values();
        final JComboBox<QuestStatus> type = new JComboBox<>(possible);
        type.addActionListener(e -> status = (QuestStatus) type.getSelectedItem());
        shapesPanel.add(type);

        gc.gridx = 0;
        gc.gridy = 2;

        final JLabel LNext = new JLabel("Next Quest : ");
        LNext.setFont(MainFrame.SMALL_FONT);
        frame.add(LNext, gc);

        gc.gridx = 1;
        final JFormattedTextField next = createIntTextField();
        next.setValue(-1);
        next.getDocument().addDocumentListener(new DocumentListener() {
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
                if (next.getText().matches("\\d+")) {
                    nextQuest = Integer.parseInt(next.getText());
                }
            }
        });
        this.frame.add(next, gc);

    }


    private void setUpFrame() {
        this.frame = new JDialog();
        this.frame.setAlwaysOnTop(true);
        this.frame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        this.frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.frame.setSize(450, 600);
        this.frame.setResizable(true);
        this.frame.setLocationRelativeTo(null);
        this.frame.setLayout(new GridBagLayout());
    }

    private void addButton() {
        JButton confirm = new JButton("Valide");
        confirm.setFont(new Font("Segoe UI", Font.BOLD, 15));
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 1;
        gc.gridy = 4;
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
        JLabel text = new JLabel("Main quest Settings!");
        text.setFont(new Font("Segoe UI", Font.BOLD, 15));
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1.0D;
        gc.weighty = 0.4D;
        this.frame.add(text, gc);
    }

    public QuestCategory getQuestCategory() {
        return new QuestCategory(id,title,status, nextQuest);
    }
}
