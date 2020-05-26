package Java_Campus_Project.Java_Warriors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.Test;

import warriors.contracts.Map;
import warriors.engine.board.Board;
import warriors.engine.board.jsonAdapter.JsonBoardCreator;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
    
//    @Test
//    public void shouldSetWarriorLifeTo10() {
//    	Warrior warrior = new Warrior("Bob", 5, 5);
//    	
//    	warrior.setLife(10);
//    	
//    	assertEquals(10, warrior.getLife());
//    }
    
    @Test
    public void testDeserial() {
    	ArrayList<Map> maps = new ArrayList<Map>();
    	JsonBoardCreator jsb = new JsonBoardCreator();
		
    	try {
			Path dirPath = Paths.get("src/main/ressources/maps");
			try (DirectoryStream<Path> dirPaths = Files.newDirectoryStream(dirPath, "*.{json}")) {																				// .jdb only
				for (Path file : dirPaths) {
					Board newMap = (Board)jsb.createBoard(file);
					System.out.println(newMap.getMapCases().toString());
					maps.add(newMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    	assertEquals(5, maps.size());
		
    	for (int i = 0; i < maps.size(); i++) {
			assertEquals(64, maps.get(i).getNumberOfCase());			
		}
    }
    
}
