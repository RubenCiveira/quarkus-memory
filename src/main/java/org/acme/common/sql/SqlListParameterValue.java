package org.acme.common.sql;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SqlListParameterValue extends SqlParameterValue {
  static SqlListParameterValue of(String... values) {
    return new GenericSqlListParameterValues<>(values,
        (index, value, ps) -> ps.setString(index, value));
  }

  static SqlListParameterValue strings(List<String> values) {
    return of(values.toArray(new String[0]));
  }

  static SqlListParameterValue of(Integer... values) {
    return new GenericSqlListParameterValues<>(values,
        (index, value, ps) -> ps.setInt(index, value));
  }

  static SqlListParameterValue integers(List<Integer> values) {
    return of(values.toArray(new Integer[0]));
  }

  static SqlListParameterValue of(Long... values) {
    return new GenericSqlListParameterValues<>(values,
        (index, value, ps) -> ps.setLong(index, value));
  }

  static SqlListParameterValue longs(List<Long> values) {
    return of(values.toArray(new Long[0]));
  }

  static SqlListParameterValue of(Double... values) {
    return new GenericSqlListParameterValues<>(values,
        (index, value, ps) -> ps.setDouble(index, value));
  }

  static SqlListParameterValue doubles(List<Double> values) {
    return of(values.toArray(new Double[0]));
  }

  static SqlListParameterValue of(Boolean... values) {
    return new GenericSqlListParameterValues<>(values,
        (index, value, ps) -> ps.setBoolean(index, value));
  }

  static SqlListParameterValue booleans(List<Boolean> values) {
    return of(values.toArray(new Boolean[0]));
  }

  static SqlListParameterValue of(BigDecimal... values) {
    return new GenericSqlListParameterValues<>(values,
        (index, value, ps) -> ps.setBigDecimal(index, value));
  }

  static SqlListParameterValue bigDecimals(List<BigDecimal> values) {
    return of(values.toArray(new BigDecimal[0]));
  }

  static SqlListParameterValue of(Date... values) {
    return new GenericSqlListParameterValues<>(values,
        (index, value, ps) -> ps.setDate(index, value));
  }

  static SqlListParameterValue dates(List<Date> values) {
    return of(values.toArray(new Date[0]));
  }

  static SqlListParameterValue of(Time... values) {
    return new GenericSqlListParameterValues<>(values,
        (index, value, ps) -> ps.setTime(index, value));
  }

  static SqlListParameterValue times(List<Time> values) {
    return of(values.toArray(new Time[0]));
  }

  static SqlListParameterValue of(Timestamp... values) {
    return new GenericSqlListParameterValues<>(values,
        (index, value, ps) -> ps.setTimestamp(index, value));
  }

  static SqlListParameterValue timestamps(List<Timestamp> values) {
    return of(values.toArray(new Timestamp[0]));
  }

  static SqlParameterValue of(LocalDate... values) {
    return new GenericSqlListParameterValues<>(values,
        (index, value, ps) -> ps.setDate(index, Date.valueOf(value)));
  }

  static SqlParameterValue localdates(List<LocalDate> values) {
    return of(values.toArray(new LocalDate[0]));
  }

  static SqlParameterValue of(LocalDateTime... values) {
    return new GenericSqlListParameterValues<>(values,
        (index, value, ps) -> ps.setTimestamp(index, Timestamp.valueOf(value)));
  }

  static SqlParameterValue localdatetimes(List<LocalDateTime> values) {
    return of(values.toArray(new LocalDateTime[0]));
  }

  int size();
}
