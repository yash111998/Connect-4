
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
/**
 * SButton-A Special Button
 * SButton extends JLabel to act as a button and provide a few Features.
 * @author Yash
 * @version 2.3
 */
public class SButton extends JLabel implements MouseListener
{
    Border compound1,compound2,compound3;
    public SButton(String s)
    {
        super(s,JLabel.CENTER);
        Border Black = BorderFactory.createLineBorder(Color.black);
        Border Red = BorderFactory.createLineBorder(Color.red);
        Border Green = BorderFactory.createLineBorder(Color.green);
        Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        //Border compound = BorderFactory.createCompoundBorder(raisedetched, loweredetched);
        Border compound=BorderFactory.createEmptyBorder(10, 5, 10, 5);
        compound1 =BorderFactory.createCompoundBorder(Black, compound);
        compound2 =BorderFactory.createCompoundBorder(Red, compound);
        compound3 =BorderFactory.createCompoundBorder(Green, compound);
        setBorder(compound1);
        setFocusable(true);
        addMouseListener(this);
    }
    @Override
    public void mousePressed(MouseEvent e)
    {
        setBorder(compound3);
        repaint();
    }
    @Override
    public void mouseReleased(MouseEvent e)
    {
        setBorder(compound2);
        repaint();
    }
    @Override
    public void mouseEntered(MouseEvent e)
    {    
        setBorder(compound2);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        repaint();
    }
    @Override
    public void mouseExited(MouseEvent e)
    {    
        setBorder(compound1);
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        repaint();
    }
    @Override
    public void mouseClicked(MouseEvent e)
    {
        requestFocusInWindow();
    }
}
