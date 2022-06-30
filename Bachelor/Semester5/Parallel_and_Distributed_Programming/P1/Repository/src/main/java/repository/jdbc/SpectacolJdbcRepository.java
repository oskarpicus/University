package repository.jdbc;

import domain.Spectacol;
import repository.SpectacolRepository;
import repository.jdbc.utils.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SpectacolJdbcRepository extends AbstractJdbcRepository<Integer, Spectacol> implements SpectacolRepository {
    public SpectacolJdbcRepository(JdbcUtils jdbcUtils) {
        super(jdbcUtils);
    }

    @Override
    protected PreparedStatement getSaveStatement(Connection connection, Spectacol entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO Spectacole(data, titlu, pret, sold) VALUES (?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
        statement.setDate(1, new java.sql.Date(entity.getData().getTime()));
        statement.setString(2, entity.getTitlu());
        statement.setFloat(3, entity.getPret());
        statement.setFloat(4, entity.getPret());
        return statement;
    }

    @Override
    protected PreparedStatement getFindStatement(Connection connection, Integer integer) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Spectacole WHERE id=?;");
        statement.setInt(1, integer);
        return statement;
    }

    @Override
    protected PreparedStatement getFindAllStatement(Connection connection) throws SQLException {
        return connection.prepareStatement("SELECT * FROM Spectacole;");
    }

    @Override
    protected PreparedStatement getUpdateStatement(Connection connection, Spectacol entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE Spectacole " +
                "SET data=?, titlu=?, pret=?, sold=? " +
                "WHERE id=?;");
        statement.setDate(1, new java.sql.Date(entity.getData().getTime()));
        statement.setString(2, entity.getTitlu());
        statement.setFloat(3, entity.getPret());
        statement.setFloat(4, entity.getSold());
        statement.setInt(5, entity.getId());
        return statement;
    }

    @Override
    protected Spectacol extractEntity(ResultSet resultSet) {
        Spectacol spectacol = null;
        try {
            int id = resultSet.getInt("id");
            Date data = resultSet.getDate("data");
            String titlu = resultSet.getString("titlu");
            float pret = resultSet.getFloat("pret");
            float sold = resultSet.getFloat("sold");
            spectacol = new Spectacol(id, data, titlu, pret, sold);
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
        return spectacol;
    }

    @Override
    public List<Integer> getLocuriVandute(Spectacol spectacol) {
        List<Integer> result = new ArrayList<>();
        try (Connection connection = jdbcUtils.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT nr_loc FROM SpectacolLocuriVandute WHERE id_spectacol=?;")) {
                statement.setInt(1, spectacol.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        result.add(resultSet.getInt("nr_loc"));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
        return result;
    }

    @Override
    public int save(Spectacol entity) {
        int lastId = super.save(entity);
        entity.setId(lastId);
        return lastId;
    }
}
