package GUI.Editor;

public abstract class BaseEditorCommand {
	private boolean allowSaveToSessionFile = true;
	
	public boolean allowSaveToSessionFile() {
		return this.allowSaveToSessionFile;
	}
	
	protected void prohibitSaveToSessionFile() {
		this.allowSaveToSessionFile = false;
	}
	
	public abstract boolean execute();
	
}
