package kr.ac.kopo.kopo12.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.ac.kopo.kopo12.domain.BoardItem;

@Repository
public interface BoardItemRepository extends JpaRepository<BoardItem, Integer> {
	List<BoardItem> findAllByBoardId(int boardId);
	List<BoardItem> findAllByParentIdIsNullAndBoardId(int boardId);
	List<BoardItem> findAllByParentId(Long id);
	BoardItem findById(int id);
}
