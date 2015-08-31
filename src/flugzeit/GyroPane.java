/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package flugzeit;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import javax.swing.JPanel;

/*Class name: GyroPane
 *Author: Thinegod the Thaumaturge
 *Created: 4:01:58 PM Jul 12, 2014
 *Description:
 */
public class GyroPane extends JPanel {
    private int P, R;

    public GyroPane()
    {
        setSize(400, 400);
        P = 50;
        R = 40;
    }
    

    
    
    public void setP(int P)
    {
        this.P = P;
    }

    public void setR(int R)
    {
        this.R = R;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        Graphics2D d = (Graphics2D) g;
        
        d.drawLine(200, 0, 200, 400);
        d.drawLine(0, 150, 400, 150);
        int a, b;
        a = (P)/75 + R/100;
        b = (P)/75 - R/100;
        d.setStroke(new BasicStroke(4));
        d.setColor(Color.red);
        d.drawLine(75, 150 + a, 325, 150 + b);
    }
    
    
}
