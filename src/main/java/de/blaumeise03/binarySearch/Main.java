/*
 *     Copyright (C) 2020  Blaumeise03 - bluegame61@gmail.com
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as published
 *     by the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.blaumeise03.binarySearch;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    static File f = null;
    static String search;

    public static void main(String[] args) {
        System.out.print("Bitte gebe den Pfad ein: ");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String in = scanner.nextLine();
            f = new File(in);
            if (!f.exists()) {
                f = null;
                System.out.println("Ungültige Eingabe!");
            } else {
                break;
            }
        }
        System.out.print("Bitte gebe den gesuchten Namen ein: ");
        while (scanner.hasNextLine()) {
            String in = scanner.nextLine();
            if (in.equals("")) {
                System.out.println("Ungültige Eingabe!");
            } else {
                search = in.toLowerCase();
                System.out.println("Starte Suche...");
                break;
            }
        }


        //Binary search
        assert f != null;
        try (
                Stream<String> lines = Files.lines(Paths.get(f.getAbsolutePath())).parallel();
        ) {
            int minIndex = 0;
            int current = -1;
            List<String> list = lines.map(String::toLowerCase).collect(Collectors.toList());
            int maxIndex = list.size() - 1;
            System.out.println("Min|Max|Mitte");
            current = (maxIndex - minIndex) / 2;
            while (true) {
                assert current < list.size() && current > 0;
                if(minIndex > maxIndex) {
                    throw new IllegalArgumentException("File does not contain name " + search + "!");
                }
                String c = list.get(current);
                int nextOperation = search.compareTo(c);
                //System.out.println(search + " " + c);
                System.out.println(minIndex + " | " + maxIndex + " | " + current);
                if(nextOperation == 0) {
                    System.out.println("Ergebnis gefunden!");
                    System.out.println("Position: " + current);
                    break;
                } else if (nextOperation < 0) {
                    maxIndex = current - 1;
                    current = minIndex + (maxIndex - minIndex) / 2;
                } else {
                    minIndex = current + 1;
                    current = minIndex + (maxIndex - minIndex) / 2;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
