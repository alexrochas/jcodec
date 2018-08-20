package br.com.alex.cielo.qrcode.codec;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class LLCodec implements Codec, SafeSubstring {

	private final Integer lengthSize = 2;
	private String label;
	private String description;
	private Integer padding;
	private Codec[] leafCodecs;
	private Logger logger = Logger.getGlobal();

	private LLCodec() {}

	public LLCodec(String label, String description, Codec... leafCodecs) {
		this.label = label;
		this.description = description;
		this.leafCodecs = leafCodecs;
		this.padding = 0;
	}

	public LLCodec(String label, String description, Integer padding, Codec... leafCodecs) {
		this.label = label;
		this.description = description;
		this.padding = padding;
		this.leafCodecs = leafCodecs;
	}

	@Override
	public Pair<Map<String, String>, String> decode(String body) {
		logger.info("Decoding field " + description);
		Map<String, String> fields = new HashMap<>();

		Integer fieldSize = Integer.valueOf(safeSubstring(() -> body.substring(padding, padding + lengthSize)));

		String field = safeSubstring(() -> body.substring(padding + lengthSize, fieldSize + padding + lengthSize));

		if (leafCodecs.length > 0) {
			HCodec HCodec = new HCodec(Arrays.asList(leafCodecs));
			Map<String, String> decoded = HCodec.decode(field).getLeft();
			fields.putAll(decoded);
		} else {
			fields.put(label, field);
		}

		String remaining = safeSubstring(() -> body.substring(fieldSize + padding + lengthSize));

		return Pair.of(fields, remaining);
	}
}


