package Java_Campus_Project.Java_Warriors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

//import java.nio.file.DirectoryStream;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;

import org.junit.Test;

//import warriors.engine.board.Board;
//import warriors.engine.board.JsonBoardCreator;
import warriors.engine.heroes.Warrior;

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
    
    @Test
    public void shouldSetWarriorLifeTo10() {
    	Warrior warrior = new Warrior("Bob", 5, 5);
    	
    	warrior.setLife(10);
    	
    	assertEquals(10, warrior.getLife());
    }
    
//    @Test
//    public void testDeserial() {
//    	JsonBoardCreator jsb = new JsonBoardCreator();
//		try {
//			Path dirPath = Paths.get("src/ressources/maps");
//			try (DirectoryStream<Path> dirPaths = Files.newDirectoryStream(dirPath, "*.{json}")) {																				// .jdb only
//				for (Path file : dirPaths) {
//					Board newMap = (Board)jsb.createBoard(file);
//					System.out.println("");
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//    }
}
