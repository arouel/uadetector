package net.sf.uadetector;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This enum represents the more commonly used operating system families. It will never be complete, but can assist in
 * identifying the version of an operating system.
 * 
 * @author André Rouél
 */
public enum OperatingSystemFamily {

	/**
	 * AIX (Advanced Interactive eXecutive) is a Unix operating system from IBM.
	 */
	AIX("AIX", Pattern.compile("AIX")),

	/**
	 * AROS is a free operating system aiming at being compatible with AmigaOS at the API level.
	 */
	AROS("AROS", Pattern.compile("AROS")),

	/**
	 * AmigaOS is the native operating system for the Commodore Amiga, consisting of the components of Workbench,
	 * AmigaDOS with the command line interpreter CLI (later renamed to shell) and for many Amiga models in the ROM
	 * included kernel <i>kickstart</i>.
	 */
	AMIGA_OS("Amiga OS", Pattern.compile("Amiga OS")),

	/**
	 * Android is both an operating system and a software platform for mobile devices like smart phones, mobile phones,
	 * netbooks and tablets, which is developed by the Open Handset Alliance.
	 */
	ANDROID("Android", Pattern.compile("Android")),

	/**
	 * The Berkeley Software Distribution (BSD) is a version of the Unix operating system, which was created at the
	 * University of California at Berkeley in 1977.
	 */
	BSD("BSD", Pattern.compile("BSD")),

	/**
	 * Bada is a service-oriented operating system that is developed by Samsung Electronics and is designed for use in
	 * smartphones.
	 */
	BADA("Bada", Pattern.compile("Bada")),

	/**
	 * Be Operating System (BeOS) was an operating system of the company <i>Be Incorporated</i> and was called in later
	 * versions Be. Due to its multimedia capabilities it is also commonly called "Media OS".
	 */
	BEOS("BeOS", Pattern.compile("BeOS")),

	/**
	 * Danger OS is a smartphone operating system. It is used on Sidekick devices, which are sold in Germany by
	 * T-Mobile.
	 */
	DANGEROS("DangerOS", Pattern.compile("DangerOS")),

	/**
	 * HP-UX (Hewlett Packard UniX) is a commercial Unix operating system from Hewlett-Packard and is based on UNIX
	 * System V.
	 */
	HPUX("HP-UX", Pattern.compile("HP-UX")),

	/**
	 * Haiku (formerly OpenBeOS) is an open-source project with the aim, to reprogram and expand that in 2001 abandoned
	 * operating system BeOS.
	 */
	HAIKU("Haiku OS", Pattern.compile("Haiku OS")),

	/**
	 * IRIX is a commercial Unix operating system of the company Silicon Graphics (SGI).
	 */
	IRIX("IRIX", Pattern.compile("IRIX")),

	/**
	 * Inferno is a distributed computer operating system that comes from Bell Laboratories.
	 */
	INFERNO_OS("Inferno OS", Pattern.compile("Inferno OS")),

	/**
	 * The Java Virtual Machine (abbreviated Java VM or JVM) is the part of the Java Runtime Environment (JRE) for Java
	 * programs, which is responsible for the execution of Java bytecode.<br>
	 * <br>
	 * This value is not an operating system family.
	 */
	JVM("JVM", Pattern.compile("JVM")),

	/**
	 * Linux or GNU/Linux are usually called free, unix-like multi-user operating systems running based on the Linux
	 * kernel and other GNU software.
	 */
	LINUX("Linux", Pattern.compile("Linux")),

	/**
	 * Mac OS is the name of the classic operating system (1984-2001) by Apple for Macintosh computers.
	 */
	MAC_OS("Mac OS", Pattern.compile("Mac OS")),

	/**
	 * Minix is a free unixoides operating system that was developed by Andrew S. Tanenbaum at the Free University of
	 * Amsterdam as a teaching tool.
	 */
	MINIX("MINIX", Pattern.compile("MINIX")),

	/**
	 * OS X, formerly Mac OS X, is a Unix-based operating systems developed by Apple. It is a proprietary distribution
	 * of the free Darwin operating system from Apple.
	 */
	OS_X("Mac OS X", Pattern.compile("Mac OS X")),

	/**
	 * MorphOS is an Amiga-compatible computer operating system. It is a mixed proprietary and open source OS produced
	 * for the Pegasos PowerPC processor based computer.
	 */
	MORPHOS("MorphOS", Pattern.compile("MorphOS")),

	/**
	 * This value indicates the operating systems from Nintendo, which they developed for their devices.<br>
	 * <br>
	 * This value is not an operating system family.
	 */
	NINTENDO("Nintendo", Pattern.compile("Nintendo")),

