package org.acme.common.sql;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import lombok.Builder;
import lombok.NonNull;

@Builder
public class SqlResultSet {
  @NonNull
  private final ResultSet set;
  @Builder.Default
  private Map<String, CompletionStage<List<?>>> childs = new HashMap<>();

  public <S> CompletionStage<List<?>> getChilds(String name) {
    return childs.containsKey(name) ? childs.get(name)
        : CompletableFuture.completedFuture(List.of());
  }

  public String getString(int columnIndex) throws SQLException {
    return set.getString(columnIndex);
  }

  public boolean getBoolean(int columnIndex) throws SQLException {
    return set.getBoolean(columnIndex);
  }

  public byte getByte(int columnIndex) throws SQLException {
    return set.getByte(columnIndex);
  }

  public short getShort(int columnIndex) throws SQLException {
    return set.getShort(columnIndex);
  }

  public int getInt(int columnIndex) throws SQLException {
    return set.getInt(columnIndex);
  }

  public long getLong(int columnIndex) throws SQLException {
    return set.getLong(columnIndex);
  }

  public float getFloat(int columnIndex) throws SQLException {
    return set.getFloat(columnIndex);
  }

  public double getDouble(int columnIndex) throws SQLException {
    return set.getDouble(columnIndex);
  }

  public byte[] getBytes(int columnIndex) throws SQLException {
    return set.getBytes(columnIndex);
  }

  public Date getDate(int columnIndex) throws SQLException {
    return set.getDate(columnIndex);
  }

  public Time getTime(int columnIndex) throws SQLException {
    return set.getTime(columnIndex);
  }

  public Timestamp getTimestamp(int columnIndex) throws SQLException {
    return set.getTimestamp(columnIndex);
  }

  public InputStream getAsciiStream(int columnIndex) throws SQLException {
    return set.getAsciiStream(columnIndex);
  }

  public InputStream getBinaryStream(int columnIndex) throws SQLException {
    return set.getBinaryStream(columnIndex);
  }

  public String getString(String columnLabel) throws SQLException {
    return set.getString(columnLabel);
  }

  public boolean getBoolean(String columnLabel) throws SQLException {
    return set.getBoolean(columnLabel);
  }

  public byte getByte(String columnLabel) throws SQLException {
    return set.getByte(columnLabel);
  }

  public short getShort(String columnLabel) throws SQLException {
    return set.getShort(columnLabel);
  }

  public int getInt(String columnLabel) throws SQLException {
    return set.getInt(columnLabel);
  }

  public long getLong(String columnLabel) throws SQLException {
    return set.getLong(columnLabel);
  }

  public float getFloat(String columnLabel) throws SQLException {
    return set.getFloat(columnLabel);
  }

  public double getDouble(String columnLabel) throws SQLException {
    return set.getDouble(columnLabel);
  }

  public byte[] getBytes(String columnLabel) throws SQLException {
    return set.getBytes(columnLabel);
  }

  public Date getDate(String columnLabel) throws SQLException {
    return set.getDate(columnLabel);
  }

  public Time getTime(String columnLabel) throws SQLException {
    return set.getTime(columnLabel);
  }

  public Timestamp getTimestamp(String columnLabel) throws SQLException {
    return set.getTimestamp(columnLabel);
  }

  public InputStream getAsciiStream(String columnLabel) throws SQLException {
    return set.getAsciiStream(columnLabel);
  }

  public InputStream getBinaryStream(String columnLabel) throws SQLException {
    return set.getBinaryStream(columnLabel);
  }

  public String getCursorName() throws SQLException {
    return set.getCursorName();
  }

  public ResultSetMetaData getMetaData() throws SQLException {
    return set.getMetaData();
  }

  public Object getObject(int columnIndex) throws SQLException {
    return set.getObject(columnIndex);
  }

  public Object getObject(String columnLabel) throws SQLException {
    return set.getObject(columnLabel);
  }

  public Reader getCharacterStream(int columnIndex) throws SQLException {
    return set.getCharacterStream(columnIndex);
  }

  public Reader getCharacterStream(String columnLabel) throws SQLException {
    return set.getCharacterStream(columnLabel);
  }

  public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
    return set.getBigDecimal(columnIndex);
  }

  public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
    return set.getBigDecimal(columnLabel);
  }

  public int getRow() throws SQLException {
    return set.getRow();
  }

  public int getFetchDirection() throws SQLException {
    return set.getFetchDirection();
  }

  public int getFetchSize() throws SQLException {
    return set.getFetchSize();
  }

  public int getConcurrency() throws SQLException {
    return set.getConcurrency();
  }

  public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
    return set.getObject(columnIndex, map);
  }

  public Ref getRef(int columnIndex) throws SQLException {
    return set.getRef(columnIndex);
  }

  public Blob getBlob(int columnIndex) throws SQLException {
    return set.getBlob(columnIndex);
  }

  public Clob getClob(int columnIndex) throws SQLException {
    return set.getClob(columnIndex);
  }

  public Array getArray(int columnIndex) throws SQLException {
    return set.getArray(columnIndex);
  }

  public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
    return set.getObject(columnLabel, map);
  }

  public Ref getRef(String columnLabel) throws SQLException {
    return set.getRef(columnLabel);
  }

  public Blob getBlob(String columnLabel) throws SQLException {
    return set.getBlob(columnLabel);
  }

  public Clob getClob(String columnLabel) throws SQLException {
    return set.getClob(columnLabel);
  }

  public Array getArray(String columnLabel) throws SQLException {
    return set.getArray(columnLabel);
  }

  public Date getDate(int columnIndex, Calendar cal) throws SQLException {
    return set.getDate(columnIndex, cal);
  }

  public Date getDate(String columnLabel, Calendar cal) throws SQLException {
    return set.getDate(columnLabel, cal);
  }

  public Time getTime(int columnIndex, Calendar cal) throws SQLException {
    return set.getTime(columnIndex, cal);
  }

  public Time getTime(String columnLabel, Calendar cal) throws SQLException {
    return set.getTime(columnLabel, cal);
  }

  public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
    return set.getTimestamp(columnIndex, cal);
  }

  public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
    return set.getTimestamp(columnLabel, cal);
  }

  public URL getURL(int columnIndex) throws SQLException {
    return set.getURL(columnIndex);
  }

  public URL getURL(String columnLabel) throws SQLException {
    return set.getURL(columnLabel);
  }

  public RowId getRowId(int columnIndex) throws SQLException {
    return set.getRowId(columnIndex);
  }

  public RowId getRowId(String columnLabel) throws SQLException {
    return set.getRowId(columnLabel);
  }

  public int getHoldability() throws SQLException {
    return set.getHoldability();
  }

  public NClob getNClob(int columnIndex) throws SQLException {
    return set.getNClob(columnIndex);
  }

  public NClob getNClob(String columnLabel) throws SQLException {
    return set.getNClob(columnLabel);
  }

  public String getNString(int columnIndex) throws SQLException {
    return set.getNString(columnIndex);
  }

  public String getNString(String columnLabel) throws SQLException {
    return set.getNString(columnLabel);
  }

  public Reader getNCharacterStream(int columnIndex) throws SQLException {
    return set.getNCharacterStream(columnIndex);
  }

  public Reader getNCharacterStream(String columnLabel) throws SQLException {
    return set.getNCharacterStream(columnLabel);
  }

  public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
    return set.getObject(columnIndex, type);
  }

  public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
    return set.getObject(columnLabel, type);
  }
}
