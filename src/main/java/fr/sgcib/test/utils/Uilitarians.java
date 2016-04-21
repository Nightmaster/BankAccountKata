package fr.sgcib.test.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Uilitarians {
	private Uilitarians() {
		throw new AssertionError("Utils class have not to be instantiated!");
	}

	public static <T extends Set<E>, E> Set<E> checkSetElements(T collection) {
		Set<E> setChecked = new HashSet<>();
		collection.forEach(element -> {
			if (null != element)
				setChecked.add(element);
		});
		return setChecked;
	}

	public static <T extends List<E>, E> List<E> checkListElements(T collection) {
		List<E> listChecked = new ArrayList<>();
		collection.forEach(element -> {
			if (null != element)
				listChecked.add(element);
		});
		return listChecked;
	}
}
