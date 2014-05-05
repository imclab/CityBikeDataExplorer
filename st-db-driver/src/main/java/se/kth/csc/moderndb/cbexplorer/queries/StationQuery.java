package se.kth.csc.moderndb.cbexplorer.queries;

import org.postgis.Point;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import se.kth.csc.moderndb.cbexplorer.domain.PostgreSQLDatabaseConnection;
import se.kth.csc.moderndb.cbexplorer.parser.data.StationData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Jeannine on 04.05.14.
 */
public class StationQuery extends BikeQuery{

    private final String getXFromPoint = "ST_X(" + PostgreSQLDatabaseConnection.POINT + "::geometry)";
    private final String getYFromPoint = "ST_Y(" + PostgreSQLDatabaseConnection.POINT + "::geometry)";

    private JdbcTemplate jdbcTemplate;

    public StationQuery() {
        super();
        this.jdbcTemplate = super.jdbcTemplate;
    }

    public List<String> giveAllStationNames() {
        System.out.println("Querying for stations names");
        List<String> names = jdbcTemplate.query(
                "select " + PostgreSQLDatabaseConnection.NAME + " from " + PostgreSQLDatabaseConnection.STATION,
                new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getString(PostgreSQLDatabaseConnection.NAME);
                    }
                }
        );
        for (String name : names) {
            System.out.println(name);
        }
        return names;
    }

    public List<StationData> giveFullStationInformationAboutAllStations() {
        System.out.println("Querying for stations");
        List<StationData> results = jdbcTemplate.query(
                "select * from " + PostgreSQLDatabaseConnection.STATION,
                new RowMapper<StationData>() {
                    @Override
                    public StationData mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Point p = givePointForStationWithID(rs.getLong(PostgreSQLDatabaseConnection.ID));
                        return new StationData(rs.getLong(PostgreSQLDatabaseConnection.ID), rs.getString(PostgreSQLDatabaseConnection.NAME),
                                p.getX(), p.getY());
                    }
                }
        );


        for (StationData station : results) {
            System.out.println(station.getName() + "... longitude: " + station.getLongitude() + "... latitude:" + station.getLatitude());
        }
        return results;
    }

    public List<StationData> giveFullStationInformationAboutStationNamed(final String name) {
        System.out.println("Querying for station named" + name);
        List<StationData> result = jdbcTemplate.query("select " + PostgreSQLDatabaseConnection.ID + ", " + getXFromPoint + ", " + getYFromPoint + "from " + PostgreSQLDatabaseConnection.STATION + " where " + PostgreSQLDatabaseConnection.NAME + " = ?",
                new Object[]{name},
                new RowMapper<StationData>() {
                    @Override
                    public StationData mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new StationData(rs.getLong(PostgreSQLDatabaseConnection.ID), name, rs.getDouble(2), rs.getDouble(3));
                    }
                }
        );
        for (StationData station : result) {
            System.out.println(station.getName() + "... longitude: " + station.getLongitude() + "... latitude:" + station.getLatitude());
        }
        return result;
    }

    public List<StationData> giveFullStationInformationAboutStationWithID(final Long id) {
        System.out.println("Querying for station with id" + id);
        List<StationData> result = jdbcTemplate.query("select " + PostgreSQLDatabaseConnection.NAME + ", " + getXFromPoint + ", " + getYFromPoint + "from " + PostgreSQLDatabaseConnection.STATION + " where " + PostgreSQLDatabaseConnection.ID + " = ?",
                new Object[]{id},
                new RowMapper<StationData>() {
                    @Override
                    public StationData mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new StationData(id, rs.getString(PostgreSQLDatabaseConnection.NAME), rs.getDouble(2), rs.getDouble(3));
                    }
                }
        );
        return result;
    }

    public Point givePointForStationWithID(long id) {
        System.out.println("Querying for point with id");
        List<Point> result = jdbcTemplate.query(
                "select " + getXFromPoint + ", " + getYFromPoint + " from " + PostgreSQLDatabaseConnection.STATION + " where " + PostgreSQLDatabaseConnection.ID + " = ?", new Object[]{id},
                new RowMapper<Point>() {
                    @Override
                    public Point mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new Point(rs.getDouble(1), rs.getDouble(2));
                    }
                }
        );

        if (result.size() == 1) {
            return result.get(0);
        } else
            return null;
    }

    public List<Point> givePointForStationNamed(String name) {
        System.out.println("Querying for point with name");
        List<Point> result = jdbcTemplate.query(
                "select " + getXFromPoint + ", " + getYFromPoint + " from " + PostgreSQLDatabaseConnection.STATION + " where " + PostgreSQLDatabaseConnection.NAME + " = ?", new Object[]{name},
                new RowMapper<Point>() {
                    @Override
                    public Point mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new Point(rs.getDouble(1), rs.getDouble(2));
                    }
                }
        );

        return result;
    }


}