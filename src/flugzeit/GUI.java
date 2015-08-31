package flugzeit;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Seba
 */
public class GUI {
    private static JFrame f;
    private static GraphPane p;
    private static GPSPane gp;
    private static JLabel airspeed;
    private static GyroPane gyroPane;
    
    public static void startup1()
    {
        GUI g  = new GUI();
        
        g.addGraph(new Graph(Color.BLUE));
        g.addGraph(new Graph(Color.RED));
        
        int i = 0;
        while (true)
        {
            i++;
            g.addPoint(0, (int)(Math.pow(2.72, -i/750.0)*135*Math.sin(i/7.5)));  
            g.addPoint(1, (int)(Math.pow(2.72, -i/750.0)*135*Math.sin(i/15.0))); 
            g.repaint();
            try {
                Thread.sleep((10));
            } catch (InterruptedException ex) {
                Logger.getLogger(Flugzeit.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void startup2()
    {
        GUI g = new GUI(0);
        g.repaint();
    }
    
    public static void startup3()
    {
        GUI g = new GUI(1);
    }
    
    public static void startup4()
    {
        GUI g = new GUI(2);
        p = new GraphPane();
        f.add(p);
        g.repaint();
    }
    
    public static void startup5()
    {
        f = new JFrame("herp");
        f.setSize(1400, 450);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new GridLayout(0,2));
        gyroPane = new GyroPane();
        f.add(gyroPane);
        
        p = new GraphPane();
        f.add(p);
    }
    /**
     * Constructs repaint Graphics Interface
     */
    public GUI() 
    {
        f = new JFrame("herp");
        f.setSize(500, 500);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        airspeed=new JLabel("0");
        p = new GraphPane();
        f.add(p);
    }
    
    public GUI(int i)
    {
        if (i == 0)
        {
            f = new JFrame("herp");
            f.setSize(500, 500);
            f.setVisible(true);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            ConsolePane c = new ConsolePane();
            f.add(c);
        }
        else if (i == 1)
        {
            f = new JFrame("herp");
            f.setSize(500, 500);
            f.setVisible(true);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            Console c = new Console();
            f.add(c);
        }
        else if (i == 2)
        {
            f = new JFrame("herp");
            f.setSize(500, 500);
            f.setVisible(true);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            gp = new GPSPane(50, 50);
            f.add(gp);
            gp.setXY(0, 0);
        }
        
    }
    
    public static void addGraph(Graph g)
    {
        p.addGraph(g);
    }
    
    /**
     * 
     * @param g
     * @param v 
     */
    public static void addPoint(int g, int v)
    {
        p.addPoint(g, v);
    }
    
    public static void setGPSLoc(int x, int y)
    {
        gp.setXY(x, y);
        repaint();
    }
    
    public static void setGPSx(int x)
    {
        gp.setX(x);
        repaint();
    }
    
    public static void setGPSy(int y)
    {
        gp.setY(y);
        repaint();
    }
    
    public static void setGyroPitch(int p)
    {
        gyroPane.setP(p);
    }
    
    public static void setGyroRoll(int p)
    {
        gyroPane.setR(p);
    }
    
    public static void repaint()
    {
        f.repaint();
    }
    
    
    /**
     * set airspeed.
     * @param airspeed 
     */
    
    public static void setAirspeed(int airspeed)
    {
        GUI.airspeed.setText(Integer.toString(airspeed));
    }
}
