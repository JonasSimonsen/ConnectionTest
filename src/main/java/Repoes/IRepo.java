/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repoes;

import java.util.List;

/**
 *
 * @author jonassimonsen
 */
public interface IRepo {
    
    List<String> getAllNames();

    List<String> getPersonEndorsementsLevelOne(String name);

    List<String> getPersonEndorsementsLevelTwo(String name);

    List<String> getPersonEndorsementsLevelThree(String name);

    List<String> getPersonEndorsementsLevelFour(String name);

    List<String> getPersonEndorsementsLevelFive(String name);
}
