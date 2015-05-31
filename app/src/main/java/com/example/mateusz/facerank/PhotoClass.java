package com.example.mateusz.facerank;

/**
 * Created by Mateusz & Grzegorz on 2015-04-26.
 */
public class PhotoClass implements Comparable<PhotoClass> {

    private String id;
    private int votes;
	private int appearances;
    private float rating;

	public PhotoClass(String id) {
		votes = 0;
		appearances = 0;
		this.id = id;
	}

	public int getAppearances() {
		return appearances;
	}

	public void setAppearances( int appearances ) {
		this.appearances = appearances;
        rating = (float)votes/(float)appearances;
	}

	public int getVotes() {
		return votes;
	}

    public float getRating(){ return rating; }

	public void setVotes( int votes ) {
		this.votes = votes;
        rating = (float)votes/(float)appearances;
	}

	public String getId() {
		return id;
	}

	public void setId( String id ) {
		this.id = id;
	}

    @Override
    public int compareTo(PhotoClass another) {

        if(this.rating > another.rating)
            return -1;
        else if(this.rating < another.rating)
            return 1;
        else if(this.appearances > another.appearances)
                return  -1;
            else
                return  1;

    }
}
