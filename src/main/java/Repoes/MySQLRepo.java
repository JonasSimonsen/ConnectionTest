/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repoes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jonassimonsen
 */
public class MySQLRepo implements IRepo{

    @Override
    public List<String> getAllNames() {
        return runQuery("SELECT name FROM t_user");
    }

    @Override
    public List<String> getPersonEndorsementsLevelOne(String name) {
        return runQuery("SELECT DISTINCT name FROM t_user "
                + "JOIN t_endorsement ON source_node_id = (SELECT node_id FROM t_user WHERE name = '" + name + "') "
                        + "AND target_node_id = node_id");
    }

    @Override
    public List<String> getPersonEndorsementsLevelTwo(String name) {
        return runQuery("SELECT DISTINCT name FROM t_user "
                + "JOIN t_endorsement ON source_node_id = (SELECT node_id FROM t_user WHERE name = '" + name + "') "
                        + "AND target_node_id IN (SELECT source_node_id FROM t_endorsement WHERE target_node_id = node_id)");
    }

    @Override
    public List<String> getPersonEndorsementsLevelThree(String name) {
        return runQuery("SELECT DISTINCT name FROM t_user "
                + "JOIN t_endorsement ON source_node_id = (SELECT node_id FROM t_user WHERE name = '" + name + "') "
                        + "AND target_node_id IN (SELECT source_node_id FROM t_endorsement WHERE target_node_id IN "
                        + "(SELECT source_node_id FROM t_endorsement WHERE target_node_id = node_id))");
    }

    @Override
    public List<String> getPersonEndorsementsLevelFour(String name) {
        return runQuery("SELECT DISTINCT name FROM t_user "
                + "JOIN t_endorsement ON source_node_id = (SELECT node_id FROM t_user WHERE name = '" + name + "') "
                        + "AND target_node_id IN (SELECT source_node_id FROM t_endorsement WHERE target_node_id IN "
                        + "(SELECT source_node_id FROM t_endorsement WHERE target_node_id IN "
                        + "(SELECT source_node_id FROM t_endorsement WHERE target_node_id = node_id)))");
    }

    @Override
    public List<String> getPersonEndorsementsLevelFive(String name) {
        return runQuery("SELECT DISTINCT name FROM t_user "
                + "JOIN t_endorsement ON source_node_id = (SELECT node_id FROM t_user WHERE name = '" + name + "') "
                        + "AND target_node_id IN (SELECT source_node_id FROM t_endorsement WHERE target_node_id IN "
                        + "(SELECT source_node_id FROM t_endorsement WHERE target_node_id IN "
                        + "(SELECT source_node_id FROM t_endorsement WHERE target_node_id IN "
                        + "(SELECT source_node_id FROM t_endorsement WHERE target_node_id = node_id))))");
    }
    
    private List<String> runQuery(String query) {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://localhost:3306/social_network?useSSL=false";
        String user = "root";
        String password = "pwd";
        
        try {

            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            rs = st.executeQuery(query);

            List<String> names = new ArrayList<>();
            
            while (rs.next()) {
                names.add(rs.getString(1));
            }
            
            return names;

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            System.err.println(ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                System.err.println(ex);
            }
        }
        
        return null;
    }

    @Override
    public String getRepoName() {
        return "MySql";
    }
}
