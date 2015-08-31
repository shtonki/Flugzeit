/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flugzeit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.swing.*;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;


/*Class name: Console
 *Author: Thinegod the Thaumaturge
 *Created: 1:48:15 AM May 31, 2014
 *Description:
 */
public class Console extends JPanel
{

    private final JTextPane input = new JTextPane();
    private final JTextArea output = new JTextArea(15, 20);
    private String suggestion = "";
    private String key = "";

    public Console()
    {
        super(new BorderLayout());
        input.setContentType("text/html");
        input.addKeyListener(new KeyHandler());
        output.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(output);
        scrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setMaximumSize(new Dimension(300, 200));
        this.add(scrollPane, BorderLayout.CENTER);
        output.setLineWrap(true);
        input.setPreferredSize(new Dimension(200, 20));
        output.setBackground(Color.BLACK);
        output.setForeground(Color.GREEN);
        input.setBackground(Color.BLACK);
        input.setForeground(Color.WHITE);
        this.add(input, BorderLayout.SOUTH);
        input.setCaretColor(Color.GREEN);
        initCommands();
        writeln("Type \"help\" if you are clueless.");
    }

    private void write(String s)
    {
        output.append(s);
        output.setCaretPosition(output.getDocument().getLength());
    }

    private void writeln(String s)
    {
        output.append(s + "\n");
    }

    private String setText(String s)
    {
        input.setText("<font color=\"#00FF00\">" + s + "</font><font color=\"#bdbdbd\">" + suggestion + "</font>");
        return input.getText();
    }

    private HashMap<String, Command> commands = new HashMap<>();

    interface Command
    {

        public Object execute(Object o);
    }

    private class KeyHandler extends KeyAdapter
    {

        @Override
        public void keyTyped(KeyEvent e)
        {
            e.consume();
        }

        @Override
        public void keyPressed(KeyEvent e)
        {
            switch (e.getKeyCode())
            {
                case 10://enter was pressed.
                    key = key.replaceFirst("\n", "") + suggestion;

                    writeln(">" + key);

                    if (key.equals("") || key.isEmpty() || key.trim().isEmpty())
                    {
                        break;
                    }
                    e.consume();
                    if (commands.containsKey(key.split(" ")[0]))
                    {
                        writeln(commands.get(key.split(" ")[0]).execute(new ArrayList<>(Arrays.asList(key.split(" ")))).toString());
                    } else
                    {
                        writeln("Unknown command.");
                    }
                    key = "";
                    suggestion = "";
                    break;
                case 32://space was pressed.
                    key += suggestion + " ";
                    suggestion = "";
                    e.consume();
                    break;
                case 8://backspace was pressed.
                    if (!key.equals(""))
                    {
                        key = key.substring(0, key.length() - 1);
                        suggestion = "";

                    }
                    e.consume();
                    break;
                case 9://tab was pressed
                    break;
                default:
                    key += e.getKeyChar();
                    if (!suggestion.equals(""))
                    {
                        suggestion = "";
                    }
                    for (String s : commands.keySet())
                    {
                        if (s.startsWith(key) && key.length() > 0)
                        {
                            suggestion = s.replaceFirst(key, "");
                            break;
                        }
                    }
                    e.consume();
                    break;
            }
            setText(key);

        }

    }

    private void initCommands()
    {
        commands.put("airspeed", new Command()
        {

            @Override
            public Object execute(Object o)
            {

                return "airspeed is 200";
            }

        });
        commands.put("as", commands.get("airspeed"));
        commands.put("position", new Command()
        {

            @Override
            public Object execute(Object o)
            {
                return "Not yet implemented";
            }

        });

        commands.put("something", new Command()
        {

            @Override
            public Object execute(Object o)
            {
                return "Not yet implemented";
            }

        });

        commands.put("hitler", new Command()
        {

            @Override
            public Object execute(Object o)
            {
                return "Not yet implemented";
            }

        });
        commands.put("exit",new Command()
        {

            @Override
            public Object execute(Object o)
            {
                System.exit(0);
                return "bye";
            }
            
        });
        
        commands.put("help",new Command()
        {

            @Override
            public Object execute(Object o)
            {
                String res="These are the available commands.\n";
                for(String s:commands.keySet())
                {
                    res+=s+"\n";
                }
                return res;
            }
            
        });
    }
}
