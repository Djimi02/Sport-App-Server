package com.example.project.repository.member;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.project.model.member.FootballMember;

@Repository
public interface FootballMemberRepository extends JpaRepository<FootballMember, Long> {
    @Query(nativeQuery = true, 
        value = "SELECT \r\n" + //
                        "CASE\r\n" + //
                        "WHEN COUNT(m) > 0 THEN true\r\n" + //
                        "ELSE false END\r\n" + //
                        "FROM football_member as m\r\n" + //
                        "WHERE m.nickname = :memberName AND m.group_id = :groupID"
    )
    public boolean existsByNameAndGroup(@Param("memberName") String name,@Param("groupID") Long groupID);

    @Query("SELECT fm FROM FootballGroup fg JOIN fg.members fm WHERE fg.id = :groupID AND fm.nickname = :memberName")
    public Optional<FootballMember> getByNameAndGroup(@Param("memberName") String name,@Param("groupID") Long groupID);
}