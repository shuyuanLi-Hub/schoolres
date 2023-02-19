package org.shuyuan.schoolres.dao;

import org.shuyuan.schoolres.domain.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MessageDao extends CrudRepository<Message, Integer>, PagingAndSortingRepository<Message, Integer>
{

}
