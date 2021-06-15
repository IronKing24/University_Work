package manar.turing;
/**
 * class Tape is used to create an empty cell
 * using it's constructor and then using it's 
 * different methods to manipulate the created 
 * and already existing cells
 * like 
 * @getCurrentCell(), @getContent, @setContent(@param ch),
 * @moveLeft(), @moveRight() and getTapeContents()
 * 
 * @author Juned Akhtar Sheikh
 * 
 */

public class Tape 
{

	private Cell currentCell; 
	/**
	 * default constructor to initialize a cell 
	 * with a blank space
	 */
	public Tape() 
	{ 
		Cell newCell = new Cell();
		newCell.content = ' ';
		newCell.prev = null;
		newCell.next = null;
		currentCell = newCell;
	}

	/**
	 * this method returns current cell i.e the pointer
	 * to current cell
	 * @return current cell pointer
	 */
	public Cell getCurrentCell() 
	{ 
		return currentCell;
	}

	/**
	 * this method is used to see
	 * the content stored at the location
	 * where currentCell is pointing
	 * @return content at currentCell
	 */
	public char getContent() {
		return currentCell.content;
	}

	/**
	 * this function is used to assign 
	 * content to a cell at location currentCell
	 * @param ch content inserted by user
	 */
	public void setContent(char ch) { 
		currentCell.content = ch;
	}

	/**
	 * this method moves the currentCell pointer to 
	 * one index left along the Tape and if there is 
	 * not already a cell ready to be pointed at
	 * then it creates a new cell in teh left side
	 * and then starts pointing to it
	 */
	public void moveLeft() { 
		if (currentCell.prev == null)
		{
			Cell newCell = new Cell();
			newCell.content = ' ';
			newCell.prev = null;
			newCell.next = currentCell;
			currentCell.prev = newCell;
		}
		currentCell = currentCell.prev;
	}

	/**
	 * this method moves the currentCell pointer to 
	 * one index Right along the Tape and if there is
	 * not already a cell ready to be pointed at
	 * then it creates a new cell in the right side
	 * and then starts pointing to it
	 */
	public void moveRight() {
		if (currentCell.next == null) {
			Cell newCell = new Cell();
			newCell.content = ' ';
			newCell.next = null;
			newCell.prev = currentCell;
			currentCell.next = newCell;
		}
		currentCell = currentCell.next;
	}

	/**
	 * this method is used to see everything that's 
	 * stored in the tape or so we say Turing Machine
	 * 
	 * @return all the elements or content stored in tape
	 * starting from left to right
	 */
	public String getTapeContents() { 
		Cell pointer = currentCell;
		while (pointer.prev != null)
			pointer = pointer.prev;
		String strContent = "";
		while (pointer != null) {
			strContent += pointer.content;
			pointer = pointer.next;
		}
		strContent = strContent.trim(); //trim() is used eradicate leading and trailing spaces
		return strContent;
	}
}