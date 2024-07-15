package com.petadoption.animalapplication.dao;

import com.petadoption.animalapplication.entity.AdoptionApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class AdoptionApplicationDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_APPLICATION_SQL =
            "INSERT INTO adoption_applications (animal_id, applicant_id, application_date, adoption_status, notes) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_APPLICATIONS_SQL =
            "SELECT * FROM adoption_applications";
    private static final String SELECT_APPLICATION_BY_ID_SQL =
            "SELECT * FROM adoption_applications WHERE id = ?";
    private static final String UPDATE_APPLICATION_SQL =
            "UPDATE adoption_applications SET adoption_status = ?, notes = ? WHERE id = ?";
    private static final String DELETE_APPLICATION_SQL =
            "DELETE FROM adoption_applications WHERE id = ?";

    public int insertApplication(AdoptionApplication application) {
        return jdbcTemplate.update(INSERT_APPLICATION_SQL,
                application.getAnimalId(),
                application.getApplicantId(),
                application.getApplicationDate(),
                application.getAdoptionStatus(),
                application.getNotes());
    }

    public List<AdoptionApplication> getAllApplications() {
        return jdbcTemplate.query(SELECT_ALL_APPLICATIONS_SQL, new AdoptionApplicationRowMapper());
    }

    public Optional<AdoptionApplication> getApplicationById(Long id) {
        return jdbcTemplate.query(SELECT_APPLICATION_BY_ID_SQL, new Object[]{id}, new AdoptionApplicationRowMapper())
                .stream()
                .findFirst();
    }

    public int updateApplication(Long id, String status, String notes) {
        return jdbcTemplate.update(UPDATE_APPLICATION_SQL, status, notes, id);
    }

    public int deleteApplication(Long id) {
        return jdbcTemplate.update(DELETE_APPLICATION_SQL, id);
    }

    private static class AdoptionApplicationRowMapper implements RowMapper<AdoptionApplication> {
        @Override
        public AdoptionApplication mapRow(ResultSet rs, int rowNum) throws SQLException {
            AdoptionApplication application = new AdoptionApplication();
            application.setId(rs.getLong("id"));
            application.setAnimalId(rs.getLong("animal_id"));
            application.setApplicantId(rs.getLong("applicant_id"));
            application.setApplicationDate(rs.getObject("application_date", LocalDateTime.class));
            application.setAdoptionStatus(rs.getString("adoption_status"));
            application.setNotes(rs.getString("notes"));
            return application;
        }
    }
}
