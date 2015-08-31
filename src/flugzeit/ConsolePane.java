/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package flugzeit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import static javax.swing.ScrollPaneConstants.*;

/**
 *
 * @author Seba
 * This means no Pders allowed
 */
public class ConsolePane extends JPanel{
    private final JTextArea textArea;
    private final JTextField t;

    public ConsolePane() {
        t =  new JTextField(20);
        t.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                writeln('>' + t.getText());
                parse(t.getText());
                t.setText("");
            }
        });
        
        
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(400, 250));
        textArea = new JTextArea(15, 20);
        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        textArea.setEditable(false);
        scrollPane.setMaximumSize(new Dimension(300, 200));
        t.setMaximumSize(new Dimension(300, 25));
        add(scrollPane);
        add(t);
        
        writeln("Shitty Language Shell v0.1");
    }
    
    private void writeln(String s)
    {
        write(s + "\n");
    }
    
    private void write(String s)
    {
        textArea.append(s);
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
    
    private void parse(String s)
    {
        if (s == null || s.length() == 0) { return; }
        String ss[] = s.split(" ");
        if (ss.length == 0) { return; }
        switch(ss[0])
        {
            case "help":
                writeln("No help available");
                break;
            case "as":
            case "asp":
            case "airspeed":
                if (ss.length == 2)
                {
                    int i = 0;
                    try
                    {
                        i = Integer.parseInt(ss[1]);
                    }
                    catch (NumberFormatException e)
                    {
                        writeln("Invalid argument.");
                        break;
                    }
                    writeln("Airspeed " + i);
                    System.out.println("AS " + i);
                }
                else 
                {
                    writeln("Invalid argument list.");
                }
                break;
            default:
                writeln("Unknown command \'" + s + '\'');
        }
    }
}
