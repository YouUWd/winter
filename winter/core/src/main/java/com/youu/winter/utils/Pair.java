package com.youu.winter.utils;

/**
 * @desc 二元组
 * @author YouN
 *
 * @param <F>
 * @param <S>
 */
public class Pair<F, S> {

	private F f;
	private S s;

	public Pair(F f, S s) {
		this.f = f;
		this.s = s;
	}

	public F getF() {
		return f;
	}

	public S getS() {
		return s;
	}

	@Override
	public String toString() {
		return "Pair [f=" + f + ", s=" + s + "]";
	}

}
