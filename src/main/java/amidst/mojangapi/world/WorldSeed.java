package amidst.mojangapi.world;

import java.util.Random;

import amidst.documentation.Immutable;

@Immutable
public class WorldSeed {
	public static enum WorldSeedType {
		// @formatter:off
		TEXT      ("Text Seed"),
		NUMERIC("Numeric Seed"),
		FILE      ("File Seed"),
		RANDOM  ("Random Seed");
		// @formatter:on

		private final String labelPrefix;

		private WorldSeedType(String labelPrefix) {
			this.labelPrefix = labelPrefix;
		}

		private String getLabel(long seed, String text) {
			if (this == TEXT) {
				return labelPrefix + ": '" + text + "' (" + seed + ")";
			} else {
				return labelPrefix + " (" + seed + ")";
			}
		}
	}

	public static WorldSeed random() {
		return new WorldSeed(new Random().nextLong(), null,
				WorldSeedType.RANDOM);
	}

	public static WorldSeed fromUserInput(String input) {
		if (input.isEmpty()) {
			return random();
		}
		try {
			long seed = Long.parseLong(input);
			return new WorldSeed(seed, null, WorldSeedType.NUMERIC);
		} catch (NumberFormatException err) {
			int seed = input.hashCode();
			return new WorldSeed(seed, input, WorldSeedType.TEXT);
		}
	}

	public static WorldSeed fromFile(long seed) {
		return new WorldSeed(seed, null, WorldSeedType.FILE);
	}

	private final long seed;
	private final String text;
	private final WorldSeedType type;
	private final String label;

	private WorldSeed(long seed, String text, WorldSeedType type) {
		this.seed = seed;
		this.text = text;
		this.type = type;
		this.label = type.getLabel(seed, text);
	}

	public long getLong() {
		return seed;
	}

	public String getText() {
		return text;
	}

	public WorldSeedType getType() {
		return type;
	}

	public String getLabel() {
		return label;
	}
}
