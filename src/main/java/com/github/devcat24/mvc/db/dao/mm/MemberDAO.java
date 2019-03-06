package com.github.devcat24.mvc.db.dao.mm;

import com.github.devcat24.mvc.dto.mm.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

//import java.sql.ResultSet;
//import java.sql.SQLException;
//import org.springframework.jdbc.core.RowMapper;


@Repository
public class MemberDAO {

    private JdbcTemplate jdbcTemplate ;

    @Autowired
    //  --> for multiple datasource -> Can define specific datasource using @Qualifier
    //  public void setDataSource(@Qualifier("fooDataSource") DataSource dataSource){
    //
    @Qualifier("dataSource")  // -> use when IntelliJ makes warning 'Could not autowire. There is more than one bean of 'DataSource'type
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @SuppressWarnings({"UnnecessaryLocalVariable", "SqlDialectInspection", "SqlNoDataSourceInspection", "unused"})
    public List<MemberDTO> getAllMemberFromId(Long id){
        String queryString
                //= " select cast(m.member_id as unsigned) as member_id, m.name as name, m.city as city, " +
                = " select m.member_id as member_id, m.name as name, m.city as city, " +
                "         m.street as street, m.zipcode as zipcode "                                   +
                " from jpa_lab_member m where member_id > ? ";

        Object[] paramList = new Object[]{id};

        List<MemberDTO> rtnList = jdbcTemplate.query(queryString, paramList,
                (rs, rowNum) -> {
                    MemberDTO mDTO = new MemberDTO();
                    mDTO.setId(rs.getLong("member_id"));
                    mDTO.setName(rs.getString("name"));
                    mDTO.setCity(rs.getString("city"));
                    mDTO.setStreet(rs.getString("street"));
                    mDTO.setStreet(rs.getString("zipcode"));
                    return mDTO;
                });

        /*List<MemberDTO> rtnList = jdbcTemplate.query(queryString, paramList,
                new RowMapper<MemberDTO> (){
                    public MemberDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                        MemberDTO mDTO = new MemberDTO();
                        mDTO.setId(rs.getLong("member_id"));
                        mDTO.setName(rs.getString("name"));
                        mDTO.setCity(rs.getString("city"));
                        mDTO.setStreet(rs.getString("street"));
                        mDTO.setStreet(rs.getString("zipcode"));
                        return mDTO;
                    }
                });*/
        return rtnList;
    }
}
