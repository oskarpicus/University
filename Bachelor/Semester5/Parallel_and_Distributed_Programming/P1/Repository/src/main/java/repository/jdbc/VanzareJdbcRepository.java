package repository.jdbc;

import domain.Spectacol;
import domain.Vanzare;
import repository.VanzareRepository;
import repository.jdbc.utils.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VanzareJdbcRepository extends AbstractJdbcRepository<Integer, Vanzare> implements VanzareRepository {
    public VanzareJdbcRepository(JdbcUtils jdbcUtils) {
        super(jdbcUtils);
    }

    @Override
    public Iterable<Vanzare> getBySpectacol(Spectacol spectacol) {
        List<Vanzare> result = new ArrayList<>();
        try (Connection connection = jdbcUtils.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Vanzari WHERE id_spectacol=?;")) {
                statement.setInt(1, spectacol.getId());
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        result.add(extractEntity(resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }

        return result;
    }

    @Override
    public List<Integer> getLocuriVandute(Vanzare vanzare) {
        List<Integer> result = new ArrayList<>();
        try (Connection connection = jdbcUtils.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT nr_loc FROM VanzariLocuri WHERE id_vanzare=?;")) {
                statement.setInt(1, vanzare.getId());
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
    protected PreparedStatement getSaveStatement(Connection connection, Vanzare entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO Vanzari(data, nr_bilete_vandute, suma, id_spectacol) VALUES (?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
        statement.setDate(1, new java.sql.Date(entity.getData().getTime()));
        statement.setInt(2, entity.getNrBileteVandute());
        statement.setFloat(3, entity.getSuma());
        statement.setInt(4, entity.getSpectacol().getId());
        return statement;
    }

    @Override
    protected PreparedStatement getFindStatement(Connection connection, Integer integer) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Vanzari WHERE id=?;");
        statement.setInt(1, integer);
        return statement;
    }

    @Override
    protected PreparedStatement getFindAllStatement(Connection connection) throws SQLException {
        return connection.prepareStatement("SELECT * FROM Vanzari;");
    }

    @Override
    protected PreparedStatement getUpdateStatement(Connection connection, Vanzare entity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE Vanzari " +
                "SET data=?, nr_bilete_vandute=?, suma=? " +
                "WHERE id=?;");
        statement.setDate(1, new java.sql.Date(entity.getData().getTime()));
        statement.setInt(2, entity.getNrBileteVandute());
        statement.setFloat(3, entity.getSuma());
        statement.setInt(4, entity.getId());
        return statement;
    }

    @Override
    protected Vanzare extractEntity(ResultSet resultSet) {
        try {
            int id = resultSet.getInt("id");
            Date data = resultSet.getDate("data");
            int nrBileteVandute = resultSet.getInt("nr_bilete_vandute");
            float suma = resultSet.getFloat("suma");
            int idSpectacol = resultSet.getInt("id_spectacol");
            return new Vanzare(id, data, nrBileteVandute, suma, new Spectacol(idSpectacol));
        } catch (SQLException e) {
            System.out.println("Error " + e);
            return null;
        }
    }

    @Override
    public int save(Vanzare vanzare) {
        int result = super.save(vanzare);

        if (result == 0) {
            return result;
        }

        vanzare.setId(result);

        for (int locVandut : vanzare.getListaLocuriVandute()) {
            saveVanzareLoc(vanzare, locVandut);
        }

        return result;
    }

    private void saveVanzareLoc(Vanzare vanzare, int nrLoc) {
        // todo transaction - we must save successfully every seat
        // niciun seat sa nu fie salvat daca nu se salveaza toate
        try (Connection connection = jdbcUtils.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO VanzariLocuri(id_vanzare, nr_loc) VALUES (?,?);")) {
                statement.setInt(1, vanzare.getId());
                statement.setInt(2, nrLoc);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
    }
}
