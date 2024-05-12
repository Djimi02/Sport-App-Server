package com.example.project.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class UUIDRepository {
    
    private EntityManager entityManager;

    public String getGroupTypeByUUID(String uuid) {

        String query = "SELECT g.dtype FROM app_group AS g WHERE g.uuid = :uuid";
        Query result = entityManager.createNativeQuery(query, String.class);
        result.setParameter("uuid", UUID.fromString(uuid));

        String gtype = null;
        try {
            gtype = (String) result.getSingleResult();
        } catch (NoResultException e) {
            System.out.print(e.toString());
        } catch (NonUniqueResultException e) {
            System.out.print(e.toString());
        }

        return gtype;
    }

}