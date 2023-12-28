package kz.serik.mvc_app.DAO;

import kz.serik.mvc_app.models.Person;

import kz.serik.mvc_app.models.PersonMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Admin on 23.09.2023
 * @project spring_mvc_app2
 */
@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PersonDAO(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Person> indexTemplate(){
        List<Person> people= null;
        try {
            //people = jdbcTemplate.query("select * from person_hw", new PersonMapper());
            people = jdbcTemplate.query("select * from person_hw", new BeanPropertyRowMapper<>(Person.class));

        }catch (Exception e){
            e.printStackTrace();
        }
        return people;
     }

    public Person getPersonTemplate(int id){
        String query;
        //first example
        query = "select * from person_hw where id=?";
        Person person1 = jdbcTemplate.query(query, new Object[]{id}, new PersonMapper()).stream().findAny().orElse(null);
        //second example
        SqlParameterSource namedParameter = new MapSqlParameterSource().addValue("id", id);
        query = "select * from person_hw where id=:id";
        Person person2 = namedParameterJdbcTemplate.queryForObject(query, namedParameter, new PersonMapper());
        //third example
        Person person3 = new Person();
        person3.setId(id);
        SqlParameterSource propertyNamedParameter = new BeanPropertySqlParameterSource(person3);
        //person3 = namedParameterJdbcTemplate.queryForObject(query, propertyNamedParameter, new PersonMapper());//
        person3 = namedParameterJdbcTemplate.queryForObject(query, propertyNamedParameter, new BeanPropertyRowMapper<>(Person.class));
        return person3;
    }



    public boolean addPersonTemplate(Person person){
        int rows=0;
        //rows = jdbcTemplate.update("insert into person(name, email, age) values (?,?,?)", new Object[]{person.getName(), person.getEmail(), person.getAge()});
        SqlParameterSource propertyNamedParameter = new BeanPropertySqlParameterSource(person);
        rows = namedParameterJdbcTemplate.update("insert into person_hw(fullname, birthyear) values (:fullname,:birthyear)",propertyNamedParameter);
        return rows>0;//people.add(person);
    }

    public boolean updatePersonTemplate(Person person){
        Person personSearch = getPersonTemplate(person.getId());
        int rows = 0;
        if(personSearch != null) {
            SqlParameterSource propertyNamedParameter = new BeanPropertySqlParameterSource(person);
            rows = namedParameterJdbcTemplate.update(
                    "update person_hw set " +
                            "fullname = :fullname, birthyear = :birthyear " +
                            "where id = :id",
                    propertyNamedParameter);
        }
        return rows>0;
    }

    public boolean deletePerson(int id) {
        int rows = 0;
        rows = jdbcTemplate.update("delete from person_hw where id = ?", id);
        return rows>0;
    }
}
