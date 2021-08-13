package ${package}.db.command;

import io.vertx.sqlclient.Row;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.apache.log4j.Logger;


public abstract class BaseCommand {

  private static final Logger LOGGER = Logger.getLogger("BaseCommand");

  protected Timestamp offsetDateTimeToTimestamp(Row row, String columnName) {
    LOGGER.info(String.format("converting: OffsetDateTime To Timestamp for %s", columnName));
    if (row.getOffsetDateTime(columnName) != null) {
      Timestamp result =
          Timestamp.valueOf(
              row.getOffsetDateTime(columnName)
                  .atZoneSameInstant(ZoneOffset.systemDefault())
                  .toLocalDateTime());
      result.setNanos(row.getOffsetDateTime(columnName).getNano());
      return result;
    }
    return null;
  }

  protected OffsetDateTime timeStampToOffsetDateTime(Timestamp time) {
    return time != null
      ? OffsetDateTime.ofInstant(time.toInstant(), ZoneOffset.systemDefault()) : null;
  }
}
