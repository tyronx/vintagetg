package at.tyron.vintagecraft;

public class ModInfo {
	public static final String ModID = "vintage-terrain-generator";
	public static final String ModName = "Vintage TG";

	public static final int VersionMajor = 1;
	public static final int VersionMinor = 0;
	public static final int VersionRevision = 0;

	public static final String ModVersion = VersionMajor+"."+VersionMinor+"."+VersionRevision;

	public static final String ModChannel = "VintageCraft";
	public static final String SERVER_PROXY_CLASS = "at.tyron.vintagecraft.CommonProxy";
	public static final String CLIENT_PROXY_CLASS = "at.tyron.vintagecraft.client.ClientProxy";

	public static final String AssetPath = "/assets/" + ModID + "/";
	public static final String AssetPathGui = "textures/gui/";
}
