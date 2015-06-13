/**
 * Main class which executes the program.
 * Accepts initial conditions like difficulty level.
 * @author Yash
 * @version 2.3
 */
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;

import java.awt.*;
import java.io.*;

public class main
{
    static JFrame f=new JFrame("Connect 4");
    static JLabel Status=new JLabel("Your Move");
    static Border compound=BorderFactory.createEmptyBorder(10, 5, 10, 5);
    static Border Black = BorderFactory.createLineBorder(Color.black);
    static Border compound1 =BorderFactory.createCompoundBorder(Black, compound);
    static String moves="";
    static Board MainBoard=new Board();
    static DrawBoard DB;
    static int previous=0;
    static int maxd=8;
    static int movecount=0;
    static boolean thinking=false;
    static boolean GameOver=false;
    static JPanel P;
    static boolean threatcheck=false;    
    
    
    
    
    
    
    
    
    
    
    
    //MainGame
    public static void main(String args[])
    {
        try {
            // Set System L&F
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } 
        catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            // handle exception
        }
        // handle exception
        // handle exception
        // handle exception
        
        
        final JFrame fPre=new JFrame("Enter Details : ");     
        fPre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel P2=new JPanel(new GridBagLayout());
        GridBagConstraints c=new GridBagConstraints();
        c.fill=GridBagConstraints.HORIZONTAL;
        c.insets=new Insets(3,3,3,3);
        
        SpinnerModel m3=new SpinnerNumberModel(maxd,1,15,1);
        final JSpinner s3;
        s3=new JSpinner(m3);
        c.weighty=0.5;
        c.gridx=2;
        c.gridy=0;
        P2.add(s3,c);
        JLabel l=new JLabel("Difficulty Level : ",JLabel.CENTER);
        c.weighty=0.5;
        c.gridx=0;
        c.gridy=0;
        P2.add(l,c);
        
