package com.google.gwt.artvan.server;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class ArtTest {
	
	static Art myArtObject =  null;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		myArtObject= new Art(47.1, 129.2, "tetName", "http://art-van.appspot.com",
				"this is a test title");

		
	}

	@Test
	public void testArt() {
		assertTrue(true);
		assertTrue(myArtObject.getLat() == 47.1);
		assertTrue(myArtObject.getLong() == 129.2);
		assertTrue(myArtObject.getName().equalsIgnoreCase("tetName"));
		assertTrue(myArtObject.getURL().equalsIgnoreCase("http://art-van.appspot.com"));
		assertTrue(myArtObject.getTitle().equalsIgnoreCase("this is a test title"));
		assertTrue(myArtObject.getVisits()==0);
		assertTrue(myArtObject.getAvgRating()==0.0);
		myArtObject.addVisit();
		assertTrue(myArtObject.getVisits()==1);
		
	}

	@Test
	public void testArtDoubleDoubleStringStringStringInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testDistance() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetAvgRating() {
		fail("Not yet implemented");
	}

	@Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testArtArtInformation() {
		fail("Not yet implemented");
	}

}