	/**
	 * OS/2 is a multitasking operating system for PCs. It was originally developed by IBM and Microsoft together with
	 * the aim to replace DOS.
	 */
	OS_2("OS/2", Pattern.compile("OS/2")),

	/**
	 * Palm OS was the operating system for organizer of the Palm series (known as PDAs) and smartphones.
	 */
	PALM_OS("Palm OS", Pattern.compile("Palm OS")),

	/**
	 * QNX is a POSIX-compatible proprietary Unix-like real-time operating system that focused primarily at the embedded
	 * market.
	 */
	QNX("QNX", Pattern.compile("QNX")),

	/**
	 * The BlackBerry OS (up to the fifth edition known as the <i>BlackBerry Device Software</i>, also known as
	 * <i>Research In Motion OS</i>) is a proprietary, free usable (freeware) multi-tasking operating system for
	 * smartphones.
	 */
	BLACKBERRY_OS("RIM OS", Pattern.compile("RIM OS")),

	/**
	 * Solaris is the name of an operating system distribution based on SunOS and is a Unix operating system. Since the
	 * takeover of Sun Microsystems in 2010 Solaris is part of Oracle.
	 */
	SOLARIS("Solaris", Pattern.compile("Solaris")),

	/**
	 * Syllable is a slim and fast desktop Unix-like operating system for x86 processors.
	 */
	SYLLABLE("Syllable", Pattern.compile("Syllable")),

	/**
	 * The Symbian platform, simply called Symbian, is an operating system for smartphones and PDAs. The Symbian
	 * platform is the successor to Symbian OS
	 */
	SYMBIAN("Symbian OS", Pattern.compile("Symbian OS")),

	/**
	 * Tizen is a free operating system based on Linux respectively Debian and was launched by the Linux Foundation and
	 * LiMo Foundation.
	 */
	TIZEN("Tizen", Pattern.compile("Tizen")),

	/**
	 * Microsoft Windows is a trademark for operating systems of the Microsoft Corporation. Microsoft Windows was
	 * originally a graphical extension of the operating system MS-DOS.
	 */
	WINDOWS("Windows", Pattern.compile("Windows")),

	/**
	 * XrossMediaBar (XMB) is the name of the graphical user interface, which are used on PlayStation 3, PlayStation
	 * Portable, Sony Blu-Ray players and Sony Bravia TVs. Also some special versions of the PlayStation 2, PSX, already
	 * using the XMB.
	 */
	XROSSMEDIABAR("XrossMediaBar (XMB)", Pattern.compile("XrossMediaBar (XMB)")),

	/**
	 * iOS (until June 2010 iPhone OS) is the standard operating system of Apple products like iPhone, iPod touch, iPad,
	 * and the second generation of Apple TV. iOS is based on Mac OS X.
	 */
	IOS("iOS", Pattern.compile("iOS|iPhone OS")),

	/**
	 * webOS is a smartphone and tablet operating system from Hewlett-Packard (formerly HP Palm). It represents the
	 * follower of Palm OS.
	 */
	WEBOS("webOS", Pattern.compile("webOS")),

	/**
	 * Unknown operating system family<br>
	 * <br>
	 * This value will be returned if the operating system family cannot be determined.
	 */
	UNKNOWN("", Pattern.compile(".*"));

	/**
	 * This method try to find by the given family name a matching enum value. The family name must match against an
	 * operating system entry in UAS data file.
	 * 
	 * @param family
	 *            name of an operating system family
	 * @return the matching enum value or {@code OperatingSystemFamily#UNKNOWN}
	 * @throws IllegalArgumentException
	 *             if the given argument is {@code null}
	 */
	public static OperatingSystemFamily evaluateByFamilyName(final String family) {
		if (family == null) {
			throw new IllegalArgumentException("Argument 'family' must not be null.");
		}

		OperatingSystemFamily result = UNKNOWN;

		// search by name
		for (final OperatingSystemFamily value : values()) {
			if (value.getName().equals(family)) {
				result = value;
				break;
			}
		}

		// search by pattern
		if (result == UNKNOWN) {
			for (final OperatingSystemFamily value : values()) {
				final Matcher m = value.getPattern().matcher(family);
				if (m.matches()) {
					result = value;
					break;
				}
			}
		}

		return result;
	}

	/**
	 * The internal family name in the UAS database.
	 */
	private final String name;

	/**
	 * The regular expression which a family name must be match.
	 */
	private final Pattern pattern;

	private OperatingSystemFamily(final String name, final Pattern pattern) {
		this.name = name;
		this.pattern = pattern;
	}

	/**
	 * Gets the internal family name in the UAS database.
	 * 
	 * @return the internal family name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the regular expression which a family name must be match with.
	 * 
	 * @return regular expression
	 */
	public Pattern getPattern() {
		return pattern;
	}

}
