package org.example.repository.jdbc;

import org.example.model.Skill;
import org.example.repository.SkillRepository;
import org.example.utility.MyConnection;
import org.example.utility.ResourceCloseHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcSkillRepositoryImpl implements SkillRepository {
    @Override
    public Skill save(Skill skill) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = MyConnection.getConnection();

            String sql = "insert into skill(name) values(?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, skill.getName());
            statement.executeUpdate();

            sql = "select * from skill";
            statement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.last();
            skill.setId(resultSet.getInt("id"));
        } catch (SQLIntegrityConstraintViolationException e) {
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ResourceCloseHandler.closeResources(connection, statement);
        }

        return skill;
    }

    @Override
    public Skill update(Skill skill) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = MyConnection.getConnection();
            String sql = "update skill set name=? where id=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, skill.getName());
            statement.setInt(2, skill.getId());
            statement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ResourceCloseHandler.closeResources(connection, statement);
        }

        return skill;
    }

    @Override
    public List<Skill> getAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        List<Skill> allSkills = new ArrayList<>();

        try {
            connection = MyConnection.getConnection();
            String sql = "select * from skill order by id";
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Skill skill = new Skill();
                skill.setId(resultSet.getInt("id"));
                skill.setName(resultSet.getString("name"));
                allSkills.add(skill);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ResourceCloseHandler.closeResources(connection, statement);
        }

        return allSkills;
    }

    @Override
    public Skill getById(Integer integer) {
        Connection connection = null;
        PreparedStatement statement = null;
        Skill skill = null;

        try {
            connection = MyConnection.getConnection();
            String sql = "select * from skill where id=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, integer);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                skill = new Skill();
                skill.setId(resultSet.getInt("id"));
                skill.setName(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ResourceCloseHandler.closeResources(connection, statement);
        }

        return skill;
    }

    @Override
    public boolean deleteById(Integer integer) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = MyConnection.getConnection();
            String sql = "delete from skill where id=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, integer);
            int status = statement.executeUpdate();
            if(status > 0) {
                return true;
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ResourceCloseHandler.closeResources(connection, statement);
        }

        return false;
    }
}