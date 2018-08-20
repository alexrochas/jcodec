package br.com.alex.cielo.qrcode.codec;

import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class HCodec implements Codec {

	private List<Codec> codecs;

	public HCodec(List<Codec> codecs) {
		this.codecs = codecs;
	}

	@Override
	public Pair<Map<String, String>, String> decode(String body) {
		Map fields = new HashMap<>();

		for (Codec stringCodec : codecs) {
			Pair<Map<String, String>, String> decoded = stringCodec.decode(body);
			body = decoded.getRight();
			fields.putAll(decoded.getLeft());
		}

		return Pair.of(fields, body);
	}

}
