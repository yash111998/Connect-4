/**
 * Board Class which reepresents a board of connect 4
 * Manages the movemaking,move reversing,score generation and #code generation
 * @author Yash
 * @version 2.3
 */

import java.util.ArrayList;
public class Board 
{
    int board[][]=new int[6][7];
    int currentPlayer;
    //1.Human
    //2.Computer
    int currentmove=-1;
    int prev[]=new int[2];
    ArrayList<Move> moves=new ArrayList<>(0);
    public Board()
    {
        for(int i=0;i<6;i++)
        {
            for(int j=0;j<7;j++)
            {
                board[i][j]=0;
            }
        }
        prev[0]=-1;
        prev[1]=-1;
        currentPlayer=1;
    }
    boolean isGameOver()
    {
        if(moves.isEmpty())
        {
            return false;
        }
        if(isWin(prev[0],prev[1],Other(currentPlayer)))
        {
            return true;
        }
        else
        {
            return moves.size()==42;
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
    boolean isWin(Move m,int k)
    {
        int row=m.row;
        int col=m.col;
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
        if(isWin(prev[0],prev[1],Other(currentPlayer)))
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
        return a>=0 && a<=6 && board[0][a]==0;
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
        currentmove++;
        Move m=new Move(i-1,a);
        moves.add(m);
        prev[0]=i-1;
        prev[1]=a;
        currentPlayer=Other(currentPlayer);
    }
    void undoMove()
    {
        Move m=moves.get(currentmove);
        board[m.row][m.col]=0;
        moves.remove(m);
        currentmove--;
        m=moves.get(currentmove);
        prev[0]=m.row;
        prev[1]=m.col;
        currentPlayer=Other(currentPlayer);
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
}
