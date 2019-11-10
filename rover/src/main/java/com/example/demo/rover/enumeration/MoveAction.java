package com.example.demo.rover.enumeration;

public enum MoveAction {
	M,R,L;

	public static boolean checkValid(String str) {
	    for (MoveAction me : MoveAction.values()) {
	        if (me.name().equalsIgnoreCase(str))
	            return true;
	    }
	    return false;
	}
}
