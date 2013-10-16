package TestFile;
import java.io.File;
import java.io.FileNotFoundException;

import ass2.spec.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class TestCAse {

	@Test
	public void test1() throws Exception {
		Terrain terrain = LevelIO.load(new File("input8"));
		//Game game = new Game(terrain);
		Road road = terrain.roads().get(0);
		road.perfectBezier(0.1);
		/*road.perfectBezier(0.3);
		road.perfectBezier(0.5);
		road.perfectBezier(0.7);
		road.perfectBezier(0.9);
		*/
		road.point(0.1);
	}
	@Test
	public void test2() throws Exception {
		Terrain terrain = LevelIO.load(new File("input8"));
		//Game game = new Game(terrain);
		Road road = terrain.roads().get(0);
		road.perfectBezier(0.7);
		/*road.perfectBezier(0.3);
		road.perfectBezier(0.5);
		road.perfectBezier(0.7);
		road.perfectBezier(0.9);
		*/
		road.point(0.7);
	}
	
	@Test
	public void test3() throws Exception {
		Terrain terrain = LevelIO.load(new File("input6"));
		//Game game = new Game(terrain);
		Road road = terrain.roads().get(0);
		System.out.println(road.bernstein(3, 3, 0.7));
		System.out.println(road.b(3, 0.7));
	}
	
}