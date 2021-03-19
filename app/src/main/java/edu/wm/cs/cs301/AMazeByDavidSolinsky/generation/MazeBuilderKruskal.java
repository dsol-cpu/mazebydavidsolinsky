package edu.wm.cs.cs301.AMazeByDavidSolinsky.generation;

import java.util.ArrayList;

public class MazeBuilderKruskal extends MazeBuilder implements Runnable {
	
	public MazeBuilderKruskal() {
		super();
		System.out.println("MazeBuilderKruskal uses Kruskal's algorithm to generate maze.");
	}

	public MazeBuilderKruskal(boolean det) {
		super(det);
		System.out.println("MazeBuilderKruskal uses Kruskal's algorithm to generate maze.");
	}

	@Override
	protected void generatePathways() {
		int cells[][] = new int[width][height];
		final ArrayList<Wallboard> candidates = new ArrayList<Wallboard>();
		// create an initial list of all wallboards that could be removed
		initializeWallboards(candidates);
		//populate a set of roomnumbers
		cells = populateRooms(cells);

		Wallboard curWallboard;
		// we need to consider each candidate wallboard and consider it only once
		while (!candidates.isEmpty()) {
			// in order to have a randomized algorithm,
			// we randomly select and extract a wallboard from our candidate set
			// this also reduces the set to make sure we terminate the loop
			curWallboard = extractWallboardFromCandidateSetRandomly(candidates);
			// check if wallboard leads to a new cell that is not connected to the spanning
			// tree yet
			int x = curWallboard.getX();
			int y = curWallboard.getY();

			int neighborX = curWallboard.getNeighborX();
			int neighborY = curWallboard.getNeighborY();
			int tempNeighbor = cells[neighborX][neighborY];
			if (floorplan.canTearDown(curWallboard)) {			
				if (cells[x][y] != tempNeighbor) {
				
				floorplan.deleteWallboard(curWallboard);
				
				if (tempNeighbor != cells[x][y])
					for (int i = 0; i < width; i++) {
						for (int j = 0; j < height; j++) {
							if (cells[i][j] == tempNeighbor)
								cells[i][j] = cells[x][y];
						}
					}
				
				// note that each wallboard can get added at most once. This is important for
				// termination and efficiency
			}
		}
	}
	}

	/**
	 * Pick a random position in the list of candidates, remove the candidate from
	 * the list and return it
	 * 
	 * @param candidates
	 * @return candidate from the list, randomly chosen
	 */
	private Wallboard extractWallboardFromCandidateSetRandomly(final ArrayList<Wallboard> candidates) {
		return candidates.remove(random.nextIntWithinInterval(0, candidates.size() - 1));
	}

	/**
	 * Initializes a floorplan with wallboards
	 * 
	 * @param ArrayList of wallboards
	 */
	private void initializeWallboards(ArrayList<Wallboard> wallboards) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Wallboard wallboard = new Wallboard(x, y, CardinalDirection.East) ;
				for (CardinalDirection cd : CardinalDirection.values()) {
					wallboard.setLocationDirection(x, y, cd);
					if (floorplan.canTearDown(wallboard))
					{
						wallboards.add(new Wallboard(x, y, cd));
					}
				}
			}
		}
	}	
	/*
	 * populate 2D array of room numbers
	 * @param 2D array
	 */
	private int[][] populateRooms(int[][] cells) {
		int cellnum = 0;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				cells[x][y] = cellnum;
				cellnum++;
			}
		}
		return cells;
	}
}