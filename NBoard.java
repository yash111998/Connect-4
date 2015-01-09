import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

/**
 * Write a description of class NBoard here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
//1.Human
//2.Computer
public class NBoard extends JPanel implements MouseListener,MouseMotionListener
{
    int row,col;
    int board[][]=new int[6][7];
    int currentPlayer;
    int prev[]=new int[2];
    int prev1[]=new int[2];
    boolean f;
    boolean vis;
    Color blue=new Color(33,150,243);
    Color red= new Color(244,67,54);
    Color yellow=new Color(255,235,59);
    public NBoard()
    {
        this(0,false);
    }
    public NBoard(int flag)
    {
        this(flag,false);
    }
    public NBoard(int flag,boolean f)
    {
        setDoubleBuffered(true);
        prev[0]=-1;
        prev[1]=-1;
        prev1[0]=-1;
        prev1[1]=-1;
        row=-1;
        col=-1;
        vis=false;
        setBackground(blue);
        if(flag!=0)
        {
            vis=true;
            addMouseListener(this);
            addMouseMotionListener(this);
            
        }
        setPreferredSize(new Dimension(5+1+(75*7),5+1+(75*6)));
        for(int i=0;i<6;i++)
        {
            for(int j=0;j<7;j++)
            {
                board[i][j]=0;
            }
        }
        currentPlayer=1;
        this.f=f;
        
    }
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
                switch(board[i][j])
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
                if(i==prev[0] && j==prev[1])
                {
                    g2d.setColor(Color.black);
                    g2d.drawString("L", 5+1+(75*j)+ (75-20)/2 ,5+1+(75*i) + (75-20)/2);
                }
            }
        }
        if(currentPlayer==1 && isMove(col) && !isGameOver())
        {
            g2d.setColor(Color.red);
            g2d.fillOval(5+1+(75*col),5+1,75-20,75-20);
        }
        if(currentPlayer==1 && f)
        {
            drawThreat(g);
        }
    }
    boolean isGameOver()
    {
        if(prev[0]==-1 || prev[1]==-1)
        {
            return false;
        }
        if(isWin(prev[0],prev[1],Other(currentPlayer)))
        {
            return true;
        }
        else
        {
            for(int i=0;i<6;i++)
            {
                for(int j=0;j<7;j++)
                {
                    if(board[i][j]==0)
                    {
                        return false;
                    }
                }
            }
            return true;
        }
    }
    boolean isWin(int row,int col,int k)
    {
        int check=k;
        int vertical=1;
        int horizontal=1;
        int diagonal1=1;
        int diagonal2=1;
        int i,j;
        for(i=row+1;i<=5 ;i++,vertical++)
        {
            if(board[i][col]!=k)
            {
                break;
            }
        }
        for(i=row-1;i>=0;i--,vertical++)
        {
            if(board[i][col]!=k)
            {
                break;
            }
        }
        if(vertical>=4)
        {
            return true;
        }
        for(i=col+1;i<=6;i++,horizontal++)
        {
            if(board[row][i]!=k)
            {
                break;
            }
        }
        for(i=col-1;i>=0;i--,horizontal++)
        {
            if(board[row][i]!=k)
            {
                break;
            }
        }
        if(horizontal>=4)
        {
            return true;
        }
        j=col-1;
        for(i=row-1;i>=0 && j>=0;i--,j--,diagonal1++)
        {
            if(board[i][j]!=k)
            {
                break;
            }
        }
        j=col+1;
        for(i=row+1;i<=5 && j<=6;i++,j++,diagonal1++)
        {
            if(board[i][j]!=k)
            {
                break;
            }
        }
        if(diagonal1>=4)
        {
            return true;
        }
        j=col+1;
        for(i=row-1;i>=0 && j<=6;i--,j++,diagonal2++)
        {
            if(board[i][j]!=k)
            {
                break;
            }
        }
        j=col-1;
        for( i=row+1;i<=5 && j>=0;i++,j--,diagonal2++)
        {
            if(board[i][j]!=k)
            {
                break;
            }
        }
        if(diagonal2>=4)
        {
            return true;
        }
        return false;
    }
    int evaluate() 
    {
        int score;
        if(prev1[0]!=-1 && prev1[1]!=-1 && isWin(prev1[0],prev1[1],currentPlayer))
        {
            score=100;
        }
        else if(isWin(prev[0],prev[1],Other(currentPlayer)))
        {
            score=-100;
        }
        else
        {
            int ctr1=0;
            int ctr2=0;
            int row=FindRow();
            for(int j=0;j<7;j++)
            {
                for(int i=5;i>=row;i--)
                {
                    int point1=0,point2=0;
                    int flag=0;
                    if(board[i][j]==0)
                    {
                        for(int k1=-1;k1<2;k1++)
                        {
                            for(int k2=-1;k2<2;k2++)
                            {
                                if(((i-k1)>=0 && (i-k1)<6)&&((j-k2)>=0 && (j-k2)<7))
                                {
                                    if(board[i-k1][j-k2]!=0)
                                    {
                                        flag++;
                                    }
                                }
                            }
                        }
                        if(flag!=0)
                        {
                            board[i][j]=currentPlayer;
                            if(isWin(i,j,currentPlayer)==true)
                            {
                                ctr1++;
                                point1=1;
                            }
                            board[i][j]=Other(currentPlayer);
                            if(isWin(i,j,Other(currentPlayer))==true)
                            {
                                ctr2++;
                                point2=1;
                            }
                            board[i][j]=0;
                        }
                    }
                    if(point1==point2 && point1==1)
                    {
                        break;
                    }
                }
            }    
            score=(ctr1-ctr2);
        }
        return score;
    }
    int evaluate(int k) 
    {
        int score;
        
        if(isWin(prev1[0],prev1[1],k))
        {
            score=100;
        }
        else if(isWin(prev[0],prev[1],Other(k)))
        {
            score=-100;
        }
        else
        {
            /*int ctr1=0;
            int ctr2=0;
            int row=FindRow();
            for(int j=0;j<7;j++)
            {
                for(int i=5;i>=row;i--)
                {
                    int flag=0;
                    if(board[i][j]==0)
                    {
                        for(int k1=-1;k1<2;k1++)
                        {
                            for(int k2=-1;k2<2;k2++)
                            {
                                if(((i-k1)>=0 && (i-k1)<6)&&((j-k2)>=0 && (j-k2)<7))
                                {
                                    if(board[i-k1][j-k2]!=0)
                                    {
                                        flag++;
                                    }
                                }
                            }
                        }
                    }
                    if(flag!=0)
                    {
                        board[i][j]=k;
                        if(isWin(i,j,k)==true)
                        {
                            ctr1++;
                        }
                        board[i][j]=Other(k);
                        if(isWin(i,j,Other(k))==true)
                        {
                            ctr2++;
                        }
                        board[i][j]=0;
                    }
                }
            }    
            score=(ctr1-ctr2);*/
            score=0;
        }
        return score;
    }
    int Other(int k)
    {
        int oth=(int)((k==1)?2:1);
        return oth;
    }
    int currentPlayer()
    {
        return currentPlayer;
    }
    boolean isMove(int a)
    {
        if(a>=0 && a<=6 && board[0][a]==0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    NBoard getMove(int a)
    {
        NBoard temp=new NBoard(0);
        for(int i=0;i<6;i++)
        {
            for(int j=0;j<7;j++)
            {
                temp.board[i][j]=this.board[i][j];
            }
        }
        int i;
        for(i=0;board[i][a]==0 && i<5 ;i++)
        {}
        if(board[i][a]==0)
        {
            i++;
        }
        temp.board[i-1][a]=this.currentPlayer;
        temp.prev1[0]=prev[0];
        temp.prev1[1]=prev[1];
        temp.prev[0]=i-1;
        temp.prev[1]=a;
        temp.currentPlayer=Other(this.currentPlayer);
        return(temp);
    }
    void makeMove(int a)
    {
        int i;
        for(i=0;board[i][a]==0 && i<5;i++)
        {}
        if(board[i][a]==0)
        {
            i++;
        }
        board[i-1][a]=currentPlayer;
        prev1[0]=prev[0];
        prev1[1]=prev[1];
        
        prev[0]=i-1;
        prev[1]=a;
        Color c=(currentPlayer==1)?red:yellow;
        currentPlayer=Other(currentPlayer);
        
       
    }
    public void mousePressed(MouseEvent e)
    {    
    }
    public void mouseReleased(MouseEvent e)
    {    
    }
    public void mouseEntered(MouseEvent e)
    {    
    }
    public void mouseExited(MouseEvent e)
    {    
    }
    public void mouseDragged(MouseEvent e)
    {    
    }
    public void mouseClicked(MouseEvent e)
    {       
        int x=e.getX();
        int y=e.getY();
        int a=(int)Math.floor((x-6)/75);
        int b=(int)Math.floor((y-6)/75);
        if(isMove(a) && currentPlayer==1 &&!isGameOver())
        {
            makeMove(a);
            NAspiration.Next();
        }
        
    }
    public void mouseMoved(MouseEvent e) 
    {
        Point glassPanePoint = e.getPoint();
        double x=e.getX();
        double y=e.getY();
        col=(int)Math.floor((x-6)/75);
        row=(int)Math.floor((y-6)/75);
        repaint();
    }
    int FindRow()
    {
        int row=5;
        for(int i=row;i>=0;i--)
        {
            int counter=0;
            for(int j=0;j<7;j++)
            {
                if(board[i][j]!=0)
                {
                    counter++;
                }
            }
            if(counter==0)
            {
                return(i);
            }
        }
        return(0);
    }
    int hash()
    {
        String s="";
        for(int i=0;i<6;i++)
        {
            for(int j=0;j<7;j++)
            {
                s+=""+board[i][j];
            }
        }
        return s.hashCode();
    }
    void reset()
    {
        prev[0]=-1;
        prev[1]=-1;
        prev1[0]=-1;
        prev1[1]=-1;
        row=-1;
        col=-1;
        for(int i=0;i<6;i++)
        {
            for(int j=0;j<7;j++)
            {
                board[i][j]=0;
            }
        }
        currentPlayer=1;
    }
    void drawThreat(Graphics g)
    {
        for(int j=0;j<7;j++)
            {
                for(int i=0;i<6;i++)
                {
                    if(board[i][j]==0)
                    {
                        int flag=0;
                        for(int k1=-1;k1<2;k1++)
                        {
                            for(int k2=-1;k2<2;k2++)
                            {
                                if(((i-k1)>=0 && (i-k1)<6)&&((j-k2)>=0 && (j-k2)<7))
                                {
                                    if(board[i-k1][j-k2]!=0)
                                    {
                                        flag++;
                                    }
                                }
                            }
                        }
                        if(flag!=0)
                        {
                            board[i][j]=(currentPlayer);
                            if(isWin(i,j,currentPlayer)==true)
                            {
                                Graphics2D g2d=(Graphics2D)g;
                                RenderingHints rh=new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                                g2d.setRenderingHints(rh);
                                Ellipse2D circ=new Ellipse2D.Double(5+6+(75*j),5+6+(75*i),65-20,65-20);
                                g2d.setColor(new Color(127,245,23));
                                
                                g2d.fill(circ);
                            }
                            board[i][j]=Other(currentPlayer);
                            if(isWin(i,j,Other(currentPlayer))==true)
                            {
                                Graphics2D g2d=(Graphics2D)g;
                                RenderingHints rh=new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                                g2d.setRenderingHints(rh);
                                Ellipse2D circ=new Ellipse2D.Double(5+6+(75*j),5+6+(75*i),65-20,65-20);
                                g2d.setColor(new Color(245,127,23));
                                
                                g2d.fill(circ);
                            }
                        }
                        board[i][j]=0;
                    }
                    
                }
            }
    }
}
