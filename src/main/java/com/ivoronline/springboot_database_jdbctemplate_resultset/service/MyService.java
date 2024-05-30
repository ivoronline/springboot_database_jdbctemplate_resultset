package com.ivoronline.springboot_database_jdbctemplate_resultset.service;

import com.ivoronline.springboot_database_jdbctemplate_resultset.dto.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class MyService {

  //PROPERTIES
  @Autowired private JdbcTemplate jdbcTemplate;

  //=========================================================================================================
  // SELECT RECORD
  //=========================================================================================================
  public PersonDTO selectRecord(Integer id) throws SQLException {

    //GET METADATA
    DataSource dataSource = jdbcTemplate.getDataSource();
    Connection connection = DataSourceUtils.getConnection(dataSource);
    Statement  statement  = connection.createStatement();
    ResultSet  resultSet  = statement.executeQuery("SELECT * FROM PERSON WHERE ID = 1");

    //STORE RECORD IN DTO
    PersonDTO personDTO = new PersonDTO();
    while(resultSet.next()) {           //NEXT RECORD
      personDTO.setId  (resultSet.getInt   ("ID"  ));
      personDTO.setName(resultSet.getString("NAME"));
      personDTO.setAge (guardAgainstNullNumbers("AGE", resultSet));
    }

    //RETURN DTO
    return personDTO;

  }

  //=========================================================================================================
  // SELECT RECORDS
  //=========================================================================================================
  public List<PersonDTO> selectRecords() throws SQLException {

    //GET METADATA
    DataSource dataSource = jdbcTemplate.getDataSource();
    Connection connection = DataSourceUtils.getConnection(dataSource);
    Statement  statement  = connection.createStatement();
    ResultSet  resultSet  = statement.executeQuery("SELECT * FROM PERSON;");

    //STORE RECORD IN DTO
    List<PersonDTO> personDTOList = new ArrayList<>();
    while(resultSet.next()) {           //NEXT RECORD
      PersonDTO personDTO = new PersonDTO();
                personDTO.setId  (resultSet.getInt   ("ID"  ));
                personDTO.setName(resultSet.getString("NAME"));
                personDTO.setAge (guardAgainstNullNumbers("AGE", resultSet));
      personDTOList.add(personDTO);
    }

    //RETURN DTO
    return personDTOList;

  }

  //=========================================================================================================
  // GUARD AGAINST NULL NUMBERS
  //=========================================================================================================
  public Integer guardAgainstNullNumbers(String columnName, ResultSet resultSet) throws SQLException {
    Integer value = resultSet.getInt(columnName); //It will be 0 for null
    return resultSet.wasNull() ? null : value;
  }

}


