/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package flugzeit;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Seba
 */

/**
 * Used to display time plots of data.
 * @author Daniel
 */
public class GraphPane extends JPanel
{
    private int drawWidth;
    
    private ArrayList<Graph> graphs;

    /**
     * Consructs a new GraphPane with no data
     */
    public GraphPane() {
        graphs = new ArrayList<>();
        setPreferredSize(new Dimension(500, 500));
    }
    
    public void addPoint(int g, int v)
    {
        graphs.get(g).addPoint(v);
    }

    /**
     * Adds an existing graph to the panel
     * @param g the graph to be drawn
     */
    public void addGraph(Graph g)
    {
        graphs.add(g);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        Graphics2D d = (Graphics2D)g;
        d.drawLine(0, this.getSize().height/2, this.getSize().width, this.getSize().height/2);
        d.setStroke(new BasicStroke(2.0f));
        drawWidth = this.getSize().width;
        d.translate(0, this.getSize().height/2);
        for (Graph a : graphs)
        {
            a.fill();
            if (a.size() == 0) { continue; };
            
            d.setColor(a.getColor() == null ? Color.PINK : a.getColor());
            
            int start = a.size() < drawWidth ? 0 : a.size() - drawWidth;
            
            
            for (int i = 0; i < drawWidth && i + start < a.size() - 1; i++)
            {
                d.drawLine(i, -a.get(i + start), i+1, -a.get(i + start + 1));
            }
        }
    }
}

/**
 * A class containing points to be drawn, information on how to draw them and 
 * a desription about the graph.
 * @author Daniel
 */
class Graph
{
    private ArrayList<Integer> points, waiting;
    private Color color;
    private String name, dscr;

    public Graph() {
        points = new ArrayList<>();
        waiting = new ArrayList<>();
    }
    
    public Graph(Color c)
    {
        this();
        color = c;
    }
    
    public int size()
    {
        return points.size();
    }
    
    public void addPoint(int a)
    {
        waiting.add(a);
    }
    
    public void fill()
    {
        for (int i = 0; i < waiting.size(); i++)
        {
            points.add(waiting.get(i));
        }
        waiting.clear();
    }
    
    public Integer get(int i)
    {
        return points.get(i);
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public String getDscr() {
        return dscr;
    }
    
    
}
