package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import springbook.user.domain.User;

public class UserDao {
	private ConnectionMaker connectionMaker;
	
	public UserDao(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}

	public void add(User user) throws ClassNotFoundException, SQLException {
		Connection conn = connectionMaker.makeConnection();

		PreparedStatement pstmt = conn.prepareStatement("insert into users(id, name, password) values(?,?,?)");
		
		pstmt.setString(1, user.getId());
		pstmt.setString(2, user.getName());
		pstmt.setString(3, user.getPassword());

		pstmt.executeUpdate();

		conn.close();
		pstmt.close();
	}

	public User get(String id) throws ClassNotFoundException, SQLException {
		Connection conn = connectionMaker.makeConnection();
		
		PreparedStatement pstmt = conn.prepareStatement("select * from users where id = ?");

		pstmt.setString(1, id);

		ResultSet rs = pstmt.executeQuery();
		rs.next();
		User user = new User();
		user.setId(rs.getString("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("password"));

		conn.close();
		pstmt.close();
		rs.close();

		return user;
	}
}
