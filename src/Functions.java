import processing.core.PApplet;
import processing.core.PImage;

import java.util.*;

/**
 * This class contains many functions written in a procedural style.
 * You will reduce the size of this class over the next several weeks
 * by refactoring this codebase to follow an OOP style.
 */
public final class Functions {
    public static final Random rand = new Random();

    public static final int COLOR_MASK = 0xffffff;
    public static final int KEYED_IMAGE_MIN = 5;
    private static final int KEYED_RED_IDX = 2;
    private static final int KEYED_GREEN_IDX = 3;
    private static final int KEYED_BLUE_IDX = 4;

    public static final List<String> PATH_KEYS = new ArrayList<>(Arrays.asList("bridge", "dirt", "dirt_horiz", "dirt_vert_left", "dirt_vert_right", "dirt_bot_left_corner", "dirt_bot_right_up", "dirt_vert_left_bot"));

    public static final double SAPLING_ACTION_ANIMATION_PERIOD = 1.000; // have to be in sync since grows and gains health at same time
    public static final int SAPLING_HEALTH_LIMIT = 5;

    public static final int PROPERTY_KEY = 0;
    public static final int PROPERTY_ID = 1;
    public static final int PROPERTY_COL = 2;
    public static final int PROPERTY_ROW = 3;
    public static final int ENTITY_NUM_PROPERTIES = 4;

    public static final String STUMP_KEY = "stump";
    public static final int STUMP_NUM_PROPERTIES = 0;

	public static final String HOLE_KEY = "hole";

	public static final int HOLE_NUM_PROPERTIES = 0;

    public static final String SAPLING_KEY = "sapling";
    public static final int SAPLING_HEALTH = 0;
    public static final int SAPLING_NUM_PROPERTIES = 1;

    public static final String OBSTACLE_KEY = "obstacle";
    public static final int OBSTACLE_ANIMATION_PERIOD = 0;
    public static final int OBSTACLE_NUM_PROPERTIES = 1;

    public static final String DUDE_KEY = "dude";
    public static final int DUDE_ACTION_PERIOD = 0;
    public static final int DUDE_ANIMATION_PERIOD = 1;
    public static final int DUDE_LIMIT = 2;
    public static final int DUDE_NUM_PROPERTIES = 3;

	public static final String ANGRY_DUDE_KEY = "angry_dude";
	public static final int ANGRY_DUDE_ACTION_PERIOD = 0;
	public static final int ANGRY_DUDE_ANIMATION_PERIOD = 1;
	public static final int ANGRY_DUDE_LIMIT = 2;
	public static final int ANGRY_DUDE_NUM_PROPERTIES = 3;

    public static final String HOUSE_KEY = "house";
    public static final int HOUSE_NUM_PROPERTIES = 0;

    public static final String FAIRY_KEY = "fairy";
    public static final int FAIRY_ANIMATION_PERIOD = 0;
    public static final int FAIRY_ACTION_PERIOD = 1;
    public static final int FAIRY_NUM_PROPERTIES = 2;

    public static final String TREE_KEY = "tree";
    public static final int TREE_ANIMATION_PERIOD = 0;
    public static final int TREE_ACTION_PERIOD = 1;
    public static final int TREE_HEALTH = 2;
    public static final int TREE_NUM_PROPERTIES = 3;

    public static final double TREE_ANIMATION_MAX = 0.600;
    public static final double TREE_ANIMATION_MIN = 0.050;
    public static final double TREE_ACTION_MAX = 1.400;
    public static final double TREE_ACTION_MIN = 1.000;
    public static final int TREE_HEALTH_MAX = 3;
    public static final int TREE_HEALTH_MIN = 1;

	public static final String BEAVER_KEY = "beaver";
	public static final int BEAVER_ANIMATION_PERIOD = 0;
	public static final int BEAVER_ACTION_PERIOD = 1;
	public static final int BEAVER_NUM_PROPERTIES = 2;


