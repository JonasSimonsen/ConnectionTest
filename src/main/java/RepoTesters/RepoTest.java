/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RepoTesters;

import Repoes.IRepo;
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
        Random r = new Random();
        IRepo neoRepo = new Neo4JRepo();
        
        List<String> allNames = neoRepo.getAllNames();
        
        List<String> randomNames = new ArrayList<>();
        
        for (int i = 0; i < 20; i++) {
            randomNames.add(allNames.get(r.nextInt(allNames.size())));
        }
        System.out.println("Level one");
        testEndorseLevelOne(randomNames, neoRepo);
        System.out.println("\nLevel two");
        testEndorseLevelTwo(randomNames, neoRepo);
        System.out.println("\nLevel three");
        testEndorseLevelThree(randomNames, neoRepo);        
    }
    
    private static void testEndorseLevelOne(List<String> randomNames, IRepo repo) {
        List<Long> times = new ArrayList<>();
        
        for (String name : randomNames) {
            long before = System.currentTimeMillis();
            
            List<String> names = repo.getPersonEndorsementsLevelOne(name);    
            
            long after = System.currentTimeMillis();
            
            times.add(after - before);
        }
        
        printTimes(times);
    }
    
    private static void testEndorseLevelTwo(List<String> randomNames, IRepo repo) {
        List<Long> times = new ArrayList<>();
        
        for (String name : randomNames) {
            long before = System.currentTimeMillis();
            
            List<String> names = repo.getPersonEndorsementsLevelTwo(name);    
            
            long after = System.currentTimeMillis();
            
            times.add(after - before);
        }
        
        printTimes(times);
    }
    
    private static void testEndorseLevelThree(List<String> randomNames, IRepo repo) {
        List<Long> times = new ArrayList<>();
        
        for (String name : randomNames) {
            long before = System.currentTimeMillis();
            
            List<String> names = repo.getPersonEndorsementsLevelThree(name);    
            
            long after = System.currentTimeMillis();
            
            times.add(after - before);
        }
        
        printTimes(times);
    }
    
    private static void printTimes(List<Long> times){
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
