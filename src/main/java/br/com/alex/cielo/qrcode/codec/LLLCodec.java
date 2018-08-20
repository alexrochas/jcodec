package br.com.alex.cielo.qrcode.codec;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public final class LLLCodec implements Codec, SafeSubstring {

	private final Integer lengthSize = 3;
	private String label;
	private String description;
	private Integer padding;
	private Codec[] codecs;
	private Logger logger = Logger.getGlobal();

	private LLLCodec() {}

	public LLLCodec(String label, String description, Codec... codecs) {
		this.label = label;
		this.description = description;
		this.codecs = codecs;
		this.padding = 0;
	}

	public LLLCodec(String label, String description, Integer padding, Codec... codecs) {
		this.label = label;
		this.description = description;
		this.padding = padding;
		this.codecs = codecs;
	}

	@Override
	public Pair<Map<String, String>, String> decode(String body) {
		logger.info("Decoding field " + description);
		Map<String, String> fields = new HashMap<>();

		Integer size = Integer.valueOf(safeSubstring(() -> body.substring(padding, padding + lengthSize)));

		String field = safeSubstring(() -> body.substring(padding + lengthSize, size + padding + lengthSize));

		if (codecs.length > 0) {
			HCodec HCodec = new HCodec(Arrays.asList(codecs));
			Map<String, String> decoded = HCodec.decode(field).getLeft();
			fields.putAll(decoded);
		} else {
			fields.put(label, field);
		}

		String remaining = safeSubstring(() -> body.substring(size + padding + lengthSize));

		return Pair.of(fields, remaining);
	}
}
