package kr.ac.kopo.kopo12.boardWeb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.ac.kopo.kopo12.dao.BoardItemRepository;
import kr.ac.kopo.kopo12.dao.BoardRepository;
import kr.ac.kopo.kopo12.domain.Board;
import kr.ac.kopo.kopo12.domain.BoardItem;
import kr.ac.kopo.kopo12.service.BoardItemService;


@Controller // annotation
public class BoardItemController {

	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private BoardItemRepository boardItemRepository;

	@Autowired
	private BoardItemService boardItemService;

	@RequestMapping(value = "/PostTable")		//게시판 화면
	public String postTable(Model model, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
			int boardId = Integer.parseInt(request.getParameter("id"));
			int from = Integer.parseInt(request.getParameter("from"));
			if (from < 1) {
				from = 1;
			}
			model.addAttribute("from", from);
			model.addAttribute("boardId", boardId);
			
			PageRequest pageable = PageRequest.of(from-1, 10);
			Page<BoardItem> page = boardItemRepository.findAllByParentIdIsNullAndBoardId(boardId, pageable);
			model.addAttribute("page", page);
			
			Board board = boardRepository.findById(boardId);
			String name = board.getTitle();
			model.addAttribute("name", name);

			List<BoardItem> boardItemList = boardItemRepository.findAllByParentIdIsNullAndBoardId(boardId);
			model.addAttribute("boardItemList", boardItemList);

			int[] boardNum = boardItemService.number(boardItemList, from);
			model.addAttribute("boardNum", boardNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "PostTable";
	}

	@RequestMapping(value = "/PostInsert") //신규 화면
	public String postInsert(Model model, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
			int boardId = Integer.parseInt(request.getParameter("id"));
			model.addAttribute("boardId", boardId);
			Board board = boardRepository.findById(boardId);
			model.addAttribute("name", board.getTitle());
			model.addAttribute("date", boardItemService.date());
		} catch (Exception e) {

		}
		return "PostInsert";
	}

