package br.com.alex.cielo.qrcode.codec;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

public interface Codec {
	Pair<Map<String, String>, String> decode(String body);
}
