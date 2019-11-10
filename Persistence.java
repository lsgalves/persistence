/*
 * The MIT License
 *
 * Copyright 2019 Leonardo Galves.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Class for persisting data in files, data is written to CSV standard.
 *
 * @author LeonardoGalves
 * @version 0.3
 */
public class Persistence {

    private final String PATH;
    private List<List<Object>> db;

    /**
     * Receives the path to the file that will serve as the final database.
     *
     * @param PATH path to the file.
     */
    public Persistence(String PATH) {
        this.PATH = PATH;
    }

    /**
     * Method to load registries from the final database (file) to
     * temporary database.
     */
    public void load() {
        List<List<Object>> dbFile = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(PATH))) {
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                String[] attributes = linha.split(",");

                List<Object> registry = new ArrayList<>();
                registry.addAll(Arrays.asList(attributes));
                dbFile.add(registry);
            }
            scanner.close();
        } catch (FileNotFoundException ex) {
            System.err.println("The database is not found: " + ex.getMessage());
        } finally {
            db = dbFile;
        }
    }

    /**
     *
     * @param i registry index.
     * @return unchanging record relative to the index.
     */
    public List<Object> get(int i) {
        return Collections.unmodifiableList(db.get(i));
    }

    /**
     *
     * @return all registries immutably from the database.
     */
    public List<List<Object>> getAll() {
        return Collections.unmodifiableList(db);
    }

    /**
     * Adds a new registry to the temporary database.
     *
     * @param registry list with the attributes of the object to be added.
     */
    public void add(List<Object> registry) {
        db.add(registry);
    }

    /**
     * Remove a registry in the temporary database.
     *
     * @param i registry index.
     */
    public void remove(int i) {
        db.remove(i);
    }

    /**
     * Updates the registry with the attributes.
     *
     * @param i registry index.
     * @param registry list with the attributes of the object to be added.
     */
    public void update(int i, List<Object> registry) {
        db.remove(i);
        db.add(i, registry);
    }

    /**
     * Saves the temporary database in the file.
     */
    public void save() {
        String[] rows = new String[db.size()];
        for (int i = 0; i < db.size(); i++) {
            rows[i] = "";
            for (int j = 0; j < db.get(i).size(); j++) {
                if (j == db.get(i).size() - 1) {
                    rows[i] += db.get(i).get(j);
                } else {
                    rows[i] += db.get(i).get(j) + ",";
                }
            }
        }

        try (PrintWriter pw = new PrintWriter(PATH)) {

            for (String row : rows) {
                pw.println(row);
            }

            pw.close();
        } catch (FileNotFoundException ex) {
            System.err.println("The database is not found: " + ex.getMessage());
        }
    }

}
