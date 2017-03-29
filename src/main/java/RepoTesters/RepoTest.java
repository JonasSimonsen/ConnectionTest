/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RepoTesters;

import Repoes.IRepo;
import Repoes.MySQLRepo;
import Repoes.Neo4JRepo;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.xml.ws.Action;

/**
 *
 * @author jonassimonsen
 */
public class RepoTest {

    public static void main(String[] args) {
        //String name = "Jeanie Mountcastle";
        Random r = new Random();

        List<IRepo> repos = new ArrayList<>();
        repos.add(new MySQLRepo());
        repos.add(new Neo4JRepo());

        List<String> allNames = repos.get(0).getAllNames();

        List<String> randomNames = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            randomNames.add(allNames.get(r.nextInt(allNames.size())));
        }

        System.out.println("Level one");
        testEndorseLevelOne(randomNames, repos);
        System.out.println("\nLevel two");
        testEndorseLevelTwo(randomNames, repos);
        System.out.println("\nLevel three");
        testEndorseLevelThree(randomNames, repos);
    }

    private static void testEndorseLevelOne(List<String> randomNames, List<IRepo> repos) {
        for (IRepo repo : repos) {

            List<Long> times = new ArrayList<>();

            for (String name : randomNames) {
                long before = System.currentTimeMillis();

                List<String> names = repo.getPersonEndorsementsLevelOne(name);

                long after = System.currentTimeMillis();

                times.add(after - before);
            }

            System.out.println("Results for: " + repo.getRepoName());
            printTimes(times);
        }
    }

    private static void testEndorseLevelTwo(List<String> randomNames, List<IRepo> repos) {
        for (IRepo repo : repos) {

            List<Long> times = new ArrayList<>();

            for (String name : randomNames) {
                long before = System.currentTimeMillis();

                List<String> names = repo.getPersonEndorsementsLevelTwo(name);

                long after = System.currentTimeMillis();

                times.add(after - before);
            }

            System.out.println("Results for: " + repo.getRepoName());
            printTimes(times);
        }
    }

    private static void testEndorseLevelThree(List<String> randomNames, List<IRepo> repos) {
        for (IRepo repo : repos) {
            
            List<Long> times = new ArrayList<>();
            
            for (String name : randomNames) {
                long before = System.currentTimeMillis();

                List<String> names = repo.getPersonEndorsementsLevelThree(name);

                long after = System.currentTimeMillis();

                times.add(after - before);
            }

            System.out.println("Results for: " + repo.getRepoName());
            printTimes(times);
        }
    }

    private static void printTimes(List<Long> times) {
        long sum = 0;

        for (Long time : times) {
            System.out.print(time + ", ");
            sum += time;
        }

        System.out.println();

        long avg = sum / times.size();

        System.out.println("Avg:" + avg);
    }
}
