package com.example.demo.rover.enumeration;

public enum Direction {
	W, N, E, S;

	public static boolean checkValid(String str) {
		for (Direction me : Direction.values()) {
			if (me.name().equalsIgnoreCase(str))
				return true;
		}
		return false;
	}

	public Direction turn(MoveAction moveAction) {
		int ordinal = this.ordinal();
		if (moveAction == MoveAction.L) {
			if (ordinal == 0)
				ordinal = 3;
			else
				ordinal--;
		} else if (moveAction == MoveAction.R) {
			if (ordinal == 3)
				ordinal = 0;
			else
				ordinal++;
		}
		return Direction.values()[ordinal];
	}


}