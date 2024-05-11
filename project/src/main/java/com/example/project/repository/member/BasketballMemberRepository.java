package com.example.project.repository.member;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.project.model.member.BasketballMember;

@Repository
public interface BasketballMemberRepository extends JpaRepository<BasketballMember, Long> {
    
    @Query(nativeQuery = true, 
        value = "SELECT \r\n" + //
                        "CASE\r\n" + //
                        "WHEN COUNT(m) > 0 THEN true\r\n" + //
                        "ELSE false END\r\n" + //
                        "FROM basketball_member as m\r\n" + //
                        "WHERE m.nickname = :memberName AND m.group_id = :groupID"
    )
    public boolean existsByNameAndGroup(@Param("memberName") String name,@Param("groupID") Long groupID);

    @Query("SELECT bm FROM BasketballGroup bg JOIN bg.members bm WHERE bg.id = :groupID AND bm.nickname = :memberName")
    public Optional<BasketballMember> getByNameAndGroup(@Param("memberName") String name,@Param("groupID") Long groupID);

    @Query("SELECT m FROM BasketballMember m JOIN m.group g WHERE g.id = :groupID")
    public List<BasketballMember> getAllMembersByGroupID(@Param("groupID") Long groupID);
}
