package com.irgndsondepp.clone.level.tile;

import com.irgndsondepp.clone.graphics.Screen;
import com.irgndsondepp.clone.graphics.Sprite;
import com.irgndsondepp.clone.level.tile.spawn_level.SpawnFlowerTile;
import com.irgndsondepp.clone.level.tile.spawn_level.SpawnGrassTile;
import com.irgndsondepp.clone.level.tile.spawn_level.SpawnRoadTile;
import com.irgndsondepp.clone.level.tile.spawn_level.SpawnRockTile;
import com.irgndsondepp.clone.level.tile.spawn_level.SpawnTreeTile;

public class Tile {
	public int x, y;
	public final int size;
	public Sprite sprite;

	public static Tile grass = new GrassTile(Sprite.grass);
	public static Tile rock = new RockTile(Sprite.rock);
	public static Tile flower = new FlowerTile(Sprite.flower);
	public static Tile tree = new TreeTile(Sprite.tree);

	// Spawn_grass Tiles
	public static Tile spawn_grass = new SpawnGrassTile(Sprite.spawn_grass);
	public static Tile spawn_grass_road_t = new SpawnGrassTile(
			Sprite.spawn_grass_road_t);
	public static Tile spawn_grass_road_b = new SpawnGrassTile(
			Sprite.spawn_grass_road_b);
	public static Tile spawn_grass_road_l = new SpawnGrassTile(
			Sprite.spawn_grass_road_l);
	public static Tile spawn_grass_road_r = new SpawnGrassTile(
			Sprite.spawn_grass_road_r);
	public static Tile spawn_grass_road_tl = new SpawnGrassTile(
			Sprite.spawn_grass_road_tl);
	public static Tile spawn_grass_road_tr = new SpawnGrassTile(
			Sprite.spawn_grass_road_tr);
	public static Tile spawn_grass_road_tlr = new SpawnGrassTile(
			Sprite.spawn_grass_road_tlr);
	public static Tile spawn_grass_road_tbl = new SpawnGrassTile(
			Sprite.spawn_grass_road_tbl);
	public static Tile spawn_grass_road_tbr = new SpawnGrassTile(
			Sprite.spawn_grass_road_tbr);
	public static Tile spawn_grass_road_bl = new SpawnGrassTile(
			Sprite.spawn_grass_road_bl);
	public static Tile spawn_grass_road_br = new SpawnGrassTile(
			Sprite.spawn_grass_road_br);
	public static Tile spawn_grass_road_tb = new SpawnGrassTile(
			Sprite.spawn_grass_road_tb);
	public static Tile spawn_grass_road_blr = new SpawnGrassTile(
			Sprite.spawn_grass_road_blr);
	public static Tile spawn_grass_road_lr = new SpawnGrassTile(
			Sprite.spawn_grass_road_lr);
	public static Tile spawn_grass_road_all = new SpawnGrassTile(
			Sprite.spawn_grass_road_all);

	// Spawn_rock Tiles
	public static Tile spawn_rock = new SpawnRockTile(Sprite.spawn_rock);
	public static Tile spawn_rock_road_t = new SpawnRockTile(
			Sprite.spawn_rock_road_t);
	public static Tile spawn_rock_road_b = new SpawnRockTile(
			Sprite.spawn_rock_road_b);
	public static Tile spawn_rock_road_l = new SpawnRockTile(
			Sprite.spawn_rock_road_l);
	public static Tile spawn_rock_road_r = new SpawnRockTile(
			Sprite.spawn_rock_road_r);
	public static Tile spawn_rock_road_tl = new SpawnRockTile(
			Sprite.spawn_rock_road_tl);
	public static Tile spawn_rock_road_tr = new SpawnRockTile(
			Sprite.spawn_rock_road_tr);
	public static Tile spawn_rock_road_tlr = new SpawnRockTile(
			Sprite.spawn_rock_road_tlr);
	public static Tile spawn_rock_road_tbl = new SpawnRockTile(
			Sprite.spawn_rock_road_tbl);
	public static Tile spawn_rock_road_tbr = new SpawnRockTile(
			Sprite.spawn_rock_road_tbr);
	public static Tile spawn_rock_road_bl = new SpawnRockTile(
			Sprite.spawn_rock_road_bl);
	public static Tile spawn_rock_road_br = new SpawnRockTile(
			Sprite.spawn_rock_road_br);
	public static Tile spawn_rock_road_tb = new SpawnRockTile(
			Sprite.spawn_rock_road_tb);
	public static Tile spawn_rock_road_blr = new SpawnRockTile(
			Sprite.spawn_rock_road_blr);
	public static Tile spawn_rock_road_lr = new SpawnRockTile(
			Sprite.spawn_rock_road_lr);
	public static Tile spawn_rock_road_all = new SpawnRockTile(
			Sprite.spawn_rock_road_all);

	// Spawn_flower Tiles
	public static Tile spawn_flower = new SpawnFlowerTile(Sprite.spawn_flower);

	// Spawn_tree Tiles
	public static Tile spawn_tree = new SpawnTreeTile(Sprite.spawn_tree);

	public static Tile spawn_road = new SpawnRoadTile(Sprite.spawn_road);

	public final static int col_spawn_grass = 0xff00ff00;
	public final static int col_spawn_rock = 0xff000000;
	public final static int col_spawn_flower = 0xffffff00;
	public final static int col_spawn_tree = 0xffff0000;
	public final static int col_spawn_road = 0xff808080;
	public final static int col_spawn_player_start = 0xffff00ff;

	public static Tile voidTile = new VoidTile(Sprite.voidSprite);

	public Tile(Sprite sprite) {
		this.sprite = sprite;
		this.size = sprite.SIZE;
	}

	public void render(int x, int y, Screen screen) {

	}

	public boolean solid() {
		return false;
	}

	public boolean breakable() {
		return false;
	}
}
