package fr.ostix.questCreator.quest;

import fr.ostix.questCreator.json.*;
import fr.ostix.questCreator.utils.*;
import org.joml.*;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.util.regex.*;

public class QuestLocation extends Quest {
    private final Vector3f pos;
    private final float range;


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
                if (!(pos.getXField().getText().matches("\\d+")) || pos.getXField().getText().isEmpty()) {
                    return;
                }
                QuestLocation.this.pos.x = Float.parseFloat(pos.getXField().getText().replaceAll(",", "."));
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
                if (!(pos.getYField().getText().matches("\\d+")) || pos.getYField().getText().isEmpty()) {
                    return;
                }
                QuestLocation.this.pos.y = Float.parseFloat(pos.getYField().getText().replaceAll(",", "."));
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
                if (!(pos.getZField().getText().matches("\\d*\\d+")) || pos.getZField().getText().isEmpty()) {
                    return;
                }
                QuestLocation.this.pos.z = Float.parseFloat(pos.getZField().getText().replaceAll(",", "."));
            }
        });
        panel.add(pos,gc);

        return panel;
    }

    /**
     * Extracts the first number out of a text.
     * Works for 1.000,1 and also for 1,000.1 returning 1000.1 (1000 plus 1 decimal).
     * When only a , or a . is used it is assumed as the float separator.
     *
     * @param sample The sample text.
     *
     * @return A float representation of the number.
     */
    static public Float extractFloat(String sample) {
        Pattern pattern = Pattern.compile("[\\d.,]+");
        Matcher matcher = pattern.matcher(sample);
        if (!matcher.find()) {
            return null;
        }

        String floatStr = matcher.group();

        if (floatStr.matches("\\d+,+\\d+")) {
            floatStr = floatStr.replaceAll(",+", ".");

        } else if (floatStr.matches("\\d+\\.+\\d+")) {
            floatStr = floatStr.replaceAll("\\.\\.+", ".");

        } else if (floatStr.matches("(\\d+\\.+)+\\d+(,+\\d+)?")) {
            floatStr = floatStr.replaceAll("\\.+", "").replaceAll(",+", ".");

        } else if (floatStr.matches("(\\d+,+)+\\d+(.+\\d+)?")) {
            floatStr = floatStr.replaceAll(",", "").replaceAll("\\.\\.+", ".");
        }

        try {
            return new Float(floatStr);
        } catch (NumberFormatException ex) {
            throw new AssertionError("Unexpected non float text: " + floatStr);
        }
    }

    @Override
    public String save() {
        return JsonUtils.gsonInstance().toJson(this);
    }
}
