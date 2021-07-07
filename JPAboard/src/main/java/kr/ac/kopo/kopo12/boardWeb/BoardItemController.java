package kr.ac.kopo.kopo12.boardWeb;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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

	@RequestMapping(value = "/PostTable")
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

			Board board = boardRepository.findById(boardId);
			String name = board.getTitle();
			model.addAttribute("name", name);

			List<BoardItem> boardItemList = boardItemRepository.findAllByParentIdIsNullAndBoardId(boardId);
			model.addAttribute("boardItemList", boardItemList);

			int[] boardNum = boardItemService.number(boardItemList, from);
			int end = boardNum[0];
			int first = boardNum[1];
			model.addAttribute("end", end);
			model.addAttribute("first", first);
			model.addAttribute("boardNum", boardNum);

			int boardItemSize = boardItemList.size();
			model.addAttribute("boardItemSize", boardItemSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "PostTable";
	}

	@RequestMapping(value = "/PostInsert")
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

	@RequestMapping(value = "/PostWrite")
	public String postWrite(Model model, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			if (title.contains("<") || content.contains("<")) {
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

	@RequestMapping(value = "/PostView")
	public String postView(Model model, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
			int boardId = Integer.parseInt(request.getParameter("boardId"));
			int id = Integer.parseInt(request.getParameter("id"));
			model.addAttribute("boardId", boardId);
			model.addAttribute("id", id);
			Board board = boardRepository.findById(boardId);
			model.addAttribute("name", board.getTitle());

			List<BoardItem> boardItemList = boardItemRepository.findAllByBoardId(boardId);
			int number = 0;
			int startNumber = 0;
			for (BoardItem boardItem2 : boardItemList) {
				if (boardItem2.getId() == id) {
					number = startNumber + 1;
				}
				startNumber++;
			}
			BoardItem boardItem = boardItemRepository.findById(id);
			String title = boardItem.getTitle();
			String content = boardItem.getContent();
			String date = boardItem.getDate();
			content = content.replaceAll("\n", "<br>");
			model.addAttribute("number", number);
			model.addAttribute("title", title);
			model.addAttribute("date", date);
			model.addAttribute("content", content);
			
			//댓글구현하기
			List<BoardItem> showComments = boardItemRepository.findAllByParentId(Long.valueOf(id));		
			model.addAttribute("showComments", showComments);
			
			
		} catch (Exception e) {
		}
		return "PostView";
	}

	@RequestMapping(value = "/CommentInsert")
	public String commentInsert(Model model, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
			int boardId = Integer.parseInt(request.getParameter("boardId"));
			int id = Integer.parseInt(request.getParameter("id"));
			String comment = request.getParameter("comment");
			if (comment.contains("<")) {
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

	@RequestMapping(value = "/PostDelete")
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

	@RequestMapping(value = "/CompleteUpdate")
	public String completeUpdate(Model model, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			if (title.contains("<") || content.contains("<")) {
				title = title.replaceAll("<", "&lt;");
				content = content.replaceAll("<", "&lt;");
			}
			int id = Integer.parseInt(request.getParameter("id"));
			int boardId = Integer.parseInt(request.getParameter("boardId"));
			BoardItem boardItem = boardItemRepository.findById(id);
			boardItem.setTitle(title);
			boardItem.setContent(content);
			
			boardItemRepository.flush();
			model.addAttribute("boardId", boardId);
			model.addAttribute("id", id);

		} catch (Exception e) {
		}
		return "CompleteUpdate";
	}

	@RequestMapping(value = "/PostUpdate")
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
			List<BoardItem> boardItemList = boardItemRepository.findAllByBoardId(boardId);
			int size = boardItemList.size();
			int number = 0;
			int startNumber = 0;
			for (BoardItem boardItem2 : boardItemList) {
				if (boardItem2.getId() == id) {
					number = startNumber + 1;
				}
				startNumber++;
			}
			String title = boardItem.getTitle();
			String content = boardItem.getContent();
			String date = boardItemService.date();
			model.addAttribute("number", number);
			model.addAttribute("title", title);
			model.addAttribute("date", date);
			model.addAttribute("content", content);

		} catch (Exception e) {
		}
		return "PostUpdate";
	}

	@RequestMapping(value = "/PostSearch")
	public String postSearch(Model model, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
			int boardId = Integer.parseInt(request.getParameter("boardId"));
			model.addAttribute("boardId", boardId);

			Board board = boardRepository.findById(boardId);
			String name = board.getTitle();
			model.addAttribute("name", name);

			String keyWord = request.getParameter("keyWord");
			if (keyWord.contains("<")) {
				keyWord = keyWord.replaceAll("<", "&lt;");
			}
			model.addAttribute("keyWord", keyWord);
			String[] spKeyWord = keyWord.split(" ");
			model.addAttribute("spKeyWord", spKeyWord);
			
			List<BoardItem> boardItemList = boardItemRepository.findAllByBoardId(boardId);
			List<BoardItem> boardItemContainList = new ArrayList<BoardItem>();
			int number = 1;
			HashSet<Integer> hash = new HashSet<Integer>();
			for (BoardItem boardItem : boardItemList) {
				for (String key : spKeyWord) {
					if (boardItem.getContent().contains(key) || boardItem.getTitle().contains(key)) {
						hash.add(boardItem.getId());
					}
				}
			}
			for(int e : hash) {
				boardItemContainList.add(boardItemRepository.findById(e));
			}
			model.addAttribute("boardItemContainList", boardItemContainList);
		} catch (Exception e) {
		}
		return "PostSearch";
	}
}