    public static int getIntFromRange(int max, int min) {
        Random rand = new Random();
        return min + rand.nextInt(max-min);
    }

    public static double getNumFromRange(double max, double min) {
        Random rand = new Random();
        return min + rand.nextDouble() * (max - min);
    }

    private static void parseSapling(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == SAPLING_NUM_PROPERTIES) {
            int health = Integer.parseInt(properties[SAPLING_HEALTH]);
            Sapling sapling = createSapling(id, pt, imageStore.getImageList(SAPLING_KEY), health);
            world.tryAddEntity(sapling);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", SAPLING_KEY, SAPLING_NUM_PROPERTIES));
        }
    }

    private static void parseDude(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == DUDE_NUM_PROPERTIES) {
            DudeNotFull dude_not_full = createDudeNotFull(id, pt, Double.parseDouble(properties[DUDE_ACTION_PERIOD]), Double.parseDouble(properties[DUDE_ANIMATION_PERIOD]), Integer.parseInt(properties[DUDE_LIMIT]), imageStore.getImageList(DUDE_KEY));
            world.tryAddEntity(dude_not_full);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", DUDE_KEY, DUDE_NUM_PROPERTIES));
        }
    }

	private static void parseAngryDude(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
		if (properties.length == ANGRY_DUDE_NUM_PROPERTIES) {
			AngryDude angry_dude = createAngryDude(id, pt, Double.parseDouble(properties[ANGRY_DUDE_ACTION_PERIOD]), Double.parseDouble(properties[ANGRY_DUDE_ANIMATION_PERIOD]), Integer.parseInt(properties[ANGRY_DUDE_LIMIT]), imageStore.getImageList(ANGRY_DUDE_KEY));
			world.tryAddEntity(angry_dude);
		}else{
			throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", DUDE_KEY, DUDE_NUM_PROPERTIES));
		}
	}


    private static void parseFairy(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == FAIRY_NUM_PROPERTIES) {
            Fairy fairy = createFairy(id, pt, Double.parseDouble(properties[FAIRY_ACTION_PERIOD]), Double.parseDouble(properties[FAIRY_ANIMATION_PERIOD]), imageStore.getImageList( FAIRY_KEY));
            world.tryAddEntity(fairy);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", FAIRY_KEY, FAIRY_NUM_PROPERTIES));
        }
    }

	private static void parseBeaver(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
		if (properties.length == Functions.BEAVER_NUM_PROPERTIES) {
			Beaver beaver = createBeaver(id, pt, Double.parseDouble(properties[Functions.BEAVER_ACTION_PERIOD]), Double.parseDouble(properties[Functions.BEAVER_ANIMATION_PERIOD]), imageStore.getImageList(Functions.BEAVER_KEY));
			world.tryAddEntity(beaver);
		}else{
			throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Functions.BEAVER_KEY, Functions.BEAVER_NUM_PROPERTIES));
		}
	}

    private static void parseTree(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == TREE_NUM_PROPERTIES) {
            Tree tree = createTree(id, pt, Double.parseDouble(properties[TREE_ACTION_PERIOD]), Double.parseDouble(properties[TREE_ANIMATION_PERIOD]), Integer.parseInt(properties[TREE_HEALTH]), imageStore.getImageList(TREE_KEY));
            world.tryAddEntity(tree);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", TREE_KEY, TREE_NUM_PROPERTIES));
        }
    }

    private static void parseObstacle(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == OBSTACLE_NUM_PROPERTIES) {
            Obstacle obstacle = createObstacle(id, pt, Double.parseDouble(properties[OBSTACLE_ANIMATION_PERIOD]), imageStore.getImageList(OBSTACLE_KEY));
            world.tryAddEntity(obstacle);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", OBSTACLE_KEY, OBSTACLE_NUM_PROPERTIES));
        }
    }

	private static void parseHole(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
		if (properties.length == Functions.HOLE_NUM_PROPERTIES) {
			Hole hole = createHole(id, pt, imageStore.getImageList(Functions.HOLE_KEY));
			world.tryAddEntity(hole);
		}else{
			throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", Functions.HOLE_KEY, Functions.HOLE_NUM_PROPERTIES));
		}
	}

    private static void parseHouse(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == HOUSE_NUM_PROPERTIES) {
            House house = createHouse(id, pt, imageStore.getImageList(HOUSE_KEY));
            world.tryAddEntity(house);
        }else{
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", HOUSE_KEY, HOUSE_NUM_PROPERTIES));
        }
    }
    private static void parseStump(WorldModel world, String[] properties, Point pt, String id, ImageStore imageStore) {
        if (properties.length == STUMP_NUM_PROPERTIES) {
            Stump stump = createStump(id, pt, imageStore.getImageList(STUMP_KEY));
            world.tryAddEntity(stump);
        } else {
            throw new IllegalArgumentException(String.format("%s requires %d properties when parsing", STUMP_KEY, STUMP_NUM_PROPERTIES));
        }
    }


    public static Optional<Entity> nearestEntity(List<Entity> entities, Point pos) {
        if (entities.isEmpty()) {
            return Optional.empty();
        } else {
            Entity nearest = entities.get(0);
            int nearestDistance = distanceSquared(nearest.getPosition(), pos);

            for (Entity other : entities) {
                int otherDistance = distanceSquared(other.getPosition(), pos);

                if (otherDistance < nearestDistance) {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }
    private static int distanceSquared(Point p1, Point p2) {
        int deltaX = p1.x - p2.x;
        int deltaY = p1.y - p2.y;

        return deltaX * deltaX + deltaY * deltaY;
    }
    /*
       Assumes that there is no entity currently occupying the
       intended destination cell.
    */

    public static House createHouse(String id, Point position, List<PImage> images) {
        return new House(id, position, images);
    }

    public static Obstacle createObstacle(String id, Point position, double animationPeriod, List<PImage> images) {
        return new Obstacle(id, position, animationPeriod, images);
    }

    public static Tree createTree(String id, Point position, double actionPeriod, double animationPeriod, int health, List<PImage> images) {
        return new Tree(id, position, images, actionPeriod, animationPeriod, health);
    }

    public static Stump createStump(String id, Point position, List<PImage> images) {
        return new Stump(id, position, images);
    }

	public static Hole createHole(String id, Point position, List<PImage> images) {
		return new Hole(id, position, images);
	}

    // health starts at 0 and builds up until ready to convert to Tree
    public static Sapling createSapling(String id, Point position, List<PImage> images, int health) {
        return new Sapling(id, position, images, SAPLING_ACTION_ANIMATION_PERIOD, SAPLING_ACTION_ANIMATION_PERIOD, 0, SAPLING_HEALTH_LIMIT);
    } //refactored right?


    public static Fairy createFairy(String id, Point position, double actionPeriod, double animationPeriod, List<PImage> images) {
        return new Fairy(id, position, actionPeriod, animationPeriod, images);
    }

	public static Beaver createBeaver(String id, Point position, double actionPeriod, double animationPeriod, List<PImage> images) {
		return new Beaver(id, position, actionPeriod, animationPeriod, images);
	}

    // need resource count, though it always starts at 0
    public static DudeNotFull createDudeNotFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images) {
        return new DudeNotFull(id, position, actionPeriod, animationPeriod, resourceLimit, images);
    }

    // don't technically need resource count ... full
    public static DudeFull createDudeFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceCount, int resourceLimit, List<PImage> images) {
        return new DudeFull(id, position, actionPeriod, animationPeriod, resourceLimit, resourceCount, images);
    }

	public static AngryDude createAngryDude(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images) {
		return new AngryDude(id, position, actionPeriod, animationPeriod, resourceLimit, images);
	}

    public static void parseEntity(WorldModel world, String line, ImageStore imageStore) {
        String[] properties = line.split(" ", Functions.ENTITY_NUM_PROPERTIES + 1);
        if (properties.length >= ENTITY_NUM_PROPERTIES) {
            String key = properties[PROPERTY_KEY];
            String id = properties[Functions.PROPERTY_ID];
            Point pt = new Point(Integer.parseInt(properties[Functions.PROPERTY_COL]), Integer.parseInt(properties[Functions.PROPERTY_ROW]));

            properties = properties.length == Functions.ENTITY_NUM_PROPERTIES ?
                    new String[0] : properties[Functions.ENTITY_NUM_PROPERTIES].split(" ");

            switch (key) {
                case Functions.OBSTACLE_KEY -> Functions.parseObstacle(world, properties, pt, id, imageStore);
                case Functions.DUDE_KEY -> Functions.parseDude(world, properties, pt, id, imageStore);
				case Functions.ANGRY_DUDE_KEY -> Functions.parseAngryDude(world, properties, pt, id, imageStore);
                case Functions.FAIRY_KEY -> Functions.parseFairy(world, properties, pt, id, imageStore);
                case Functions.HOUSE_KEY -> Functions.parseHouse(world, properties, pt, id, imageStore);
                case Functions.TREE_KEY -> Functions.parseTree(world, properties, pt, id, imageStore);
                case Functions.SAPLING_KEY -> Functions.parseSapling(world, properties, pt, id, imageStore);
                case Functions.STUMP_KEY -> Functions.parseStump(world, properties, pt, id, imageStore);
				case Functions.HOLE_KEY -> Functions.parseHole(world,properties, pt, id, imageStore);
				case Functions.BEAVER_KEY -> Functions.parseBeaver(world, properties, pt, id, imageStore);
                default -> throw new IllegalArgumentException("Entity key is unknown");
            }
        }else{
            throw new IllegalArgumentException("Entity must be formatted as [key] [id] [x] [y] ...");
        }
    }

    public static int clamp(int value, int low, int high) {
        return Math.min(high, Math.max(value, low));
    }


    private static void processImageLine(Map<String, List<PImage>> images, String line, PApplet screen) {
        String[] attrs = line.split("\\s");
        if (attrs.length >= 2) {
            String key = attrs[0];
            PImage img = screen.loadImage(attrs[1]);
            if (img != null && img.width != -1) {
                List<PImage> imgs = getImages(images, key);
                imgs.add(img);

                if (attrs.length >= KEYED_IMAGE_MIN) {
                    int r = Integer.parseInt(attrs[KEYED_RED_IDX]);
                    int g = Integer.parseInt(attrs[KEYED_GREEN_IDX]);
                    int b = Integer.parseInt(attrs[KEYED_BLUE_IDX]);
                    setAlpha(img, screen.color(r, g, b), 0);
                }
            }
        }
    }

    private static List<PImage> getImages(Map<String, List<PImage>> images, String key) {
        return images.computeIfAbsent(key, k -> new LinkedList<>());
    }

    /*
      Called with color for which alpha should be set and alpha value.
      setAlpha(img, color(255, 255, 255), 0));
    */
    private static void setAlpha(PImage img, int maskColor, int alpha) {
        int alphaValue = alpha << 24;
        int nonAlpha = maskColor & COLOR_MASK;
        img.format = PApplet.ARGB;
        img.loadPixels();
        for (int i = 0; i < img.pixels.length; i++) {
            if ((img.pixels[i] & COLOR_MASK) == nonAlpha) {
                img.pixels[i] = alphaValue | nonAlpha;
            }
        }
        img.updatePixels();
    }

    public static void loadImages(Scanner in, ImageStore imageStore, PApplet screen) {
        int lineNumber = 0;
        while (in.hasNextLine()) {
            try {
                processImageLine(imageStore.getImages(), in.nextLine(), screen);
            } catch (NumberFormatException e) {
                System.out.printf("Image format error on line %d\n", lineNumber);
            }
            lineNumber++;
        }
    }
}
