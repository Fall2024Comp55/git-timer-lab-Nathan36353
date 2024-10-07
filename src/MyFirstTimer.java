import acm.graphics.*;
import acm.program.*;
import javax.swing.*;
import java.awt.event.*;

public class MyFirstTimer extends GraphicsProgram implements ActionListener {
    public static final int PROGRAM_HEIGHT = 600;
    public static final int PROGRAM_WIDTH = 800;
    private GLabel myLabel;
    private Timer timer;
    private int numTimes; 

    public void init() {
        setSize(PROGRAM_WIDTH, PROGRAM_HEIGHT);
        requestFocus();
        
        myLabel = new GLabel("times called? 0", 0, 100);
        add(myLabel);
    }
    
    public void run() {

        numTimes = 0;
        
       
        timer = new Timer(1000, this);
        
  
        timer.setInitialDelay(3000);
        
       
        timer.start();
    }

  
    public void actionPerformed(ActionEvent e) {
        
        numTimes++;
        
      
        if (numTimes >= 10) {
            timer.stop();
            return;  
        }
        
       
        myLabel.move(5, 0);
        
       
        myLabel.setLabel("times called? " + numTimes);
    }

    public static void main(String[] args) {
        new MyFirstTimer().start();
    }
}
