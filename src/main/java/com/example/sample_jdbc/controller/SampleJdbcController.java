package com.example.sample_jdbc.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("api")
public class SampleJdbcController {
 
    @Autowired
	private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "TestSelect", method = RequestMethod.POST)
    private String TestSelect(
        HttpSession session, 
        HttpServletResponse servletResponse, 
        @RequestParam(name="ID") int id ) 
    {
        List<Map<String,Object>> list;
        list = jdbcTemplate.queryForList("SELECT * FROM test_user WHERE id = ?", id);
        return list.toString();
    }

    public static class UserBean {
        public UserBean(int id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }
        public int id;
        public String name;
        public int age;
    }

    @RequestMapping(value = "TestSelects", method = RequestMethod.POST)
    private List<UserBean> TestSelects(
        HttpSession session, 
        HttpServletResponse servletResponse, 
        @RequestParam(name="Age") int age ) 
    {
        SqlRowSet rs = jdbcTemplate.queryForRowSet("SELECT * FROM test_user WHERE age = ?", age);
        
        List<UserBean> users = new ArrayList<>();
        while(rs.next()) {
            users.add(new UserBean(
                rs.getInt("id"), 
                rs.getString("name"),
                rs.getInt("age")));
        }

        return users;
    }
}
