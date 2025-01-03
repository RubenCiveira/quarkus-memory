package org.acme.common.sql;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public interface SqlParameterValue {
  static SqlParameterValue of(String value) {
    return (index, ps) -> ps.setString(index, value);
  }

  static SqlParameterValue ofNullString() {
    return (index, ps) -> ps.setNull(index, Types.VARCHAR);
  }

  static SqlParameterValue of(long value) {
    return (index, ps) -> ps.setLong(index, value);
  }

  static SqlParameterValue ofNullLong() {
    return (index, ps) -> ps.setNull(index, Types.NUMERIC);
  }

  static SqlParameterValue of(int value) {
    return (index, ps) -> ps.setInt(index, value);
  }

  static SqlParameterValue ofNullInteger() {
    return (index, ps) -> ps.setNull(index, Types.INTEGER);
  }

  static SqlParameterValue of(double value) {
    return (index, ps) -> ps.setDouble(index, value);
  }

  static SqlParameterValue ofNullDouble() {
    return (index, ps) -> ps.setNull(index, Types.DOUBLE);
  }

  static SqlParameterValue of(float value) {
    return (index, ps) -> ps.setFloat(index, value);
  }

  static SqlParameterValue ofNullFloat() {
    return (index, ps) -> ps.setNull(index, Types.FLOAT);
  }

  static SqlParameterValue of(boolean value) {
    return (index, ps) -> ps.setBoolean(index, value);
  }

  static SqlParameterValue ofNullBoolean() {
    return (index, ps) -> ps.setNull(index, Types.BOOLEAN);
  }

  static SqlParameterValue of(byte value) {
    return (index, ps) -> ps.setByte(index, value);
  }

  static SqlParameterValue ofNullByte() {
    return (index, ps) -> ps.setNull(index, Types.SMALLINT);
  }

  static SqlParameterValue of(short value) {
    return (index, ps) -> ps.setShort(index, value);
  }

  static SqlParameterValue ofNullShort() {
    return (index, ps) -> ps.setNull(index, Types.SMALLINT);
  }

  static SqlParameterValue of(BigDecimal value) {
    return (index, ps) -> ps.setBigDecimal(index, value);
  }

  static SqlParameterValue ofNullBigDecimal() {
    return (index, ps) -> ps.setNull(index, Types.BIGINT);
  }

  static SqlParameterValue of(Date value) {
    return (index, ps) -> ps.setDate(index, value);
  }

  static SqlParameterValue ofNullDate() {
    return (index, ps) -> ps.setNull(index, Types.DATE);
  }

  static SqlParameterValue of(Time value) {
    return (index, ps) -> ps.setTime(index, value);
  }

  static SqlParameterValue ofNullTime() {
    return (index, ps) -> ps.setNull(index, Types.TIME);
  }

  static SqlParameterValue of(Timestamp value) {
    return (index, ps) -> ps.setTimestamp(index, value);
  }

  static SqlParameterValue ofNullTimestamp() {
    return (index, ps) -> ps.setNull(index, Types.TIMESTAMP);
  }

  static SqlParameterValue ofNullStream() {
    return (index, ps) -> ps.setNull(index, Types.BINARY);
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

  static SqlParameterValue of(LocalDate value) {
    return (index, ps) -> ps.setDate(index, Date.valueOf(value));
  }

  static SqlParameterValue ofNullLocalDate() {
    return (index, ps) -> ps.setNull(index, Types.DATE);
  }

  static SqlParameterValue of(LocalDateTime value) {
    return (index, ps) -> ps.setTimestamp(index, Timestamp.valueOf(value));
  }

  static SqlParameterValue ofNullLocalDateTime() {
    return (index, ps) -> ps.setNull(index, Types.TIMESTAMP);
  }

  static SqlParameterValue of(OffsetDateTime value) {
    return (index, ps) -> ps.setTimestamp(index, Timestamp.valueOf(value.toLocalDateTime()));
  }

  static SqlParameterValue ofNullOffsetDateTime() {
    return (index, ps) -> ps.setNull(index, Types.TIMESTAMP);
  }


  void accept(int index, PreparedStatement ps) throws SQLException;
}
