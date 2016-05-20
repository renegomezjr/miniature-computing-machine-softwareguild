/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.dvdlibrary.dao;

import com.swcguild.dvdlibrary.dao.DvdLibraryDao;
import com.swcguild.dvdlibrary.dto.Dvd;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rene Gomez & Solomon Kim
 */
public class DvdLibraryDAOImpl_RGSK implements DvdLibraryDao {

    ArrayList<Dvd> DVDCollection = new ArrayList<>();
    private static final String DVD_LIBRARY = "DVDLibrary.txt";
    private static final String DELIMITER = "::";
    boolean testMode;

    public DvdLibraryDAOImpl_RGSK() {
        testMode = false;
        if (!testMode) {
            try {
                loadDVDLibrary();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(DvdLibraryDAOImpl_RGSK.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public DvdLibraryDAOImpl_RGSK(boolean testMode) {//use this for junit tests
        this.testMode = testMode;
        if (!testMode) {
            try {
                loadDVDLibrary();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(DvdLibraryDAOImpl_RGSK.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void add(Dvd dvd) {
        DVDCollection.add(dvd);
        try {
            writeDVDLibrary();
        } catch (IOException ex) {
            System.out.println("Could not write to file.");
        }
    }

    @Override
    public List<Dvd> listAll() {
        return DVDCollection;
    }

    @Override
    public void remove(int id) {
        for (Dvd d : DVDCollection) {
            if (d.getId() == id) {
                DVDCollection.remove(d);
                break;
            }
        }
        if (!testMode) {
            try {
                writeDVDLibrary();
            } catch (IOException ex) {
                System.out.println("Could not write to file.");
            }
        }
    }

    private void loadDVDLibrary() throws FileNotFoundException {
        Scanner sc = new Scanner(new BufferedReader(new FileReader(DVD_LIBRARY)));

        String recordLine = "";

        while (sc.hasNextLine()) {
            recordLine = sc.nextLine();

            String[] recordProperties = recordLine.split(DELIMITER);
            if (recordProperties.length != 7) {
                continue;
            }
            Dvd dvdToAdd = new Dvd();
            dvdToAdd.setId(Integer.parseInt(recordProperties[0]));
            dvdToAdd.setTitle(recordProperties[1]);
            dvdToAdd.setReleaseDate(LocalDate.parse(recordProperties[2]));
            dvdToAdd.setMpaaRating(recordProperties[3]);
            dvdToAdd.setDirector(recordProperties[4]);
            dvdToAdd.setStudio(recordProperties[5]);
            dvdToAdd.setNote(recordProperties[6]);
            DVDCollection.add(dvdToAdd);

        }
        sc.close();
    }

    public void writeDVDLibrary() throws IOException {
        if (!testMode) {
            PrintWriter writer = new PrintWriter(new FileWriter(DVD_LIBRARY));

            for (Dvd d : DVDCollection) {
                writer.println(d.getId() + DELIMITER + d.getTitle() + DELIMITER + d.getReleaseDate() + DELIMITER + d.getMpaaRating() + DELIMITER + d.getDirector() + DELIMITER + d.getStudio() + DELIMITER + d.getNote());
            }

            writer.flush();
            writer.close();

        }
    }

    @Override
    public Dvd getById(int id) {
        for (Dvd d : DVDCollection) {
            if (d.getId() == id) {
                return d;
            }
        }
        return null;
    }

    @Override
    public List<Dvd> getByTitle(String title) {
        ArrayList<Dvd> dvdList = new ArrayList<>();
        for (Dvd d : DVDCollection) {
            if (d.getTitle().equalsIgnoreCase(title)) {

                dvdList.add(d);
            }
        }
        return dvdList;
    }

    @Override
    public List<Dvd> getByRating(String rating) {
        ArrayList<Dvd> dvdList = new ArrayList<>();
        for (Dvd d : DVDCollection) {
            if (d.getMpaaRating().equalsIgnoreCase(rating)) {
                dvdList.add(d);
            }
        }
        return dvdList;
    }

    @Override
    public List<Dvd> getByStudio(String studio) {
        ArrayList<Dvd> dvdList = new ArrayList<>();
        for (Dvd d : DVDCollection) {
            if (d.getStudio().equalsIgnoreCase(studio)) {
                dvdList.add(d);
            }
        }
        return dvdList;
    }
}
