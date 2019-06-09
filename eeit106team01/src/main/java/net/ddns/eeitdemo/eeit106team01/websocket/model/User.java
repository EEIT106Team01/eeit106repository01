package net.ddns.eeitdemo.eeit106team01.websocket.model;

import java.security.Principal;

public final class User implements Principal {

	private final String name;

	public User(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}