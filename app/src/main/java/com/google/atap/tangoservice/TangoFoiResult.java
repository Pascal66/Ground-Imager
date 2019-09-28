package com.google.atap.tangoservice;

public class TangoFoiResult {
	public String id;
	public int status;

	public TangoFoiResult(int status, String id) {
		this.status = status;
		this.id = id;
	}
}