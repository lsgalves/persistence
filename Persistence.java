/*
 * The MIT License
 *
 * Copyright 2019 Leo.
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
 * Classe para persist�ncia de dados em arquivos, os dados s�o gravados no
 * padr�o CSV.
 *
 * @author Leo
 * @version 0.2
 */
public class Persistence {

    private final String PATH;
    private List<List<Object>> db;

    /**
     * Recebe o caminho para o arquivo *.csv (n�o � necess�rios que a extens�o
     * seja .csv) que servir� de base de dados final.
     *
     * @param PATH o caminho para o arquivo.
     */
    public Persistence(String PATH) {
        this.PATH = PATH;
    }

    /**
     *
     * @param i o �ndice do registro a ser buscado.
     * @return o registro imut�vel relativo ao �ndice passado como par�metro.
     */
    public List<Object> get(int i) {
        return Collections.unmodifiableList(db.get(i));
    }

    /**
     *
     * @return todos os registros de forma imut�vel da base de dados.
     */
    public List<List<Object>> getAll() {
        return db;
    }

    /**
     * M�todo para carregar os registros da base de dados final (arquivo) para
     * base de dados tempor�ria.
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
            System.out.println("Warning: " + ex.getMessage());
        } finally {
            db = dbFile;
        }
    }

    /**
     * Adiciona um novo registro na base de dados final e tempor�ria.
     *
     * @param registry uma lista com os atributos do objeto a ser adicionado.
     */
    public void add(List<Object> registry) {
        db.add(registry);
        save();
    }
    
    /**
     * Remove um registro na base de dados final e tempor�ria.
     * 
     * @param i o �ndice do registro a ser removido.
     */
    public void remove(int i) {
        db.remove(i);
        save();
    }

    /**
     * Remove um registro na base de dados final e temporária.
     *
     * @param i o índice do registro a ser removido.
     */
    public void remove(int i) {
        db.remove(i);
        save();
    }

    /**
     * Atualiza os dados com a lista dos novos atributos do registro que foi
     * passado o índice como parâmetro.
     *
     * @param i o índice do registro a ser atualizado.
     * @param registry a lista com os atributos do objeto a ser adicionado.
     */
    public void update(int i, List<Object> registry) {
        db.remove(i);
        db.add(i, registry);
        save();
    }

    /**
     * Salva a base de dados temporária.
     */
    private void save() {
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
            System.out.println("Warning: " + ex.getMessage());
        }
    }

}
