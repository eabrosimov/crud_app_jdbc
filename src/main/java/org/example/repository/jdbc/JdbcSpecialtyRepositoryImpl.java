package org.example.repository.jdbc;

import org.example.model.Specialty;
import org.example.repository.SpecialtyRepository;
import org.example.utility.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcSpecialtyRepositoryImpl implements SpecialtyRepository {
    @Override
    public Specialty save(Specialty specialty) {
        String sql = "insert into specialty(name) values(?)";

        try {
            PreparedStatement statement = JdbcUtils.getPreparedStatementWithKeys(sql);
            statement.setString(1, specialty.getName());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            specialty.setId(resultSet.getInt(1));
        } catch (SQLIntegrityConstraintViolationException e) {
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return specialty;
    }

    @Override
    public Specialty update(Specialty specialty) {
        String sql = "update specialty set name=? where id=?";

        try {
            PreparedStatement statement = JdbcUtils.getPreparedStatement(sql);
            statement.setString(1, specialty.getName());
            statement.setInt(2, specialty.getId());
            statement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return specialty;
    }

    @Override
    public List<Specialty> getAll() {
        List<Specialty> allSpecialties = new ArrayList<>();
        String sql = "select * from specialty order by id";

        try {
            PreparedStatement statement = JdbcUtils.getPreparedStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Specialty specialty = new Specialty();
                specialty.setId(resultSet.getInt("id"));
                specialty.setName(resultSet.getString("name"));
                allSpecialties.add(specialty);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allSpecialties;
    }

    @Override
    public Specialty getById(Integer integer) {
        Specialty specialty = null;
        String sql = "select * from specialty where id=?";

        try {
            PreparedStatement statement = JdbcUtils.getPreparedStatement(sql);
            statement.setInt(1, integer);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                specialty = new Specialty();
                specialty.setId(resultSet.getInt("id"));
                specialty.setName(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return specialty;
    }

    @Override
    public boolean deleteById(Integer integer) {
        String sql = "delete from specialty where id=?";

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