	@RequestMapping(value = "/PostWrite") //게시글 쓰기 화면
	public String postWrite(Model model, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			if (title.contains("<") || content.contains("<")) {	//예외처리
				title = title.replaceAll("<", "&lt;");
				content = content.replaceAll("<", "&lt;");
			}
			int boardId = Integer.parseInt(request.getParameter("id"));
			
			Board board = boardRepository.findById(boardId);
			BoardItem boardItem = new BoardItem(title, content, boardItemService.date());
			boardItem.setBoard(board);
			List<BoardItem> list = new ArrayList<>();
			list.add(boardItem);
			board.setBoardItem(list);
			boardRepository.save(board);

			List<BoardItem> postNumber = boardItemRepository.findAllByBoardId(boardId);
			int id = postNumber.get(postNumber.size() - 1).getId();
			model.addAttribute("boardId", boardId);
			model.addAttribute("id", id);
		} catch (Exception e) {

		}
		return "PostWrite";
	}

	@RequestMapping(value = "/PostView") //게시글 화면
	public String postView(Model model, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
			int boardId = Integer.parseInt(request.getParameter("boardId"));
			int id = Integer.parseInt(request.getParameter("id"));
			model.addAttribute("boardId", boardId);
			model.addAttribute("id", id);
			Board board = boardRepository.findById(boardId);
			model.addAttribute("name", board.getTitle());
			
			List<BoardItem> boardItemList = boardItemRepository.findAllByParentIdIsNullAndBoardId(boardId);
			int number = 0;
			int startNumber = 0;
			for (BoardItem boardItem2 : boardItemList) {
				if (boardItem2.getId() == id) {
					number = startNumber + 1;
				}
				startNumber++;
			}
			BoardItem boardItem = boardItemRepository.findById(id);
			model.addAttribute("boardItem",boardItem);
			String content = boardItem.getContent();
			content = content.replaceAll("\n", "<br>");	//예외처리
			boardItem.setContent(content);
			model.addAttribute("number", number);
			
			//댓글보여주기
			List<BoardItem> showComments = boardItemRepository.findAllByParentId(Long.valueOf(id));		
			model.addAttribute("showComments", showComments);
			
			
		} catch (Exception e) {
		}
		return "PostView";
	}

	@RequestMapping(value = "/CommentInsert") //댓글 쓰기 화면
	public String commentInsert(Model model, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
			int boardId = Integer.parseInt(request.getParameter("boardId"));
			int id = Integer.parseInt(request.getParameter("id"));
			String comment = request.getParameter("comment");
			if (comment.contains("<")) {		//예외처리
				comment = comment.replaceAll("<", "&lt;");
			}
			comment = comment.replaceAll("\n", "<br>");	
			Board board = boardRepository.findById(boardId);
			BoardItem boardItem = new BoardItem(comment, Long.valueOf(id), boardItemService.date());
			boardItem.setBoard(board);
			List <BoardItem> list = new ArrayList<>();
			list.add(boardItem);
			board.setBoardItem(list);
			boardRepository.save(board);
			
			model.addAttribute("boardId", boardId);
			model.addAttribute("id", id);
		} catch (Exception e) {
		}
		return "CommentInsert";
	}

	@RequestMapping(value = "/PostDelete") //게시글 삭제 화면
	public String postDelete(Model model, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
			int id = Integer.parseInt(request.getParameter("id"));
			int boardId = Integer.parseInt(request.getParameter("boardId"));
			boardItemRepository.deleteById(id);
			model.addAttribute("boardId", boardId);
		} catch (Exception e) {
		}
		return "PostDelete";
	}

	@RequestMapping(value = "/CompleteUpdate") //게시글 업데이트 완료 화면
	public String completeUpdate(Model model, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			System.out.println(title + "   " + content);
			if (title.contains("<") || content.contains("<")) {	//예외처리
				title = title.replaceAll("<", "&lt;");
				content = content.replaceAll("<", "&lt;");
			}
			int id = Integer.parseInt(request.getParameter("id"));
			int boardId = Integer.parseInt(request.getParameter("boardId"));
			BoardItem boardItem = boardItemRepository.findById(id);
			String date = boardItemService.date();
			boardItem.setTitle(title);
			boardItem.setContent(content);
			boardItem.setDate(date);
			
			boardItemRepository.flush();
			model.addAttribute("boardId", boardId);
			model.addAttribute("id", id);

		} catch (Exception e) {
		}
		return "CompleteUpdate";
	}

	@RequestMapping(value = "/PostUpdate") //게시글 업데이트 화면
	public String postUpdate(Model model, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
			int id = Integer.parseInt(request.getParameter("id"));
			int boardId = Integer.parseInt(request.getParameter("boardId"));
			model.addAttribute("id", id);
			model.addAttribute("boardId", boardId);

			Board board = boardRepository.findById(boardId);
			String name = board.getTitle();
			model.addAttribute("name", name);
			
			BoardItem boardItem = boardItemRepository.findById(id);
			List<BoardItem> boardItemList = boardItemRepository.findAllByParentIdIsNullAndBoardId(boardId);
			int number = 0;
			int startNumber = 0;
			for (BoardItem boardItem2 : boardItemList) {
				if (boardItem2.getId() == id) {
					number = startNumber + 1;
				}
				startNumber++;
			}
			model.addAttribute("number", number);
			model.addAttribute("boardItem", boardItem);

		} catch (Exception e) {
		}
		return "PostUpdate";
	}

	@RequestMapping(value = "/PostSearch") //게시글 검색
	public String postSearch(Model model, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
			int boardId = Integer.parseInt(request.getParameter("boardId"));
			model.addAttribute("boardId", boardId);

			Board board = boardRepository.findById(boardId);
			String name = board.getTitle();
			model.addAttribute("name", name);

			String keyWord = request.getParameter("keyWord");
			if (keyWord.contains("<")) {	//예외처리
				keyWord = keyWord.replaceAll("<", "&lt;");
			}
			model.addAttribute("keyWord", keyWord);
			String[] spKeyWord = keyWord.split(" ");

			List<BoardItem> boardItemList = boardItemRepository.findAllByParentIdIsNullAndBoardId(boardId);
			HashSet<Integer> hash = new HashSet<Integer>();
			for (BoardItem boardItem : boardItemList) {
				for(String key : spKeyWord) {
					if (boardItem.getContent().contains(key) || boardItem.getTitle().contains(key)) {
						hash.add(boardItem.getId());
					}
				}
			}
			List<BoardItem> boardItemContainList = new ArrayList<BoardItem>();
			for(int e : hash) {
				boardItemContainList.add(boardItemRepository.findById(e));
			}
			model.addAttribute("boardItemContainList", boardItemContainList);
		} catch (Exception e) {
		}
		return "PostSearch";
	}
}
