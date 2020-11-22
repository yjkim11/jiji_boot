package org.jiji;

import org.jiji.domain.Board;
import org.jiji.persistence.BoardRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest
class Boot03ApplicationTests {
	
	@Autowired
	private BoardRepository repo;
	
	@Test
	public void testInsert200() {
		
		for(int i=1; i<=200; i++) {
			
			Board board = new Board();
			board.setTitle("제목..." + i);
			board.setContent("내용....." + i + " 채우기 ");
			board.setWriter("user0" + (i % 10));
			repo.save(board);
			
		}
	}


}
