/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.dvdlibrary.dao;

import com.swcguild.dvdlibrary.dto.Dvd;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author apprentice
 */
public class DvdLibraryDAOImplTest {

    LocalDate date;
    DvdLibraryDAOImpl_RGSK testObj = new DvdLibraryDAOImpl_RGSK(true);

    public DvdLibraryDAOImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    @Test
    public void addDVD() throws FileNotFoundException {
        Dvd testDVD = new Dvd();

        testObj.add(testDVD);
        Assert.assertEquals(1, testObj.DVDCollection.size());

    }

    @Test
    public void addOneDvd() {
        Dvd testDVD = new Dvd();
        testObj.add(testDVD);
        Assert.assertNotNull(testObj.DVDCollection);
    }

    @Test
    public void getallDVDEmpty() {
        Assert.assertEquals(0, testObj.DVDCollection.size());
    }

    @Test
    public void addTwoDVDGetAll() {
        Dvd testDVD = new Dvd();
        Dvd testDVD2 = new Dvd();
        testObj.add(testDVD);
        testObj.add(testDVD2);

        Assert.assertEquals(2, testObj.DVDCollection.size());

    }

    @Test
    public void addTwoDVDRemoveOne() {
        Dvd testDVD = new Dvd();
        Dvd testDVD2 = new Dvd();
        testObj.add(testDVD);
        testObj.add(testDVD2);
        testDVD.setId(1000);
        testObj.remove(1000);

        Assert.assertEquals(1, testObj.DVDCollection.size());

    }

    @Test
    public void addTwoDVDRemoveTwo() {
        Dvd testDVD = new Dvd();
        Dvd testDVD2 = new Dvd();
        testObj.add(testDVD);
        testObj.add(testDVD2);
        testDVD.setId(1000);
        testObj.remove(1000);
        testDVD2.setId(2000);
        testObj.remove(2000);

        Assert.assertEquals(0, testObj.DVDCollection.size());

    }

    @Test
    public void addTwoDVDRemoveThree() {
        Dvd testDVD = new Dvd();
        Dvd testDVD2 = new Dvd();
        testObj.add(testDVD);
        testObj.add(testDVD2);
        testDVD.setId(1000);
        testObj.remove(1000);
        testDVD2.setId(2000);
        testObj.remove(2000);
        testObj.remove(3000);

        Assert.assertEquals(0, testObj.DVDCollection.size());

    }

    @Test
    public void searchForTitle() {
        Dvd testDVD = new Dvd();
        Dvd testDVD2 = new Dvd();
        testDVD.setTitle("Jaws");
        testDVD2.setTitle("ET");
        testObj.add(testDVD);
        testObj.add(testDVD2);

        List<Dvd> returnedDVDList = testObj.getByTitle("Jaws");
        Assert.assertEquals(1, returnedDVDList.size());
    }

    @Test
    public void searchForTitleNotThere() {
        Dvd testDVD = new Dvd();
        Dvd testDVD2 = new Dvd();
        testDVD.setTitle("Jaws");
        testDVD2.setTitle("ET");
        testObj.add(testDVD);
        testObj.add(testDVD2);

        List<Dvd> returnedDVDList = testObj.getByTitle("Rene and Solomon Love Java!");
        Assert.assertEquals(0, returnedDVDList.size());
    }

    @Test
    public void searchForRating() {
        Dvd testDVD = new Dvd();
        Dvd testDVD2 = new Dvd();
        testDVD.setMpaaRating("PG");
        testDVD2.setMpaaRating("G");
        testObj.add(testDVD);
        testObj.add(testDVD2);

        List<Dvd> returnedDVDList = testObj.getByRating("G");
        Assert.assertEquals(1, returnedDVDList.size());
    }
}
