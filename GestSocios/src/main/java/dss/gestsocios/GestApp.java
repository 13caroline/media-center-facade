/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dss.gestsocios;
import javax.swing.SwingUtilities;

/**
 *
 * @author carolina
 */
public class GestApp {
    
    private GestApp() {
    }
    
    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                GestModel model = new GestModel();
                GestController controller = new GestController(model);
                GestView view = new GestView(controller);
                //controller.addObserver(view);
                //model.addObserver(view);
                //model.addObserver(controller);           
                view.run();
            }
        });
    }

}