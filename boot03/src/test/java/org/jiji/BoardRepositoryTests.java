package org.jiji;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jiji.domain.Board;
import org.jiji.domain.QBoard;
import org.jiji.persistence.BoardRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.querydsl.core.BooleanBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardRepositoryTests {

	@Autowired
	private BoardRepository boardRepo;
	
   
   
	@Test
	public void testInsert200() {
		
		for(int i=201; i<=400; i++) {
			
			Board board = new Board();
			board.setTitle("제목..." + i);
			board.setContent("내용....." + i + " 채우기 ");
			board.setWriter("user0" + (i % 10));
			boardRepo.save(board);
			
		}
	}
	
	
	@Test
	public void testByTitle() {
		
		boardRepo.findBoardByTitle("제목...10")
			.forEach(board -> System.out.println(board));
	}
	
	
	@Test
	public void testByWriter() {
		
		Collection<Board> results = boardRepo.findByWriter("user00");
		
		results.forEach(board -> System.out.println(board));
	}
	
	@Test
	public void testByWriterContaining() {
		
		Collection<Board> results = boardRepo.findByWriterContaining("05");
		
		results.forEach(board -> System.out.println(board));
	}
	
	

	@Test
	public void testByTitleAndBno() {
		Collection<Board> results = boardRepo.findByTitleContainingAndBnoGreaterThan("5", 50L);
		
		results.forEach(board -> System.out.println(board));
	}
	
	@Test
	public void testBnoOrderby() {
		Collection<Board> results = boardRepo.findByBnoGreaterThanOrderByBnoDesc(90L);
		
		results.forEach(board -> System.out.println(board));
	}
	
	@Test
	public void testBnoOrderByPaging() {
		
	  Pageable paging = PageRequest.of(4, 20);
      Collection<Board> results = boardRepo.findByBnoGreaterThanOrderByBnoDesc(0L, paging);
      results.forEach(board -> System.out.println(board));
		
	}
	
	
//	@Test
//	public void testBnoPagingSort() {
//		
//	  Pageable paging = PageRequest.of(0, 10, Sort.Direction.ASC, "bno");
//      Collection<Board> results = boardRepo.findByBnoGreaterThan(0L, paging);
//      results.forEach(board -> System.out.println(board));
//		
//	}
	
	
	@Test
	public void testBnoPagingSort() {
		
	  Pageable paging = PageRequest.of(0, 10, Sort.Direction.ASC, "bno");
      Page<Board> results = boardRepo.findByBnoGreaterThan(0L, paging);
      
      System.out.println("PAGE SIZE: " + results.getSize());
      System.out.println("TOTAL PAGES: " + results.getTotalPages());
      System.out.println("TOTAL COUNT: " + results.getTotalElements());
      System.out.println("NEXT: " + results.nextPageable());
      
      List<Board> list = results.getContent();
      
      list.forEach(board -> System.out.println(board));
		
	}
	
	@Test
	public void testtByTitle2() {
		boardRepo.findByTitle("17")
			.forEach(board -> System.out.println(board));
	}
	
	
	@Test
	public void testtByTitle17() {
		boardRepo.findByTitle2("17")
			.forEach(arr -> System.out.println(Arrays.toString(arr)));
	}
	
	@Test
	public void testtByTitle17_2() {
		boardRepo.findByTitle3("17")
			.forEach(arr -> System.out.println(Arrays.toString(arr)));
	}
	
	
	@Test
	public void testByPaging() {
		Pageable pageable = PageRequest.of(0, 10);
		boardRepo.findByPage(pageable).forEach(board -> System.out.println(board));
	}
	
	
	@Test
	public void testPredicate() {
		
		String type = "t";
		String keyword = "17";
		
		BooleanBuilder builder = new BooleanBuilder();
		
		QBoard board  = QBoard.board;
		
		if(type.equals("t")) {
			builder.and(board.title.like("%" + keyword + "%"));
		}
		
		builder.and(board.bno.gt(0L));
		
		builder.and(board.writer.like("%" + "00" + "%"));
		
		Pageable pagebable = PageRequest.of(0, 10);
		
		Page<Board> results = boardRepo.findAll(builder, pagebable);
		
		 System.out.println("PAGE SIZE: " + results.getSize());
	      System.out.println("TOTAL PAGES: " + results.getTotalPages());
	      System.out.println("TOTAL COUNT: " + results.getTotalElements());
	      System.out.println("NEXT: " + results.nextPageable());
	      
	       
	      List<Board> list = results.getContent();
	      
	      list.forEach(b -> System.out.println(b));
	}
	
   
   
}
