package org.acme.common.sql;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface SqlParameterValue {
  static SqlParameterValue of(String value) {
    return (index, ps) -> ps.setString(index, value);
  }

  static SqlParameterValue of(long value) {
    return (index, ps) -> ps.setLong(index, value);
  }

  static SqlParameterValue of(int value) {
    return (index, ps) -> ps.setInt(index, value);
  }

  static SqlParameterValue of(double value) {
    return (index, ps) -> ps.setDouble(index, value);
  }

  static SqlParameterValue of(float value) {
    return (index, ps) -> ps.setFloat(index, value);
  }

  static SqlParameterValue of(boolean value) {
    return (index, ps) -> ps.setBoolean(index, value);
  }

  static SqlParameterValue of(byte value) {
    return (index, ps) -> ps.setByte(index, value);
  }

  static SqlParameterValue of(short value) {
    return (index, ps) -> ps.setShort(index, value);
  }

  static SqlParameterValue of(BigDecimal value) {
    return (index, ps) -> ps.setBigDecimal(index, value);
  }

  static SqlParameterValue of(Date value) {
    return (index, ps) -> ps.setDate(index, value);
  }

  static SqlParameterValue of(Time value) {
    return (index, ps) -> ps.setTime(index, value);
  }

  static SqlParameterValue of(Timestamp value) {
    return (index, ps) -> ps.setTimestamp(index, value);
  }

  static SqlParameterValue of(Object value) {
    return (index, ps) -> ps.setObject(index, value);
  }

  static SqlParameterValue of(byte[] value) {
    return (index, ps) -> ps.setBytes(index, value);
  }

  static SqlParameterValue ofBinary(InputStream value) {
    return (index, ps) -> ps.setBinaryStream(index, value);
  }

  static SqlParameterValue ofText(InputStream value) {
    return (index, ps) -> ps.setAsciiStream(index, value);
  }

  static SqlParameterValue ofNull(int sqlType) {
    return (index, ps) -> ps.setNull(index, sqlType);
  }

  static SqlParameterValue of(LocalDate value) {
    return (index, ps) -> ps.setDate(index, Date.valueOf(value));
  }

  static SqlParameterValue of(LocalDateTime value) {
    return (index, ps) -> ps.setTimestamp(index, Timestamp.valueOf(value));
  }

  void accept(int index, PreparedStatement ps) throws SQLException;
}
