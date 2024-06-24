import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.util.Random;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.event.*;
import java.util.ArrayList;

class Main {
  private JFrame frmMain;
  private JLabel lblDice1, lblDice2, lblInfo, lblOutcome, lblDistribution, lblAxis, lblTotal, lblStreak;
  private JButton btnThrowOnce, btnThrowBurst;
  private JTextField txtTrials;
  private JPanel pnlGraph;
  private Random rnd = new Random();
  final private String[] IMG_NAMES = { "img/1.png",
      "img/2.png",
      "img/3.png",
      "img/4.png",
      "img/5.png",
      "img/6.png" };
  private int[] frequencies = new int[11];
  private JLabel bars[] = new JLabel[11];
  private int total = 0;
  private ArrayList<Integer> events = new ArrayList<Integer>();
  int longest = 0;
  int specialSum = 0;

  public void createAndShowGUI() {
    frmMain = new JFrame("Dice stats");
    frmMain.setLayout(null);
    frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frmMain.setBounds(10, 10, 500, 600);

    int first = throwDice();
    lblDice1 = new JLabel(new ImageIcon(IMG_NAMES[first - 1]));
    int second = throwDice();
    lblDice2 = new JLabel(new ImageIcon(IMG_NAMES[second - 1]));
    longest = 1;
    specialSum = first + second;
    lblDice1.setBounds(10, 10, 100, 100);
    lblDice2.setBounds(120, 10, 100, 100);
    frmMain.add(lblDice1);
    frmMain.add(lblDice2);

    lblInfo = new JLabel("Outcome");
    lblInfo.setBounds(240, 10, 150, 50);
    frmMain.add(lblInfo);

    lblOutcome = new JLabel("" + (first + second));
    lblOutcome.setOpaque(true);
    lblOutcome.setBackground(new Color(255, 255, 204));
    lblOutcome.setBounds(240, 60, 75, 50);
    lblOutcome.setHorizontalAlignment(SwingConstants.CENTER);
    frmMain.add(lblOutcome);

    btnThrowOnce = new JButton("Throw Once");
    btnThrowOnce.setBounds(10, 200, 150, 50);
    btnThrowBurst = new JButton("Throw Burst");
    btnThrowBurst.setBounds(170, 200, 150, 50);
    frmMain.add(btnThrowOnce);
    frmMain.add(btnThrowBurst);

    txtTrials = new JTextField("100");
    txtTrials.setBounds(340, 200, 75, 50);
    frmMain.add(txtTrials);

    lblDistribution = new JLabel("Distribution");
    lblDistribution.setBounds(150, 250, 150, 50);
    frmMain.add(lblDistribution);

    pnlGraph = new JPanel();
    pnlGraph.setLayout(null);
    pnlGraph.setBounds(10, 300, 400, 250);
    pnlGraph.setBackground(new Color(200, 255, 204));

    for (int i = 0; i < 11; i++) {
      bars[i] = new JLabel();
      bars[i].setBounds(65 + i * 25, 60, 10, 5);
      bars[i].setBackground(new Color(0, 0, 0));
      bars[i].setOpaque(true);
      pnlGraph.add(bars[i]);
    }

    lblAxis = new JLabel("    2    3    4    5    6    7    8    9    10    11    12    ");
    lblAxis.setBounds(50, 10, 350, 50);

    lblTotal = new JLabel();
    lblTotal.setBackground(new Color(255, 153, 153));
    lblTotal.setHorizontalAlignment(SwingConstants.CENTER);
    lblTotal.setBounds(300, 200, 50, 50);
    lblTotal.setOpaque(true);

    lblStreak = new JLabel();
    lblStreak.setBackground(new Color(255, 153, 153));
    lblStreak.setHorizontalAlignment(SwingConstants.CENTER);
    lblStreak.setBounds(10, 200, 100, 50);
    lblStreak.setOpaque(true);
    lblStreak.setText("Sum " + specialSum + " x " + longest);
    pnlGraph.add(lblStreak);
    pnlGraph.add(lblAxis);
    pnlGraph.add(lblTotal);
    frmMain.add(pnlGraph);

    frmMain.setVisible(true);
  }

  public void actions() {

    btnThrowOnce.addActionListener((l) -> {
      int first = throwDice();
      lblDice1.setIcon(new ImageIcon(IMG_NAMES[first - 1]));
      int second = throwDice();
      lblDice2.setIcon(new ImageIcon(IMG_NAMES[second - 1]));
      events.add(first + second);
      lblOutcome.setText("" + (first + second));
      int height = bars[first + second - 2].getHeight();
      bars[first + second - 2].setSize(10, height + 1);
      frequencies[first + second - 2]++;
      total++;
      lblTotal.setText("" + total);
      int potentialLongest = longestConsecutive(events, longest);
      if (potentialLongest > longest) {
        longest = potentialLongest;
        specialSum = first + second;
      }
      lblStreak.setText("Sum " + specialSum + " x " + longest);
    });

    btnThrowBurst.addActionListener((l) -> {
      int trials = 100;
      try {
        trials = Math.abs(Integer.parseInt(txtTrials.getText().trim()));
      } catch (NumberFormatException e) {

      }
      final int TRIALS = trials;
      int oldTotal = total;
      int interval = 100;
      if (trials <= 50) {
        interval = 100;
      } else if (trials <= 100) {
        interval = 50;
      } else {
        interval = 20;
      }
      javax.swing.Timer t = new javax.swing.Timer(interval, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          int first = throwDice();
          lblDice1.setIcon(new ImageIcon(IMG_NAMES[first - 1]));
          int second = throwDice();
          lblDice2.setIcon(new ImageIcon(IMG_NAMES[second - 1]));
          events.add(first + second);
          lblOutcome.setText("" + (first + second));
          int height = bars[first + second - 2].getHeight();
          bars[first + second - 2].setSize(10, height + 1);
          frequencies[first + second - 2]++;
          total++;
          lblTotal.setText("" + total);
          int potentialLongest = longestConsecutive(events, longest);
          if (potentialLongest > longest) {
            longest = potentialLongest;
            specialSum = first + second;
          }
          lblStreak.setText("Sum " + specialSum + " x " + longest);
          if (oldTotal + TRIALS <= total) {
            ((javax.swing.Timer) e.getSource()).stop();
          }
        }
      });
      t.start();
    });
  }

  protected int throwDice() {
    return rnd.nextInt(1, 7);
  }

  protected int longestConsecutive(ArrayList<Integer> events, int longest) {
    if (events.size() >= 2) {
      int count = 1;
      int event = events.get(events.size() - 1);
      for (int i = events.size() - 2; i >= 0; i--) {
        if (events.get(i) == event) {
          count++;
        } else {
          break;
        }
      }
      if (count >= longest) {
        return count;
      }
      return longest;
    }
    return longest;
  }

  public static void main(String[] args) {
    Main driver = new Main();
    driver.createAndShowGUI();
    driver.actions();
  }
}