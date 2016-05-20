/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swcguild.dvdlibrary.ops;

import com.swcguild.dvdlibrary.dao.DvdLibraryDAOImpl_RGSK;
import com.swcguild.dvdlibrary.dao.DvdLibraryDao;
import com.swcguild.dvdlibrary.dto.Dvd;
import com.swcguild.dvdlibrary.ui.ConsoleIO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

/**
 *
 * @author apprentice
 */
public class DvdLibraryController {

    DvdLibraryDao dao;
    ConsoleIO console = new ConsoleIO();
    Random r = new Random();

    public DvdLibraryController(DvdLibraryDao dao) {
        this.dao = dao;

    }

    public void run() throws FileNotFoundException, IOException {
        boolean keepRunning = true;

        while (keepRunning) {
            printMenu();
            switch (console.readInt("Enter a choice...", 1, 7)) {
                case 1:
                    addDVD();
                    break;
                case 2:
                    remove();
                    break;
                case 3:
                    listAll();
                    break;
                case 4:
                    findDVD();
                    break;
                case 5:
                    runSearch();
                    break;
                case 6:
                    editDVD();
                    break;
                case 7:
                    keepRunning = false;
                    break;
                default:
                    keepRunning = false;
                    break;
            }

        }
    }

    public void printMenu() {
        console.print("DVD Library\n"
                + "1. add a DVD\n"
                + "2. remove a DVD\n"
                + "3. list all DVDs\n"
                + "4. display a DVD\n"
                + "5. search for DVDs\n"
                + "6. edit a DVD\n"
                + "7. exit");
    }

    public void runSearch() {

        boolean runSearch = true;
        while (runSearch) {
            printSearchMenu();
            switch (console.readInt("What do you want to search for enter 1-3", 1, 3)) {

                case 1:
                    searchByMPAARating();
                    break;

                case 2:
                    searchByStudio();
                    break;

                case 3:
                    runSearch = false;
                    break;

            }
        }
    }

    public void printSearchMenu() {
        console.print("Search menu\n"
                + "1. MPAA Rating\n"
                + "2. Studio\n"
                + "3. Exit\n");
    }

    private void addDVD() {

        String title = console.readString("title:");
        String releaseDate = console.readString("release date:");
        String MPAARating = console.readString("MPAA rating:");
        String director = console.readString("director");
        String studio = console.readString("studio:");
        String note = console.readString("note:");
        Dvd dvdToAdd = new Dvd();

        dvdToAdd.setId(r.nextInt(1000));
        dvdToAdd.setTitle(title);
        dvdToAdd.setReleaseDate(LocalDate.parse(releaseDate));
        dvdToAdd.setMpaaRating(MPAARating);
        dvdToAdd.setDirector(director);
        dvdToAdd.setStudio(studio);
        dvdToAdd.setNote(note);

        dao.add(dvdToAdd);
        console.print("\n" + title + " (" + releaseDate + ") was successfully added to the DVD library.");
    }

    private void listAll() {
        List<Dvd> DVDCollection = dao.listAll();
        for (Dvd d : DVDCollection) {
            console.print("\n" + d.getTitle() + " (" + d.getReleaseDate() + ")");
        }

    }

    private void remove() {
        int id = console.readInt("Enter the id of the DVD to remove:");
        dao.remove(id);
    }

    public void searchByMPAARating() {
        String searchMPAARating = console.readString("What MPAA Rating would you like to search for?");
        List<Dvd> filteredResults = dao.getByRating(searchMPAARating);
        for (Dvd d : filteredResults) {
            console.print(d.getTitle());
        }
    }

    public void searchByStudio() {
        String searchStudio = console.readString("What studio would you like to search for?");
        List<Dvd> filteredResults = dao.getByStudio(searchStudio);
        for (Dvd d : filteredResults) {
            console.print(d.getTitle());
        }
    }

    public void findDVD() {
        List<Dvd> foundDVDs = dao.getByTitle(console.readString("Enter the title to display DVD information."));
        for (Dvd d : foundDVDs) {

            console.print(d.getTitle());
            console.print("Rating: " + d.getMpaaRating());
            console.print("Director: " + d.getDirector());
            console.print("Release Date: " + d.getReleaseDate());
            console.print("Studio: " + d.getStudio());
            console.print("Note: " + d.getNote());
            console.print("ID: " + d.getId());
        }
    }

    public void editDVD() throws FileNotFoundException, IOException {
        String TitleOfDVDToEdit = console.readString("Enter the title of the DVD to edit");

        List<Dvd> ListOfDvdsToEdit = dao.getByTitle(TitleOfDVDToEdit);
        for (Dvd d : ListOfDvdsToEdit) {
            console.print("title: " + d.getTitle());
            console.print("id: " + d.getId());
        }

        int idOfDvdToEdit = console.readInt("Enter the ID of the DVD to edit:");
        Dvd DVDToEdit = null;
        for (Dvd d : ListOfDvdsToEdit) {
            if (d.getId() == idOfDvdToEdit) {
                DVDToEdit = d;
            }
        }

        if (DVDToEdit == null) {
            console.print("That title is not in the library.");
        } else {
            boolean DVDUpdated = false;
            String updateReleaseDate = console.readString("release date (press Enter to keep the current value): " + DVDToEdit.getReleaseDate());
            if (!updateReleaseDate.isEmpty()) {
                DVDToEdit.setReleaseDate(LocalDate.parse(updateReleaseDate));
                DVDUpdated = true;
            }
            String updateMPAARating = console.readString("MPAA rating (press enter to keep the current value): " + DVDToEdit.getMpaaRating());
            if (!updateMPAARating.isEmpty()) {
                DVDToEdit.setMpaaRating(updateMPAARating);
                DVDUpdated = true;
            }
            String updateDirector = console.readString("director (press enter to keep the current value): " + DVDToEdit.getDirector());
            if (!updateDirector.isEmpty()) {
                DVDToEdit.setDirector(updateDirector);
                DVDUpdated = true;
            }
            String updateStudio = console.readString("studio (press enter to keep the current value): " + DVDToEdit.getStudio());
            if (!updateStudio.isEmpty()) {
                DVDToEdit.setStudio(updateStudio);
                DVDUpdated = true;
            }
            String updateNote = console.readString("your note (press Enter to keep the current value): " + DVDToEdit.getNote());
            if (!updateNote.isEmpty()) {
                DVDToEdit.setNote(updateNote);
                DVDUpdated = true;
            }

            if (DVDUpdated) {
                DvdLibraryDAOImpl_RGSK daoCheat = new DvdLibraryDAOImpl_RGSK(false);
                daoCheat.writeDVDLibrary();
                console.print("DVD has been updated.");
            } else {
                console.print("Nothing has been updated.");
            }
        }
    }
}
