package kz.serik.mvc_app.DAO;

import kz.serik.mvc_app.models.Book;
import kz.serik.mvc_app.models.BookMapper;
import kz.serik.mvc_app.models.Person;
import kz.serik.mvc_app.models.PersonMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BooksDAO {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public BooksDAO(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Book> indexTemplate(){
        List<Book> books= null;
        try {
            //books = jdbcTemplate.query("select * from book",new BookMapper());
            books = jdbcTemplate.query("select * from book", new BeanPropertyRowMapper<>(Book.class));
        }catch (Exception e){
            e.printStackTrace();
        }
        return books;
    }
    public List<Book> getBooksTemplate(int personId){
        List<Book> books= null;
        try {
            //books = jdbcTemplate.query("select * from book",new BookMapper());
            books = jdbcTemplate.query("select * from book where personid=?", new Object[]{personId}, new BeanPropertyRowMapper<>(Book.class));
        }catch (Exception e){
            e.printStackTrace();
        }
        return books;
    }

    public Book getBookTemplate(int id){
        String query;
        query = "select * from book where id=:id";
        Book book = new Book();
        book.setId(id);
        SqlParameterSource propertyNamedParameter = new BeanPropertySqlParameterSource(book);
        book = namedParameterJdbcTemplate.queryForObject(query, propertyNamedParameter, new BeanPropertyRowMapper<>(Book.class));
        return book;
    }



    public boolean addBookTemplate(Book book){
        int rows=0;
        SqlParameterSource propertyNamedParameter = new BeanPropertySqlParameterSource(book);
        rows = namedParameterJdbcTemplate.update("insert into book(name, author, year ) values (:name,:author, :year)", propertyNamedParameter);
        return rows>0;//people.add(person);
    }

    public boolean updateBookTemplate(Book book){
        Book bookToSearch = getBookTemplate(book.getId());
        int rows = 0;
        String sql;
        if(book.getPersonId()!=null) {
            sql = "update book set " +
                    "name = :name, author = :author, year = :year, personid= :personId " +
                    "where id = :id";
        }else {
            sql = "update book set " +
                    "name = :name, author = :author, year = :year, personid= null " +
                    "where id = :id";
        }
        if(bookToSearch != null) {
            SqlParameterSource propertyNamedParameter = new BeanPropertySqlParameterSource(book);
            rows = namedParameterJdbcTemplate.update(sql, propertyNamedParameter);
        }
        return rows>0;
    }

    public boolean deleteBook(int id) {
        int rows = 0;
        rows = jdbcTemplate.update("delete from book where id = ?", id);
        return rows>0;
    }
}
