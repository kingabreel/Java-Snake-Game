package Util;

import javax.swing.*;
import java.awt.*;

public class Style {
    public Style(){}

    public JButton styledButton(JButton button){

        button.setBackground(new Color(25, 25, 25));

        button.setBorder(BorderFactory.createLineBorder(new Color(25, 25, 25), 15, true));

        button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        button.setForeground(Color.WHITE);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.gray);
                button.setBorder(BorderFactory.createLineBorder(new Color(50,50,50),15, true));
                button.setBackground(new Color(50,50,50));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.white);
                button.setBorder(BorderFactory.createLineBorder(new Color(25, 25, 25), 15, true));
                button.setBackground(new Color(25, 25, 25));
            }
        });
        return button;
    }
}
