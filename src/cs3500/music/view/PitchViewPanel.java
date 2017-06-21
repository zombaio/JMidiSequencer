package cs3500.music.view;

import java.awt.Graphics;
import java.awt.Dimension;

import javax.swing.JPanel;

import cs3500.music.model.IjVirtualInstrument;
import cs3500.music.model.JMidiComposition;
import cs3500.music.util.JMidiUtils;

/**
 * A {@link JPanel} for displaying the range of pitches in the provided {@link JMidiComposition}.
 * If the range of pitches in the composition is less than twelve, twelve will be displayed
 * regardless. Otherwise, a range extending from the lowest note in the composition to the highest
 * in the composition will be generated.
 */
public class PitchViewPanel extends JPanel {
  private MusicEditorGUI gui;

  /**
   * Constructs a new PitchViewPanel based on the provided composition.
   *
   * @throws IllegalArgumentException if the composition is null.
   */
  public PitchViewPanel(MusicEditorGUI gui) throws IllegalArgumentException {
    this.gui = gui;

    setPreferredSize(new Dimension(DrawValues.RECTANGLE_W, determineHeight()));
    setMaximumSize(getPreferredSize());
  }



  private int determineHeight() {
    JMidiComposition composition = gui.getComposition();
    int maxPitch = composition.getMaxPitch();

    if (DrawValues.MIN_GRID_HEIGHT < (DrawValues.RECTANGLE_H * maxPitch)) {

      return DrawValues.RECTANGLE_H * maxPitch;

    } else {

      return DrawValues.MIN_GRID_HEIGHT;

    }
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    JMidiComposition composition = gui.getComposition();
    IjVirtualInstrument inst = JMidiUtils.defualtVI();

    setPreferredSize(new Dimension(DrawValues.RECTANGLE_W, determineHeight()));
    g.setFont(DrawValues.VERDANA);

    int minPitch = composition.getMinPitch();
    int maxPitch = composition.getMaxPitch();

    if (maxPitch < 12) {
      maxPitch = 12;
    }

    for (int i = minPitch; i <= maxPitch; i++) {
      g.drawString(inst.getNoteRepresentation(i) + (i / inst.getOctaveDegree()), 0,
              (maxPitch - i) * 20 + DrawValues.GRID_MARGIN + 20);
    }
  }
}
