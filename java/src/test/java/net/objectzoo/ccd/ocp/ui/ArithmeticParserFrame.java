package net.objectzoo.ccd.ocp.ui;


import net.objectzoo.ccd.ocp.TestContainer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ArithmeticParserFrame extends JFrame {
    JPanel contentPane;
    BorderLayout borderLayout1 = new BorderLayout();
    JPanel mainPanel = new JPanel();

    private TestContainer testContainer;
    JTextPane tpUsageText = new JTextPane();
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    JPanel northPanel = new JPanel();
    GridLayout gridLayout1 = new GridLayout();
    JButton btnPerformParserTest = new JButton();
    JTextField fldArithStringInput = new JTextField();
    JLabel labVariableValues = new JLabel();
    JLabel labArithStringInput = new JLabel();
    JLabel labResult = new JLabel();
    JTextField fldVariableValues = new JTextField();
    JTextField fldResult = new JTextField();
    JPanel southPanel = new JPanel();
    JButton btnSetDefault = new JButton();
    GridLayout gridLayout2 = new GridLayout();
    JButton btnClear = new JButton();

    /**Den Frame konstruieren*/
    public ArithmeticParserFrame() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**Initialisierung der Komponenten*/
    private void jbInit() throws Exception  {
        //setIconImage(Toolkit.getDefaultToolkit().createImage(ArithmeticParserFrame.class.getResource("[Ihr Symbol]")));
        contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(borderLayout1);
        this.setSize(new Dimension(565, 443));
        this.setTitle("A Simple Arithmetic Parser");
        mainPanel.setLayout(gridBagLayout1);
        contentPane.setMinimumSize(new Dimension(700, 300));
        contentPane.setPreferredSize(new Dimension(700, 300));
        tpUsageText.setPreferredSize(new Dimension(200, 300));
        tpUsageText.setSize(new Dimension(565, 443));
        tpUsageText.setEditable(false);
        tpUsageText.setMinimumSize(new Dimension(100, 300));
        northPanel.setLayout(gridLayout1);
        gridLayout1.setRows(7);
        gridLayout1.setColumns(1);
        btnPerformParserTest.setToolTipText("");
        btnPerformParserTest.setActionCommand("performParsertest");
        btnPerformParserTest.setText("Parser starten");
        btnPerformParserTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnPerformParserTest_actionPerformed(e);
            }
        });
        labVariableValues.setText("Werte der Variablen:");
        labArithStringInput.setText("Eingabe (arithmetischer Ausdruck):");
        labResult.setText("Ergebnis:");
        fldVariableValues.setEditable(false);
        fldResult.setEditable(false);
        btnSetDefault.setActionCommand("setDefault");
        btnSetDefault.setText("Default-Ausdruck");
        btnSetDefault.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnSetDefault_actionPerformed(e);
            }
        });
        southPanel.setLayout(gridLayout2);
        gridLayout2.setRows(2);
        gridLayout2.setColumns(1);
        btnClear.setText("Zurücksetzen");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnClear_actionPerformed(e);
            }
        });
        btnClear.setActionCommand("clear");
        contentPane.add(mainPanel, BorderLayout.CENTER);
        mainPanel.add(tpUsageText, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 365, -256));
        contentPane.add(northPanel, BorderLayout.NORTH);
        northPanel.add(btnPerformParserTest, null);
        northPanel.add(labArithStringInput, null);
        northPanel.add(fldArithStringInput, null);
        northPanel.add(labVariableValues, null);
        northPanel.add(fldVariableValues, null);
        northPanel.add(labResult, null);
        northPanel.add(fldResult, null);
        contentPane.add(southPanel, BorderLayout.SOUTH);
        southPanel.add(btnSetDefault, null);
        southPanel.add(btnClear, null);

        initData ();
    }
    /** Überschrieben, so dass eine Beendigung beim Schließen des Fensters möglich ist.*/
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            System.exit(0);
        }
    }

    private void initData () {
        testContainer = new TestContainer ();
        tpUsageText.setText(testContainer.getUsageText ());
        fldArithStringInput.setText(testContainer.getDefaultExpression());
    }

    private void clear () {
        fldArithStringInput.setText ( "" );
        fldVariableValues.setText ( "" );
        fldResult.setText ( "" );
    }

    public void performParserTest () {
        String input = fldArithStringInput.getText ();
        testContainer.performParserTest ( input );
        fldVariableValues.setText ( testContainer.getVariableValueExpression () );
        fldResult.setText ( testContainer.getResult () );
    }

    public void setDefault () {
        fldArithStringInput.setText ( testContainer.getDefaultExpression() );
        fldVariableValues.setText ( "" );
        fldResult.setText ( "" );
    }

    void btnPerformParserTest_actionPerformed(ActionEvent e) {
        performParserTest ();
    }

    public void btnClear_actionPerformed(ActionEvent e) {
        clear ();
    }

    void btnSetDefault_actionPerformed(ActionEvent e) {
        setDefault ();
    }

}