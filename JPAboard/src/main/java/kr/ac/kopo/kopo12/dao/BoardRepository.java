package kr.ac.kopo.kopo12.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.ac.kopo.kopo12.domain.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer>{
	List<Board> findAll();
	Board findById(int id);
	Page<Board> findAllByTitle(String title, Pageable pageable);
}
