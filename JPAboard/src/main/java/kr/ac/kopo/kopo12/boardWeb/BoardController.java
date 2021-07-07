package kr.ac.kopo.kopo12.boardWeb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.ac.kopo.kopo12.dao.BoardRepository;
import kr.ac.kopo.kopo12.domain.Board;

@Controller	//annotation
public class BoardController {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@RequestMapping(value = "/boardSelect")
	public String boardSelect(Model model) {
		List<Board> list= boardRepository.findAll();
		model.addAttribute("boardList", list);
		return "boardSelect";
	}
	
	
}
