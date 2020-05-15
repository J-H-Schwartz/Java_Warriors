package Java_Campus_Project.Java_Warriors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

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
}
