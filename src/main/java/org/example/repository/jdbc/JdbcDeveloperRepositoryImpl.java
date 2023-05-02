package org.example.repository.jdbc;

import org.example.model.Developer;
import org.example.model.Skill;
import org.example.model.Specialty;
import org.example.model.Status;
import org.example.repository.DeveloperRepository;
import org.example.utility.MyConnection;
import org.example.utility.ResourceCloseHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcDeveloperRepositoryImpl implements DeveloperRepository {

    @Override
    public Developer save(Developer developer) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = MyConnection.getConnection();

            String sql = "insert into developer(first_name, last_name, status) values(?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, developer.getFirstName());
            statement.setString(2, developer.getLastName());
            statement.setString(3, developer.getStatus().toString());
            statement.executeUpdate();

            sql = "select * from developer";
            statement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery();
            resultSet.last();
            developer.setId(resultSet.getInt("id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ResourceCloseHandler.closeResources(connection, statement);
        }

        return developer;
    }

    @Override
    public Developer update(Developer developer) {
        Connection connection = null;
        PreparedStatement statement = null;
        Developer outdatedDeveloper = getByIdInternal(developer.getId());

        try {
            connection = MyConnection.getConnection();
            String sql = "update developer set first_name=?, last_name=?, specialty_id=? where id=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, developer.getFirstName());
            statement.setString(2, developer.getLastName());
            if(developer.getSpecialty() != null)
                statement.setInt(3, developer.getSpecialty().getId());
            else
                statement.setNull(3, Types.INTEGER);
            statement.setInt(4, developer.getId());
            statement.executeUpdate();

            if(developer.getSkills().size() > outdatedDeveloper.getSkills().size()){
                List<Skill> skills = new ArrayList<>(developer.getSkills());
                skills.removeAll(outdatedDeveloper.getSkills());
                sql = "insert into skill_set(skill_id, developer_id) values(?, ?)";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, skills.get(0).getId());
                statement.setInt(2, developer.getId());
                statement.executeUpdate();
            } else if(developer.getSkills().size() < outdatedDeveloper.getSkills().size()){
                outdatedDeveloper.getSkills().removeAll(developer.getSkills());
                sql = "delete from skill_set where skill_id=?";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, outdatedDeveloper.getSkills().get(0).getId());
                statement.executeUpdate();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ResourceCloseHandler.closeResources(connection, statement);
        }

        return developer;
    }

    @Override
    public List<Developer> getAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        List<Developer> allDevelopers = new ArrayList<>();

        try {
            connection = MyConnection.getConnection();
            String sql = "select * from developer where status not like 'DELETED'";
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Developer developer = buildDeveloperObject(resultSet, statement, connection);
                allDevelopers.add(developer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ResourceCloseHandler.closeResources(connection, statement);
        }

        return allDevelopers;
    }

    @Override
    public Developer getById(Integer integer) {
        return getByIdInternal(integer);
    }

    @Override
    public boolean deleteById(Integer integer) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = MyConnection.getConnection();
            String sql = "update developer set status='DELETED', specialty_id=null " +
                         "where id=? and status not like 'DELETED'";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, integer);
            int status = statement.executeUpdate();
            if(status > 0){
                sql = "delete from skill_set where developer_id=?";
                statement = connection.prepareStatement(sql);
                statement.setInt(1, integer);
                statement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ResourceCloseHandler.closeResources(connection, statement);
        }
        return false;
    }

    private Developer getByIdInternal(Integer integer) {
        Connection connection = null;
        PreparedStatement statement = null;
        Developer developer = null;

        try {
            connection = MyConnection.getConnection();
            String sql = "select * from developer where id=? and status not like 'DELETED'";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, integer);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                developer = buildDeveloperObject(resultSet, statement, connection);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ResourceCloseHandler.closeResources(connection, statement);
        }

        return developer;
    }

    private Developer buildDeveloperObject(ResultSet resultSet, PreparedStatement statement, Connection connection) throws SQLException {
        Developer developer = new Developer();
        developer.setId(resultSet.getInt("id"));
        developer.setFirstName(resultSet.getString("first_name"));
        developer.setLastName(resultSet.getString("last_name"));
        developer.setStatus(Status.valueOf(resultSet.getString("status")));
        int specialty_id = resultSet.getInt("specialty_id");
        if(!resultSet.wasNull()){
            Specialty specialty = new Specialty();
            String sql1 = "select * from specialty where id=?";
            statement = connection.prepareStatement(sql1);
            statement.setInt(1, specialty_id);
            ResultSet resultSet1 = statement.executeQuery();
            resultSet1.next();
            specialty.setId(resultSet1.getInt("id"));
            specialty.setName(resultSet1.getString("name"));
            developer.setSpecialty(specialty);
        }
        String sql2 = "select * from skill join skill_set on(skill.id = skill_set.skill_id) where developer_id=?";
        statement = connection.prepareStatement(sql2);
        statement.setInt(1, developer.getId());
        ResultSet resultSet2 = statement.executeQuery();
        while (resultSet2.next()){
            Skill skill = new Skill();
            skill.setId(resultSet2.getInt("skill.id"));
            skill.setName(resultSet2.getString("name"));
            developer.getSkills().add(skill);
        }

        return developer;
    }
}
