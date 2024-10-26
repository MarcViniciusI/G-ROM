/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author marco
 */
public class IconUtils {
    public static void loadIcon(JFrame frame) {
        try {
            URL iconURL = frame.getClass().getResource("/Icon/mj.png");
            if (iconURL != null) {
                ImageIcon icon = new ImageIcon(iconURL);
                frame.setIconImage(icon.getImage());
            } else {
                System.err.println("Icone não encontrado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
