package org.example.repository.jdbc;

import org.example.model.Skill;
import org.example.repository.SkillRepository;
import org.example.utility.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcSkillRepositoryImpl implements SkillRepository {
    @Override
    public Skill save(Skill skill) {
        String sql = "insert into skill(name) values(?)";

        try {
            PreparedStatement statement = JdbcUtils.getPreparedStatementWithKeys(sql);
            statement.setString(1, skill.getName());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            skill.setId(resultSet.getInt(1));
        } catch (SQLIntegrityConstraintViolationException e) {
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return skill;
    }

    @Override
    public Skill update(Skill skill) {
        String sql = "update skill set name=? where id=?";

        try {
            PreparedStatement statement = JdbcUtils.getPreparedStatement(sql);
            statement.setString(1, skill.getName());
            statement.setInt(2, skill.getId());
            statement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return skill;
    }

    @Override
    public List<Skill> getAll() {
        List<Skill> allSkills = new ArrayList<>();
        String sql = "select * from skill order by id";

        try {
            PreparedStatement statement = JdbcUtils.getPreparedStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Skill skill = new Skill();
                skill.setId(resultSet.getInt("id"));
                skill.setName(resultSet.getString("name"));
                allSkills.add(skill);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allSkills;
    }

    @Override
    public Skill getById(Integer integer) {
        Skill skill = null;
        String sql = "select * from skill where id=?";

        try {
            PreparedStatement statement = JdbcUtils.getPreparedStatement(sql);
            statement.setInt(1, integer);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                skill = new Skill();
                skill.setId(resultSet.getInt("id"));
                skill.setName(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return skill;
    }

    @Override
    public boolean deleteById(Integer integer) {
        String sql = "delete from skill where id=?";

        try {
            PreparedStatement statement = JdbcUtils.getPreparedStatement(sql);
            statement.setInt(1, integer);
            int status = statement.executeUpdate();
            if(status > 0) {
                return true;
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}