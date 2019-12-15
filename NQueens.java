
// Class: NQueens
//
// Author: Kevin H. Bhimani
// Date: October 18,2017
// With assistance from: Tim Rutledge
//
// License Information:
//   This class is free software; you can redistribute it and/or modify
//   it under the terms of the GNU General Public License as published by
//   the Free Software Foundation.
//
//   This class is distributed in the hope that it will be useful,
//   but WITHOUT ANY WARRANTY; without even the implied warranty of
//   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//   GNU General Public License for more details.

import edu.kzoo.grid.BoundedGrid;
import edu.kzoo.grid.Grid;
import edu.kzoo.grid.Location;
import edu.kzoo.grid.display.GridDisplay;

/**
 * Environment-Based Applications:<br>
 *
 * The NQueens class implements the N Queens problem.
 *
 * @author Kevin H. Bhimani (based on a template provided by Alyce Brady)
 * @version October 18, 2017
 **/

public class NQueens {
	// Instance Variables: Encapsulated data for EACH NQueens problem
	private Grid board;
	private GridDisplay display;
	private int[] queenLocations;

	// constructor

	/**
	 * Constructs an object that solves the N Queens Problem.
	 * 
	 * @param n
	 *            the number of queens to be placed on an <code>n</code> x
	 *            <code>n</code> board
	 * @param d
	 *            an object that knows how to display an <code>n</code> x
	 *            <code>n</code> board and the queens on it
	 **/
	public NQueens(int n, GridDisplay d) {
		board = new BoundedGrid(n, n);
		display = d;
		display.setGrid(board);
		display.showGrid();
	}

	// methods

	/** Returns the number of queens to be placed on the board. **/
	public int numQueens() {
		// Returns the number of columns in the board.
		return this.board.numCols();
	}

	/** Solves (or attempts to solve) the N Queens Problem. **/
	public boolean solve() {

		// this.placeQueen(4);
		return placeQueen(0);
	}

	/**
	 * Attempts to place the <code>q</code>th queen on the board. (Precondition:
	 * <code>0 <= q < numQueens()</code>)
	 * 
	 * @param q
	 *            index of next queen to place
	 **/
	private boolean placeQueen(int q) {
		// Queen q is placed in row q. The only question is
		// which column she will be in. Try them in turn.
		// Whenever we find a column that could work, put her
		// there and see if we can place the rest of the queens.
		/*
		 * Location loc = new Location(q, 4); this.addQueen(loc);
		 * this.display.showGrid(); this.removeQueen(loc); this.display.showGrid();
		 */

		if (q >= this.board.numRows()) {
			return true;
		} else {
			for (int col = 0; col < this.board.numCols(); col++) {
				// Test to see if the current location is valid
				if (this.locationIsOK(new Location(q, col))) {
					// Adds a new queen to the given location
					this.addQueen(new Location(q, col));
					// Redisplay the environment
					this.display.showGrid();
					// tries to put a queen in the next row
					if (this.placeQueen(q + 1)) {
						return true;
					}
					// if we can't put the queen in the next row then we go back to the previous row
					// and remove the piece.
					// This is because we know that the solution won't work for the next row.
					else {
						Location locr = new Location(q, col);
						this.removeQueen(locr);
					}
				}
			}

		}
		return false;
	}

	/**
	 * Determines whether a queen can be placed at the specified location.
	 * 
	 * @param loc
	 *            the location to test
	 **/
	private boolean locationIsOK(Location loc) {
		// Verify that another queen can't attack this location.
		// (Only queens in previous rows have been placed.)

		// goes through the columns of the given ro.
		for (int i = 0; i < loc.col(); i++) {
			Location checkLoc = new Location(loc.row(), i);
			if (!this.board.isEmpty(checkLoc)) {
				// return false if the location is occupied.
				return false;
			}
		}

		// goes through the rows of the given column.
		for (int j = 0; j < loc.row(); j++) {
			Location checkLoc2 = new Location(j, loc.col());
			if (!this.board.isEmpty(checkLoc2)) {
				// return false if the location is occupied.
				return false;
			}
		}

		// sees if there is a queen in the diagonal elements.
		for (int k = 0; k < this.board.numRows(); k++) {
			Location loc1 = new Location(loc.row() - k, loc.col() - k);
			Location loc2 = new Location(loc.row() + k, loc.col() + k);
			Location loc3 = new Location(loc.row() - k, loc.col() + k);
			Location loc4 = new Location(loc.row() + k, loc.col() - k);

			if ((!this.board.isEmpty(loc1) && isOnboard(loc1)) || (!this.board.isEmpty(loc2) && isOnboard(loc2))
					|| (!this.board.isEmpty(loc3) && isOnboard(loc3))
					|| (!this.board.isEmpty(loc4)) && isOnboard(loc4)) {
				return false;

			}
		}

		return true;

	}

	/**
	 * Checks if the location is on the board
	 * 
	 * @param loc
	 *            the location to be checked
	 **/

	private boolean isOnboard(Location loc) {
		if (loc.row() < 0 || loc.col() < 0 || loc.row() > this.board.numRows() - 1
				|| loc.col() > this.board.numCols() - 1) {
			return false;
		}
		return true;
	}

	/**
	 * Adds a queen to the specified location.
	 * 
	 * @param loc
	 *            the location where the queen should be placed
	 **/
	private void addQueen(Location loc) {
		new Queen(board, loc); // queens add themselves to the board
	}

	/**
	 * Removes a queen from the specified location.
	 * 
	 * @param loc
	 *            the location where the queen should be removed
	 **/
	private void removeQueen(Location loc) {
		this.board.remove(loc);
	}

}
