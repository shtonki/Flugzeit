/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flugzeit;

import java.awt.Color;


/*Class name: ComHandler
 *Author: Thinegod the Thaumaturge
 *Created: 7:37:38 PM Jul 12, 2014
 *Description:
 */
public class ComHandler
{
    
 
    public static void init()
    {
        System.out.println("BÖRK");
        airspeed = new Graph(Color.red);
        altitude = new Graph(Color.blue);
        sonar=new Graph(Color.MAGENTA);
        radarAltimeter = new Graph(Color.green);

        GUI.addGraph(airspeed);
        //GUI.addGraph(sonar);
        GUI.addGraph(altitude);
    }

    public static final byte VERTICALSPEED = 0x10,
            AIRSPEED = 0x11,
            SONAR = 0x12,
            ALTITUDE = 0x13,
            PITCH = 0x14,
            ROLL = 0x15;

    private static Graph airspeed, altitude, radarAltimeter, sonar;

    public static void handle(int m, int v)
    {
        switch (m)
        {
            case ALTITUDE:
                if (v < 300 && v > 0)
                {
                    System.out.println("ALTITUDE: " + v);
                    altitude.addPoint(v);
                }
                break;
            case 0x10:
                System.out.println("VERTICAL SPED:  " + v / 10);
                break;
            case AIRSPEED:
                if (v < 3000)
                {
                    System.out.println("Airspeed: "+Integer.toString(v-1600));
                    airspeed.addPoint(v - 1600);
                }
                break;
            case SONAR:
                System.out.println("SOnar : " + v);
                sonar.addPoint(v / 100);
                break;
            case PITCH:
                if (v > 32768)
                {
                    v = v - 65536;
                }
                System.out.println("P: " + v);
                GUI.setGyroPitch(v);
                GUI.repaint();
                break;
            case ROLL:
                if (v > 32768)
                {
                    v = v - 65536;
                }
                System.out.println("R: " + v);
                GUI.setGyroRoll(v);
                GUI.repaint();
                break;
            case 0x40:
                System.out.println("GPS X MSB");
                break;
            case 0x41:
                System.out.println("X: " + v);
                v /= 20;
                v = (v % 400);
                GUI.setGPSx(v);
                break;
            case 0x42:
                System.out.println("GPS Y MSB");
                break;
            case 0x43:  //GPS Y LSB
                System.out.println("Y: " + v);
                v = v / 20;
                v = (v % 400);
                GUI.setGPSy(v);
                break;
            case 0x50:  //x vel

                System.out.println("XV: " + (v > 32768 ? 32678 - v : v));
                break;
            case 0x51:  //y vel

                System.out.println("YV: " + (v > 32768 ? 32678 - v : v));
                break;

        }
        GUI.repaint();
    }
}