package org.example.repository.jdbc;

import org.example.model.Specialty;
import org.example.repository.SpecialtyRepository;
import org.example.utility.MyConnection;
import org.example.utility.ResourceCloseHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcSpecialtyRepositoryImpl implements SpecialtyRepository {
    @Override
    public Specialty save(Specialty specialty) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = MyConnection.getConnection();

            String sql = "insert into specialty(name) values(?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, specialty.getName());
            statement.executeUpdate();

            sql = "select * from specialty";
            statement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.last();
            specialty.setId(resultSet.getInt("id"));
        } catch (SQLIntegrityConstraintViolationException e) {
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ResourceCloseHandler.closeResources(connection, statement);
        }

        return specialty;
    }

    @Override
    public Specialty update(Specialty specialty) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = MyConnection.getConnection();
            String sql = "update specialty set name=? where id=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, specialty.getName());
            statement.setInt(2, specialty.getId());
            statement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ResourceCloseHandler.closeResources(connection, statement);
        }

        return specialty;
    }

    @Override
    public List<Specialty> getAll() {
        List<Specialty> allSpecialties = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = MyConnection.getConnection();
            String sql = "select * from specialty order by id";
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                Specialty specialty = new Specialty();
                specialty.setId(resultSet.getInt("id"));
                specialty.setName(resultSet.getString("name"));
                allSpecialties.add(specialty);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ResourceCloseHandler.closeResources(connection, statement);
        }

        return allSpecialties;
    }

    @Override
    public Specialty getById(Integer integer) {
        Connection connection = null;
        PreparedStatement statement = null;
        Specialty specialty = null;

        try {
            connection = MyConnection.getConnection();
            String sql = "select * from specialty where id=?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, integer);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                specialty = new Specialty();
                specialty.setId(resultSet.getInt("id"));
                specialty.setName(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ResourceCloseHandler.closeResources(connection, statement);
        }

        return specialty;
    }

    @Override
    public boolean deleteById(Integer integer) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = MyConnection.getConnection();
            String sql = "delete from specialty where id=?";
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