/**
 * The Move Class
 * Contains Details of a move. 
 * @author Yash
 * @version 2.3
 */
public class Move
{
    int row;//Row in which move was made
    int col;//Column in which move was made
    /**
     * Default Constructor for objects of class Move
     * Initialises move with illegal value of -1
     */
    public Move()
    {
        this(-1,-1);
    }
    /**
     * Parameterised Constructor for objects of class Move
     * Initialises move with values of row and column
     * @param r Row
     * @param c Column
     */
    public Move(int r,int c)
    {
        row=r;
        col=c;
    }
}