        JCheckBox CB=new JCheckBox("Show Threats??");
        CB.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                if(e.getStateChange()==ItemEvent.DESELECTED)
                {
                    threatcheck=false;
                }
                else if(e.getStateChange()==ItemEvent.SELECTED)
                {
                    threatcheck=true; 
                }
            }
        });
        c.weightx=4;
        c.weighty=0.5;
        c.gridx=0;
        c.gridy=1;
        c.gridwidth=GridBagConstraints.REMAINDER;
        
        P2.add(CB,c);
        
        SButton b=new SButton("Done ");
        b.addMouseListener(new MouseListener(){
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
            public void mouseClicked(MouseEvent e)
            {   
                maxd=(int)s3.getValue();
                fPre.dispose();
                Begin();
            }
        });
        c.weightx=4;
        c.weighty=0.5;
        c.gridx=0;
        c.gridy=2;
        c.gridwidth=GridBagConstraints.REMAINDER;
        
        P2.add(b,c);
        TitledBorder title=BorderFactory.createTitledBorder(Black,"Set Difficulty of Game");
        title.setTitleJustification(TitledBorder.CENTER);
        Border comp=BorderFactory.createCompoundBorder(title, compound);
        P2.setBorder(comp);
        fPre.add(P2);
        fPre.pack();
        fPre.setLocationRelativeTo(null);
        fPre.setVisible(true);
        
    }
    static void Begin()
    {
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().setLayout(new GridBagLayout());
        DB=new DrawBoard(MainBoard,threatcheck);
        
        GridBagConstraints c=new GridBagConstraints();
        c.insets=new Insets(3,3,3,3);
        c.gridx=0;
        c.gridy=0;
        c.gridwidth=5;
        DB.setBorder(compound1);
        f.add(DB,c);
        
        JLabel l=new JLabel("Difficulty : "+maxd);
        l.setBorder(compound1);
        l.setHorizontalTextPosition(JLabel.CENTER);
        P=new JPanel(new GridBagLayout());
        c.fill=GridBagConstraints.BOTH;
        c.weightx=1;
        c.gridx=0;
        c.gridy=1;
        c.gridwidth=2;
        
        P.add(l,c);
        Status.setBorder(compound1);
        Status.setHorizontalTextPosition(JLabel.CENTER);
        
        
        c.fill=GridBagConstraints.BOTH;
        c.weightx=1;
        c.gridx=3;
        c.gridy=1;
        c.gridwidth=2;
        P.add(Status,c);
        f.add(P,c);
        l.setHorizontalAlignment(JLabel.CENTER);
        l.setText("Difficulty : "+maxd);
        Status.setText("Your Move");
        Status.setHorizontalAlignment(JLabel.CENTER);
        
        
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        
    }
    static void Next()
    {
        DB.repaint();
        f.repaint();
        f.revalidate();
        f.toFront();
        moves+=MainBoard.moves.get(MainBoard.currentmove).col+" ";
        if(MainBoard.isWin(MainBoard.moves.get(MainBoard.currentmove),1))
        {
            JOptionPane.showMessageDialog(f,"You Win!!" , "Victory", JOptionPane.INFORMATION_MESSAGE);   
            Status.setText("You Win!!");
            GridBagConstraints c=new GridBagConstraints();
            c.insets=new Insets(3,3,3,3);
            c.gridx=2;
            c.gridy=1;
            final SButton b=new SButton("Save MoveSet");
            b.addMouseListener(new MouseListener(){
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
                public void mouseClicked(MouseEvent e)
                {   
                     try
                     {  
                         try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("MoveSet.yash",true))))
                         {
                             out.println("## MoveSet Difficulty : "+maxd+" ##");
                             out.println(moves+" &&");
                         }
                         JOptionPane.showMessageDialog(f,"DONE" , "Saving Complete", JOptionPane.INFORMATION_MESSAGE);
                         P.remove(b);
                     }
                     catch(Exception e2)
                     {
                         JOptionPane.showMessageDialog(f,e2.toString() , "Error", JOptionPane.ERROR_MESSAGE);
                     }
                }
            });
            P.add(b,c);
            P.repaint();
            GameOver=true;
            DB.GameOver=true;
        }
        else if(++movecount==21)
        {
            JOptionPane.showMessageDialog(f,"You Draw!!" , "Good Work", JOptionPane.INFORMATION_MESSAGE);
            Status.setText("You Draw!!");
            GameOver=true;
            DB.GameOver=true;
        }
        else
        {
            Status.setText("Thinking");
            Thread t=new Thread(new Eval());
            t.start();
        }
    }
    static class Eval implements Runnable
    {
        @Override
        public void run()
        {
            try{
                Thread.sleep(100);
            }
            catch(Exception e1)
            {
                System.out.println(e1.toString());
            }
            int mov;
            thinking=true;
            mov=getBestMove(MainBoard,2,maxd);
            moves+=mov+" ";
            MainBoard.makeMove(mov);
            DB.repaint();
            f.repaint();
            f.revalidate();
            f.toFront();
            Status.setText("Your Move");
            
            if(MainBoard.isWin(MainBoard.moves.get(MainBoard.currentmove),2))
            {
                JOptionPane.showMessageDialog(f,"You Lose!!" , "Better Luck Next Time", JOptionPane.INFORMATION_MESSAGE);
                Status.setText("You Lose!!");
                DB.GameOver=true;
                GameOver=true;
            }
            thinking=false;
        }
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //Algorithm being Used
    
    
    static int getBestMove(Board b,int p,int maxdep)
    {
        Board temp=new Board();
        for(int i=0;i<6;i++)
        {
            for(int j=0;j<7;j++)
            {
                temp.board[i][j]=b.board[i][j];
            }
        }
        temp.currentPlayer=b.currentPlayer;
        temp.currentmove=b.currentmove;
        temp.moves.addAll(b.moves);
        temp.prev[0]=b.prev[0];
        temp.prev[1]=b.prev[1];
        /*try{
            Scanner sc=new Scanner(new File("Table "+maxdep+""+2+".yash"));
            int count=0;
            while(sc.hasNextInt())
            {
                int code=sc.nextInt();
                int mov=sc.nextInt();
                int sco=sc.nextInt();
                if(code==temp.hash())
                {
                    previous=sco;
                    sc.close();
                    return mov;
                }
                count++;
            }
            sc.close();
        }
        catch(Exception e)
        {
        }*/
        int scomov[]=new int[2];
        scomov=aspiration(temp,maxdep,previous);
        int mov;
        previous=scomov[0];
        mov=scomov[1];
        /*try{
            PrintWriter out;
            out=new PrintWriter(new BufferedWriter(new FileWriter("Table "+maxdep+""+2+".yash",true)));
            out.println(""+b.hash()+" "+mov+" "+previous);
            out.close();
        }
        catch(Exception e)
        {
        }*/
        return mov;
    }
    static int[] aspiration(Board b,int maxdep,int previous)
    {
        int alpha,beta;
        if(previous==0)
        {
            alpha=-1000;
            beta=1000;
        }
        else
        {
            alpha=previous-100;
            beta=previous+100;
        }
        while(true)
        {
            int scomov[]=new int[2];
            scomov=ABnegamax(b,maxdep,0,alpha,beta);
            if(scomov[0]<=alpha)
            {
                alpha=-1000;
            }
            else if(scomov[0]>=beta)
            {
                beta=1000;
            }
            else
            {
                return scomov;
            }
        }
    }
    static int[] ABnegamax(Board b,int maxdep,int curdep,int alpha,int beta)
    {
        if(b.isGameOver() || curdep==maxdep)
        {
            int scomov[]=new int[2];
            scomov[0]=b.evaluate()+curdep;
            scomov[1]=-1;
            return scomov;
        }
        int bmov;
        bmov=-1;
        int bscore;
        bscore=-1000;
        for(int i=0;i<7;i++)
        {
            if(b.isMove(i))
            {
                b.makeMove(i);
                int rscomov[]=new int[2];
                rscomov=ABnegamax(b,maxdep,curdep+1,-beta,-(int)Math.max(alpha,bscore));
                int cscomov[]=new int[2];
                cscomov[0]=-rscomov[0];
                cscomov[1]=rscomov[1];
                if(cscomov[0]>bscore)
                {
                    bscore=cscomov[0];
                    bmov=i;
                    if(bscore>=beta)
                    {
                        int bscomov[]=new int[2];
                        bscomov[0]=bscore;
                        bscomov[1]=bmov;
                        b.undoMove();
                        return bscomov;
                    }
                }
                b.undoMove();
            }                
        }
        int bscomov[]=new int[2];
        bscomov[0]=bscore;
        bscomov[1]=bmov;
        return bscomov;
    }
}
