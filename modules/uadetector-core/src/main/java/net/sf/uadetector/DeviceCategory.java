/*******************************************************************************
 * Copyright 2012 André Rouél
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.uadetector;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.Check;

/**
 * This enum represents the type of an device. The assignment to a type is performed within the UAS data.
 * 
 * @author André Rouél
 */
public enum DeviceCategory {

	/**
	 * A game console is an interactive computer that produces a video display signal which can be used with a display
	 * device (a television, monitor, etc.) to display a video game. The term "game console" is used to distinguish a
	 * machine designed for people to buy and use primarily for playing video games on a TV in contrast to arcade
	 * machines, handheld game consoles, or home computers.
	 */
	GAME_CONSOLE("Game console"),

	/**
	 * A device that doesn't match the other types
	 */
	OTHER("Other"),

	/**
	 * A personal computer (PC) is a general-purpose computer, whose size, capabilities, and original sale price makes
	 * it useful for individuals, and which is intended to be operated directly by an end-user with no intervening
	 * computer operator.
	 */
	PERSONAL_COMPUTER("Personal computer"),

	/**
	 * A smart TV, sometimes referred to as connected TV or hybrid TV
	 */
	SMART_TV("Smart TV"),

	/**
	 * A smartphone is a mobile phone built on a mobile operating system, with more advanced computing capability and
	 * connectivity than a feature phone
	 */
	SMARTPHONE("Smartphone"),

	/**
	 * A tablet computer, or simply tablet, is a mobile computer with display, circuitry and battery in a single unit.
	 * Tablets are often equipped with sensors, including cameras, microphone, accelerometer and touchscreen, with
	 * finger or stylus gestures replacing computer mouse and keyboard.
	 */
	TABLET("Tablet"),

	/**
	 * An unknown device type
	 */
	UNKNOWN("");

	/**
	 * Tries to find by the given type name a matching enum value. The type name must match against an device entry in
	 * the <i>UAS data</i>.
	 * 
	 * @param typeName
	 *            name of an device type
	 * @return the matching enum value or {@code DeviceType#UNKNOWN}
	 * @throws net.sf.qualitycheck.exception.IllegalNullArgumentException
	 *             if the given argument is {@code null}
	 */
	public static DeviceCategory evaluate(@Nonnull final String typeName) {
		Check.notNull(typeName, "typeName");

		DeviceCategory result = UNKNOWN;
		for (final DeviceCategory value : values()) {
			if (value.getName().equals(typeName)) {
				result = value;
				break;
			}
		}
		return result;
	}

	/**
	 * Name of the device type
	 */
	@Nonnull
	private final String name;

	private DeviceCategory(@Nonnull final String name) {
		this.name = name;
	}

	/**
	 * Returns the name of the device type.
	 * 
	 * @return name of the type
	 */
	@Nonnull
	public String getName() {
		return name;
	}

}
