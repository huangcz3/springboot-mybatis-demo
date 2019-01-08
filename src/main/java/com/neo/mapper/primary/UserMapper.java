package com.neo.mapper.primary;

import java.util.List;
import java.util.Map;

import com.neo.entity.UserEntity;
import org.springframework.stereotype.Repository;

public interface UserMapper {
	
	List<UserEntity> getAll();
	
	UserEntity getOne(Long id);

	int insert(UserEntity user);

	void update(UserEntity user);

	void delete(Long id);

    List<Map> getLogs();
}