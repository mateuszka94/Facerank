package com.example.mateusz.facerank;

import android.net.Uri;

/**
 * Created by Mateusz & Grzegorz on 2015-04-26.
 */
public class Photo {

    private String id;
    private int votes;
	private int appearances;

	public Photo( String id ) {
		votes = 0;
		appearances = 0;
		this.id = id;
	}

	public int getAppearances() {
		return appearances;
	}

	public void setAppearances( int appearances ) {
		this.appearances = appearances;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes( int votes ) {
		this.votes = votes;
	}

	public String getId() {
		return id;
	}

	public void setId( String id ) {
		this.id = id;
	}
}
