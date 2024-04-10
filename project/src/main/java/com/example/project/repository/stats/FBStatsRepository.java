package com.example.project.repository.stats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.project.model.stats.FBStats;

@Repository
public interface FBStatsRepository extends JpaRepository<FBStats, Long> {
 
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE FBStats SET member_id = NULL WHERE member_id = :memberID")
    public void setStatsToMemberReferencesToNull(@Param("memberID") long memberID);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM FBStats s WHERE s.member_id = :memberID")
    public void deleteStatsMappingToMember(@Param("memberID") long memberID);

}