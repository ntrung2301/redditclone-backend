package com.trungnguyen.model;

public enum VoteType {
	UPVOTE(0), DOWNVOTE(1);
	
	private int direction;
	
	VoteType(int direction) {
	}
	
	public Integer getDirection() {return direction;}
}
