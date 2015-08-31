/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package flugzeit;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Com implements SerialPortEventListener {

	SerialPort serialPort;
        /** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { 
			"/dev/tty.usbserial-A9007UX1", // Mac OS X
                        "/dev/ttyACM0", // Raspberry Pi
			"/dev/ttyUSB0", // Linux
			"COM3", // Windows
	};
	/**
	* A BufferedReader which will be fed by a InputStreamReader 
	* converting the bytes into characters 
	* making the displayed results codepage independent
	*/
	private BufferedReader input;
	/** The output stream to the port */
	private OutputStream output;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;


	public void initialize() {
            ComHandler.init();
                // the next line is for Raspberry Pi and 
                // gets us into the while loop and was suggested here was suggested http://www.raspberrypi.org/phpBB3/viewtopic.php?f=81&t=32186
                System.setProperty("gnu.io.rxtx.SerialPorts", PORT_NAMES[3]);

		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

                
                
                
		//First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}
		if (portId == null) {
			System.out.println("Could not find COM port.");
			return;
		}
                
                
                
		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream(), "ISO-8859-1"));
			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	/**
	 * This should be called when you stop using the port.
	 * This will prevent port locking on platforms like Linux.
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
//		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
//			try {
//                                ArrayList<Integer> bs = new ArrayList<>();
//                                int c = 0;
//                                
//                                while (c != 0xFF)
//                                {
//                                    if (input.ready()) { c = input.read(); }
//                                }
//                                
//                                g.a();
//			} catch (Exception e) {
//				System.err.println(e.toString());
//			}
//		}
//		// Ignore all the other eventTypes, but you should consider the other ones.
	}
        
        public void runMe()
        {
            Thread t=new Thread() {
			public void run() 
                        { 
                            try
                            {
                                while (true)
                                {
                                    waitFor(0xBEEF, 2);
                                    int ms = 1;
                                    
                                    for (int i = 0; i < ms; i++)
                                    {
                                        int m = readBytes(1);
                                        //System.out.printf("M: %x\n", m);
                                        int v = readBytes(2);
                                        
                                        ComHandler.handle(m, v);
                                    }
                                        
                                        
                                }
                            } 
                            catch (Exception ex)
                            {
                                ex.printStackTrace();
                            }
                        }
		};
		t.start();
		System.out.println("Started the com port nonsense");
        }

        private int readBytes(int bs)
        {
            int r = 0;
            for (int i = 0; i < bs; i++)
            {
                r += readByte() << ((bs - 1 -i)*8);
            }
            return r;
        }
        
        private int readByte()
        {
            try
            {
                while (!input.ready()) { Thread.sleep(1);}
                return input.read();
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
            return -1;
        }
        
        private void waitFor(int w, int bs)
        {
            //while (w != -4354) { System.out.printf("%x\n", readByte()); }
            int[] ba = new int[bs];
            
            for (int i = 0; i < bs; i++)
            {
                ba[bs - 1 - i] = (w & (0xFF << (i*8))) >> (i*8);
            }
            
            int r = readByte();
            
            for (int i = 0; i < bs;)
            {
                if (r == ba[i]) { i++; }
                else { i = 0; }
                r = readByte();
            }
        }
}