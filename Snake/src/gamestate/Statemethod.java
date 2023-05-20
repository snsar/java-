package src.gamestate;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public interface Statemethod {
    public void update();
    public void actionPerformed(ActionEvent e);
    public void draw(Graphics g);
    public void mouseDragged(MouseEvent e);
    public void mouseClicked(MouseEvent e);
    public void mousePressed(MouseEvent e);
    public void mouseReleased(MouseEvent e);
    public void mouseMoved(MouseEvent e);
    public void keyPressed(KeyEvent e);
    public void KeyReleased(KeyEvent e);
}