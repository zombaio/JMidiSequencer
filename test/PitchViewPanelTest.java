
import org.junit.Test;

import cs3500.music.view.gui.PitchViewPanel;

/**
 * Tests for PitchViewPanel.
 */
public class PitchViewPanelTest {
  @Test(expected = IllegalArgumentException.class)
  public void testNullConstructor() {
    PitchViewPanel fail = new PitchViewPanel(null);
  }
}