package info.gridworld.maze;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.*;
import sun.util.logging.resources.logging;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import javax.swing.JOptionPane;

/**
 * A <code>MazeBug</code> can find its way in a maze. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class MazeBug extends Bug {
	public Location next;
	public Location aim; 
	public boolean ifInit = false;
	public boolean isEnd = false;
	public Stack<ArrayList<Location>> stackLoc = new Stack<ArrayList<Location>>();
	public Integer stepCount = 0;
	boolean hasShown = false;//final message has been shown
	/**
	 * Constructs a box bug that traces a square of a given side length
	 * 
	 * @param length
	 *            the side length
	 */
	public MazeBug() {
		setColor(Color.GREEN);
	}
	/**
	 * Moves to the next location of the square.
	 */
	public void act() {
		boolean willMove = canMove();
		if(!ifInit) {
			ifInit = true;
			init();
		}
		
		if(aim.getRow() == this.getLocation().getRow() && 
					aim.getCol() == this.getLocation().getCol() ) {
			isEnd = true;
		}
		
		if (isEnd == true) {
		//to show step count when reach the goal		
			if (hasShown == false) {
				String msg = stepCount.toString() + " steps";
				JOptionPane.showMessageDialog(null, msg);
				hasShown = true;
			}
		} else {
			if(canMove() ) {
				move();
				stepCount++;
			}
		} 
	}

	/**
	 * Find all positions that can be move to.
	 * 
	 * @param loc
	 *            the location to detect.
	 * @return List of positions.
	 */
	public ArrayList<Location> getValid(Location loc) {
		Grid<Actor> gr = getGrid();
		if (gr == null)
			return null;
		ArrayList<Location> valid = new ArrayList<Location>();
		return valid;
	}

	/**
	 * Tests whether this bug can move forward into a location that is empty or
	 * contains a flower.
	 * 
	 * @return true if this bug can move.
	 */
	public boolean canMove() {
		if( stackLoc.isEmpty() ) {
			return false;
		}
		else {
			return true;
		}
	}
	/**
	 * Moves the bug forward, putting a flower into the location it previously
	 * occupied.
	 */
	public void move() {
		Grid<Actor> gr = getGrid();
		if (gr == null)
			return;
		if(stackLoc.peek().size() != 1 ) {
			moveForward();
		}
		else {
			moveBack();
		}
		moveTo(next);
	}
	
	void moveForward() {
		ArrayList<Location> nextArrList = new ArrayList<>();
		ArrayList<Location> locArr = stackLoc.pop();
		next = locArr.get(locArr.size() - 1);
		locArr.remove(locArr.size() - 1);
		nextArrList.add(next);
		Location []adjLocArr = getLocationArr(next);
		for(int loop = 0 ; loop < adjLocArr.length ; loop++) {
			if( (adjLocArr[loop].getRow() != this.getLocation().getRow() 
					|| adjLocArr[loop].getCol() != this.getLocation().getCol()) 
					&&!(getGrid().get( adjLocArr[loop]) instanceof Rock) ) {
				nextArrList.add(adjLocArr[loop]);
			}
			else if(adjLocArr[loop].getRow() == aim.getRow()
					&& adjLocArr[loop].getCol() == aim.getCol() ) {
				nextArrList.add(aim);
			}
		}
		stackLoc.push(locArr);
		stackLoc.push(nextArrList);
	}
	
	//通过权重得到数组。
	Location []getLocationArr(Location next){
		int nextRow = next.getRow() , nextCol = next.getCol() ;
		Location upLoc = new Location(nextRow - 1, nextCol ) , 
				leftLoc = new Location(nextRow , nextCol - 1 ) , 
				downLoc = new Location(nextRow + 1 , nextCol ) ,
				rightLoc= new Location(nextRow , nextCol + 1 ) ;
		int rowWeight = aim.getRow() - this.getLocation().getRow();
		int colWeight = aim.getCol() - this.getLocation().getCol();
		int []directionWeight = {rowWeight , -rowWeight ,  colWeight , -colWeight};
		//weight: up , down , right , left
		Location []adjLocArr = {downLoc , upLoc , rightLoc , leftLoc};
		for(int loop0 = 0 ; loop0 < adjLocArr.length ; loop0++) {
			System.out.println(directionWeight[loop0] );
		}
		//按照权重给adjLocArr排序，权重大的排在后面。
		for(int loop0 = 0 ; loop0 < adjLocArr.length ; loop0++) {
			
			for(int loop1 = loop0 + 1 ; loop1 < adjLocArr.length ; loop1++) {
				if(directionWeight[loop1] < directionWeight[loop1 - 1]) {
					int tempNum = directionWeight[loop1];
					Location tempLoction = adjLocArr[loop1];
					
					directionWeight[loop1] = directionWeight[loop1 - 1 ];
					directionWeight[loop1 - 1 ] = tempNum;
					
					adjLocArr[loop1] = adjLocArr[loop1 - 1];
					adjLocArr[loop1 - 1] = tempLoction;
				}
			}
		}
		
		for(int loop0 = 0 ; loop0 < adjLocArr.length ; loop0++) {
			System.out.println(adjLocArr[loop0].getRow() + " " + adjLocArr[loop0].getCol() );
		}
		return adjLocArr;
	}
	
	void moveBack() {
		stackLoc.pop();
		if(stackLoc.isEmpty() ) {
			return;
		}
		else {
			next = stackLoc.peek().get(0);
		}
	}
	
	private void init() {
		int nowRow , nowCol;
		aim = getAim();
		Location nowLoc = this.getLocation();
		ArrayList<Location> nowArrList = new ArrayList<>();
		nowArrList.add(nowLoc);
		Location []adjLocArr = getLocationArr(nowLoc);
		for(int loop = 0 ; loop < adjLocArr.length ; loop++) {
			if(! (getGrid().get( adjLocArr[loop]) instanceof Rock) ) {
				nowArrList.add(adjLocArr[loop]);
			}
			else if(adjLocArr[loop].getRow() == aim.getRow()
					&& adjLocArr[loop].getCol() == aim.getCol() ) {
				nowArrList.add(aim);
			}
		}
		stackLoc.push(nowArrList);
		
	} 

	private Location getAim() {
		Location aim = null;
		ArrayList<Location> locArr = getGrid().getOccupiedLocations();
		for(int loop = 0 ; loop < locArr.size() ; loop++) {
			if( getGrid().get(locArr.get(loop) ) instanceof Rock 
					&& getGrid().get(locArr.get(loop)).getColor().getRed() == 255){
				aim = locArr.get(loop);
			}
			
		}
		return aim;
	}
}
