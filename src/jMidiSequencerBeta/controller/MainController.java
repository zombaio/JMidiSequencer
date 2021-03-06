package jMidiSequencerBeta.controller;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import jMidiSequencerBeta.model.JMidiComposition;
import jMidiSequencerBeta.util.JMidiUtils;
import jMidiSequencerBeta.util.MusicReader;
import jMidiSequencerBeta.view.ICompositionView;
import jMidiSequencerBeta.view.ViewManager;

/**
 * The class {@ModeSelector} Creates a view according to the user instructions.
 */
public class MainController {
  
  /**
   * User input.
   */
  final Readable rd;
  /**
   * output.
   */
  final Appendable ap;
  /**
   * holds file to open.
   */
  private String fileName;
  /**
   * holds input by word.
   */
  private Scanner scanner;
  /**
   * the generated composition.
   */
  private JMidiComposition composition;
  /**
   * the selected view.
   */
  private ICompositionView selected;
  
  /**
   * Constructs a {@code MainController} object.
   *
   * @param rd takes user input
   * @param ap transmits output
   * @throws IllegalStateException if the controller has not been initialized properly to receive
   *                               input and transmit output.
   */
  public MainController(Readable rd, Appendable ap) {
    if (rd == null) {
      throw new IllegalStateException("readable can't be null!");
    }
    if (ap == null) {
      throw new IllegalStateException("Appendable can't be null!");
    }
    this.rd = rd;
    this.ap = ap;
    this.fileName = null;
    this.composition = null;
    this.scanner = new Scanner(rd);
    this.selected = null;
  }

  /**
   * Interacts with the user to select the desired view.
   */
  public void run() {
  
    JMidiUtils.message("please write the name of the file you want to open and its extension or "
            + "write Q to quit", ap);
  
    //look around the appendable
    while (scanner.hasNextLine()) {
  
      String next = scanner.nextLine();
  
      //make sure if q is pressed it can be aborted
      if (next.equalsIgnoreCase("Q")) {

        JMidiUtils.message("bye!", ap);
        return;
  
        //ask for a file
      } else if (fileName == null) {

        fileName = next;

        try {
  
          Readable file = new FileReader("resources/musicTxt/" + fileName + ".txt");
          JMidiUtils.message(String.format("Parsing " + fileName), ap);
  
          composition = MusicReader.parseFile(file, JMidiComposition.builder());
  
          JMidiUtils.message("console, gui, composite or MIDI?", ap);

        } catch (IOException | IllegalArgumentException e) {

          fileName = null;
  
          JMidiUtils.message(e.toString(), ap);

        }
  
        //ask for a view
      } else if (selected == null) {

        try {
  
          selected = ViewManager.select(next, composition, ap);
          selected.initController();

        } catch (IllegalArgumentException e) {
  
          selected = null;
          JMidiUtils.message(e.toString(), ap);

        }

      }

    }
    
  }

}
