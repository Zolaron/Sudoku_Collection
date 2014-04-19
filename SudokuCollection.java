public class SudokuCollection{
/*Sudoku collection program by Dominic Harper*/

	public static void main (String[] args) {
		/*test variable setup, most would be contained in objects or defined by menu choices or object(s) */
		int gridSize = 9; /* **would be an object variable, this is a test number, normally selected from menu*/
		/*public int counter;  **only used for switch, may not be needed*/
		String type, starter, completion; 
		type = "Sudoku"; /* **test value, will be used to select the class or object to use, a number might be better*/
		boolean hasBlocks = true, hasDiags = false;  /* **test values would be set in class constructor*/
		String[] rowRef, colRef, blockRef, diagRef;
		
		rowRef = new String[gridSize];
		colRef = new String[gridSize];
		blockRef = new String[gridSize];
		diagRef = new String[2];
		
		/* **these would be set in constructor though compiler gives errors if they are not initilised */
		
		int rowTracker = 0, blockNum = 0, blockTrack = 0, blockRow = 0;
		/* **how many of these could be held in objects?*/
				
		/*grid creation, for sudoku puzzles that do not include cages, cages would have to be made separately*/
		starter = "A3B7A1B9B428A4A1692D87B957B31D6563A7A495B3B2A8B3A"; /* **test starter (from daily mirror 22/10/2013) will need to be read from store file*/
		
		/* **in case string replacement doesn't work
		counter = 0; //or starter.length()
		do {  //or for (int i = 0; i < counter [starter.length]; i++)or for (char c : starter.toCharArray()) which might be better or remove need for counter.
			switch (starter.charAt(counter)) { //or starter.charAt(i) or c
				case "A" : 
					completion += "00";
					break;
				case "B" : 
					completion += "000";
					break;
				case "C" : 
					completion += "0000";
					break;	
				case "D" : 
					completion += "00000";
					break;
				case "E" : 
					completion += "000000";
					break;
				case "F" : 
					completion += "0000000";
					break;
				default : 
					completion += starter.charAt(counter);
					break;	
				}
			counter ++;
		} while (counter < starter.length());
		*/
		
		/* completion creation as string char replacement, **if this works */
		completion = starter;
		completion = completion.replace("A", "00");
		completion = completion.replace("B", "000");
		completion = completion.replace("C", "0000");
		completion = completion.replace("D", "00000");
		completion = completion.replace("E", "000000");
		completion = completion.replace("F", "0000000");
		
		//check starter string produces valid completion string
		if (completion.length() != (gridSize * gridSize)) {
			System.out.println("Starting string is invalid");
		}
		System.out.println("completion string at start is :" + completion); //debug line - grid printout would happen about here.

		/*reference array creations*/
		/*rows and columns first as these are always present*/
		for (int i = 0; i < gridSize; i++) {
			rowRef[i]="";
			colRef[i]="";
			for (int rowPos = 0; rowPos < gridSize; rowPos++) {
				if (rowTracker < 10) {
					rowRef[i] += "0"; //to add a '0' so all cell references are in 2 digit format
				}
				rowRef[i] += Integer.toString(rowTracker);
				rowTracker++;
			}
			System.out.println("row " + i + " is :" + rowRef[i]); //debug line
			
			for (int colPos = 0; colPos <gridSize; colPos++) {
				if (((gridSize * colPos) + i) < 10) { 
					colRef[i] += "0"; //to add a '0' so all cell references are in 2 digit format
				}
				colRef[i] += Integer.toString((gridSize * colPos) + i);
			}
			System.out.println("column " + i + " is :" + colRef[i]); //debug line
		}
		
		/*blocks if puzzle type includes them*/
		if (hasBlocks) {
			/*set block height and widths; since blocks are only present in square or rectangle numbers only 4 possibilities (up to size 9)*/
			int blockW = 0, blockH = 0;
			switch (gridSize) {
				case 4 :
					blockW = 2;
					blockH = 2;
					break;
				case 6 :
					blockW = 3;
					blockH = 2;
					break;
				case 8 :
					blockW = 4;
					blockH = 2;
					break;
				case 9 :
					blockW = 3;
					blockH = 3;
					break;	
				default : 
					System.out.println("This size of grid should not have Blocks"); //oops!
					break;
			}
			/*now use width and height to create array*/
			for (int i = 0; i < gridSize; i++) {
				blockRef[i]="";
				if (i % blockH == 0) {
					blockTrack = gridSize * i;
					blockRow = 0;
				}
				for (int hPos = 0; hPos < blockH; hPos++){
					for (int wPos = 0; wPos < blockW; wPos++){
						blockNum = (wPos + (gridSize * hPos) + (blockRow * blockW) + blockTrack); 
						/*work out based on current hpos and wpos which cell should be added to string*/
						
						if (blockNum < 10) { 
							blockRef[i] += "0"; //to add a '0' so all cell references are in 2 digit format
						}				
						blockRef[i] += Integer.toString(blockNum);
					}
				}
				blockRow++;
				System.out.println("block " + i + " is :" + blockRef[i]); //debug line
			}
		
		}
		
		/*now diagonals if puzzle type includes them. 
		as there are only ever 2 diags it is easier to just do each array at same time rather than looping through them*/
		if (hasDiags) {

			diagRef[0]=""; 
			diagRef[1]=""; 
			
			for (int diagPos = 0; diagPos < gridSize; diagPos++) {
				diagRef[0] = Integer.toString((gridSize + 1 ) * diagPos);
				diagRef[1] = Integer.toString((gridSize - 1) * (diagPos + 1));
			}
			System.out.println("1st diagonal is :" + diagRef[0] + "; second diagonal is :" + diagRef[1]); //debug line
		}

		/*completion checker part (should be its own method)*/
		/*test completion string*/
		//completion = "453612798 176598234 289743516 925364187 861957423 347281965 632175849 514839672 798426351" //spaces should be removed before testing
		int numFound, cellNum;
		String errorStr = "", numFndStr;
		
		//check if any blanks
		if (completion.indexOf("0") >= 0){
			errorStr += "there are 1 or more blank spaces;"; //**for gui change to cell shading. 
		}
		//check numbers in rows are correct
		for (int row = 0; row < gridSize; row++) {
			for (int n = 1; n <= gridSize; n++) {
				numFound = 0;
				numFndStr = "";
				for (int cell = 0; cell < gridSize; cell++) {
					cellNum = getCellNum(rowRef[row], cell);
					if (completion.charAt(cellNum) == n) {
						numFound ++;
						numFndStr += (cellNum + ","); //**adding int to string ?
					}
				}
				if (numFound == 0){ 
					errorStr += ("number " + n + " not found in row " + (row + 1) + ";");
				}
				else if (numFound > 1) {
					errorStr += ("number " + n + " duplicated in row " + (row + 1) + "in cells " + numFndStr + ";"); 
					//**for gui change to number higlighting. also, add a check against expanded starting string to see if any are definitely correct.
				}
			}
		}
		
		//check numbers in columns are correct
		for (int col = 0; col < gridSize; col++) {
			for (int n = 1; n <= gridSize; n++) {
				numFound = 0;
				numFndStr = "";
				for (int cell = 0; cell < gridSize; cell++) {
					cellNum = getCellNum(colRef[col], cell);
					if (completion.charAt(cellNum) == n) {
						numFound ++;
						numFndStr += (cellNum + ","); //**adding int to string ?
					}
				}
				if (numFound == 0){ 
					errorStr += ("number " + n + " not found in column " + (col + 1) + ";");
				}
				else if (numFound > 1) {
					errorStr += ("number " + n + " is duplicated in column " + (col + 1) + "in cells " + numFndStr + ";"); 
					//**for gui change to number higlighting. also, add a check against expanded starting string to see if any are definitely correct.
				}
			}
		}
		
		//check numbers in blocks are correct if blocks are present
		if (hasBlocks) {
			for (int block = 0; block < gridSize; block++) {
				for (int n = 1; n <= gridSize; n++) {
					numFound = 0;
					numFndStr = "";
					for (int cell = 0; cell < gridSize; cell++) {
						cellNum = getCellNum(blockRef[block], cell);
						if (completion.charAt(cellNum) == n) {
							numFound ++;
							numFndStr += (cellNum + ","); //**adding int to string ?
						}
					}
					if (numFound == 0){ 
						errorStr += ("number " + n + " not found in block " + (block + 1) + ";");
					}
					else if (numFound > 1) {
						errorStr += ("number " + n + " duplicated in block " + (block + 1) + "in cells " + numFndStr + ";"); 
						//**for gui change to number higlighting. also, add a check against expanded starting string to see if any are definitely correct.
					}
				}
			}
		}
		
		//check numbers in diagonals are correct if diagonals are present
		if (hasDiags) {
			for (int diag = 0; diag < 2; diag++) {
				for (int n = 1; n <= gridSize; n++) {
					numFound = 0;
					numFndStr = "";
					for (int cell = 0; cell < gridSize; cell++) {
						cellNum = getCellNum(diagRef[diag], cell);
						if (completion.charAt(cellNum) == n) {
							numFound ++;
							numFndStr += (cellNum + ","); //**adding int to string ?
						}
					}
					if (numFound == 0){ 
						errorStr += ("number " + n + " not found in diagonal " + (diag + 1) + ";");
					}
					else if (numFound > 1) {
						errorStr += ("number " + n + " duplicated in diagonal " + (diag + 1) + "in cells " + numFndStr + ";"); 
						//**for gui change to number higlighting. also, add a check against expanded starting string to see if any are definitely correct.
					}
				}
			}
		}
		
		//print errors ** this would change for gui
		if (errorStr.length() > 0 ){
			System.out.println ("Following errors were encountered: " + errorStr);
		}
		else {
			System.out.println ("Congratulations, you have completed this puzzle!");
		}
				
	} //end main 

	public static int getCellNum(String refStr, int pos ) {
		/*method to create a 2 digit number from chars in the ref string to be used to get number in completion string or potential array. */
		String cellRef = "";
		
		pos *= 2;
		cellRef += refStr.charAt(pos);
		cellRef += refStr.charAt(pos+1);
		return Integer.valueOf(cellRef);
	} // end getCellNum

} //end sudokucollection
