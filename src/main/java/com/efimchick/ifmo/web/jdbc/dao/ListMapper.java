package com.efimchick.ifmo.web.jdbc.dao;

import java.sql.ResultSet;

public interface ListMapper<T> {
    T mapList(ResultSet resultSet);
}
