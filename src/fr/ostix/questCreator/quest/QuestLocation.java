package fr.ostix.questCreator.quest;

import fr.ostix.questCreator.frame.*;
import fr.ostix.questCreator.json.*;
import fr.ostix.questCreator.utils.*;
import org.joml.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;

import static fr.ostix.questCreator.utils.Utils.*;

public class QuestLocation extends Quest {
    private final Vector3f pos;
    private float range;


    public QuestLocation() {
        this.pos = new Vector3f();
        this.range = 5;
    }

    public static QuestLocation load(String questData) {
        return JsonUtils.gsonInstance().fromJson(questData, QuestLocation.class);
    }

    public Vector3f getPos() {
        return pos;
    }

    public float getRange() {
        return range;
    }

    @Override
    public String getType() {
        return "Location";
    }

    @Override
    public JPanel getPanel() {
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        final JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(700, 600));
        panel.setLayout(new GridBagLayout());
        final VectorPanel pos = new VectorPanel(600, 150, "Location", this.pos.x(), this.pos.y(), this.pos.z());
        pos.addXFieldListener(new DocumentListener() {
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
                Float x = extractFloat(pos.getXField().getText().replace(",", "."));
                if (x != null) {
                    QuestLocation.this.pos.x = x;
                }
            }
        });
        pos.addYFieldListener(new DocumentListener() {
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
                Float y = extractFloat(pos.getYField().getText().replace(",", "."));
                if (y != null) {
                    QuestLocation.this.pos.y = y;
                }
            }
        });
        pos.addZFieldListener(new DocumentListener() {
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
                Float z = extractFloat(pos.getZField().getText().replace(",", "."));
                if (z != null) {
                    QuestLocation.this.pos.z = z;
                }
            }
        });
        panel.add(pos, gc);

        gc.gridy = 1;
        GridBagConstraints gc2 = new GridBagConstraints();
        gc2.gridx = 0;
        gc2.gridy = 0;
        JPanel tPanel = new JPanel();
        tPanel.setPreferredSize(new Dimension(700, 100));
        tPanel.setLayout(new GridBagLayout());

        final JLabel LRange = new JLabel("Range : ");
        LRange.setFont(MainFrame.SMALL_FONT);
        tPanel.add(LRange, gc2);

        final JFormattedTextField range = createFloatTextField();
        range.setColumns(5);
        range.setValue(this.range);
        range.getDocument().addDocumentListener(new DocumentListener() {
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
                Float value = extractFloat(range.getText().replace(",", "."));
                if (value != null) {
                    QuestLocation.this.range = value;
                }
            }
        });
        gc2.gridx = 1;
        tPanel.add(range, gc2);
        panel.add(tPanel,gc);
        return panel;
    }

    @Override
    public String save() {
        return JsonUtils.gsonInstance().toJson(this);
    }
}
