package br.com.alex.cielo.qrcode.codec;

import java.util.function.Supplier;

import static java.lang.String.format;

public interface SafeSubstring {
	default String safeSubstring(Supplier<String> substringAction) {
		try {
			return substringAction.get();
		} catch (StringIndexOutOfBoundsException ex) {
			throw new IllegalArgumentException(format("Trying to decode an invalid string"));
		}
	}
}
