import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

/**
 * DrawBoard is a JPanel which manages drawing the board and registering moves.
 * 
 * @author Yash
 * @version 2.3
 */
//1.Human
//2.Computer
public class DrawBoard extends JPanel implements MouseListener,MouseMotionListener
{
    int row,col;
    Board b;
    boolean f;
    Color blue=new Color(33,150,243);
    Color red= new Color(244,67,54);
    Color yellow=new Color(255,235,59);
    boolean GameOver;
    public DrawBoard(Board b,boolean f)
    {
        this.b=b;
        GameOver=false;
        row=-1;
        col=-1;
        setDoubleBuffered(true);
        setBackground(blue);
        setPreferredSize(new Dimension(5+1+(75*7),5+1+(75*6)));
        this.f=f; 
        addMouseListener(this);
        addMouseMotionListener(this);    
    }
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d=(Graphics2D)g;
        RenderingHints rh=new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);
        for(int i=0;i<6;i++)
        {
            for(int j=0;j<7;j++)
            {
                Rectangle2D Box=new Rectangle2D.Double(5+1+(75*j),5+1+(75*i),75-20,75-20);
                g2d.setColor(blue);
                g2d.fill(Box);
                Ellipse2D circ=new Ellipse2D.Double(5+1+(75*j),5+1+(75*i),75-20,75-20);
                switch(b.board[i][j])
                {
                    case 0:
                    g2d.setColor(Color.white);
                    break;
                    case 1:
                    g2d.setColor(red);
                    break;
                    case 2:
                    g2d.setColor(yellow);
                    break;
                }
                g2d.fill(circ);
                /*if((new Move(i,j)).equals(b.moves.get(b.currentmove)))
                {
                    g2d.setColor(Color.black);
                    g2d.drawString("Last", 5+1+(75*j)+ (75-20)/3 ,5+1+(75*i) + (75-20)/2);
                }*/
            }
        }
        if(b.currentPlayer==1 && isMove(col))
        {
            g2d.setColor(Color.red);
            g2d.fillOval(5+1+(75*col),5+1,75-20,75-20);
        }
        if(f)
        {
            drawThreat(g);
        }
    }
    boolean isMove(int a)
    {
        return a>=0 && a<=6 && b.board[0][a]==0;
    }
    @Override
    public void mousePressed(MouseEvent e)
    {    
    }
    @Override
    public void mouseReleased(MouseEvent e)
    {    
    }
    @Override
    public void mouseEntered(MouseEvent e)
    {    
    }
    @Override
    public void mouseExited(MouseEvent e)
    {    
    }
    @Override
    public void mouseDragged(MouseEvent e)
    {    
    }
    @Override
    public void mouseClicked(MouseEvent e)
    {       
        int x=e.getX();
        int y=e.getY();
        int a=(int)Math.floor((x-6)/75);
        if(isMove(a) && b.currentPlayer==1 && !GameOver)
        {
            b.makeMove(a);
            main.Next();
        }
    }
    @Override
    public void mouseMoved(MouseEvent e) 
    {
        Point glassPanePoint = e.getPoint();
        double x=e.getX();
        double y=e.getY();
        col=(int)Math.floor((x-6)/75);
        row=(int)Math.floor((y-6)/75);
        repaint();
    }
    void drawThreat(Graphics g)
    {
        for(int j=0;j<7;j++)
        {
            for(int i=0;i<6;i++)
            {
                if(b.board[i][j]==0)
                {
                    int flag=0;
                    for(int k1=-1;k1<2;k1++)
                    {
                        for(int k2=-1;k2<2;k2++)
                        {
                            if(((i-k1)>=0 && (i-k1)<6)&&((j-k2)>=0 && (j-k2)<7))
                            {
                                if(b.board[i-k1][j-k2]!=0)
                                {
                                    flag++;
                                }
                            }
                        }
                    }
                    if(flag!=0)
                    {
                        b.board[i][j]=(b.currentPlayer);
                        if(b.isWin(i,j,b.currentPlayer)==true)
                        {
                            Graphics2D g2d=(Graphics2D)g;
                            RenderingHints rh=new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                            g2d.setRenderingHints(rh);
                            Ellipse2D circ=new Ellipse2D.Double(5+6+(75*j),5+6+(75*i),65-20,65-20);
                            g2d.setColor(new Color(127,245,23));
                            g2d.fill(circ);
                        }
                        b.board[i][j]=b.Other(b.currentPlayer);
                        if(b.isWin(i,j,b.Other(b.currentPlayer))==true)
                        {
                            Graphics2D g2d=(Graphics2D)g;
                            RenderingHints rh=new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                            g2d.setRenderingHints(rh);
                            Ellipse2D circ=new Ellipse2D.Double(5+6+(75*j),5+6+(75*i),65-20,65-20);
                            g2d.setColor(new Color(245,127,23));
                            g2d.fill(circ);
                        }
                    }
                    b.board[i][j]=0;
                }        
            }
        }
    }
}
