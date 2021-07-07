package kr.ac.kopo.kopo12.repository.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import kr.ac.kopo.kopo12.dao.BoardItemRepository;
import kr.ac.kopo.kopo12.dao.BoardRepository;
import kr.ac.kopo.kopo12.domain.BoardItem;
import kr.ac.kopo.kopo12.service.BoardItemService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class test {
	
	@Autowired
	private BoardItemRepository boardItemRepository;
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private BoardItemService boardItemService;

	@Test
	void search() {
		List<BoardItem> boardItemList = boardItemRepository.findAllByParentIdIsNullAndBoardId(1);
		List<BoardItem> boardItemContainList = new ArrayList<BoardItem>();
		HashSet<Integer> hash = new HashSet<Integer>();
		for (BoardItem boardItem : boardItemList) {
				if (boardItem.getContent().contains("지금") || boardItem.getTitle().contains("지금")) {
					hash.add(boardItem.getId());
				}
		}
		System.out.println(boardItemRepository.findById(8).getTitle());
		
		//System.out.println(hash.size());
		for(int e : hash) {
			boardItemContainList.add(boardItemRepository.findById(e));
		//boardItemContainList.add(boardItemRepository.findById(e));
		}
		System.out.println(boardItemContainList.size());
		//System.out.println(boardItemContainList.size());
		
	}
	

//	@Test
//	void findbyid3() {
//		Optional<BoardItem> boardItem =  boardItemRepository.findById(5);
//		System.out.println("boardId : " + boardItem.get().getBoard().getId() + "title = " + boardItem.get().getBoard().getTitle());
//	}
//	
	
//	@Test
//	void findbyid4() {
////		List<BoardItem> boardItemList = boardItemRepository.findAllByParentIdIsNullAndBoardId(1);
//		BoardItem boardItem = new BoardItem("cute", 10L, 1, boardItemService.date());
//		boardItemRepository.save(boardItem);
////		System.out.println(showComments.get(1).getContent());
//	}
//	
//	@Test
//	void create() {
//		Board board = boardRepository.findById(1);
//		BoardItem boardItem = new BoardItem("fasdfdsf", "fdsfdasf", boardItemService.date());
//		boardItem.setBoard(board);
//		List<BoardItem> list = new ArrayList<>();
//		list.add(boardItem);
//		board.setBoardItem(list);
//		boardRepository.save(board);

//		BoardItem boardItem = new BoardItem("fdsfsdf", "fadsfdsfds", boardItemService.date());
//		List<BoardItem> list = new ArrayList<>();
//		list.add(boardItem);
//		board1.setBoardItem(list);
//		boardRepository.save(board1);
		
//		BoardItem boardItem1 = new BoardItem();
//		boardItem1.setTitle("boardItem1");
//		boardItem1.setBoard(board1);
//		
//		BoardItem boardItem2 = new BoardItem();
//		boardItem2.setTitle("boardItem2");
//		boardItem2.setBoard(board1);
//		
//		List<BoardItem> list = new ArrayList<>();
//		list.add(boardItem1);
//		list.add(boardItem2);
//		
//		board1.setBoardItem(list);
	}
