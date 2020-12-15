package com.example.FlowFireHub.Respositories;

import com.example.FlowFireHub.Domains.Friend;
import com.example.FlowFireHub.Utilities.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import javax.transaction.Transactional;
import java.util.List;


public interface FriendRepository extends JpaRepository<Friend, Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE friend SET is_active = true WHERE friend_id = :friendid and user_id = :userid", nativeQuery = true)
    Integer acceptFriend(Long friendid, Long userid);

    @Query(value = "select U.username, U.id from user U where U.id in (select F.user_id from friend F where F.friend_id = :id and is_active = false)", nativeQuery = true)
    List<UserType> getIncomming(Long id);

    @Query(value = "select U.username, U.id from user U where U.id in (select F.friend_id from friend F where F.user_id = :id and is_active = false)", nativeQuery = true)
    List<UserType> getPending(Long id);

    @Query(value = "select username, id from user INNER JOIN friend on user.id = friend.friend_id where friend.user_id = :id and IS_ACTIVE = 'TRUE'\n" +
            "union\n" +
            "select username, id\n" +
            "from user\n" +
            "INNER JOIN friend on user.id = friend.user_id where friend.friend_id = :id and IS_ACTIVE = 'TRUE'", nativeQuery = true)
    List<UserType> getFriends(Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE from Friend where friend_id = :friendid and user_id = :userid AND is_active = false", nativeQuery = true)
    Integer declineFriend(Long friendid, Long userid);

    @Modifying
    @Transactional
    @Query(value = "DELETE from user where id in (select friend_id from friend where user_id=1 and is_active = true) or id in (select user_id from friend where friend_id=1 and is_active = true)", nativeQuery = true)
    Integer deleteFriend(Long friendid, Long userid);


}
