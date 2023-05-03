package org.example.repository.jdbc;

import org.example.model.Developer;
import org.example.model.Skill;
import org.example.model.Specialty;
import org.example.model.Status;
import org.example.repository.DeveloperRepository;
import org.example.utility.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcDeveloperRepositoryImpl implements DeveloperRepository {
    @Override
    public Developer save(Developer developer) {
        String sql = "insert into developer(first_name, last_name, status) values(?, ?, ?)";

        try {
            PreparedStatement statement = JdbcUtils.getPreparedStatementWithKeys(sql);
            statement.setString(1, developer.getFirstName());
            statement.setString(2, developer.getLastName());
            statement.setString(3, developer.getStatus().toString());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            developer.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return developer;
    }

    @Override
    public Developer update(Developer developer) {
        Developer outdatedDeveloper = getByIdInternal(developer.getId());
        String sql1 = "update developer set first_name=?, last_name=?, specialty_id=? where id=?";
        String sql2 = "insert into skill_set(skill_id, developer_id) values(?, ?)";
        String sql3 = "delete from skill_set where skill_id=?";

        try {
            PreparedStatement statement = JdbcUtils.getPreparedStatement(sql1);
            statement.setString(1, developer.getFirstName());
            statement.setString(2, developer.getLastName());
            if(developer.getSpecialty() != null) {
                statement.setInt(3, developer.getSpecialty().getId());
            } else {
                statement.setNull(3, Types.INTEGER);
            }
            statement.setInt(4, developer.getId());
            statement.executeUpdate();

            if(developer.getSkills().size() > outdatedDeveloper.getSkills().size()) {
                Skill newSkill = developer.getSkills().get(developer.getSkills().size() - 1);
                statement = JdbcUtils.getPreparedStatement(sql2);
                statement.setInt(1, newSkill.getId());
                statement.setInt(2, developer.getId());
                statement.executeUpdate();
            } else if(developer.getSkills().size() < outdatedDeveloper.getSkills().size()) {
                outdatedDeveloper.getSkills().removeAll(developer.getSkills());
                statement = JdbcUtils.getPreparedStatement(sql3);
                statement.setInt(1, outdatedDeveloper.getSkills().get(0).getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return developer;
    }

    @Override
    public List<Developer> getAll() {
        List<Developer> allDevelopers = new ArrayList<>();
        String sql = "select * from developer " +
                "left join specialty on developer.specialty_id = specialty.id " +
                "left join skill_set on developer.id = skill_set.developer_id " +
                "left join skill on skill.id = skill_set.skill_id where status like 'ACTIVE'";

        int flag = -1;
        try (PreparedStatement statement = JdbcUtils.getPreparedStatementMovable(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                int developerId = resultSet.getInt("developer.id");
                if(developerId != flag) {
                    flag = developerId;
                    Developer developer = new Developer();
                    developer.setId(developerId);
                    developer.setFirstName(resultSet.getString("first_name"));
                    developer.setLastName(resultSet.getString("last_name"));
                    developer.setStatus(Status.valueOf(resultSet.getString("status")));
                    int specialty_id = resultSet.getInt("specialty_id");
                    if (!resultSet.wasNull()) {
                        Specialty specialty = new Specialty();
                        specialty.setId(specialty_id);
                        specialty.setName(resultSet.getString("specialty.name"));
                        developer.setSpecialty(specialty);
                    }
                    if (resultSet.getString("skill.name") != null) {
                        int row = resultSet.getRow();
                        resultSet.previous();
                        while (resultSet.next()) {
                            if(flag == resultSet.getInt("developer.id")){
                                Skill skill = new Skill();
                                skill.setId(resultSet.getInt("skill.id"));
                                skill.setName(resultSet.getString("skill.name"));
                                developer.setSkills(skill);
                            }
                        }
                        resultSet.absolute(row);
                    }
                    allDevelopers.add(developer);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return allDevelopers;
    }

    @Override
    public Developer getById(Integer integer) {
        return getByIdInternal(integer);
    }

    @Override
    public boolean deleteById(Integer integer) {
        String sql1 = "update developer set status='DELETED', specialty_id=null " +
                         "where id=? and status like 'ACTIVE'";
        String sql2 = "delete from skill_set where developer_id=?";

        try {
            PreparedStatement statement = JdbcUtils.getPreparedStatement(sql1);
            statement.setInt(1, integer);
            int status = statement.executeUpdate();
            if(status > 0) {
                statement = JdbcUtils.getPreparedStatement(sql2);
                statement.setInt(1, integer);
                statement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    private Developer getByIdInternal(Integer integer) {
        Developer developer = null;
        String sql = "select * from developer " +
                "left join specialty on developer.specialty_id = specialty.id " +
                "left join skill_set on developer.id = skill_set.developer_id " +
                "left join skill on skill.id = skill_set.skill_id where status like 'ACTIVE' and developer.id=?";
        try {
            PreparedStatement statement = JdbcUtils.getPreparedStatementMovable(sql);
            statement.setInt(1, integer);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                developer = new Developer();
                developer.setId(resultSet.getInt("developer.id"));
                developer.setFirstName(resultSet.getString("first_name"));
                developer.setLastName(resultSet.getString("last_name"));
                developer.setStatus(Status.valueOf(resultSet.getString("status")));
                int specialtyId = resultSet.getInt("specialty_id");
                if(!resultSet.wasNull()) {
                    Specialty specialty = new Specialty();
                    specialty.setId(specialtyId);
                    specialty.setName(resultSet.getString("specialty.name"));
                    developer.setSpecialty(specialty);
                }
                if(resultSet.getString("skill.name") != null) {
                    resultSet.previous();
                    while(resultSet.next()){
                        Skill skill = new Skill();
                        skill.setId(resultSet.getInt("skill.id"));
                        skill.setName(resultSet.getString("skill.name"));
                        developer.setSkills(skill);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return developer;
    }
}
