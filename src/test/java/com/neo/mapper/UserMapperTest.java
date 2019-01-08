package com.neo.mapper;

import com.neo.Application;
import com.neo.entity.UserEntity;
import com.neo.enums.UserSexEnum;
import com.neo.mapper.primary.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserMapperTest {

	@Autowired
	private com.neo.mapper.primary.UserMapper userMapper;

	@Autowired
	@Qualifier("primarySqlSessionFactory")
	SqlSessionFactory sqlSessionFactory;

	@Test
	public void testInsert() throws Exception {
		userMapper.insert(new UserEntity("aa", "a123456", UserSexEnum.MAN));
		userMapper.insert(new UserEntity("bb", "b123456", UserSexEnum.WOMAN));
		userMapper.insert(new UserEntity("cc", "b123456", UserSexEnum.WOMAN));

		Assert.assertEquals(3, userMapper.getAll().size());
	}

	@Test
	public void testQuery() throws Exception {
		List<UserEntity> users = userMapper.getAll();
		if(users==null || users.size()==0){
			System.out.println("is null");
		}else{
			System.out.println(users.toString());
		}
	}
	
	
	@Test
	public void testUpdate() throws Exception {
		UserEntity user = userMapper.getOne(6l);
		System.out.println(user.toString());
		user.setNickName("neo");
		userMapper.update(user);
		Assert.assertTrue(("neo".equals(userMapper.getOne(6l).getNickName())));
	}


	@Test
	public void testLocalCache() throws Exception {
		SqlSession sqlSession1 = sqlSessionFactory.openSession(true);
		SqlSession sqlSession2 = sqlSessionFactory.openSession(true);

		UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);
		UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);

		System.out.println(userMapper1.getOne(32L));
		sqlSession1.close();
		System.out.println(userMapper2.getOne(32L));
		System.out.println(userMapper2.getOne(32L));
		System.out.println(userMapper2.getOne(32L));



	}

	@Test
	public void testLocalCacheClear() throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession(true); // 自动提交事务
		UserMapper studentMapper = sqlSession.getMapper(UserMapper.class);

		System.out.println(studentMapper.getOne(32L));
		// 增删改会清空缓存
		System.out.println("增加了" + 1 + "个学生");
		studentMapper.insert(new UserEntity("aa", "a123456", UserSexEnum.MAN));
		// 会从数据库查数据
		System.out.println(studentMapper.getOne(32L));

		sqlSession.close();
	}



}