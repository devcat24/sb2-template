package com.github.devcat24.mvc.svc.db.dao.mm;

import com.github.devcat24.mvc.svc.db.dto.mm.MemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MemberDAO {

    private JdbcTemplate jdbcTemplate ;

    @Autowired
    public void setDataSource(@Qualifier("fooDataSource") DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<MemberDTO> getAllMemberFromId(Long id){
        String queryString
                //= " select cast(m.member_id as unsigned) as member_id, m.name as name, m.city as city, " +
                = " select m.member_id as member_id, m.name as name, m.city as city, " +
                  "         m.street as street, m.zipcode as zipcode "                                   +
                  " from jpa_lab_member m where member_id > ? ";

        Object[] paramList = new Object[]{id};

        List<MemberDTO> rtnList = jdbcTemplate.query(queryString, paramList,
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
                });
        return rtnList;
    }
}
