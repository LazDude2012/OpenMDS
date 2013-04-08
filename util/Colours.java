package OpenMDS.util;

public enum Colours
{
	WHITE,
	ORANGE,
	MAGENTA,
	LTBLUE,
	YELLOW,
	LIME,
	PINK,
	GREY,
	LTGREY,
	CYAN,
	PURPLE,
	BLUE,
	BROWN,
	GREEN,
	RED,
	BLACK;

    static String[] valid = new String[]{"WHITE","ORANGE","MAGENTA","LTBLUE","YELLOW","LIME","PINK","GREY","LTGREY","CYAN","PURPLE","BLUE","BROWN","GREEN","RED","BLACK"};
	public static Colours fromInt(int i)
	{
		return Colours.valueOf(valid[i]);
	}
}