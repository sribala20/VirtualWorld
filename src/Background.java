import processing.core.PImage;

import java.util.List;

/**
 * Represents a background for the 2D world.
 */
public final class Background {
    private final String id;
    private final List<PImage> images;
    private int imageIndex;

    public Background(String id, List<PImage> images) {
        this.id = id;
        this.images = images;
    }

	public List<PImage> getImages() {
		return images;
	}
	public int getImageIndex() {
		return imageIndex;
	}

	public PImage getCurrentImage() {
			return this.getImages().get(this.getImageIndex());
	}
}
