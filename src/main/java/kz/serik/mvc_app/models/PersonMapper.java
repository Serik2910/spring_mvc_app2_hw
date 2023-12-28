package kz.serik.mvc_app.models;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Person(
            resultSet.getInt("id"),
            resultSet.getString("fullname"),
            resultSet.getInt("birthyear")
        );
    }
}
