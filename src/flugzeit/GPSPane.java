/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package flugzeit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author Seba
 */
public class GPSPane extends JPanel{
    int x, y, ox, oy, c, trackSize;
    Dimension track[];

    public GPSPane(int ox, int oy) {
        trackSize = 200000;
        setPreferredSize(new Dimension(400, 400));
        this.ox = -ox;
        this.oy = -oy;
        track = new Dimension[trackSize];
        for (int i = 0; i < trackSize; i++)
        {
            track[i] = new Dimension(0, 0);
        }
        c = 0;
        //setBackground(Color.red);
    }
    
    public void setXY(int a, int b)
    {
        x = a;
        y = b;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }
    
    
    
    public void moveXY(int a, int b)
    {
        x += a;
        y += b;
    }
    
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        
        Graphics2D d = (Graphics2D) g;
        int xv, yv;
        xv = (x-ox);
        yv = (y-oy);
        
        track[c++%trackSize] = new Dimension(xv, yv);
        
        d.setColor(Color.red);
        for (Dimension m : track)
        {
            d.drawRect(m.width, m.height, 2, 2);
        }
        d.setColor(Color.BLACK);
        d.fillOval(xv - 5, yv - 5, 10, 10);
    }
    
}
